package com.bot.employeeFilter.interfaces;

import com.bot.employeeFilter.model.OrgHierarchyModel;

import java.util.List;

public interface IOrganizationTreeService {
    String addOrganizationHierarchyService(List<OrgHierarchyModel> orgHierarchies) throws Exception;
    List<OrgHierarchyModel> getOrganizationHierarchyService(int companyId) throws Exception;
    List<OrgHierarchyModel> getOrgTreeByRoleService(int companyId, int roleId) throws Exception;
}
