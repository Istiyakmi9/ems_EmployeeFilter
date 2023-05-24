package com.bot.employeeFilter.interfaces;

import com.bot.employeeFilter.entity.Leave;
import com.bot.employeeFilter.entity.LeaveNotification;

import java.util.List;

public interface IProcessingPayrollService {
    List<?> getLeaveAndLOPService(int year, int month) throws Exception;
    String leaveApprovalService(Leave requestDetail) throws Exception;
}
