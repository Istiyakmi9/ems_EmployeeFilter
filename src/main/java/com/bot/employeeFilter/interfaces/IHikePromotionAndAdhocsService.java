package com.bot.employeeFilter.interfaces;

import com.bot.employeeFilter.entity.BonusShiftOvertime;
import com.bot.employeeFilter.entity.HikeBonusSalaryAdhoc;

import java.util.List;

public interface IHikePromotionAndAdhocsService {
    String saveHikePromotionData(List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhocs) throws Exception;
    String manageBonusShiftOvertimeService(BonusShiftOvertime bonusShiftOvertime) throws  Exception;
}
