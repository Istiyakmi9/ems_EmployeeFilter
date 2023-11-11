package com.bot.employeeFilter.service;

import com.bot.employeeFilter.db.service.DbManager;
import com.bot.employeeFilter.db.utils.LowLevelExecution;
import com.bot.employeeFilter.entity.EmployeeBrief;
import com.bot.employeeFilter.model.FilterModel;
import com.bot.employeeFilter.interfaces.EmployeeSearchInterface;
import com.bot.employeeFilter.model.DbParameters;
import com.bot.employeeFilter.model.FilteredEmployee;
import com.bot.employeeFilter.model.OrgHierarchyModel;
import com.bot.employeeFilter.repository.EmployeeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.*;
import java.util.stream.Stream;

@Service
public class EmployeeSearchService implements EmployeeSearchInterface {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DbManager dbManager;
    @Autowired
    LowLevelExecution lowLevelExecution;
    @Autowired
    ObjectMapper mapper;

    public List<EmployeeBrief> getAllEmployee() throws Exception {
        return dbManager.get(EmployeeBrief.class);
    }

    public List<EmployeeBrief> employeePageRecrod(FilterModel filterModel) throws Exception {
        if (filterModel.getSearchString() == null || filterModel.getSearchString().isEmpty())
            filterModel.setSearchString("1=1");

        return employeeRepository.getEmployeePage(filterModel);
    }

    public String addOrganizationHierarchyService(List<OrgHierarchyModel> orgHierarchies) throws Exception {
        String status = "failed";
        try {

            List<Integer> nodeIds = orgHierarchies.stream()
                    .mapToInt(OrgHierarchyModel::getRoleId)
                    .boxed()
                    .toList();

            var result = dbManager.getAllByIntKeys(nodeIds, OrgHierarchyModel.class);
            List<OrgHierarchyModel> hierarchyData = new ArrayList<>();

            if (!result.isEmpty()) {
                for (var data : result) {
                    var item = orgHierarchies.stream()
                            .filter(x -> x.getRoleId().equals(data.getRoleId()))
                            .findFirst()
                            .orElse(data);

                    hierarchyData.add(item);
                }

                hierarchyData = Stream.concat(hierarchyData.stream(), (orgHierarchies.stream()
                        .filter(x -> result.stream()
                                .noneMatch(i -> i.getRoleId().equals(x.getRoleId())))
                        .toList()).stream()).toList();
            } else {
                hierarchyData = orgHierarchies;
            }

            dbManager.saveAll(hierarchyData, OrgHierarchyModel.class);
            status = "successfull";
        } catch (Exception ex) {
            status = "failed";
        }

        return status;
    }

    public List<OrgHierarchyModel> getOrganizationHierarchyService(int companyId) throws Exception {
        String query = "select * from org_hierarchy where CompanyId = " + companyId;
        return dbManager.queryList(query, OrgHierarchyModel.class);
    }

    public List<FilteredEmployee> employeeFilterByName(FilterModel filterModel) throws Exception {
        Map<String, Object> result = lowLevelExecution.executeProcedure("sp_employee_autocomplete_data", Arrays.asList(
           new DbParameters("_SearchString", filterModel.getSearchString(), Types.VARCHAR),
                new DbParameters("_PageIndex", filterModel.getPageIndex(), Types.INTEGER),
                new DbParameters("_PageSize", filterModel.getPageSize(), Types.INTEGER),
                new DbParameters("_CompanyId", filterModel.getCompanyId(), Types.INTEGER)
        ));

        return mapper.convertValue(result.get("#result-set-1"), new TypeReference<List<FilteredEmployee>>() {
        });
    }
}
