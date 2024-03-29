package com.bot.employeeFilter.entity;

import com.bot.employeeFilter.db.annotations.Column;
import com.bot.employeeFilter.db.annotations.Id;
import com.bot.employeeFilter.db.annotations.Table;
import com.bot.employeeFilter.db.annotations.Transient;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@Table(name = "leave_request_notification")
public class LeaveNotification {
    @Id
    @Column(name = "LeaveRequestNotificationId")
    @JsonProperty("LeaveRequestNotificationId")
    Long leaveRequestNotificationId;
    @Column(name = "LeaveRequestId")
    @JsonProperty("LeaveRequestId")
    Long leaveRequestId;
    @Column(name = "UserMessage")
    @JsonProperty("UserMessage")
    String userMessage;
    @Column(name = "EmployeeId")
    @JsonProperty("EmployeeId")
    Long employeeId;
    @Column(name = "AssigneeId")
    @JsonProperty("AssigneeId")
    Long assigneeId;
    @Column(name = "ReportingManagerId")
    @JsonProperty("ReportingManagerId")
    Long reportingManagerId;
    @Column(name = "FromDate")
    @JsonProperty("FromDate")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    Date fromDate;
    @Column(name = "ToDate")
    @JsonProperty("ToDate")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    Date toDate;
    @Column(name = "NumOfDays")
    @JsonProperty("NumOfDays")
    int numOfDays;
    @Column(name = "RequestStatusId")
    @JsonProperty("RequestStatusId")
    int requestStatusId;
    @Column(name = "LeaveTypeId")
    @JsonProperty("LeaveTypeId")
    int leaveTypeId;
    @Column(name = "RecordId")
    @JsonProperty("RecordId")
    String recordId;
    @Transient
    @JsonProperty("PlanName")
    String planName;
    @Transient
    @JsonProperty("FirstName")
    String firstName;
    @Transient
    @JsonProperty("LastName")
    String lastName;

    @Transient
    @JsonProperty("IsOnLeave")
    boolean isOnLeave;

    @Transient
    @JsonProperty("LeaveQuotaDetail")
    String leaveQuotaDetail;
    public String getLeaveQuotaDetail() {
        return leaveQuotaDetail;
    }

    public void setLeaveQuotaDetail(String leaveQuotaDetail) {
        this.leaveQuotaDetail = leaveQuotaDetail;
    }


    public Long getLeaveRequestNotificationId() {
        return leaveRequestNotificationId;
    }

    public void setLeaveRequestNotificationId(Long leaveRequestNotificationId) {
        this.leaveRequestNotificationId = leaveRequestNotificationId;
    }

    public Long getLeaveRequestId() {
        return leaveRequestId;
    }

    public void setLeaveRequestId(Long leaveRequestId) {
        this.leaveRequestId = leaveRequestId;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(int numOfDays) {
        this.numOfDays = numOfDays;
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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isOnLeave() {
        return isOnLeave;
    }

    public void setOnLeave(boolean onLeave) {
        isOnLeave = onLeave;
    }
}
