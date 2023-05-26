package com.bot.employeeFilter.controller;

import com.bot.employeeFilter.entity.EmployeeBrief;
import com.bot.employeeFilter.entity.FilterModel;
import com.bot.employeeFilter.interfaces.EmployeeSearchInterface;
import com.bot.employeeFilter.model.ApiResponse;
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
    public ResponseEntity<List<EmployeeBrief>> getAllEmployees() {
        var result = employeeSearchInterface.getAllEmployee();
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "pagination", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> getPaginationData(@RequestBody FilterModel filtermodel) {
        var result = employeeSearchInterface.employeePageRecrod(filtermodel);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }
}
