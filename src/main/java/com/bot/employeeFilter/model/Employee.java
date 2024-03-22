package com.bot.employeeFilter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Employee {
    @JsonProperty("EmployeeId")
    long employeeId;
    @JsonProperty("FirstName")
    String firstName;
    @JsonProperty("LastName")
    String lastName;
    @JsonProperty("Mobile")
    String mobile;
    @JsonProperty("Email")
    String email;
    @JsonProperty("CreatedOn")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    Date createdOn;
    @JsonProperty("DOL")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    Date dOL;
    @JsonProperty("CTC")
    double cTC;
    @JsonProperty("InDays")
    int inDays;
    @JsonProperty("IsServingNotice")
    boolean isServingNotice;
    @JsonProperty("InProbation")
    boolean inProbation;
    @JsonProperty("ResignationStatus")
    int resignationStatus;
    @JsonProperty("Reason")
    String reason;
    @JsonProperty("PaymentActionType")
    String paymentActionType;
    @JsonProperty("Comments")
    String comments;
    @JsonProperty("SalaryAdhocId")
    long salaryAdhocId;
}
