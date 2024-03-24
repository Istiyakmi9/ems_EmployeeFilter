package com.bot.employeeFilter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AnnualSalaryBreakup {
    @JsonProperty("MonthName")
    private String monthName;

    @JsonProperty("IsPayrollExecutedForThisMonth")
    private boolean isPayrollExecutedForThisMonth;

    @JsonProperty("MonthNumber")
    private int monthNumber;

    @JsonProperty("IsArrearMonth")
    private boolean isArrearMonth;

    @JsonProperty("IsActive")
    private boolean isActive;

    @JsonProperty("IsPreviouEmployer")
    private boolean isPreviouEmployer;

    @JsonProperty("SalaryBreakupDetails")
    private List<CalculatedSalaryBreakupDetail> salaryBreakupDetails;
}
