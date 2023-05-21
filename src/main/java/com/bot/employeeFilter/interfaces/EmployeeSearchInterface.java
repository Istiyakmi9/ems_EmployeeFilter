package com.bot.employeeFilter.interfaces;

import com.bot.employeeFilter.entity.EmployeeBrief;
import com.bot.employeeFilter.entity.FilterModel;

import java.util.List;

public interface EmployeeSearchInterface {
    List<EmployeeBrief> employeePageRecrod(FilterModel filterModel);

    List<EmployeeBrief> getAllEmployee();
}
