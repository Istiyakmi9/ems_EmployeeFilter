package com.bot.employeeFilter.service;

import com.bot.employeeFilter.db.service.DbManager;
import com.bot.employeeFilter.db.utils.LowLevelExecution;
import com.bot.employeeFilter.entity.EmployeeCompoff;
import com.bot.employeeFilter.entity.Leave;
import com.bot.employeeFilter.interfaces.IEmployeeCompoffService;
import com.bot.employeeFilter.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Types;
import java.util.*;

@Service
public class EmployeeCompoffService implements IEmployeeCompoffService {

    @Autowired
    CurrentSession currentSession;
    @Autowired
    DbManager dbManager;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    LowLevelExecution lowLevelExecution;

    public String addEmployeeCompoffService(EmployeeCompoff employeeCompoff) throws Exception {
        validateEmployeeCompOff(employeeCompoff);

        java.util.Date utilDate = new java.util.Date();
        var date = new java.sql.Timestamp(utilDate.getTime());
        long employeeCompoffOvertimeId = dbManager.nextLongPrimaryKey(EmployeeCompoff.class);

        employeeCompoff.setEmployeeCompoffOvertimeId(employeeCompoffOvertimeId);
        employeeCompoff.setReportingManagerId(currentSession.getReportingManagerId());
        employeeCompoff.setStatus(ApplicationConstant.Pending);
        employeeCompoff.setEmployeeId(currentSession.getUserId());
        employeeCompoff.setUpdatedBy(currentSession.getUserId());
        employeeCompoff.setUpdatedOn(date);
        employeeCompoff.setCreatedBy(currentSession.getUserId());
        employeeCompoff.setCreatedOn(date);

        dbManager.save(employeeCompoff);
        return "Compoff apply successfully";
    }

    private void validateEmployeeCompOff(EmployeeCompoff employeeCompoff) throws Exception {
        if (employeeCompoff.getComment() == null || employeeCompoff.getComment().isEmpty())
            throw new Exception("Comment add comment");

        if (employeeCompoff.getProjectId() == 0)
            throw new Exception("Ïnvalid project selected");

        if (employeeCompoff.getFromDateTime() == null)
            throw new Exception("Invalid from time");

        if (employeeCompoff.getToDateTime() == null)
            throw new Exception("Invalid to time");
    }

    public List<EmployeeCompoff> approveCompoffService(long employeeCompoffOvertimeId, int status) throws Exception {
        if (employeeCompoffOvertimeId == 0)
            throw new Exception("Invalid compoff selected");

        var employeeCompOff = changeCompoffStatus(employeeCompoffOvertimeId, ApplicationConstant.Approved);

        var employeeLeaveRequest = getEmployeeLeaveRequestRepository(employeeCompOff.getEmployeeId(), currentSession.getFinancialStartYear());
        if (employeeLeaveRequest == null || Objects.equals(employeeLeaveRequest.getLeaveQuotaDetail(), "[]"))
            throw new Exception("Leave quota not found. Please contact to admin");

        updateEmployeeCompoffLeave(employeeLeaveRequest, employeeCompOff);

        updateEmployeeLeaveRequestRepository(employeeLeaveRequest);

        FilterModel filterModel = FilterModel.builder().searchString("1=1 and e.Status=" + status)
                .pageIndex(1).pageSize(10).build();
        return getEmployeeCompoffFilterService(filterModel);
    }

    private void updateEmployeeCompoffLeave(Leave employeeLeaveRequest, EmployeeCompoff employeeCompOff) throws Exception {
        var leaveTypeBrief = objectMapper.readValue(employeeLeaveRequest.getLeaveQuotaDetail(), new TypeReference<List<LeaveTypeBrief>>() {
        });
        var compOffLeaveData = leaveTypeBrief.stream().filter(x -> Objects.equals(x.getLeavePlanTypeName(), "COMP OFF")).findFirst();

        if (compOffLeaveData.isEmpty())
            throw new Exception("Comp off leave component not found. Please contact to admin");

        double compoffDays = (double) employeeCompOff.getDuration() / (60 * 8);

        var compoffLeave = compOffLeaveData.get();
        compoffLeave.setAccruedSoFar(compoffLeave.getAccruedSoFar() + compoffDays);
        compoffLeave.setAvailableLeaves(compoffLeave.getAvailableLeaves() + compoffDays);
        compoffLeave.setTotalLeaveQuota(compoffLeave.getTotalLeaveQuota() + compoffDays);

        employeeLeaveRequest.setLeaveQuotaDetail(objectMapper.writeValueAsString(leaveTypeBrief));
        employeeLeaveRequest.setTotalLeaveQuota((int) (employeeLeaveRequest.getTotalLeaveQuota() + compoffDays));
    }

