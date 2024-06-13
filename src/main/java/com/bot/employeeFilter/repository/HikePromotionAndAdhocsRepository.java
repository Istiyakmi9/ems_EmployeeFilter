package com.bot.employeeFilter.repository;

import com.bot.employeeFilter.db.service.DbManager;
import com.bot.employeeFilter.db.utils.LowLevelExecution;
import com.bot.employeeFilter.db.utils.TypedParameter;
import com.bot.employeeFilter.entity.BonusShiftOvertime;
import com.bot.employeeFilter.entity.EmployeeBrief;
import com.bot.employeeFilter.entity.HikeBonusSalaryAdhoc;
import com.bot.employeeFilter.model.DbParameters;
import com.bot.employeeFilter.model.FilterModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HikePromotionAndAdhocsRepository {
    @Autowired
    LowLevelExecution lowLevelExecution;
    final Logger LOGGER = LoggerFactory.getLogger(HikePromotionAndAdhocsRepository.class);
    @Autowired
    DbManager dbManager;
    @Autowired
    ObjectMapper objectMapper;

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

    public boolean manageBonusShiftOvertimeRepository(BonusShiftOvertime bonusShiftOvertime) {
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

    public boolean addHikeBonusAdhocRepository(HikeBonusSalaryAdhoc hikeBonusSalaryAdhocs) {
        try {
            List<TypedParameter> parameters = dbManager.getParameters(hikeBonusSalaryAdhocs, HikeBonusSalaryAdhoc.class);
            List<DbParameters> params = new ArrayList<>();
            for (var filed : parameters) {
                params.add(new DbParameters("_" + filed.getKey(), filed.getValue(), filed.getDataType()));
            }

            lowLevelExecution.executeProcedure("sp_hike_bonus_salary_adhoc_ins_update", params);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }

        return true;
    }

    public List<HikeBonusSalaryAdhoc> filterHikeBonusSalaryAdhocRepository(FilterModel filterModel) throws Exception {
        List<DbParameters> dbParameters = new ArrayList<>();
        dbParameters.add(new DbParameters("_SearchString", filterModel.getSearchString(), Types.VARCHAR));
        dbParameters.add(new DbParameters("_SortBy", filterModel.getSortBy(), Types.VARCHAR));
        dbParameters.add(new DbParameters("_PageIndex", filterModel.getPageIndex(), Types.INTEGER));
        dbParameters.add(new DbParameters("_PageSize", filterModel.getPageSize(), Types.INTEGER));

        var dataSet = lowLevelExecution.executeProcedure("sp_hike_bonus_salary_adhoc_filter", dbParameters);
        return objectMapper.convertValue(dataSet.get("#result-set-1"), new TypeReference<List<HikeBonusSalaryAdhoc>>() { });
    }
}
