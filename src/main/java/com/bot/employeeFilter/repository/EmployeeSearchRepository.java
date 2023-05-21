package com.bot.employeeFilter.repository;

import com.bot.employeeFilter.entity.EmployeeBrief;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeSearchRepository extends JpaRepository<EmployeeBrief, Long> {
    @Query(value = "call SP_Employee_GetAll(:_SearchString, :_SortBy, :_PageIndex, :_PageSize)", nativeQuery = true)
    List<EmployeeBrief> employeeByPagination(@Param(""));
}
