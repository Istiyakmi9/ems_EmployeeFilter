package com.bot.employeeFilter.entity;

import lombok.Data;
import com.bot.employeeFilter.db.annotations.Id;
import com.bot.employeeFilter.db.annotations.Table;
@Data
@Table(name = "salary_group")
public class SalaryGroup {
    @Id
    int salaryGroupId;

    String salaryComponents;
    String groupName;
}
