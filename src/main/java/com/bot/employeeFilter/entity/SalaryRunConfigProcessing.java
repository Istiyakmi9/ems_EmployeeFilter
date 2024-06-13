package com.bot.employeeFilter.entity;

import com.bot.employeeFilter.db.annotations.Column;
import com.bot.employeeFilter.db.annotations.Id;
import com.bot.employeeFilter.db.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@Table(name = "salary_run_config_processing")
public class SalaryRunConfigProcessing {
    @Id
    @JsonProperty("SalaryRunConfigProcessingId")
    @Column(name = "SalaryRunConfigProcessingId")
    int salaryRunConfigProcessingId;

    @JsonProperty("ForMonth")
    @Column(name = "ForMonth")
    int forMonth;

    @JsonProperty("ForYear")
    @Column(name = "ForYear")
    int forYear;

    @JsonProperty("ProcessingStatus")
    @Column(name = "ProcessingStatus")
    int processingStatus;

    @JsonProperty("CompanyId")
    @Column(name = "CompanyId")
    int companyId;
}
