package com.bot.employeeFilter.service;

import com.bot.employeeFilter.entity.BonusShiftOvertime;
import com.bot.employeeFilter.entity.HikeBonusSalaryAdhoc;
import com.bot.employeeFilter.interfaces.IHikePromotionAndAdhocsService;
import com.bot.employeeFilter.model.ApplicationConstant;
import com.bot.employeeFilter.model.CurrentSession;
import com.bot.employeeFilter.repository.HikePromotionAndAdhocsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class HikePromotionAndAdhocsService implements IHikePromotionAndAdhocsService {
    @Autowired
    HikePromotionAndAdhocsRepository hikePromotionAndAdhocsRepository;
    @Autowired
    CurrentSession currentSession;
    public String saveHikePromotionData(List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhocs) throws Exception {
        hikeBonusSalaryAdhocs.forEach(x -> {
            x.setCompanyId(currentSession.getUserDetail().getCompanyId());
            x.setOrganizationId(currentSession.getUserDetail().getOrganizationId());
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
        boolean flag = hikePromotionAndAdhocsRepository.updateHikeBonusAdhocRepository(hikeBonusSalaryAdhocs);
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

    public String manageBonusShiftOvertimeService(BonusShiftOvertime bonusShiftOvertime) throws Exception {
        validateBonusShiftOvertime(bonusShiftOvertime);
        java.util.Date utilDate = new java.util.Date();
        var date = new java.sql.Timestamp(utilDate.getTime());
        bonusShiftOvertime.setCompanyId(currentSession.getUserDetail().getCompanyId());
        bonusShiftOvertime.setOrganizationId(currentSession.getUserDetail().getOrganizationId());
        bonusShiftOvertime.setUpdatedBy(currentSession.getUserDetail().getUserId());
        bonusShiftOvertime.setUpdatedOn(date);
        boolean flag = hikePromotionAndAdhocsRepository.anageBonusShiftOvertimeRepository(bonusShiftOvertime);
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
}
