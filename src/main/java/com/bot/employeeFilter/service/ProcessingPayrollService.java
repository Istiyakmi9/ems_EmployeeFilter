package com.bot.employeeFilter.service;

import com.bot.employeeFilter.entity.Attendance;
import com.bot.employeeFilter.entity.LeaveNotification;
import com.bot.employeeFilter.interfaces.IProcessingPayrollService;
import com.bot.employeeFilter.model.DbParameters;
import com.bot.employeeFilter.repository.LowLevelExecution;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessingPayrollService implements IProcessingPayrollService {
    @Autowired
    LowLevelExecution lowLevelExecution;
    @Autowired
    ObjectMapper objectMapper;
    @Override
    public List<?> getLeaveAndLOPService(int year, int month) throws Exception {
        if (year == 0)
            throw new Exception("Invalid year selected. Please select a valid year");

        if (month == 0)
            throw new Exception("Invalid month selected. Please select a valid month");

        List<DbParameters> dbParameters = new ArrayList<>();
        dbParameters.add(new DbParameters("_Year", year, Types.INTEGER));
        dbParameters.add(new DbParameters("_Month", month, Types.INTEGER));
        var dataSet = lowLevelExecution.executeProcedure("sp_leave_and_lop_get", dbParameters);
        var result = new ArrayList<>();
        result.add(objectMapper.convertValue(dataSet.get("#result-set-1"), new TypeReference<List<LeaveNotification>>() {}));
        result.add(objectMapper.convertValue(dataSet.get("#result-set-2"), new TypeReference<List<Attendance>>() {}));
        return  result;
    }
}
