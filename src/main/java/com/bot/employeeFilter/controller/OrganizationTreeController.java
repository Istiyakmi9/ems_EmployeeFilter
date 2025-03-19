package com.bot.employeeFilter.controller;

import com.bot.employeeFilter.interfaces.IOrganizationTreeService;
import com.bot.employeeFilter.model.ApiResponse;
import com.bot.employeeFilter.model.DeleteOrgTreeNode;
import com.bot.employeeFilter.model.OrgHierarchyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/ef/orgtree/")
public class OrganizationTreeController {
    @Autowired
    IOrganizationTreeService organizationTreeService;

    @RequestMapping(value = "addOrganizationHierarchy", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> addOrganizationHierarchy(@RequestBody List<OrgHierarchyModel> orgHierarchies) throws Exception {
        var result = organizationTreeService.addOrganizationHierarchyService(orgHierarchies);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }

    @RequestMapping(value = "getOrganizationHierarchy/{companyId}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse> getOrganizationHierarchy(@PathVariable("companyId") int companyId) throws Exception {
        var result = organizationTreeService.getOrganizationHierarchyService(companyId);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }

    @RequestMapping(value = "getOrgTreeByRole/{companyId}/{roleId}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse> getOrgTreeByRole(@PathVariable("companyId") int companyId, @PathVariable("roleId") int roleId) throws Exception {
        var result = organizationTreeService.getOrgTreeByRoleService(companyId, roleId);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }

    @RequestMapping(value = "deleteOrganizationHierarchy", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> deleteOrganizationHierarchy(@RequestBody DeleteOrgTreeNode deleteOrgTreeNode) throws Exception {
        var result = organizationTreeService.deleteOrganizationHierarchyService(deleteOrgTreeNode);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }
}
