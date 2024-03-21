package com.bot.employeeFilter.entity;

import com.bot.employeeFilter.db.annotations.Column;
import com.bot.employeeFilter.db.annotations.Id;
import com.bot.employeeFilter.db.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@Table(name = "hike_bonus_salary_adhoc")
public class HikeBonusSalaryAdhoc {
    @Id
    @Column(name = "SalaryAdhocId")
    @JsonProperty("SalaryAdhocId")
    long salaryAdhocId;
    @Column(name = "EmployeeId")
    @JsonProperty("EmployeeId")
    long employeeId;
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
    @Column(name = "Description")
    @JsonProperty("Description")
    String description;
    @Column(name = "Amount")
    @JsonProperty("Amount")
    double amount;
    @Column(name = "ApprovedBy")
    @JsonProperty("ApprovedBy")
    long approvedBy;
    @Column(name = "StartDate")
    @JsonProperty("StartDate")
    Date startDate;
    @Column(name = "EndDate")
    @JsonProperty("EndDate")
    Date endDate;
    @Column(name = "IsActive")
    @JsonProperty("IsActive")
    boolean isActive;
    @Column(name = "DOJ")
    @JsonProperty("DOJ")
    Date dOJ;
    @Column(name = "LWD")
    @JsonProperty("LWD")
    Date lWD;
    @Column(name = "DOR")
    @JsonProperty("DOR")
    Date dOR;
    @Column(name = "NoOfDays")
    @JsonProperty("NoOfDays")
    double noOfDays;
    @Column(name = "PaymentActionType")
    @JsonProperty("PaymentActionType")
    String paymentActionType;
    @Column(name = "Reason")
    @JsonProperty("Reason")
    String reason;
    @Column(name = "Comments")
    @JsonProperty("Comments")
    String comments;
    @Column(name = "Status")
    @JsonProperty("Status")
    int status;
    @Column(name = "ForYear")
    @JsonProperty("ForYear")
    int forYear;
    @Column(name = "ForMonth")
    @JsonProperty("ForMonth")
    int forMonth;
    @Column(name = "WorkedMinutes")
    @JsonProperty("WorkedMinutes")
    double workedMinutes;
}
