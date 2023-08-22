package com.bot.employeeFilter.entity;

import lombok.Data;
import com.bot.employeeFilter.db.annotations.Column;
import com.bot.employeeFilter.db.annotations.Id;
import com.bot.employeeFilter.db.annotations.Table;

@Data
@Table(name = "attendance")
public class Attendance {
    @Id
    @Column(name = "AttendanceId")
    Long attendanceId;
    @Column(name = "EmployeeId")
    Long employeeId;
    @Column(name = "AttendanceDetail")
    String attendanceDetail;
    @Column(name = "TotalDays")
    int totalDays;
    @Column(name = "DaysPending")
    int daysPending;
    @Column(name = "ForYear")
    int forYear;
    @Column(name = "ForMonth")
    int forMonth;
    @Column(name = "EmployeeName")
    String employeeName;
}
