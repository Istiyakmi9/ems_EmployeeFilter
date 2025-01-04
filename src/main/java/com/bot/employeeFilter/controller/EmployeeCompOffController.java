package com.bot.employeeFilter.controller;

import com.bot.employeeFilter.entity.EmployeeCompoff;
import com.bot.employeeFilter.interfaces.IEmployeeCompoffService;
import com.bot.employeeFilter.model.ApiResponse;
import com.bot.employeeFilter.model.FilterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ef/compoff/")
public class EmployeeCompOffController {
    @Autowired
    IEmployeeCompoffService iEmployeeCompoffService;

    @PostMapping(value = "addEmployeeCompoff")
    public ResponseEntity<ApiResponse> addEmployeeCompoff(@RequestBody EmployeeCompoff employeeCompoff) throws Exception {
        var result = iEmployeeCompoffService.addEmployeeCompoffService(employeeCompoff);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }

    @GetMapping("approveCompoff/{employeeCompoffOvertimeId}/{status}")
    public ResponseEntity<ApiResponse> approveCompoff(@PathVariable long employeeCompoffOvertimeId, @PathVariable int status) throws Exception {
        var result = iEmployeeCompoffService.approveCompoffService(employeeCompoffOvertimeId, status);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }

    @GetMapping("rejectCompoff/{employeeCompoffOvertimeId}/{status}")
    public ResponseEntity<ApiResponse> rejectCompoff(@PathVariable long employeeCompoffOvertimeId, @PathVariable int status) throws Exception {
        var result = iEmployeeCompoffService.rejectCompoffService(employeeCompoffOvertimeId, status);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }

    @PostMapping("getEmployeeCompoffFilter")
    public ResponseEntity<ApiResponse> getEmployeeCompoffFilter(@RequestBody FilterModel filterModel) throws Exception {
        var result = iEmployeeCompoffService.getEmployeeCompoffFilterService(filterModel);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }
}
