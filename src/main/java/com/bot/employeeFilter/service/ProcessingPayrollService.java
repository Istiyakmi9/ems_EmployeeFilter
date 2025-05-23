package com.bot.employeeFilter.service;

import com.bot.employeeFilter.entity.*;
import com.bot.employeeFilter.model.FilterModel;
import com.bot.employeeFilter.interfaces.IProcessingPayrollService;
import com.bot.employeeFilter.model.*;
import com.bot.employeeFilter.repository.HikePromotionAndAdhocsRepository;
import com.bot.employeeFilter.repository.ProcessingPayrollRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ProcessingPayrollService implements IProcessingPayrollService {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ProcessingPayrollRepository processingPayrollRepository;
    @Autowired
    CurrentSession currentSession;
    @Autowired
    HikePromotionAndAdhocsRepository hikePromotionAndAdhocsRepository;
    @Override
    public List<?> getLeaveAndLOPService(int year, int month) throws Exception {
        if (year == 0)
            throw new Exception("Invalid year selected. Please select a valid year");

        if (month == 0)
            throw new Exception("Invalid month selected. Please select a valid month");

        return  processingPayrollRepository.getLeaveAndLOPRepository(year, month, currentSession.getUserDetail().getCompanyId());
    }

    public List<Attendance> getAttendanceByPage(FilterModel filterModel) throws Exception {
        if (filterModel.getSearchString() == null || filterModel.getSearchString().isEmpty()) {
            filterModel.setSearchString("1=1");
        }

        return processingPayrollRepository.getAttendanceByPageRepository(filterModel);
    }

    public List<Employee> getJoineeAndExitingEmployeesService() throws Exception {
        return processingPayrollRepository.getJoineeAndExitingEmployeesRepository(currentSession.getUserDetail().getCompanyId());
    }

    public String leaveApprovalService(Leave requestDetail) throws Exception {
        return updateLeaveDetail(requestDetail, ApplicationConstant.Approved);
    }

    public List<PayrollMonthlyDetail> getPayrollProcessingDetailService(int month, int year) throws Exception {
        if (month == 0)
            throw new Exception("Invalid month selected");

        if (year == 0)
            throw new Exception("Invalid year selected");

        Optional<List<PayrollMonthlyDetail>> payrollMonthlyDetail = processingPayrollRepository.getPayrollProcessingDetailRepository(month, year, currentSession.getUserDetail().getCompanyId());
        payrollMonthlyDetail.orElseThrow(() -> new RuntimeException("No record found"));
        return payrollMonthlyDetail.get();
    }

    @Transactional
    private String updateLeaveDetail(Leave requestDetail, int status) throws Exception {
        if (requestDetail.getLeaveRequestNotificationId() == 0)
            throw new Exception("Invalid request. Please check your detail first.");

        List<Leave> leaveRequested = processingPayrollRepository.getEmployeeLeaveRequestRepository(requestDetail.getLeaveRequestNotificationId());
        var leaveRequestDetail = leaveRequested.get(0);
        if (leaveRequestDetail == null || leaveRequestDetail.getLeaveDetail().isEmpty())
            throw new Exception("Unable to find leave detail. Please contact to admin.");

        List<CompleteLeaveDetail> completeLeaveDetails = objectMapper.convertValue(leaveRequestDetail.getLeaveDetail(), new TypeReference<List<CompleteLeaveDetail>>() {});
        if (completeLeaveDetails.isEmpty())
            throw new Exception("Unable to find applied leave detail. Please contact to admin");

        var pendingCount = completeLeaveDetails.stream().filter(x -> !x.getRecordId().equals(requestDetail.getRecordId())).
                filter(i -> i.getLeaveStatus() == ApplicationConstant.Pending).toList().size();
        var singleLeaveDetail = completeLeaveDetails.stream().filter(x -> x.getRecordId().equals(requestDetail.getRecordId())).findFirst().get();

        long nextId = 0;
        leaveRequestDetail.setRequestStatusId(status);
        singleLeaveDetail.setLeaveStatus(status);
        if (ApplicationConstant.Rejected == status)
        {
            Long totalLeaves = TimeUnit.MILLISECONDS.toDays(requestDetail.getLeaveToDay().getTime() - requestDetail.getLeaveFromDay().getTime()) % 365;
            updateLeaveCountOnRejected(leaveRequestDetail, requestDetail.getLeaveTypeId(), totalLeaves);
        }
        else
        {
            leaveRequestDetail.setAssigneeId(0L);
        }

        singleLeaveDetail.setRespondedBy(1L);
        leaveRequestDetail.setLeaveDetail(objectMapper.writeValueAsString(completeLeaveDetails));
        processingPayrollRepository.updateLeaveDetailRepository(leaveRequestDetail, pendingCount);
        return null;
    }

    private void updateLeaveCountOnRejected(Leave LeaveRequestDetail, int leaveTypeId, Long leaveCount) throws Exception {
        if (leaveTypeId <= 0)
            throw new Exception("Invalid leave type id");

        if (!LeaveRequestDetail.getLeaveQuotaDetail().isEmpty()) {
            List<LeaveTypeBrief> records = objectMapper.convertValue(LeaveRequestDetail.getLeaveQuotaDetail(), new TypeReference<java.util.List<LeaveTypeBrief>>() {});
            if (!records.isEmpty()) {
                var result = records.stream().filter(x -> x.getLeavePlanTypeId() == leaveTypeId).findFirst();
                if(result.isPresent()) {
                    LeaveTypeBrief item = result.get();

                    item.setAvailableLeaves((int) (item.getAvailableLeaves() + leaveCount));
                    if (item.getAvailableLeaves() > item.getTotalLeaveQuota())
                        item.setAvailableLeaves(item.getTotalLeaveQuota());
                }
                else {
                    throw new Exception("Fail to get leave plans");
                }

                LeaveRequestDetail.setLeaveQuotaDetail(objectMapper.writeValueAsString(records));
            }
        }
    }

    public List<BonusShiftOvertime> getBonusShiftOTService(int forMonth, int forYear) throws Exception {
        return processingPayrollRepository.getBonusShiftOTRepository(currentSession.getUserDetail().getCompanyId(),
                                                                        forMonth, forYear);
    }

    public List<Employee> getSalaryHoldEmployees(int forMonth, int forYear) throws Exception {
        return processingPayrollRepository.getSalaryHoldEmployeeService(currentSession.getUserDetail().getCompanyId(),
                forMonth, forYear);
    }

    public List<ReimbursementAdhocDeduction> getReimbursementAdhocDeductionService(int forMonth, int forYear) throws Exception {
        return processingPayrollRepository.getReimbursementAdhocDeductionRepository(currentSession.getUserDetail().getCompanyId(),
                                                                                    forMonth, forYear);
    }

    public boolean holdSalaryDetailService(HikeBonusSalaryAdhoc hikeBonusSalaryAdhoc) throws Exception {
        try {
            if (hikeBonusSalaryAdhoc.getEmployeeId() == 0)
                throw new Exception("Invalid employee selected");

            if (hikeBonusSalaryAdhoc.getForMonth() == 0)
                throw new Exception("Month is invalid");

            if (hikeBonusSalaryAdhoc.getForYear() == 0)
                throw new Exception("Year is invalid");

            FilterModel filterModel = new FilterModel();
            var searchString = "1=1 and IsSalaryOnHold = true and ForMonth=" + hikeBonusSalaryAdhoc.getForMonth() + " and ForYear=" + hikeBonusSalaryAdhoc.getForYear() + " and EmployeeId = " + hikeBonusSalaryAdhoc.getEmployeeId();
            filterModel.setSearchString(searchString);
            var existSalaryOnHoldData = hikePromotionAndAdhocsRepository.filterHikeBonusSalaryAdhocRepository(filterModel);
            if (existSalaryOnHoldData.size() > 0) {
                var existingRecord = existSalaryOnHoldData.get(0);
                hikeBonusSalaryAdhoc.setSalaryAdhocId(existingRecord.getSalaryAdhocId());
            }
            hikeBonusSalaryAdhoc.setFinancialYear(currentSession.getUserDetail().getFinancialYear());
            hikeBonusSalaryAdhoc.setOrganizationId(currentSession.getUserDetail().getOrganizationId());
            hikeBonusSalaryAdhoc.setCompanyId(currentSession.getUserDetail().getCompanyId());
            hikeBonusSalaryAdhoc.setSalaryOnHold(true);
            hikeBonusSalaryAdhoc.setActive(true);
            hikeBonusSalaryAdhoc.setStatus(ApplicationConstant.Approved);
            hikeBonusSalaryAdhoc.setProgressState(ApplicationConstant.Approved);
            return hikePromotionAndAdhocsRepository.addHikeBonusAdhocRepository(hikeBonusSalaryAdhoc);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<HikeBonusSalaryAdhoc> filterHikeBonusSalaryAdhocService(FilterModel filterModel) throws Exception {
        return hikePromotionAndAdhocsRepository.filterHikeBonusSalaryAdhocRepository(filterModel);
    }

    public String finalizeSalaryRunConfigService(SalaryRunConfigProcessing salaryRunConfigProcessing) throws Exception {
        if (salaryRunConfigProcessing.getForMonth() == 0)
            throw new Exception("Invalid month selected");

        if (salaryRunConfigProcessing.getForYear() == 0)
            throw new Exception("Invalid year selected");

        salaryRunConfigProcessing.setCompanyId(currentSession.getUserDetail().getCompanyId());
        var processingConfigData = processingPayrollRepository.getSalaryRunConfigRepository(salaryRunConfigProcessing);
        if (processingConfigData.size() == 0)
            throw new Exception("Processing config record not found");

        var existProcessingRecord = processingConfigData.get(0);
        existProcessingRecord.setProcessingStatus(ApplicationConstant.Completed);
        return processingPayrollRepository.updateSalaryRunConfigRepository(existProcessingRecord);
    }

    public List<BonusShiftOvertime> getBonusService(FilterModel filterModel) throws Exception {
        filterModel.setSearchString(filterModel.getSearchString() + " and b.IsBonus = true");
        return processingPayrollRepository.getBonusRepository(filterModel);
    }

    public List<BonusShiftOvertime> getOvertimeService(FilterModel filterModel) throws Exception {
        filterModel.setSearchString(filterModel.getSearchString() + " and b.IsOvertime = true");
        return processingPayrollRepository.getBonusRepository(filterModel);
    }
}
