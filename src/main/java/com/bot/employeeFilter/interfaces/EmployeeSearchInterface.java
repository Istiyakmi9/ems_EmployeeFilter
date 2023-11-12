package com.bot.employeeFilter.interfaces;

import com.bot.employeeFilter.entity.EmployeeBrief;
import com.bot.employeeFilter.model.FilterModel;
import com.bot.employeeFilter.model.FilteredEmployee;
import com.bot.employeeFilter.model.OrgHierarchyModel;

import java.util.List;

public interface EmployeeSearchInterface {
    List<EmployeeBrief> employeePageRecrod(FilterModel filterModel) throws Exception;
    List<EmployeeBrief> getAllEmployee() throws Exception;
    List<FilteredEmployee> employeeFilterByName(FilterModel filterModel) throws Exception;
}
