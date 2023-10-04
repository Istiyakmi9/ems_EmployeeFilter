package com.bot.employeeFilter.entity;

import com.bot.employeeFilter.db.annotations.Column;
import com.bot.employeeFilter.db.annotations.Id;
import com.bot.employeeFilter.db.annotations.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@Table(name = "attendance")
public class Attendance {
    @Id
    @Column(name = "AttendanceId")
    @JsonProperty("AttendanceId")
    Long attendanceId;
    @Column(name = "EmployeeId")
    @JsonProperty("EmployeeId")
    Long employeeId;
    @Column(name = "AttendanceDetail")
    @JsonProperty("AttendanceDetail")
    String attendanceDetail;
    @Column(name = "TotalDays")
    @JsonProperty("TotalDays")
    int totalDays;
    @Column(name = "DaysPending")
    @JsonProperty("DaysPending")
    int daysPending;
    @Column(name = "ForYear")
    @JsonProperty("ForYear")
    int forYear;
    @Column(name = "ForMonth")
    @JsonProperty("ForMonth")
    int forMonth;
    @Column(name = "EmployeeName")
    @JsonProperty("EmployeeName")
    String employeeName;
    @JsonProperty("Total")
    String total;
    @JsonProperty("AttendanceDay")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    Date attendanceDay;
    @JsonProperty("IsOnLeave")
    boolean isOnLeave;

}
