package com.bot.employeeFilter.repository;

import com.bot.employeeFilter.db.utils.LowLevelExecution;
import com.bot.employeeFilter.entity.*;
import com.bot.employeeFilter.model.Employee;
import com.bot.employeeFilter.model.FilterModel;
import com.bot.employeeFilter.model.DbParameters;
import com.bot.employeeFilter.model.PayrollMonthlyDetail;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

@Repository
public class ProcessingPayrollRepository {
    @Autowired
    LowLevelExecution lowLevelExecution;
    @Autowired
    ObjectMapper objectMapper;

    public List<?> getLeaveAndLOPRepository(int year, int month, int companyId) throws Exception {
        List<DbParameters> dbParameters = new ArrayList<>();
        dbParameters.add(new DbParameters("_Year", year, Types.INTEGER));
        dbParameters.add(new DbParameters("_Month", month, Types.INTEGER));
        dbParameters.add(new DbParameters("_CompanyId", companyId, Types.INTEGER));
        var dataSet = lowLevelExecution.executeProcedure("sp_leave_and_lop_get", dbParameters);
        var result = new ArrayList<>();
        List<LeaveNotification> leaves = objectMapper.convertValue(dataSet.get("#result-set-1"), new TypeReference<List<LeaveNotification>>() {});
        if (leaves.size() > 0) {
            int enddate =  YearMonth.of(year, month-1).atEndOfMonth().getDayOfMonth();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
            String date = year +"-"+ enddate + "-" + (month-1);
            Date startDate = formatter.parse(date);
            date = year +"-"+ 1 + "-" + (month+1);
            Date endDate = formatter.parse(date);
            leaves = leaves.stream().filter(x -> (x.getFromDate().after(startDate) && x.getToDate().before(endDate))
                    || (x.getToDate().after(startDate) && x.getToDate().before(endDate))
                    || (x.getFromDate().before(endDate) && x.getToDate().after(endDate))
            ).toList();
        }
        result.add(leaves);
        result.add(objectMapper.convertValue(dataSet.get("#result-set-2"), new TypeReference<List<Attendance>>() {}));
        return result;
    }

    public List<Leave> getEmployeeLeaveRequestRepository(long leaveRequestNotificationId) throws Exception {
        List<DbParameters> dbParameters = new ArrayList<>();
        dbParameters.add(new DbParameters("_LeaveRequestNotificationId", leaveRequestNotificationId, Types.BIGINT));
        var dataSet = lowLevelExecution.executeProcedure("sp_employee_leave_request_GetById", dbParameters);
        return objectMapper.convertValue(dataSet.get("#result-set-1"), new TypeReference<List<Leave>>() {
        });
    }

    public Optional<List<PayrollMonthlyDetail>> getPayrollProcessingDetailRepository(int year, int companyId) throws Exception {

        if (year != LocalDateTime.now().getYear())
            throw new Exception("Invalid year passed");

        List<DbParameters> dbParameters = new ArrayList<>();
        dbParameters.add(new DbParameters("_ForYear", year, Types.INTEGER));
        dbParameters.add(new DbParameters("_CompanyId", companyId, Types.INTEGER));
        var dataSet = lowLevelExecution.executeProcedure("sp_payroll_monthly_detail_get_processed_data", dbParameters);
        return Optional.of(objectMapper.convertValue(dataSet.get("#result-set-1"), new TypeReference<List<PayrollMonthlyDetail>>() {
        }));
    }

