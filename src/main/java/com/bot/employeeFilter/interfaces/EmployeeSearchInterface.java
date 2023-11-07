package com.bot.employeeFilter.interfaces;

import com.bot.employeeFilter.entity.EmployeeBrief;
import com.bot.employeeFilter.entity.FilterModel;
import com.bot.employeeFilter.model.OrgHierarchyModel;

import java.util.List;

public interface EmployeeSearchInterface {
    List<EmployeeBrief> employeePageRecrod(FilterModel filterModel) throws Exception;

    List<EmployeeBrief> getAllEmployee() throws Exception;
    String addOrganizationHierarchyService(List<OrgHierarchyModel> orgHierarchies) throws Exception;
    List<OrgHierarchyModel> getOrganizationHierarchyService(int companyId) throws Exception;
}
