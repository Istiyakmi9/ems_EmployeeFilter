package com.bot.employeeFilter.controller;

import com.bot.employeeFilter.interfaces.IProcessingPayrollService;
import com.bot.employeeFilter.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/runpayroll/")
public class ProcessingPayrollController {
    @Autowired
    IProcessingPayrollService iProcessingPayrollService;
    @GetMapping("getLeaveAndLOP/{year}/{month}")
    public ResponseEntity<ApiResponse> getLeaveAndLOP(@PathVariable int year, @PathVariable int month) throws Exception {
        var result = iProcessingPayrollService.getLeaveAndLOPService(year, month);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }
}
