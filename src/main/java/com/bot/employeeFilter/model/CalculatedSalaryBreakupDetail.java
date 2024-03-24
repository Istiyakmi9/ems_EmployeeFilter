package com.bot.employeeFilter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CalculatedSalaryBreakupDetail {
    @JsonProperty("ComponentId")
    private String componentId;

    @JsonProperty("ComponentName")
    private String componentName;

    @JsonProperty("Formula")
    private String formula;

    @JsonProperty("FinalAmount")
    private double finalAmount;

    @JsonProperty("ComponentTypeId")
    private BigDecimal componentTypeId;

    @JsonProperty("IsIncludeInPayslip")
    private boolean isIncludeInPayslip;
}