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
        // List<EmployeeBrief> employees = employeeSearchRepository.findAll();

        // List<EmployeeBrief> employees = employeeRepository.getEmployeePage(filterModel);
        // List<EmployeeBrief> employees = employeeRepository.getEmployeeAndSalaryGroup(filterModel);

        List<DbParameters> dbParameters = new ArrayList<>();
        dbParameters.add(new DbParameters("_CompanyId", 1, Types.INTEGER));
        Map<String, Object> result = lowLevelExecution.executeProcedure("sp_salary_group_and_components_get", dbParameters);


/*        List<Object> items = (List<Object>) result.get("#result-set-1");
        List<SalaryGroup> salaryGroups = new ArrayList<>();
        for(Object obj : items) {
            salaryGroups.add((SalaryGroup) obj);
        }

        List<SalaryComponents> salaryComponents = Arrays.asList((SalaryComponents[]) result.get("#result-set-2"));*/
        return null;
    }
}
