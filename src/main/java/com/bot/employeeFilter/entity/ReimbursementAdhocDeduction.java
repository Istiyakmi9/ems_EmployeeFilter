package com.bot.employeeFilter.entity;

import com.bot.employeeFilter.db.annotations.Column;
import com.bot.employeeFilter.db.annotations.Id;
import com.bot.employeeFilter.db.annotations.Table;
import com.bot.employeeFilter.db.annotations.Transient;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@Table(name = "reimburs_adhoc_and_deduction")
public class ReimbursementAdhocDeduction {
    @Id
    @Column( name = "ReimbursAdhocAndDeduction")
    @JsonProperty("ReimbursAdhocAndDeduction")
    long reimbursAdhocAndDeduction;

    @Column( name = "EmployeeId")
    @JsonProperty("EmployeeId")
    long employeeId;
    @Column( name = "IsReimburs")
    @JsonProperty("IsReimburs")
    boolean IsReimburs;

    @Column( name = "IsAdhoc")
    @JsonProperty("IsAdhoc")
    boolean IsAdhoc;

    @Column( name = "IsDeduction")
    @JsonProperty("IsDeduction")
    boolean IsDeduction;

    @Column( name = "ForYear")
    @JsonProperty("ForYear")
    int ForYear;

    @Column( name = "ForMonth")
    @JsonProperty("ForMonth")
    int ForMonth;

    @Column( name = "CompanyId")
    @JsonProperty("CompanyId")
    int CompanyId;

    @Column( name = "OrganizationId")
    @JsonProperty("OrganizationId")
    int OrganizationId;

    @Column( name = "ComponentId")
    @JsonProperty("ComponentId")
    String ComponentId;

    @Column( name = "Amount")
    @JsonProperty("Amount")
    double Amount;

    @Column( name = "Comments")
    @JsonProperty("Comments")
    String Comments;

    @Column( name = "Status")
    @JsonProperty("Status")
    int Status;

    @Column( name = "Reason")
    @JsonProperty("Reason")
    String Reason;

    @Column( name = "UpdatedBy")
    @JsonProperty("UpdatedBy")
    long UpdatedBy;

    @Column( name = "UpdatedOn")
    @JsonProperty("UpdatedOn")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    Date UpdatedOn;

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
    @JsonProperty("SalaryAdhocId")
    private long salaryAdhocId;

    @Transient
    @JsonProperty("ClaimAmount")
    private double claimAmount;

    @Transient
    @JsonProperty("PaymentActionType")
    private String paymentActionType;

    @Transient
    @JsonProperty("ComponentType")
    private String componentType;
}