    private void updateEmployeeLeaveRequestRepository(Leave leaveRequestDetail) throws Exception {
        List<DbParameters> dbParams = new ArrayList<>();
        dbParams.add(new DbParameters("_LeaveRequestId", leaveRequestDetail.getLeaveRequestId(), Types.BIGINT));
        dbParams.add(new DbParameters("_EmployeeId", leaveRequestDetail.getEmployeeId(), Types.BIGINT));
        dbParams.add(new DbParameters("_LeaveDetail", leaveRequestDetail.getLeaveDetail(), Types.VARCHAR));
        dbParams.add(new DbParameters("_Year", leaveRequestDetail.getYear(), Types.INTEGER));
        dbParams.add(new DbParameters("_IsPending", leaveRequestDetail.isPending(), Types.BIT));
        dbParams.add(new DbParameters("_AvailableLeaves", leaveRequestDetail.getAvailableLeaves(), Types.BIGINT));
        dbParams.add(new DbParameters("_TotalApprovedLeave", leaveRequestDetail.getTotalApprovedLeave(), Types.BIGINT));
        dbParams.add(new DbParameters("_TotalLeaveApplied", leaveRequestDetail.getTotalLeaveApplied(), Types.BIGINT));
        dbParams.add(new DbParameters("_TotalLeaveQuota", leaveRequestDetail.getTotalLeaveQuota(), Types.BIGINT));
        dbParams.add(new DbParameters("_LeaveQuotaDetail", leaveRequestDetail.getLeaveQuotaDetail(), Types.VARCHAR));

        lowLevelExecution.executeProcedure("sp_employee_leave_request_InsUpdate", dbParams);
    }

    private Leave getEmployeeLeaveRequestRepository(long employeeId, int year) throws Exception {
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

    private EmployeeCompoff changeCompoffStatus(long employeeCompoffOvertimeId, int status) throws Exception {
        var existingCompoff = dbManager.getById(employeeCompoffOvertimeId, EmployeeCompoff.class);
        if (existingCompoff == null)
            throw new Exception("Employee compoff record not found");

        existingCompoff.setStatus(status);
        dbManager.save(existingCompoff);

        return existingCompoff;
    }

    public List<EmployeeCompoff> rejectCompoffService(long employeeCompoffOvertimeId, int status) throws Exception {
        if (employeeCompoffOvertimeId == 0)
            throw new Exception("Invalid compoff selected");

        changeCompoffStatus(employeeCompoffOvertimeId, ApplicationConstant.Rejected);

        FilterModel filterModel = FilterModel.builder().searchString("1=1 and e.Status=" + status)
                .pageIndex(1).pageSize(10).build();
        return getEmployeeCompoffFilterService(filterModel);
    }

    public List<EmployeeCompoff> getEmployeeCompoffFilterService(@RequestBody FilterModel filterModel) throws Exception {
        filterModel.setSearchString(filterModel.getSearchString() + " and e.ReportingManagerId = " + currentSession.getUserId());

        Map<String, Object> result = lowLevelExecution.executeProcedure("sp_employee_compoff_filter", Arrays.asList(
                new DbParameters("_SearchString", filterModel.getSearchString(), Types.VARCHAR),
                new DbParameters("_PageIndex", filterModel.getPageIndex(), Types.INTEGER),
                new DbParameters("_PageSize", filterModel.getPageSize(), Types.INTEGER)
        ));

        return objectMapper.convertValue(result.get("#result-set-1"), new TypeReference<List<EmployeeCompoff>>() {
        });
    }

}
