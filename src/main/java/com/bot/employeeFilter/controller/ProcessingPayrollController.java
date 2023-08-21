package com.bot.employeeFilter.controller;

import com.bot.employeeFilter.entity.Leave;
import com.bot.employeeFilter.interfaces.IProcessingPayrollService;
import com.bot.employeeFilter.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ef/runpayroll/")
public class ProcessingPayrollController {
    @Autowired
    IProcessingPayrollService iProcessingPayrollService;
    @GetMapping("getLeaveAndLOP/{year}/{month}")
    public ResponseEntity<ApiResponse> getLeaveAndLOP(@PathVariable int year, @PathVariable int month) throws Exception {
        var result = iProcessingPayrollService.getLeaveAndLOPService(year, month);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }

    @PostMapping("leaveApproval")
    public ResponseEntity<ApiResponse> leaveApproval(@RequestBody Leave requestDetail) throws Exception {
        var result = iProcessingPayrollService.leaveApprovalService(requestDetail);
        return  ResponseEntity.ok(ApiResponse.Ok(result));
    }
}
