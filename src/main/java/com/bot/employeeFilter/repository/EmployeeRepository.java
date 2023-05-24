package com.bot.employeeFilter.repository;

import com.bot.employeeFilter.entity.EmployeeBrief;
import com.bot.employeeFilter.entity.FilterModel;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Persistent;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Persistent
public class EmployeeRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final Logger _logger = LoggerFactory.getLogger(EmployeeRepository.class);

    public List<EmployeeBrief> getEmployeePage(FilterModel filterModel) {
        List<EmployeeBrief> employees = new ArrayList<>();

        try {
            StoredProcedureQuery query = this.entityManager.createNamedStoredProcedureQuery("get_Employee_ByFilter");

            query.setParameter("_SearchString", filterModel.serachString);
            query.setParameter("_SortBy", filterModel.sortBy);
            query.setParameter("_PageIndex", filterModel.pageIndex);
            query.setParameter("_PageSize", filterModel.pageSize);

            employees = (List<EmployeeBrief>) query.getResultList();
            this.entityManager.close();
        } catch (Exception ex) {
            _logger.error(ex.getMessage());
            throw ex;
        }

        return employees;
    }

    public List<EmployeeBrief> getEmployeeAndSalaryGroup(FilterModel filterModel) {
        List<EmployeeBrief> employees = new ArrayList<>();

        try {
            StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("sp_salary_group_and_components_get")
                            .registerStoredProcedureParameter("_CompanyId", Integer.class, ParameterMode.IN);

            query.setParameter("_CompanyId", 1);

            var resultList = query.getResultList();

            this.entityManager.close();
        } catch (Exception ex) {
            _logger.error(ex.getMessage());
            throw ex;
        }

        return employees;
    }
}
