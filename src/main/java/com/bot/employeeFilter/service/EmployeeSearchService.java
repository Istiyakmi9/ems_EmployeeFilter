package com.bot.employeeFilter.service;

import com.bot.employeeFilter.db.service.DbManager;
import com.bot.employeeFilter.db.utils.LowLevelExecution;
import com.bot.employeeFilter.entity.EmployeeBrief;
import com.bot.employeeFilter.model.FilterModel;
import com.bot.employeeFilter.interfaces.EmployeeSearchInterface;
import com.bot.employeeFilter.model.DbParameters;
import com.bot.employeeFilter.model.FilteredEmployee;
import com.bot.employeeFilter.repository.EmployeeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.*;

@Service
public class EmployeeSearchService implements EmployeeSearchInterface {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DbManager dbManager;
    @Autowired
    LowLevelExecution lowLevelExecution;
    @Autowired
    ObjectMapper mapper;

    public List<EmployeeBrief> getAllEmployee() throws Exception {
        return dbManager.get(EmployeeBrief.class);
    }

    public List<EmployeeBrief> employeePageRecrod(FilterModel filterModel) throws Exception {
        if (filterModel.getSearchString() == null || filterModel.getSearchString().isEmpty())
            filterModel.setSearchString("1=1");

        return employeeRepository.getEmployeePage(filterModel);
    }

    public List<FilteredEmployee> employeeFilterByName(FilterModel filterModel) throws Exception {
        Map<String, Object> result = lowLevelExecution.executeProcedure("sp_employee_autocomplete_data", Arrays.asList(
           new DbParameters("_SearchString", filterModel.getSearchString(), Types.VARCHAR),
                new DbParameters("_PageIndex", filterModel.getPageIndex(), Types.INTEGER),
                new DbParameters("_PageSize", filterModel.getPageSize(), Types.INTEGER),
                new DbParameters("_CompanyId", filterModel.getCompanyId(), Types.INTEGER)
        ));

        return mapper.convertValue(result.get("#result-set-1"), new TypeReference<List<FilteredEmployee>>() {
        });
    }
}
