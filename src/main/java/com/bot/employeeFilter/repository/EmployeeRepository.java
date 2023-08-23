package com.bot.employeeFilter.repository;

import com.bot.employeeFilter.db.utils.LowLevelExecution;
import com.bot.employeeFilter.entity.EmployeeBrief;
import com.bot.employeeFilter.entity.FilterModel;
import com.bot.employeeFilter.model.DbParameters;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    @Autowired
    LowLevelExecution lowLevelExecution;
    @Autowired
    ObjectMapper objectMapper;
    public List<EmployeeBrief> getEmployeePage(FilterModel filter) throws Exception {
        List<DbParameters> dbParameters = new ArrayList<>();
        dbParameters.add(new DbParameters("_SortBy", filter.getSortBy(), Types.VARCHAR));
        dbParameters.add(new DbParameters("_PageIndex", filter.getPageIndex(), Types.INTEGER));
        dbParameters.add(new DbParameters("_pageSize", filter.getPageSize(), Types.INTEGER));
        dbParameters.add(new DbParameters("_SearchString", filter.getSerachString(), Types.VARCHAR));

        var dataSet = lowLevelExecution.executeProcedure("sp_objective_catagory_filter", dbParameters);
        return objectMapper.convertValue(dataSet.get("#result-set-1"), new TypeReference<List<EmployeeBrief>>() { });
    }

    public List<EmployeeBrief> getEmployeeAndSalaryGroup(FilterModel filterModel) throws Exception {
        List<DbParameters> dbParameters = new ArrayList<>();
        dbParameters.add(new DbParameters("_CompanyId", 1, Types.INTEGER));

        var dataSet = lowLevelExecution.executeProcedure("sp_objective_catagory_filter", dbParameters);
        return objectMapper.convertValue(dataSet.get("#result-set-1"), new TypeReference<List<EmployeeBrief>>() { });
    }
}
