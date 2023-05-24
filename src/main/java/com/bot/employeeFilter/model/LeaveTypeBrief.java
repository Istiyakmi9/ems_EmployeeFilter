package com.bot.employeeFilter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LeaveTypeBrief {
    @JsonProperty("LeavePlanTypeId")
    int leavePlanTypeId;
    @JsonProperty("LeavePlanTypeName")
    String leavePlanTypeName;
    @JsonProperty("AvailableLeaves")
    int availableLeaves;
    @JsonProperty("TotalLeaveQuota")
    int totalLeaveQuota;
    @JsonProperty("IsCommentsRequired")
    boolean isCommentsRequired;
    @JsonProperty("IsFutureDateAllowed")
    boolean isFutureDateAllowed;
    @JsonProperty("IsHalfDay")
    boolean isHalfDay;
    @JsonProperty("AccruedSoFar")
    int accruedSoFar;

    public int getLeavePlanTypeId() {
        return leavePlanTypeId;
    }

    public void setLeavePlanTypeId(int leavePlanTypeId) {
        this.leavePlanTypeId = leavePlanTypeId;
    }

    public String getLeavePlanTypeName() {
        return leavePlanTypeName;
    }

    public void setLeavePlanTypeName(String leavePlanTypeName) {
        this.leavePlanTypeName = leavePlanTypeName;
    }

    public int getAvailableLeaves() {
        return availableLeaves;
    }

    public void setAvailableLeaves(int availableLeaves) {
        this.availableLeaves = availableLeaves;
    }

    public int getTotalLeaveQuota() {
        return totalLeaveQuota;
    }

    public void setTotalLeaveQuota(int totalLeaveQuota) {
        this.totalLeaveQuota = totalLeaveQuota;
    }

    public boolean isCommentsRequired() {
        return isCommentsRequired;
    }

    public void setCommentsRequired(boolean commentsRequired) {
        isCommentsRequired = commentsRequired;
    }

    public boolean isFutureDateAllowed() {
        return isFutureDateAllowed;
    }

    public void setFutureDateAllowed(boolean futureDateAllowed) {
        isFutureDateAllowed = futureDateAllowed;
    }

    public boolean isHalfDay() {
        return isHalfDay;
    }

    public void setHalfDay(boolean halfDay) {
        isHalfDay = halfDay;
    }

    public int getAccruedSoFar() {
        return accruedSoFar;
    }

    public void setAccruedSoFar(int accruedSoFar) {
        this.accruedSoFar = accruedSoFar;
    }
}
