package com.bot.employeeFilter.controller;

import com.bot.employeeFilter.model.FilterModel;
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

    @RequestMapping(value = "getPayrollProcessingDetail/{year}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse> getPayrollProcessingDetail(@PathVariable int year) throws Exception {
        var result = iProcessingPayrollService.getPayrollProcessingDetailService(year);
        return  ResponseEntity.ok(ApiResponse.Ok(result));
    }

    @RequestMapping(value = "getAttendancePage", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> getAttendancePage(@RequestBody FilterModel filterModel) throws Exception {
        var result = iProcessingPayrollService.getAttendanceByPage(filterModel);
        return  ResponseEntity.ok(ApiResponse.Ok(result));
    }

    @RequestMapping(value = "getJoineeAndExitingEmployees", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse> getJoineeAndExitingEmployees() throws Exception {
        var result = iProcessingPayrollService.getJoineeAndExitingEmployeesService();
        return  ResponseEntity.ok(ApiResponse.Ok(result));
    }

    @RequestMapping(value = "getBonusShiftOT/{forMonth}/{forYear}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse> getBonusShiftOT(@PathVariable int forMonth, @PathVariable int forYear) throws Exception {
        var result = iProcessingPayrollService.getBonusShiftOTService(forMonth, forYear);
        return  ResponseEntity.ok(ApiResponse.Ok(result));
    }

    @RequestMapping(value = "getReimbursementAdhocDeduction/{forMonth}/{forYear}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse> getReimbursementAdhocDeduction(@PathVariable int forMonth, @PathVariable int forYear) throws Exception {
        var result = iProcessingPayrollService.getReimbursementAdhocDeductionService(forMonth, forYear);
        return  ResponseEntity.ok(ApiResponse.Ok(result));
    }
}
