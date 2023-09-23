package com.bot.employeeFilter.model;

import com.bot.employeeFilter.db.annotations.Table;
import lombok.Data;

import java.util.Date;

@Table(name = "payroll_monthly_detail")
@Data
public class PayrollMonthlyDetail {
    int PayrollMonthlyDetailId;
    int ForYear;
    int ForMonth;
    double TotalPayableToEmployees;
    double TotalPFByEmployer;
    double TotalProfessionalTax;
    int PayrollStatus;
    String Reason;
    Date PaymentRunDate;
    String ProofOfDocumentPath;
    long ExecutedBy;
    Date ExecutedOn;
    int CompanyId;
    int TotalEmployees;
}
