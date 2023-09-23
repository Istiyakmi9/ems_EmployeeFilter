package com.bot.employeeFilter.repository;

import com.bot.employeeFilter.db.utils.LowLevelExecution;
import com.bot.employeeFilter.entity.Attendance;
import com.bot.employeeFilter.entity.FilterModel;
import com.bot.employeeFilter.entity.Leave;
import com.bot.employeeFilter.entity.LeaveNotification;
import com.bot.employeeFilter.model.DbParameters;
import com.bot.employeeFilter.model.PayrollMonthlyDetail;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class ProcessingPayrollRepository {
    @Autowired
    LowLevelExecution lowLevelExecution;
    @Autowired
    ObjectMapper objectMapper;

    public List<?> getLeaveAndLOPRepository(int year, int month) throws Exception {
        List<DbParameters> dbParameters = new ArrayList<>();
        dbParameters.add(new DbParameters("_Year", year, Types.INTEGER));
        dbParameters.add(new DbParameters("_Month", month, Types.INTEGER));
        var dataSet = lowLevelExecution.executeProcedure("sp_leave_and_lop_get", dbParameters);
        var result = new ArrayList<>();

        result.add(objectMapper.convertValue(dataSet.get("#result-set-1"), new TypeReference<List<LeaveNotification>>() {
        }));
        result.add(objectMapper.convertValue(dataSet.get("#result-set-2"), new TypeReference<List<Attendance>>() {
        }));
        return result;
    }

    public List<Leave> getEmployeeLeaveRequestRepository(long leaveRequestNotificationId) throws Exception {
        List<DbParameters> dbParameters = new ArrayList<>();
        dbParameters.add(new DbParameters("_LeaveRequestNotificationId", leaveRequestNotificationId, Types.BIGINT));
        var dataSet = lowLevelExecution.executeProcedure("sp_employee_leave_request_GetById", dbParameters);
        return objectMapper.convertValue(dataSet.get("#result-set-1"), new TypeReference<List<Leave>>() {
        });
    }

    public Optional<List<PayrollMonthlyDetail>> getPayrollProcessingDetailRepository(int year, int month) throws Exception {

        if (year != LocalDateTime.now().getYear())
            throw new Exception("Invalid year passed");

        if (month <= 0 || month > 12)
            throw new Exception("Invalid month passed");

        List<DbParameters> dbParameters = new ArrayList<>();
        dbParameters.add(new DbParameters("_ForYear", year, Types.INTEGER));
        dbParameters.add(new DbParameters("_CompanyId", month, Types.INTEGER));
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
}

