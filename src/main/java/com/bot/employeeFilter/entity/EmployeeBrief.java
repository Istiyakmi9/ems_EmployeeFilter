package com.bot.employeeFilter.entity;

import com.bot.employeeFilter.db.annotations.Column;
import com.bot.employeeFilter.db.annotations.Id;
import com.bot.employeeFilter.db.annotations.Table;
import lombok.Data;

@Data
@Table(name = "employees")
//@NamedStoredProcedureQueries({
//        @NamedStoredProcedureQuery(
//                name = "get_Employee_ByFilter",
//                procedureName = "SP_Employee_GetAll",
//                resultClasses = { EmployeeBrief.class },
//                parameters = {
//                        @StoredProcedureParameter(name = "_SearchString", type = String.class, mode = ParameterMode.IN),
//                        @StoredProcedureParameter(name = "_SortBy", type = String.class, mode = ParameterMode.IN),
//                        @StoredProcedureParameter(name = "_PageIndex", type = Integer.class, mode = ParameterMode.IN),
//                        @StoredProcedureParameter(name = "_PageSize", type = Integer.class, mode = ParameterMode.IN)
//                }
//        )
//})
public class EmployeeBrief {
    @Id
    public long employeeUid;
    public String firstName;
    public String lastName;
    public String mobile;
    public String email;
    public long designationId;
    public int leavePlanId;
    public int companyId;
    public int workShiftId;
    @Column(name = "ProjectId")
    public int projectId;
}
