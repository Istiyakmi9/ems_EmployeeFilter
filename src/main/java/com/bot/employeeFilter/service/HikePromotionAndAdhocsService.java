package com.bot.employeeFilter.service;

import com.bot.employeeFilter.entity.HikeBonusSalaryAdhoc;
import com.bot.employeeFilter.model.ApplicationConstant;
import com.bot.employeeFilter.model.CurrentSession;
import com.bot.employeeFilter.repository.HikePromotionAndAdhocsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HikePromotionAndAdhocsService {
    @Autowired
    HikePromotionAndAdhocsRepository hikePromotionAndAdhocsRepository;
    @Autowired
    CurrentSession currentSession;
    public String saveHikePromotionData(List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhocs) throws Exception {
        //boolean flag = hikePromotionAndAdhocsRepository.save(hikeBonusSalaryAdhoc);
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
            if (x.getStatus() == ApplicationConstant.NotSubmitted)
                x.setStatus(ApplicationConstant.Pending);
        });
        validateHikeBonusSalaryAdhoc(hikeBonusSalaryAdhocs);
        boolean flag = true;
    public String saveHikePromotionData(List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhocs) {
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
}
