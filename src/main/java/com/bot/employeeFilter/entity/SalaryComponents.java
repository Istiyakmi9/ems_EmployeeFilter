package com.bot.employeeFilter.entity;

import com.bot.employeeFilter.db.annotations.Id;
import com.bot.employeeFilter.db.annotations.Table;
import lombok.Data;

@Data
@Table(name = "salary_components")
//@NamedStoredProcedureQueries({
//        @NamedStoredProcedureQuery(
//                name = "get_Employee_And_SalaryGroup",
//                procedureName = "sp_salary_group_and_components_get",
//                resultClasses = { SalaryGroup.class, SalaryComponents.class },
//                parameters = {
//                        @StoredProcedureParameter(name = "_CompanyId", type = Integer.class, mode = ParameterMode.IN)
//                }
//        )
//})
public class SalaryComponents {
    @Id
    String componentId;

    String componentFullName;
}
