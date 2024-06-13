package com.bot.employeeFilter.entity;

import com.bot.employeeFilter.db.annotations.Column;
import com.bot.employeeFilter.db.annotations.Id;
import com.bot.employeeFilter.db.annotations.Table;
import com.bot.employeeFilter.db.annotations.Transient;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Table(name = "hike_bonus_salary_adhoc")
@Data
public class HikeBonusSalaryAdhoc {
    @Id
    @Column(name = "SalaryAdhocId")
    @JsonProperty("SalaryAdhocId")
    long salaryAdhocId;

    @Column(name = "SalaryRunConfigProcessingId")
    @JsonProperty("SalaryRunConfigProcessingId")
    int salaryRunConfigProcessingId;

    @Column(name = "EmployeeId")
    @JsonProperty("EmployeeId")
    long employeeId;

    @Column(name = "ProcessStepId")
    @JsonProperty("ProcessStepId")
    int processStepId;

    @Column(name = "FinancialYear")
    @JsonProperty("FinancialYear")
    int financialYear;

    @Column(name = "OrganizationId")
    @JsonProperty("OrganizationId")
    int organizationId;

    @Column(name = "CompanyId")
    @JsonProperty("CompanyId")
    int companyId;

    @Column(name = "IsPaidByCompany")
    @JsonProperty("IsPaidByCompany")
    boolean isPaidByCompany;

    @Column(name = "IsPaidByEmployee")
    @JsonProperty("IsPaidByEmployee")
    boolean isPaidByEmployee;

    @Column(name = "IsFine")
    @JsonProperty("IsFine")
    boolean isFine;

    @Column(name = "IsHikeInSalary")
    @JsonProperty("IsHikeInSalary")
    boolean isHikeInSalary;

    @Column(name = "IsBonus")
    @JsonProperty("IsBonus")
    boolean isBonus;

    @Column(name = "IsReimbursment")
    @JsonProperty("IsReimbursment")
    boolean isReimbursment;

    @Column(name = "IsSalaryOnHold")
    @JsonProperty("IsSalaryOnHold")
    boolean isSalaryOnHold;

    @Column(name = "IsArrear")
    @JsonProperty("IsArrear")
    boolean isArrear;

    @Column(name = "Amount")
    @JsonProperty("Amount")
    double amount;

    @Column(name = "IsActive")
    @JsonProperty("IsActive")
    boolean isActive;

    @Column(name = "PaymentActionType")
    @JsonProperty("PaymentActionType")
    String paymentActionType;

    @Column(name = "Comments")
    @JsonProperty("Comments")
    String comments;

    @Column(name = "ForYear")
    @JsonProperty("ForYear")
    int forYear;

    @Column(name = "ForMonth")
    @JsonProperty("ForMonth")
    int forMonth;

    @Column(name = "Status")
    @JsonProperty("Status")
    int status;

    @Column(name = "IsOvertime")
    @JsonProperty("IsOvertime")
    boolean isOvertime;

    @Column(name = "AmountInPercentage")
    @JsonProperty("AmountInPercentage")
    double amountInPercentage;

    @Column(name = "ProgressState")
    @JsonProperty("ProgressState")
    double progressState;

    @Column(name = "IsCompOff")
    @JsonProperty("IsCompOff")
    boolean isCompOff;

    @Column(name = "OTCalculatedOn")
    @JsonProperty("OTCalculatedOn")
    String oTCalculatedOn;

    @JsonProperty("FullName")
    @Transient
    String fullName;
}
