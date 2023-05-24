package com.bot.employeeFilter.service;

import com.bot.employeeFilter.entity.Attendance;
import com.bot.employeeFilter.entity.Leave;
import com.bot.employeeFilter.entity.LeaveNotification;
import com.bot.employeeFilter.entity.LeavePlanType;
import com.bot.employeeFilter.interfaces.IProcessingPayrollService;
import com.bot.employeeFilter.model.ApplicationConstant;
import com.bot.employeeFilter.model.CompleteLeaveDetail;
import com.bot.employeeFilter.model.DbParameters;
import com.bot.employeeFilter.model.LeaveTypeBrief;
import com.bot.employeeFilter.repository.LowLevelExecution;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public String leaveApprovalService(Leave requestDetail) throws Exception {
        return updateLeaveDetail(requestDetail, ApplicationConstant.Approved);
    }

    @Transactional
    private String updateLeaveDetail(Leave requestDetail, int status) throws Exception {
        if (requestDetail.getLeaveRequestNotificationId() == 0)
            throw new Exception("Invalid request. Please check your detail first.");

        List<DbParameters> dbParameters = new ArrayList<>();
        dbParameters.add(new DbParameters("_LeaveRequestNotificationId", requestDetail.getLeaveRequestNotificationId(), Types.BIGINT));
        var dataSet = lowLevelExecution.executeProcedure("sp_employee_leave_request_GetById", dbParameters);
        List<Leave> leaveRequested = objectMapper.convertValue(dataSet.get("#result-set-1"), new TypeReference<List<Leave>>() {});
        var leaveRequestDetail = leaveRequested.get(0);
        if (leaveRequestDetail == null || leaveRequestDetail.getLeaveDetail().isEmpty())
            throw new Exception("Unable to find leave detail. Please contact to admin.");

        List<CompleteLeaveDetail> completeLeaveDetails = objectMapper.convertValue(leaveRequestDetail.getLeaveDetail(), new TypeReference<List<CompleteLeaveDetail>>() {});
        if (completeLeaveDetails.isEmpty())
            throw new Exception("Unable to find applied leave detail. Please contact to admin");
        var pendingCount = completeLeaveDetails.stream().filter(x -> !x.getRecordId().equals(requestDetail.getRecordId())).
                filter(i -> i.getLeaveStatus() == ApplicationConstant.Pending).toList().size();
        var singleLeaveDetail = completeLeaveDetails.stream().filter(x -> x.getRecordId().equals(requestDetail.getRecordId())).findFirst().get();

        long nextId = 0;
        leaveRequestDetail.setRequestStatusId(status);
        singleLeaveDetail.setLeaveStatus(status);
        if (ApplicationConstant.Rejected == status)
        {
            Long totalLeaves = TimeUnit.MILLISECONDS.toDays(requestDetail.getLeaveToDay().getTime() - requestDetail.getLeaveFromDay().getTime()) % 365;
            updateLeaveCountOnRejected(leaveRequestDetail, requestDetail.getLeaveTypeId(), totalLeaves);
        }
        else
        {
            leaveRequestDetail.setAssigneeId(0L);
        }

        singleLeaveDetail.setRespondedBy(1L);
        leaveRequestDetail.setLeaveDetail(objectMapper.writeValueAsString(completeLeaveDetails));
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

        var message = lowLevelExecution.executeProcedure("sp_leave_notification_and_request_InsUpdate", dbParams);
        return  null;
    }

    private void updateLeaveCountOnRejected(Leave LeaveRequestDetail, int leaveTypeId, Long leaveCount) throws Exception {
        if (leaveTypeId <= 0)
            throw new Exception("Invalid leave type id");

        if (!LeaveRequestDetail.getLeaveQuotaDetail().isEmpty()) {
            List<LeaveTypeBrief> records = objectMapper.convertValue(LeaveRequestDetail.getLeaveQuotaDetail(), new TypeReference<List<LeaveTypeBrief>>() {});
            if (records.size() > 0) {
                LeaveTypeBrief item = records.stream().filter(x -> x.getLeavePlanTypeId() == leaveTypeId).findFirst().get();
                item.setAvailableLeaves((int) (item.getAvailableLeaves() + leaveCount));
                if (item.getAvailableLeaves() > item.getTotalLeaveQuota())
                    item.setAvailableLeaves(item.getTotalLeaveQuota());

                LeaveRequestDetail.setLeaveQuotaDetail(objectMapper.writeValueAsString(records));
            }
        }
    }
}
