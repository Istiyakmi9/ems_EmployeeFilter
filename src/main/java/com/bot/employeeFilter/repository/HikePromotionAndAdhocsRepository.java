package com.bot.employeeFilter.repository;

import com.bot.employeeFilter.db.service.DbManager;
import com.bot.employeeFilter.db.utils.LowLevelExecution;
import com.bot.employeeFilter.db.utils.TypedParameter;
import com.bot.employeeFilter.entity.BonusShiftOvertime;
import com.bot.employeeFilter.entity.HikeBonusSalaryAdhoc;
import com.bot.employeeFilter.model.DbParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HikePromotionAndAdhocsRepository {
    @Autowired
    LowLevelExecution lowLevelExecution;
    final Logger LOGGER = LoggerFactory.getLogger(HikePromotionAndAdhocsRepository.class);
    @Autowired
    DbManager dbManager;

    public boolean updateHikeBonusAdhocRepository(List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhocs) {
        try {
            List<DbParameters> params = null;
            List<TypedParameter> parameters = null;

            for (var records : hikeBonusSalaryAdhocs) {
                parameters = dbManager.getParameters(records, HikeBonusSalaryAdhoc.class);
                params = new ArrayList<>();
                for (var filed : parameters) {
                    params.add(new DbParameters("_" + filed.getKey(), filed.getValue(), filed.getDataType()));
                }

                lowLevelExecution.executeProcedure("sp_hike_bonus_salary_adhoc_ins_update", params);
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }

        return true;
    }

    public boolean anageBonusShiftOvertimeRepository(BonusShiftOvertime bonusShiftOvertime) {
        try {
            List<TypedParameter> parameters = dbManager.getParameters(bonusShiftOvertime, BonusShiftOvertime.class);
            List<DbParameters> params = new ArrayList<>();
            for (var filed : parameters) {
                params.add(new DbParameters("_" + filed.getKey(), filed.getValue(), filed.getDataType()));
            }

            lowLevelExecution.executeProcedure("sp_bonus_shift_overtime_ins_upd", params);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        return true;
    }
}
