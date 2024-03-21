package com.bot.employeeFilter.repository;

import com.bot.employeeFilter.db.service.DbManager;
import com.bot.employeeFilter.entity.HikeBonusSalaryAdhoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HikePromotionAndAdhocsRepository {

    final Logger LOGGER = LoggerFactory.getLogger(HikePromotionAndAdhocsRepository.class);
    @Autowired
    DbManager dbManager;

    public boolean save(HikeBonusSalaryAdhoc hikeBonusSalaryAdhoc) {
        try {
            dbManager.save(hikeBonusSalaryAdhoc);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }

        return true;
    }
}
