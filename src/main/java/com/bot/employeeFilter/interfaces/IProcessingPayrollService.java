package com.bot.employeeFilter.interfaces;

import java.util.List;

public interface IProcessingPayrollService {
    List<?> getLeaveAndLOPService(int year, int month) throws Exception;
}
