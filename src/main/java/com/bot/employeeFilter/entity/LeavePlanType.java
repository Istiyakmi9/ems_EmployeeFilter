package com.bot.employeeFilter.entity;

import com.bot.employeeFilter.db.annotations.Column;
import com.bot.employeeFilter.db.annotations.Id;
import com.bot.employeeFilter.db.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "leave_plans_type")
public class LeavePlanType {
    @Id
    @Column(name = "LeavePlanTypeId")
    @JsonProperty("LeavePlanTypeId")
    Long LeavePlanTypeId;
    @Column(name = "LeavePlanCode")
    @JsonProperty("LeavePlanCode")
    String leavePlanCode;
    @Column(name = "PlanDescription")
    @JsonProperty("PlanDescription")
    String planDescription;
    @Column(name = "MaxLeaveLimit")
    @JsonProperty("MaxLeaveLimit")
    int maxLeaveLimit;
    @Column(name = "ShowDescription")
    @JsonProperty("ShowDescription")
    boolean showDescription;
    @Column(name = "IsPaidLeave")
    @JsonProperty("IsPaidLeave")
    boolean isPaidLeave;
    @Column(name = "IsSickLeave")
    @JsonProperty("IsSickLeave")
    boolean isSickLeave;

    @Column(name = "IsStatutoryLeave")
    @JsonProperty("IsStatutoryLeave")
    boolean isStatutoryLeave;
    @Column(name = "IsMale")
    @JsonProperty("IsMale")
    boolean isMale;
    @Column(name = "IsMarried")
    @JsonProperty("IsMarried")
    boolean isMarried;
    @Column(name = "IsRestrictOnGender")
    @JsonProperty("IsRestrictOnGender")
    boolean isRestrictOnGender;
    @Column(name = "IsRestrictOnMaritalStatus")
    @JsonProperty("IsRestrictOnMaritalStatus")
    boolean isRestrictOnMaritalStatus;
    @Column(name = "Reasons")
    @JsonProperty("Reasons")
    String reasons;
    @Column(name = "PlanConfigurationDetail")
    @JsonProperty("PlanConfigurationDetail")
    String planConfigurationDetail;

    public Long getLeavePlanTypeId() {
        return LeavePlanTypeId;
    }

    public void setLeavePlanTypeId(Long leavePlanTypeId) {
        LeavePlanTypeId = leavePlanTypeId;
    }

    public String getLeavePlanCode() {
        return leavePlanCode;
    }

    public void setLeavePlanCode(String leavePlanCode) {
        this.leavePlanCode = leavePlanCode;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public int getMaxLeaveLimit() {
        return maxLeaveLimit;
    }

    public void setMaxLeaveLimit(int maxLeaveLimit) {
        this.maxLeaveLimit = maxLeaveLimit;
    }

    public boolean isShowDescription() {
        return showDescription;
    }

    public void setShowDescription(boolean showDescription) {
        this.showDescription = showDescription;
    }

    public boolean isPaidLeave() {
        return isPaidLeave;
    }

    public void setPaidLeave(boolean paidLeave) {
        isPaidLeave = paidLeave;
    }

    public boolean isSickLeave() {
        return isSickLeave;
    }

    public void setSickLeave(boolean sickLeave) {
        isSickLeave = sickLeave;
    }

    public boolean isStatutoryLeave() {
        return isStatutoryLeave;
    }

    public void setStatutoryLeave(boolean statutoryLeave) {
        isStatutoryLeave = statutoryLeave;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    public boolean isRestrictOnGender() {
        return isRestrictOnGender;
    }

    public void setRestrictOnGender(boolean restrictOnGender) {
        isRestrictOnGender = restrictOnGender;
    }

    public boolean isRestrictOnMaritalStatus() {
        return isRestrictOnMaritalStatus;
    }

    public void setRestrictOnMaritalStatus(boolean restrictOnMaritalStatus) {
        isRestrictOnMaritalStatus = restrictOnMaritalStatus;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getPlanConfigurationDetail() {
        return planConfigurationDetail;
    }

    public void setPlanConfigurationDetail(String planConfigurationDetail) {
        this.planConfigurationDetail = planConfigurationDetail;
    }
}
