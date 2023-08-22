package com.bot.employeeFilter.service;

import com.bot.employeeFilter.db.service.DbManager;
import com.bot.employeeFilter.entity.EmployeeBrief;
import com.bot.employeeFilter.entity.FilterModel;
import com.bot.employeeFilter.interfaces.EmployeeSearchInterface;
import com.bot.employeeFilter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeSearchService implements EmployeeSearchInterface {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DbManager dbManager;

    public List<EmployeeBrief> getAllEmployee() throws Exception {
        return  dbManager.get(EmployeeBrief.class);
    }

    public List<EmployeeBrief> employeePageRecrod(FilterModel filterModel) {
        if(filterModel.serachString == null || filterModel.serachString.isEmpty())
            filterModel.serachString = "1=1";

        return employeeRepository.getEmployeePage(filterModel);
    }
}
