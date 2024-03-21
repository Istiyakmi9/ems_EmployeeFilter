package com.bot.employeeFilter.controller;

import com.bot.employeeFilter.entity.HikeBonusSalaryAdhoc;
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
    HikePromotionAndAdhocsService hikePromotionAndAdhocsService;

    @PostMapping("updateHikePromotionAndAdhocs")
    public ResponseEntity<ApiResponse> updateHikePromotionAndAdhocs(@RequestBody List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhoc) {
        // String status = hikePromotionAndAdhocsService.saveHikePromotionData(hikeBonusSalaryAdhoc.get(0));
        String status = "updated";
        return ResponseEntity.ok(ApiResponse.Ok(status));
    }
}
