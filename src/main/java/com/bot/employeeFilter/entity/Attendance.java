package com.bot.employeeFilter.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
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
