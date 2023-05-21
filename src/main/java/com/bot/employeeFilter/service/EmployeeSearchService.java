package com.bot.employeeFilter.service;

import com.bot.employeeFilter.entity.EmployeeBrief;
import com.bot.employeeFilter.entity.FilterModel;
import com.bot.employeeFilter.interfaces.EmployeeSearchInterface;
import com.bot.employeeFilter.repository.EmployeeSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeSearchService implements EmployeeSearchInterface {
    @Autowired
    EmployeeSearchRepository employeeSearchRepository;

    public List<EmployeeBrief> getAllEmployee() {
        List<EmployeeBrief> employees = employeeSearchRepository.findAll();
        return employees;
    }

    public List<EmployeeBrief> employeePageRecrod(FilterModel filterModel) {
        List<EmployeeBrief> employees = employeeSearchRepository.findAll();
        return employees;
    }
}
