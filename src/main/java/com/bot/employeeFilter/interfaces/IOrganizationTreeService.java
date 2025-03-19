package com.bot.employeeFilter.interfaces;

import com.bot.employeeFilter.model.DeleteOrgTreeNode;
import com.bot.employeeFilter.model.OrgHierarchyModel;

import java.util.List;

public interface IOrganizationTreeService {
    List<OrgHierarchyModel> addOrganizationHierarchyService(List<OrgHierarchyModel> orgHierarchies) throws Exception;
    List<OrgHierarchyModel> getOrganizationHierarchyService(int companyId) throws Exception;
    List<OrgHierarchyModel> getOrgTreeByRoleService(int companyId, int roleId) throws Exception;
    List<OrgHierarchyModel> deleteOrganizationHierarchyService(DeleteOrgTreeNode deleteOrgTreeNode) throws Exception;
}
