package com.bot.employeeFilter.controller;

import com.bot.employeeFilter.entity.EmployeeBrief;
import com.bot.employeeFilter.model.FilterModel;
import com.bot.employeeFilter.interfaces.EmployeeSearchInterface;
import com.bot.employeeFilter.model.ApiResponse;
import com.bot.employeeFilter.model.OrgHierarchyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ef/filter/")
public class EmployeeSearchController {
    @Autowired
    EmployeeSearchInterface employeeSearchInterface;

    @RequestMapping(value = "getall", method = RequestMethod.GET)
    public ResponseEntity<List<EmployeeBrief>> getAllEmployees() throws Exception {
        var result = employeeSearchInterface.getAllEmployee();
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "pagination", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> getPaginationData(@RequestBody FilterModel filtermodel) throws Exception {
        var result = employeeSearchInterface.employeePageRecrod(filtermodel);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }

    @RequestMapping(value = "addOrganizationHierarchy", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> addOrganizationHierarchy(@RequestBody List<OrgHierarchyModel> orgHierarchies) throws Exception {
        var result = employeeSearchInterface.addOrganizationHierarchyService(orgHierarchies);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }

    @RequestMapping(value = "getOrganizationHierarchy/{companyId}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse> getOrganizationHierarchy(@PathVariable("companyId") int companyId) throws Exception {
        var result = employeeSearchInterface.getOrganizationHierarchyService(companyId);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }

    @RequestMapping(value = "employeeFilterByName", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> employeeFilterByName(@RequestBody FilterModel filterModel) throws Exception {
        var result = employeeSearchInterface.employeeFilterByName(filterModel);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }
}
