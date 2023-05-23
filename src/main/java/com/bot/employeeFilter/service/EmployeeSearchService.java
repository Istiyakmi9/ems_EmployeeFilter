package com.bot.employeeFilter.service;

import com.bot.employeeFilter.entity.EmployeeBrief;
import com.bot.employeeFilter.entity.FilterModel;
import com.bot.employeeFilter.entity.SalaryComponents;
import com.bot.employeeFilter.entity.SalaryGroup;
import com.bot.employeeFilter.interfaces.EmployeeSearchInterface;
import com.bot.employeeFilter.model.DbParameters;
import com.bot.employeeFilter.repository.EmployeeRepository;
import com.bot.employeeFilter.repository.EmployeeSearchRepository;
import com.bot.employeeFilter.repository.LowLevelExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeSearchService implements EmployeeSearchInterface {
    @Autowired
    EmployeeSearchRepository employeeSearchRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    LowLevelExecution lowLevelExecution;

    public List<EmployeeBrief> getAllEmployee() {
        List<EmployeeBrief> employees = employeeSearchRepository.findAll();
        return employees;
    }

    public List<EmployeeBrief> employeePageRecrod(FilterModel filterModel) {
        if(filterModel.serachString == null || filterModel.serachString.isEmpty()) {
            filterModel.serachString = "1=1";
        }

        return employeeRepository.getEmployeePage(filterModel);
    }
}
