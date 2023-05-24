package com.bot.employeeFilter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class CompleteLeaveDetail {
    @JsonProperty("RecordId")
    String recordId;
    @JsonProperty("EmployeeId")
    Long employeeId;
    @JsonProperty("EmployeeName")
    String employeeName;
    @JsonProperty("ProjectId")
    Long projectId;
    @JsonProperty("AssignTo")
    Long assignTo;
    @JsonProperty("LeaveTypeId")
    int leaveTypeId;
    @JsonProperty("LeaveTypeName")
    String leaveTypeName;
    @JsonProperty("NumOfDays")
    int numOfDays;
    @JsonProperty("Session")
    int session;
    @JsonProperty("LeaveFromDay")
    Date leaveFromDay;
    @JsonProperty("LeaveToDay")
    Date leaveToDay;
    @JsonProperty("LeaveStatus")
    int leaveStatus;
    @JsonProperty("RespondedBy")
    Long respondedBy;
    @JsonProperty("UpdatedOn")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    Date updatedOn;
    @JsonProperty("Reason")
    String reason;
    @JsonProperty("FeedBack")
    String FeedBack;
    @JsonProperty("LeavePlanId")
    int leavePlanId;
    @JsonProperty("RequestChain")
    List<RequestChainModal> requestChain;
    @JsonProperty("RequestedOn")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    Date requestedOn;
    @JsonProperty("FileIds")
    String fileIds;
    @JsonProperty("ApprovalWorkFlowId")
    int approvalWorkFlowId;
    @JsonProperty("AutoExpiredAfter")
    int autoExpiredAfter;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(Long assignTo) {
        this.assignTo = assignTo;
    }

    public int getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(int leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }

    public int getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(int numOfDays) {
        this.numOfDays = numOfDays;
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }

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

    public int getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(int leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public Long getRespondedBy() {
        return respondedBy;
    }

    public void setRespondedBy(Long respondedBy) {
        this.respondedBy = respondedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFeedBack() {
        return FeedBack;
    }

    public void setFeedBack(String feedBack) {
        FeedBack = feedBack;
    }

    public int getLeavePlanId() {
        return leavePlanId;
    }

    public void setLeavePlanId(int leavePlanId) {
        this.leavePlanId = leavePlanId;
    }

    public List<RequestChainModal> getRequestChain() {
        return requestChain;
    }

    public void setRequestChain(List<RequestChainModal> requestChain) {
        this.requestChain = requestChain;
    }

    public Date getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(Date requestedOn) {
        this.requestedOn = requestedOn;
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public int getApprovalWorkFlowId() {
        return approvalWorkFlowId;
    }

    public void setApprovalWorkFlowId(int approvalWorkFlowId) {
        this.approvalWorkFlowId = approvalWorkFlowId;
    }

    public int getAutoExpiredAfter() {
        return autoExpiredAfter;
    }

    public void setAutoExpiredAfter(int autoExpiredAfter) {
        this.autoExpiredAfter = autoExpiredAfter;
    }
}

