package com.bot.employeeFilter.interfaces;

import com.bot.employeeFilter.entity.*;
import com.bot.employeeFilter.model.Employee;
import com.bot.employeeFilter.model.FilterModel;
import com.bot.employeeFilter.model.PayrollMonthlyDetail;

import java.util.List;

public interface IProcessingPayrollService {
    List<?> getLeaveAndLOPService(int year, int month) throws Exception;
    String leaveApprovalService(Leave requestDetail) throws Exception;
    List<PayrollMonthlyDetail> getPayrollProcessingDetailService(int year) throws Exception;
    List<Attendance> getAttendanceByPage(FilterModel filterModel) throws Exception;
    List<Employee> getJoineeAndExitingEmployeesService() throws Exception;
    List<BonusShiftOvertime> getBonusShiftOTService(int forMonth, int forYear) throws Exception;
    List<ReimbursementAdhocDeduction> getReimbursementAdhocDeductionService(int forMonth, int forYear) throws Exception;
}
