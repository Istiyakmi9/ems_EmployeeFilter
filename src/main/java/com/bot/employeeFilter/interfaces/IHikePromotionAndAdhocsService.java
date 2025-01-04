package com.bot.employeeFilter.interfaces;

import com.bot.employeeFilter.entity.BonusShiftOvertime;
import com.bot.employeeFilter.entity.HikeBonusSalaryAdhoc;

import java.util.List;

public interface IHikePromotionAndAdhocsService {
    String saveHikePromotionData(List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhocs) throws Exception;

    List<BonusShiftOvertime> manageBonusService(List<BonusShiftOvertime> bonusShiftOvertime) throws  Exception;

    String manageNewJoineeExitsFinalSattlementService(List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhocs) throws Exception;

    String manageBonusSalaryOvertimeService(List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhocs) throws Exception;

    String manageReimbursementAdhocPaymentDeductionService(List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhocs) throws Exception;
}