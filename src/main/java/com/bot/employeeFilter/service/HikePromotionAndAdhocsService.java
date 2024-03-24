package com.bot.employeeFilter.service;

import com.bot.employeeFilter.entity.BonusShiftOvertime;
import com.bot.employeeFilter.entity.HikeBonusSalaryAdhoc;
import com.bot.employeeFilter.entity.Leave;
import com.bot.employeeFilter.interfaces.IHikePromotionAndAdhocsService;
import com.bot.employeeFilter.model.AnnualSalaryBreakup;
import com.bot.employeeFilter.model.ApplicationConstant;
import com.bot.employeeFilter.model.CurrentSession;
import com.bot.employeeFilter.model.LeaveTypeBrief;
import com.bot.employeeFilter.repository.HikePromotionAndAdhocsRepository;
import com.bot.employeeFilter.repository.ProcessingPayrollRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

@Service
public class HikePromotionAndAdhocsService implements IHikePromotionAndAdhocsService {
    @Autowired
    HikePromotionAndAdhocsRepository hikePromotionAndAdhocsRepository;
    @Autowired
    CurrentSession currentSession;
    @Autowired
    ProcessingPayrollRepository processingPayrollRepository;
    @Autowired
    ObjectMapper objectMapper;
    public String saveHikePromotionData(List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhocs) throws Exception {
        hikeBonusSalaryAdhocs.forEach(x -> {
            x.setCompanyId(currentSession.getUserDetail().getCompanyId());
            x.setOrganizationId(currentSession.getUserDetail().getOrganizationId());
            x.setStatus(ApplicationConstant.Pending);
            if (x.getPaymentActionType() != null && !x.getPaymentActionType().equals("")) {
                if (x.getPaymentActionType().equalsIgnoreCase("hold salary processing this month")
                 || x.getPaymentActionType().equalsIgnoreCase("hold salary payout this month")) {
                    x.setSalaryOnHold(true);
                } else {
                    x.setSalaryOnHold(false);
                }
            }
        });
        validateHikeBonusSalaryAdhoc(hikeBonusSalaryAdhocs);
        boolean flag = false;

        var overtimeDetail = hikeBonusSalaryAdhocs.stream().filter(x -> x.isOvertime() && !x.isCompOff()).toList();
        if (overtimeDetail.size() > 0)
            validateOvertimeDetial(overtimeDetail);

        var overtimeConvertAsCompOff = hikeBonusSalaryAdhocs.stream().filter(x -> x.isOvertime() && x.isCompOff()).toList();
        if (overtimeConvertAsCompOff.size() > 0)
            flag = convertOvertimeAsCompOff(hikeBonusSalaryAdhocs);
        else
            flag = hikePromotionAndAdhocsRepository.updateHikeBonusAdhocRepository(hikeBonusSalaryAdhocs);
//        boolean flag = true;
        if(!flag) {
            return "fail";
        }

        return "updated";
    }

    private void validateHikeBonusSalaryAdhoc(List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhocs) throws Exception {
        for (HikeBonusSalaryAdhoc hikeBonusSalaryAdhoc : hikeBonusSalaryAdhocs) {
            if (hikeBonusSalaryAdhoc.getForMonth() == 0)
                throw new Exception("Invalid for month.");

            if (hikeBonusSalaryAdhoc.getForYear() == 0)
                throw new Exception("Invalid for year.");

            if (hikeBonusSalaryAdhoc.getEmployeeId() == 0)
                throw new Exception("Invalid employee selected");
        }
    }

