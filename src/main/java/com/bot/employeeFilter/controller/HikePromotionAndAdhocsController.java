package com.bot.employeeFilter.controller;

import com.bot.employeeFilter.entity.BonusShiftOvertime;
import com.bot.employeeFilter.entity.HikeBonusSalaryAdhoc;
import com.bot.employeeFilter.interfaces.IHikePromotionAndAdhocsService;
import com.bot.employeeFilter.model.ApiResponse;
import com.bot.employeeFilter.service.HikePromotionAndAdhocsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ef/promotionoradhocs/")
public class HikePromotionAndAdhocsController {
    @Autowired
    IHikePromotionAndAdhocsService hikePromotionAndAdhocsService;

    @PostMapping("updateHikePromotionAndAdhocs")
    public ResponseEntity<ApiResponse> updateHikePromotionAndAdhocs(@RequestBody List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhoc) throws Exception {
        String status = hikePromotionAndAdhocsService.saveHikePromotionData(hikeBonusSalaryAdhoc);
        return ResponseEntity.ok(ApiResponse.Ok(status));
    }

    @PostMapping("manageBonus")
    public ResponseEntity<ApiResponse> manageBonus(@RequestBody List<BonusShiftOvertime> hikeBonusSalaryAdhoc) throws Exception {
        var result = hikePromotionAndAdhocsService.manageBonusService(hikeBonusSalaryAdhoc);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }

    @PostMapping("manageNewJoineeExitsFinalSattlement")
    public ResponseEntity<ApiResponse> manageNewJoineeExitsFinalSattlement(@RequestBody List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhoc) throws Exception {
        String status = hikePromotionAndAdhocsService.manageNewJoineeExitsFinalSattlementService(hikeBonusSalaryAdhoc);
        return ResponseEntity.ok(ApiResponse.Ok(status));
    }

    @PostMapping("manageBonusSalaryRevisionOvertime")
    public ResponseEntity<ApiResponse> manageBonusSalaryRevisionOvertime(@RequestBody List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhoc) throws Exception {
        String status = hikePromotionAndAdhocsService.manageBonusSalaryOvertimeService(hikeBonusSalaryAdhoc);
        return ResponseEntity.ok(ApiResponse.Ok(status));
    }

    @PostMapping("manageReimbursementAdhocPaymentDeduction")
    public ResponseEntity<ApiResponse> manageReimbursementAdhocPaymentDeduction(@RequestBody List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhoc) throws Exception {
        String status = hikePromotionAndAdhocsService.manageReimbursementAdhocPaymentDeductionService(hikeBonusSalaryAdhoc);
        return ResponseEntity.ok(ApiResponse.Ok(status));
    }
}