    public void updateLeaveDetailRepository(Leave leaveRequestDetail, int pendingCount) throws Exception {
        List<DbParameters> dbParams = new ArrayList<>();
        dbParams.add(new DbParameters("_LeaveRequestId", leaveRequestDetail.getLeaveRequestId(), Types.BIGINT));
        dbParams.add(new DbParameters("_EmployeeId", leaveRequestDetail.getEmployeeId(), Types.BIGINT));
        dbParams.add(new DbParameters("_LeaveDetail", leaveRequestDetail.getLeaveDetail(), Types.VARCHAR));
        dbParams.add(new DbParameters("_Reason", leaveRequestDetail.getReason(), Types.VARCHAR));
        dbParams.add(new DbParameters("_AssigneeId", leaveRequestDetail.getAssigneeId(), Types.BIGINT));
        dbParams.add(new DbParameters("_ReportingManagerId", leaveRequestDetail.getReportingManagerId(), Types.BIGINT));
        dbParams.add(new DbParameters("_Year", leaveRequestDetail.getYear(), Types.INTEGER));
        dbParams.add(new DbParameters("_LeaveFromDay", leaveRequestDetail.getLeaveFromDay(), Types.DATE));
        dbParams.add(new DbParameters("_LeaveToDay", leaveRequestDetail.getLeaveToDay(), Types.DATE));
        dbParams.add(new DbParameters("_LeaveTypeId", leaveRequestDetail.getLeaveTypeId(), Types.INTEGER));
        dbParams.add(new DbParameters("_RequestStatusId", leaveRequestDetail.getRequestStatusId(), Types.BIGINT));
        dbParams.add(new DbParameters("_AvailableLeaves", leaveRequestDetail.getAvailableLeaves(), Types.BIGINT));
        dbParams.add(new DbParameters("_TotalLeaveApplied", leaveRequestDetail.getTotalLeaveApplied(), Types.BIGINT));
        dbParams.add(new DbParameters("_TotalLeaveQuota", leaveRequestDetail.getTotalLeaveQuota(), Types.BIGINT));
        dbParams.add(new DbParameters("_NumOfDays", 0, Types.INTEGER));
        dbParams.add(new DbParameters("_LeaveRequestNotificationId", leaveRequestDetail.getLeaveRequestNotificationId(), Types.BIGINT));
        dbParams.add(new DbParameters("_RecordId", leaveRequestDetail.getRecordId(), Types.BIGINT));
        dbParams.add(new DbParameters("_IsPending", pendingCount > 0 ? true : false, Types.BIT));
        dbParams.add(new DbParameters("_FeedBackMessage", leaveRequestDetail.getReason(), Types.VARCHAR));

        lowLevelExecution.executeProcedure("sp_leave_notification_and_request_InsUpdate", dbParams);
    }

    public List<Attendance> getAttendanceByPageRepository(FilterModel filterModel) throws Exception {
        List<DbParameters> dbParams = new ArrayList<>();
        dbParams.add(new DbParameters("_SearchString", filterModel.getSearchString(), Types.VARCHAR));
        dbParams.add(new DbParameters("_SortBy", filterModel.getSortBy(), Types.VARCHAR));
        dbParams.add(new DbParameters("_PageIndex", filterModel.getPageIndex(), Types.INTEGER));
        dbParams.add(new DbParameters("_PageSize", filterModel.getPageSize(), Types.INTEGER));

        Map<String, Object> result = lowLevelExecution.executeProcedure("sp_attendance_get_by_page_yearmonth", dbParams);
        List<Attendance> attendances = objectMapper.convertValue(result.get("#result-set-1"), new TypeReference<List<Attendance>>() {
        });

        Optional.ofNullable(attendances).orElseThrow(() -> new RuntimeException("Fail to get attendance detail. Please contact to admin"));
        return attendances;
    }

    public List<Employee> getJoineeAndExitingEmployeesRepository(int companyId) throws Exception {
        List<DbParameters> dbParams = new ArrayList<>();
        dbParams.add(new DbParameters("_CompanyId", companyId, Types.INTEGER));

        Map<String, Object> result = lowLevelExecution.executeProcedure("sp_employee_get_newjoinee_exiting", dbParams);
        List<Employee> employees = objectMapper.convertValue(result.get("#result-set-1"), new TypeReference<List<Employee>>() {
        });

        return employees;
    }