    private void validateOvertimeDetial(List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhocs) throws Exception {
        try {
            List<BonusShiftOvertime> bonusShiftOvertimes = processingPayrollRepository.getBonusShiftOTRepository
                    (hikeBonusSalaryAdhocs.get(0).getCompanyId(), hikeBonusSalaryAdhocs.get(0).getForMonth(),
                            hikeBonusSalaryAdhocs.get(0).getForYear());
            int totalTimeinMonth = getDaysInMonth(hikeBonusSalaryAdhocs.get(0).getForMonth(), hikeBonusSalaryAdhocs.get(0).getForYear()) * 24 *60;

            for (HikeBonusSalaryAdhoc hikeBonusSalaryAdhoc : hikeBonusSalaryAdhocs) {
                var bonusShiftOvertimeData = bonusShiftOvertimes.stream().filter(x -> x.getEmployeeId() == hikeBonusSalaryAdhoc.getEmployeeId()).findFirst();
                if (bonusShiftOvertimeData.isEmpty())
                    throw new Exception("Overtime record not found");

                var bonusShiftOvertime = bonusShiftOvertimeData.get();
                var annualSalaryBreakup = objectMapper.readValue(bonusShiftOvertime.getCompleteSalaryDetail(), new TypeReference<List<AnnualSalaryBreakup>>() {
                });
                AnnualSalaryBreakup currentMontBreakup = annualSalaryBreakup.stream().filter(x -> x.getMonthNumber() == hikeBonusSalaryAdhocs.get(0).getForMonth()).findFirst().get();
                double amount = 0;
                double grossAmount = currentMontBreakup.getSalaryBreakupDetails().stream().filter(x -> Objects.equals(x.getComponentId(), "Gross")).findFirst().get().getFinalAmount();
                double basicAmount = currentMontBreakup.getSalaryBreakupDetails().stream().filter(x -> Objects.equals(x.getComponentId(), "BS")).findFirst().get().getFinalAmount();
                if (hikeBonusSalaryAdhoc.getOTCalculatedOn().equalsIgnoreCase("gross")) {
                    if (bonusShiftOvertime.getPayCalculationId() ==1 )
                        amount = (grossAmount / totalTimeinMonth) * bonusShiftOvertime.getTotalMinutes();
                    else
                        amount = (grossAmount/totalTimeinMonth) * bonusShiftOvertime.getTotalMinutes();
                } else {
                    if (bonusShiftOvertime.getPayCalculationId() ==1 )
                        amount = (basicAmount / totalTimeinMonth) * bonusShiftOvertime.getTotalMinutes();
                    else
                        amount = (basicAmount/totalTimeinMonth) * bonusShiftOvertime.getTotalMinutes();
                }

                if (amount != hikeBonusSalaryAdhoc.getAmount())
                    throw new Exception("Calculated amount not matched");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private boolean convertOvertimeAsCompOff(List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhocs) throws Exception {
        try {
            List<BonusShiftOvertime> bonusShiftOvertimes = processingPayrollRepository.getBonusShiftOTRepository
                    (hikeBonusSalaryAdhocs.get(0).getCompanyId(), hikeBonusSalaryAdhocs.get(0).getForMonth(),
                            hikeBonusSalaryAdhocs.get(0).getForYear());

            for (HikeBonusSalaryAdhoc hikeBonusSalaryAdhoc : hikeBonusSalaryAdhocs) {
                var bonusShiftOvertimeData = bonusShiftOvertimes.stream()
                        .filter(x -> x.getEmployeeId() == hikeBonusSalaryAdhoc.getEmployeeId()).findFirst();
                if (bonusShiftOvertimeData.isEmpty())
                    throw new Exception("Overtime record not found");

                var bonusShiftOvertime = bonusShiftOvertimeData.get();
                double compoffDays = (double) bonusShiftOvertime.getTotalMinutes() /(60 * 8);
                Leave leave = processingPayrollRepository.getEmployeeLeaveRequestRepository(hikeBonusSalaryAdhoc.getEmployeeId(),
                        Year.now().getValue());
                if (leave == null || Objects.equals(leave.getLeaveQuotaDetail(), "[]"))
                    throw new Exception("Leave quota not found. Please contact to admin");
                var leaveTypeBrief = objectMapper.readValue(leave.getLeaveQuotaDetail(), new TypeReference<List<LeaveTypeBrief>>() {
                });
                var compOffLeaveData = leaveTypeBrief.stream().filter(x -> Objects.equals(x.getLeavePlanTypeName(), "COMP OFF")).findFirst();
                if (compOffLeaveData.isEmpty())
                    throw new Exception("Comp off leave component not found. Please contact to admin");

                var compOffLeave = compOffLeaveData.get();
                compOffLeave.setAvailableLeaves(compOffLeave.getAvailableLeaves() + compoffDays);
                compOffLeave.setAccruedSoFar(compOffLeave.getAccruedSoFar() + compoffDays);
                compOffLeave.setTotalLeaveQuota(compOffLeave.getTotalLeaveQuota() + compoffDays);
                leave.setLeaveQuotaDetail(objectMapper.writeValueAsString(leaveTypeBrief));
                leave.setTotalLeaveQuota((int) (leave.getTotalLeaveQuota() + compoffDays));
                processingPayrollRepository.updateEmployeeLeaveRequestRepository(leave);
            }
            return true;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public String manageBonusService(BonusShiftOvertime bonusShiftOvertime) throws Exception {
        validateBonusShiftOvertime(bonusShiftOvertime);
        java.util.Date utilDate = new java.util.Date();
        var date = new java.sql.Timestamp(utilDate.getTime());
        bonusShiftOvertime.setCompanyId(currentSession.getUserDetail().getCompanyId());
        bonusShiftOvertime.setOrganizationId(currentSession.getUserDetail().getOrganizationId());
        bonusShiftOvertime.setUpdatedBy(currentSession.getUserDetail().getUserId());
        bonusShiftOvertime.setUpdatedOn(date);
        boolean flag = hikePromotionAndAdhocsRepository.manageBonusShiftOvertimeRepository(bonusShiftOvertime);
        if(!flag) {
            return "fail";
        }

        return "updated";
    }

    private void validateBonusShiftOvertime(BonusShiftOvertime bonusShiftOvertime) throws Exception {
        if (bonusShiftOvertime.getForMonth() == 0)
            throw new Exception("Invalid for month.");

        if (bonusShiftOvertime.getForYear() == 0)
            throw new Exception("Invalid for year.");

        if (bonusShiftOvertime.getEmployeeId() == 0)
            throw new Exception("Invalid employee selected");

        if (bonusShiftOvertime.isBonus() && (bonusShiftOvertime.getComponentId() == null || bonusShiftOvertime.getComponentId().equals("")))
            throw new Exception("Invalid bonus component selected");

        if (Objects.equals(bonusShiftOvertime.getAmount(), BigDecimal.ZERO))
            throw new Exception("Invalid amount enter");
    }

    private int getDaysInMonth(int forMonth, int forYear) {
        YearMonth yearMonth = YearMonth.of(forYear, forMonth);
        return yearMonth.lengthOfMonth();
    }

    public String manageNewJoineeExitsFinalSattlementService(List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhocs) throws Exception {
        validateNewJoineeExitsFinalSattlement(hikeBonusSalaryAdhocs);
        boolean flag = false;

        flag = hikePromotionAndAdhocsRepository.updateHikeBonusAdhocRepository(hikeBonusSalaryAdhocs);
        if(!flag) {
            return "fail";
        }

        return "updated";
    }

    private void validateNewJoineeExitsFinalSattlement(List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhocs) throws Exception {
        for (HikeBonusSalaryAdhoc hikeBonusSalaryAdhoc : hikeBonusSalaryAdhocs) {
            hikeBonusSalaryAdhoc.setCompanyId(currentSession.getUserDetail().getCompanyId());
            hikeBonusSalaryAdhoc.setOrganizationId(currentSession.getUserDetail().getOrganizationId());
            hikeBonusSalaryAdhoc.setStatus(ApplicationConstant.Pending);

            if (hikeBonusSalaryAdhoc.getPaymentActionType() == null || hikeBonusSalaryAdhoc.getPaymentActionType().equals(""))
                throw new Exception("Please select payment action type");

            if (hikeBonusSalaryAdhoc.getPaymentActionType() != null && !hikeBonusSalaryAdhoc.getPaymentActionType().equals("")) {
                if (hikeBonusSalaryAdhoc.getPaymentActionType().equalsIgnoreCase("hold salary processing this month")
                        || hikeBonusSalaryAdhoc.getPaymentActionType().equalsIgnoreCase("hold salary payout this month")) {
                    hikeBonusSalaryAdhoc.setSalaryOnHold(true);
                } else {
                    hikeBonusSalaryAdhoc.setSalaryOnHold(false);
                }
            }
        }
    }

    public String manageBonusSalaryOvertimeService(List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhocs) throws Exception {
        validateBonusSalaryOvertime(hikeBonusSalaryAdhocs);
        boolean flag = false;

        var overtimeDetail = hikeBonusSalaryAdhocs.stream().filter(x -> x.isOvertime() && !x.isCompOff()).toList();
        if (overtimeDetail.size() > 0)
            validateOvertimeDetial(overtimeDetail);

        var overtimeConvertAsCompOff = hikeBonusSalaryAdhocs.stream().filter(x -> x.isOvertime() && x.isCompOff()).toList();
        if (overtimeConvertAsCompOff.size() > 0)
            flag = convertOvertimeAsCompOff(hikeBonusSalaryAdhocs);
        else
            flag = hikePromotionAndAdhocsRepository.updateHikeBonusAdhocRepository(hikeBonusSalaryAdhocs);

        if(!flag) {
            return "fail";
        }

        return "updated";
    }

    private void validateBonusSalaryOvertime(List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhocs) throws Exception {
        for (HikeBonusSalaryAdhoc hikeBonusSalaryAdhoc : hikeBonusSalaryAdhocs) {
            hikeBonusSalaryAdhoc.setCompanyId(currentSession.getUserDetail().getCompanyId());
            hikeBonusSalaryAdhoc.setOrganizationId(currentSession.getUserDetail().getOrganizationId());
            hikeBonusSalaryAdhoc.setStatus(ApplicationConstant.Pending);

            if (hikeBonusSalaryAdhoc.getPaymentActionType() == null || hikeBonusSalaryAdhoc.getPaymentActionType().equals(""))
                throw new Exception("Please select payment action type");

            if (hikeBonusSalaryAdhoc.getPaymentActionType() != null && !hikeBonusSalaryAdhoc.getPaymentActionType().equals("")) {
                if (hikeBonusSalaryAdhoc.getPaymentActionType().equalsIgnoreCase("hold salary processing this month")
                        || hikeBonusSalaryAdhoc.getPaymentActionType().equalsIgnoreCase("hold salary payout this month")) {
                    hikeBonusSalaryAdhoc.setSalaryOnHold(true);
                } else {
                    hikeBonusSalaryAdhoc.setSalaryOnHold(false);
                }
            }

            if (hikeBonusSalaryAdhoc.isBonus()) {
                hikeBonusSalaryAdhoc.setOvertime(false);
            }
        }
    }
}
