package com.bot.employeeFilter.service;

import com.bot.employeeFilter.entity.HikeBonusSalaryAdhoc;
import com.bot.employeeFilter.repository.HikePromotionAndAdhocsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HikePromotionAndAdhocsService {
    @Autowired
    HikePromotionAndAdhocsRepository hikePromotionAndAdhocsRepository;
    public String saveHikePromotionData(HikeBonusSalaryAdhoc hikeBonusSalaryAdhoc) {
        boolean flag = hikePromotionAndAdhocsRepository.save(hikeBonusSalaryAdhoc);

        if(!flag) {
            return "fail";
        }

        return "updated";
    }
}
