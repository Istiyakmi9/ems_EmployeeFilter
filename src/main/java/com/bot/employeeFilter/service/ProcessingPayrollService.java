package com.bot.employeeFilter.service;

import com.bot.employeeFilter.entity.*;
import com.bot.employeeFilter.model.FilterModel;
import com.bot.employeeFilter.interfaces.IProcessingPayrollService;
import com.bot.employeeFilter.model.*;
import com.bot.employeeFilter.repository.ProcessingPayrollRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ProcessingPayrollService implements IProcessingPayrollService {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ProcessingPayrollRepository processingPayrollRepository;
    @Autowired
    CurrentSession currentSession;
    @Override
    public List<?> getLeaveAndLOPService(int year, int month) throws Exception {
        if (year == 0)
            throw new Exception("Invalid year selected. Please select a valid year");

        if (month == 0)
            throw new Exception("Invalid month selected. Please select a valid month");

        return  processingPayrollRepository.getLeaveAndLOPRepository(year, month, currentSession.getUserDetail().getCompanyId());
    }

    public List<Attendance> getAttendanceByPage(FilterModel filterModel) throws Exception {
        if (filterModel.getSearchString() == null || filterModel.getSearchString().isEmpty()) {
            filterModel.setSearchString("1=1");
        }

        return processingPayrollRepository.getAttendanceByPageRepository(filterModel);
    }

    public List<Employee> getJoineeAndExitingEmployeesService() throws Exception {
        return processingPayrollRepository.getJoineeAndExitingEmployeesRepository(currentSession.getUserDetail().getCompanyId());
    }

    public String leaveApprovalService(Leave requestDetail) throws Exception {
        return updateLeaveDetail(requestDetail, ApplicationConstant.Approved);
    }

    public List<PayrollMonthlyDetail> getPayrollProcessingDetailService(int year) throws Exception {
        Optional<List<PayrollMonthlyDetail>> payrollMonthlyDetail = processingPayrollRepository.getPayrollProcessingDetailRepository(year, currentSession.getUserDetail().getCompanyId());
        payrollMonthlyDetail.orElseThrow(() -> new RuntimeException("No record found"));
        return payrollMonthlyDetail.get();
    }

    @Transactional
    private String updateLeaveDetail(Leave requestDetail, int status) throws Exception {
        if (requestDetail.getLeaveRequestNotificationId() == 0)
            throw new Exception("Invalid request. Please check your detail first.");

        List<Leave> leaveRequested = processingPayrollRepository.getEmployeeLeaveRequestRepository(requestDetail.getLeaveRequestNotificationId());
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
        processingPayrollRepository.updateLeaveDetailRepository(leaveRequestDetail, pendingCount);
        return null;
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

    public List<BonusShiftOvertime> getBonusShiftOTService(int forMonth, int forYear) throws Exception {
        return processingPayrollRepository.getBonusShiftOTRepository(currentSession.getUserDetail().getCompanyId(),
                                                                        forMonth, forYear);
    }

    public List<ReimbursementAdhocDeduction> getReimbursementAdhocDeductionService(int forMonth, int forYear) throws Exception {
        return processingPayrollRepository.getReimbursementAdhocDeductionRepository(currentSession.getUserDetail().getCompanyId(),
                                                                                    forMonth, forYear);
    }
}
