package com.bot.employeeFilter.service;

import com.bot.employeeFilter.entity.HikeBonusSalaryAdhoc;
import com.bot.employeeFilter.repository.HikePromotionAndAdhocsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HikePromotionAndAdhocsService {
    @Autowired
    HikePromotionAndAdhocsRepository hikePromotionAndAdhocsRepository;
    public String saveHikePromotionData(List<HikeBonusSalaryAdhoc> hikeBonusSalaryAdhocs) {
        boolean flag = hikePromotionAndAdhocsRepository.updateHikeBonusAdhocRepository(hikeBonusSalaryAdhocs);

        if(!flag) {
            return "fail";
        }

        return "updated";
    }
}
