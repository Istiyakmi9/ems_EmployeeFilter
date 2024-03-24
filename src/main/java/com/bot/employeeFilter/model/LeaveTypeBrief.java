package com.bot.employeeFilter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LeaveTypeBrief {
    @JsonProperty("LeavePlanTypeId")
    int leavePlanTypeId;

    @JsonProperty("LeavePlanTypeName")
    String leavePlanTypeName;

    @JsonProperty("AvailableLeaves")
    double availableLeaves;

    @JsonProperty("TotalLeaveQuota")
    double totalLeaveQuota;

    @JsonProperty("IsCommentsRequired")
    boolean isCommentsRequired;

    @JsonProperty("IsFutureDateAllowed")
    boolean isFutureDateAllowed;

    @JsonProperty("IsHalfDay")
    boolean isHalfDay;

    @JsonProperty("AccruedSoFar")
    double accruedSoFar;
}
