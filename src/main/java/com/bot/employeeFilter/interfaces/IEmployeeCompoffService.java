package com.bot.employeeFilter.interfaces;

import com.bot.employeeFilter.entity.EmployeeCompoff;
import com.bot.employeeFilter.model.FilterModel;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IEmployeeCompoffService {
    String addEmployeeCompoffService(EmployeeCompoff employeeCompoff) throws Exception;

    List<EmployeeCompoff> approveCompoffService(long employeeCompoffOvertimeId, int status) throws Exception;

    List<EmployeeCompoff> rejectCompoffService(long employeeCompoffOvertimeId, int status) throws Exception;

    List<EmployeeCompoff> getEmployeeCompoffFilterService(@RequestBody FilterModel filterModel) throws Exception;
}
