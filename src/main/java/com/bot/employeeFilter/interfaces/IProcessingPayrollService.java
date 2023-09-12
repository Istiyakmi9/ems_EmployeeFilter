package com.bot.employeeFilter.interfaces;

import com.bot.employeeFilter.entity.Attendance;
import com.bot.employeeFilter.entity.FilterModel;
import com.bot.employeeFilter.entity.Leave;
import com.bot.employeeFilter.entity.LeaveNotification;

import java.util.List;

public interface IProcessingPayrollService {
    List<?> getLeaveAndLOPService(int year, int month) throws Exception;
    String leaveApprovalService(Leave requestDetail) throws Exception;

    List<Attendance> getAttendanceByPage(FilterModel filterModel) throws Exception;
}
