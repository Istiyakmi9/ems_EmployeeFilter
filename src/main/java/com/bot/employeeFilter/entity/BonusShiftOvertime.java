package com.bot.employeeFilter.entity;

import com.bot.employeeFilter.db.annotations.Column;
import com.bot.employeeFilter.db.annotations.Id;
import com.bot.employeeFilter.db.annotations.Table;
import com.bot.employeeFilter.db.annotations.Transient;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Table(name = "bonus_shift_overtime")
@Data
public class BonusShiftOvertime {
    @Id
    @Column(name = "BonusShiftOvertimeId")
    @JsonProperty("BonusShiftOvertimeId")
    private Long bonusShiftOvertimeId;

    @Column(name = "EmployeeId")
    @JsonProperty("EmployeeId")
    private Long employeeId;

    @Column(name = "IsBonus")
    @JsonProperty("IsBonus")
    private boolean isBonus;

    @Column(name = "IsShift")
    @JsonProperty("IsShift")
    private boolean isShift;

    @Column(name = "IsOvertime")
    @JsonProperty("IsOvertime")
    private boolean isOvertime;

    @Column(name = "ForYear")
    @JsonProperty("ForYear")
    private int forYear;

    @Column(name = "ForMonth")
    @JsonProperty("ForMonth")
    private int forMonth;

    @Column(name = "CompanyId")
    @JsonProperty("CompanyId")
    private int companyId;

    @Column(name = "OrganizationId")
    @JsonProperty("OrganizationId")
    private int organizationId;

    @Column(name = "ComponentId")
    @JsonProperty("ComponentId")
    private String componentId;

    @Column(name = "Amount")
    @JsonProperty("Amount")
    private double amount;

    @Column(name = "Comments")
    @JsonProperty("Comments")
    private String comments;

    @Column(name = "Status")
    @JsonProperty("Status")
    private int status;

    @Column(name = "TotalMinutes")
    @JsonProperty("TotalMinutes")
    private int totalMinutes;


    @Column(name = "UpdatedBy")
    @JsonProperty("UpdatedBy")
    private Long updatedBy;

    @Column(name = "UpdatedOn")
    @JsonProperty("UpdatedOn")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date updatedOn;

    @Transient
    @JsonProperty("FirstName")
    private String firstName;

    @Transient
    @JsonProperty("LastName")
    private String lastName;

    @Transient
    @JsonProperty("ComponentFullName")
    private String componentFullName;

    @Transient
    @JsonProperty("PaymentActionType")
    private String paymentActionType;

    @Transient
    @JsonProperty("CompleteSalaryDetail")
    private String completeSalaryDetail;

    @Transient
    @JsonProperty("PayCalculationId")
    private int payCalculationId;

    @Transient
    @JsonProperty("OTCalculatedOn")
    private String oTCalculatedOn;

    @Transient
    @JsonProperty("IsCompOff")
    private boolean isCompOff;

    @Transient
    @JsonProperty("SalaryAdhocId")
    private long salaryAdhocId;

    @Transient
    @JsonProperty("Total")
    private int total;

    @Transient
    @JsonProperty("RowIndex")
    private int rowIndex;
}
