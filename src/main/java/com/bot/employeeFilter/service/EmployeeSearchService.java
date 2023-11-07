package com.bot.employeeFilter.service;

import com.bot.employeeFilter.db.service.DbManager;
import com.bot.employeeFilter.entity.EmployeeBrief;
import com.bot.employeeFilter.entity.FilterModel;
import com.bot.employeeFilter.interfaces.EmployeeSearchInterface;
import com.bot.employeeFilter.model.OrgHierarchyModel;
import com.bot.employeeFilter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmployeeSearchService implements EmployeeSearchInterface {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DbManager dbManager;

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
                    .mapToInt(OrgHierarchyModel::getNode)
                    .boxed()
                    .toList();

            var hierarchyData = dbManager.getAllByIntKeys(nodeIds, OrgHierarchyModel.class);
            if (!hierarchyData.isEmpty()) {
                hierarchyData = hierarchyData.stream()
                        .map(i -> orgHierarchies.stream()
                                .filter(x -> Objects.equals(x.getNode(), i.getNode()))
                                .findFirst()
                                .orElse(i)
                        ).toList();
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
}
