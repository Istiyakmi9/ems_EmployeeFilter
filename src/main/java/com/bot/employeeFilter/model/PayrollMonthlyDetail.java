package com.bot.employeeFilter.model;

import com.bot.employeeFilter.db.annotations.Table;
import com.bot.employeeFilter.db.annotations.Transient;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Table(name = "payroll_monthly_detail")
@Data
public class PayrollMonthlyDetail {
    @JsonProperty(value = "PayrollMonthlyDetailId")
    int payrollMonthlyDetailId;
    @JsonProperty(value = "EmployeeCount")
    int EmployeeCount;
    @JsonProperty(value = "ForYear")
    int forYear;
    @JsonProperty(value = "ForMonth")
    int forMonth;
    @JsonProperty(value = "TotalPayableToEmployees")
    double totalPayableToEmployees;
    @JsonProperty(value = "TotalPFByEmployer")
    double totalPFByEmployer;
    @JsonProperty(value = "TotalDeduction")
    @Transient
    double totalDeduction;
    @JsonProperty(value = "TotalPFByEmployee")
    double totalPFByEmployee;
    @JsonProperty(value = "TotalProfessionalTax")
    double totalProfessionalTax;
    @JsonProperty(value = "PayrollStatus")
    int payrollStatus;
    @JsonProperty(value = "Reason")
    String reason;
    @JsonProperty(value = "PaymentRunDate")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    Date paymentRunDate;
    @JsonProperty(value = "ProofOfDocumentPath")
    String proofOfDocumentPath;
    @JsonProperty(value = "ExecutedBy")
    long executedBy;
    @JsonProperty(value = "ExecutedOn")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    Date executedOn;
    @JsonProperty(value = "CompanyId")
    int companyId;
    @JsonProperty(value = "TotalEmployees")
    @Transient
    int totalEmployees;
    @JsonProperty(value = "ProcessOn")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Transient
    Date processOn;
}
