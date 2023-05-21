package com.bot.employeeFilter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity
@Data
@Table(name = "employees")
public class EmployeeBrief {
    @Id
    public long employeeUid;
    public String firstName;
    public String lastName;
    public String mobile;
    public String email;
    public long designationId;
    public int leavePlanId;
    public int salaryGroupId;
    public int companyId;
    public int workShiftId;
    public int projectId;
}