    public List<BonusShiftOvertime> getBonusShiftOTRepository(int companyId, int forMonth, int forYear) throws Exception {
        List<DbParameters> dbParams = new ArrayList<>();
        dbParams.add(new DbParameters("_CompanyId", companyId, Types.INTEGER));
        dbParams.add(new DbParameters("_ForMonth", forMonth, Types.INTEGER));
        dbParams.add(new DbParameters("_ForYear", forYear, Types.INTEGER));

        Map<String, Object> result = lowLevelExecution.executeProcedure("sp_employee_get_bonus_shift_overtime", dbParams);
        List<BonusShiftOvertime> bonusShiftOvertimes = objectMapper.convertValue(result.get("#result-set-1"), new TypeReference<List<BonusShiftOvertime>>() {
        });

        return bonusShiftOvertimes;
    }

    public List<ReimbursementAdhocDeduction> getReimbursementAdhocDeductionRepository(int companyId, int forMonth,
                                                                                      int forYear) throws Exception {
        List<DbParameters> dbParams = new ArrayList<>();
        dbParams.add(new DbParameters("_CompanyId", companyId, Types.INTEGER));
        dbParams.add(new DbParameters("_ForMonth", forMonth, Types.INTEGER));
        dbParams.add(new DbParameters("_ForYear", forYear, Types.INTEGER));

        Map<String, Object> result = lowLevelExecution.executeProcedure("sp_employee_get_reimburs_adhoc_and_deduction", dbParams);
        List<ReimbursementAdhocDeduction> reimbursementAdhocDeductions = objectMapper.convertValue(result.get("#result-set-1"), new TypeReference<List<ReimbursementAdhocDeduction>>() {
        });

        return reimbursementAdhocDeductions;
    }

    public Leave getEmployeeLeaveRequestRepository(long employeeId, int year) throws Exception {
        List<DbParameters> dbParams = new ArrayList<>();
        dbParams.add(new DbParameters("_EmployeeId", employeeId, Types.BIGINT));
        dbParams.add(new DbParameters("_Year", year, Types.INTEGER));

        Map<String, Object> result = lowLevelExecution.executeProcedure("sp_employee_leave_request_by_empid", dbParams);
        List<Leave> leaves = objectMapper.convertValue(result.get("#result-set-1"), new TypeReference<List<Leave>>() {
        });
        if (leaves.size() == 1)
            return leaves.get(0);
        else
            return null;
    }

    public void updateEmployeeLeaveRequestRepository(Leave leaveRequestDetail) throws Exception {
        List<DbParameters> dbParams = new ArrayList<>();
        dbParams.add(new DbParameters("_LeaveRequestId", leaveRequestDetail.getLeaveRequestId(), Types.BIGINT));
        dbParams.add(new DbParameters("_EmployeeId", leaveRequestDetail.getEmployeeId(), Types.BIGINT));
        dbParams.add(new DbParameters("_LeaveDetail", leaveRequestDetail.getLeaveDetail(), Types.VARCHAR));
        dbParams.add(new DbParameters("_Year", leaveRequestDetail.getYear(), Types.INTEGER));
        dbParams.add(new DbParameters("_IsPending", leaveRequestDetail.isPending(), Types.BIT));
        dbParams.add(new DbParameters("_AvailableLeaves", leaveRequestDetail.getAvailableLeaves(), Types.BIGINT));
        dbParams.add(new DbParameters("_TotalLeaveApplied", leaveRequestDetail.getTotalLeaveApplied(), Types.BIGINT));
        dbParams.add(new DbParameters("_TotalLeaveQuota", leaveRequestDetail.getTotalLeaveQuota(), Types.BIGINT));
        dbParams.add(new DbParameters("_LeaveQuotaDetail", leaveRequestDetail.getLeaveQuotaDetail(), Types.VARCHAR));

        lowLevelExecution.executeProcedure("sp_employee_leave_request_InsUpdate", dbParams);
    }
}

