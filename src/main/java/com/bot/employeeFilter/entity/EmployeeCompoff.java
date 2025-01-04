package com.bot.employeeFilter.entity;

import com.bot.employeeFilter.db.annotations.Column;
import com.bot.employeeFilter.db.annotations.Id;
import com.bot.employeeFilter.db.annotations.Table;
import com.bot.employeeFilter.db.annotations.Transient;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee_compoff_and_overtime")
public class EmployeeCompoff {
    @Id
    @Column(name ="EmployeeCompoffOvertimeId")
    @JsonProperty("EmployeeCompoffOvertimeId")
    long employeeCompoffOvertimeId;

    @Column(name ="EmployeeId")
    @JsonProperty("EmployeeId")
    long employeeId;

    @Column(name ="ReportingManagerId")
    @JsonProperty("ReportingManagerId")
    long reportingManagerId;

    @Column(name ="ProjectId")
    @JsonProperty("ProjectId")
    long projectId;

    @Column(name ="FromDateTime")
    @JsonProperty("FromDateTime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    Date fromDateTime;

    @Column(name ="ToDateTime")
    @JsonProperty("ToDateTime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    Date toDateTime;

    @Column(name ="Duration")
    @JsonProperty("Duration")
    int duration;

    @Column(name ="ProjectName")
    @JsonProperty("ProjectName")
    String projectName;

    @Column(name ="Status")
    @JsonProperty("Status")
    int status;

    @Column(name ="Comment")
    @JsonProperty("Comment")
    String comment;

    @Column(name = "UpdatedBy")
    @JsonProperty("UpdatedBy")
    Long updatedBy;

    @Column(name = "UpdatedOn")
    @JsonProperty("UpdatedOn")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    Date updatedOn;

    @Column(name = "CreatedBy")
    @JsonProperty("CreatedBy")
    Long createdBy;

    @Column(name = "CreatedOn")
    @JsonProperty("CreatedOn")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    Date createdOn;

    @Transient
    @JsonProperty("Total")
    int total;

    @Transient
    @JsonProperty("FirstName")
    String firstName;

    @Transient
    @JsonProperty("LastName")
    String lastName;
}
