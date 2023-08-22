package com.bot.employeeFilter.entity;

import com.bot.employeeFilter.db.annotations.Column;
import com.bot.employeeFilter.db.annotations.Id;
import com.bot.employeeFilter.db.annotations.Table;
import com.bot.employeeFilter.db.annotations.Transient;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@Table(name = "employee_leave_request")
public class Leave {
    @Id
    @Column( name = "LeaveRequestId")
    @JsonProperty("LeaveRequestId")
    Long leaveRequestId;
    @Column( name = "EmployeeId")
    @JsonProperty("EmployeeId")
    Long employeeId;
    @Column( name = "LeaveDetail")
    @JsonProperty("LeaveDetail")
    String leaveDetail;
    @Column( name = "Year")
    @JsonProperty("Year")
    int year;
    @Column( name = "IsPending")
    @JsonProperty("IsPending")
    boolean isPending;
    @Column( name = "AvailableLeaves")
    @JsonProperty("AvailableLeaves")
    int availableLeaves;
    @Column( name = "TotalLeaveApplied")
    @JsonProperty("TotalLeaveApplied")
    int totalLeaveApplied;
    @Column( name = "TotalApprovedLeave")
    @JsonProperty("TotalApprovedLeave")
    int totalApprovedLeave;
    @Column( name = "TotalLeaveQuota")
    @JsonProperty("TotalLeaveQuota")
    int totalLeaveQuota;
    @Column( name = "LeaveQuotaDetail")
    @JsonProperty("LeaveQuotaDetail")
    String leaveQuotaDetail;
    @Transient
    @JsonProperty("LeaveRequestNotificationId")
    Long leaveRequestNotificationId;
    @Transient
    @JsonProperty("RecordId")
    String recordId;
    @Transient
    @JsonProperty("Reason")
    String reason;
    @Transient
    @JsonProperty("AssigneeId")
    Long assigneeId;
    @Transient
    @JsonProperty("ReportingManagerId")
    Long reportingManagerId;
    @Transient
    @JsonProperty("LeaveFromDay")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    Date leaveFromDay;
    @Transient
    @JsonProperty("LeaveToDay")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    Date leaveToDay;

    public Date getLeaveFromDay() {
        return leaveFromDay;
    }

    public void setLeaveFromDay(Date leaveFromDay) {
        this.leaveFromDay = leaveFromDay;
    }

    public Date getLeaveToDay() {
        return leaveToDay;
    }

    public void setLeaveToDay(Date leaveToDay) {
        this.leaveToDay = leaveToDay;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    public Long getReportingManagerId() {
        return reportingManagerId;
    }

    public void setReportingManagerId(Long reportingManagerId) {
        this.reportingManagerId = reportingManagerId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Long getLeaveRequestNotificationId() {
        return leaveRequestNotificationId;
    }

    public void setLeaveRequestNotificationId(Long leaveRequestNotificationId) {
        this.leaveRequestNotificationId = leaveRequestNotificationId;
    }

    public int getRequestStatusId() {
        return requestStatusId;
    }

    public void setRequestStatusId(int requestStatusId) {
        this.requestStatusId = requestStatusId;
    }

    public int getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(int leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    @Transient
    @JsonProperty("RequestStatusId")
    int requestStatusId;
    @Transient
    @JsonProperty("LeaveTypeId")
    int leaveTypeId;
    public Long getLeaveRequestId() {
        return leaveRequestId;
    }

    public void setLeaveRequestId(Long leaveRequestId) {
        this.leaveRequestId = leaveRequestId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getLeaveDetail() {
        return leaveDetail;
    }

    public void setLeaveDetail(String leaveDetail) {
        this.leaveDetail = leaveDetail;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }

    public int getAvailableLeaves() {
        return availableLeaves;
    }

    public void setAvailableLeaves(int availableLeaves) {
        this.availableLeaves = availableLeaves;
    }

    public int getTotalLeaveApplied() {
        return totalLeaveApplied;
    }

    public void setTotalLeaveApplied(int totalLeaveApplied) {
        this.totalLeaveApplied = totalLeaveApplied;
    }

    public int getTotalApprovedLeave() {
        return totalApprovedLeave;
    }

    public void setTotalApprovedLeave(int totalApprovedLeave) {
        this.totalApprovedLeave = totalApprovedLeave;
    }

    public int getTotalLeaveQuota() {
        return totalLeaveQuota;
    }

    public void setTotalLeaveQuota(int totalLeaveQuota) {
        this.totalLeaveQuota = totalLeaveQuota;
    }

    public String getLeaveQuotaDetail() {
        return leaveQuotaDetail;
    }

    public void setLeaveQuotaDetail(String leaveQuotaDetail) {
        this.leaveQuotaDetail = leaveQuotaDetail;
    }
}
