package com.bot.employeeFilter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "salary_group")
public class SalaryGroup {
    @Id
    int salaryGroupId;

    String salaryComponents;
    String groupName;
}
