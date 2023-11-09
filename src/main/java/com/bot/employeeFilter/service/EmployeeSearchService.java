package com.bot.employeeFilter.service;

import com.bot.employeeFilter.db.service.DbManager;
import com.bot.employeeFilter.entity.EmployeeBrief;
import com.bot.employeeFilter.entity.FilterModel;
import com.bot.employeeFilter.interfaces.EmployeeSearchInterface;
import com.bot.employeeFilter.model.OrgHierarchyModel;
import com.bot.employeeFilter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

            var result = dbManager.getAllByIntKeys(nodeIds, OrgHierarchyModel.class);
            List<OrgHierarchyModel> hierarchyData = new ArrayList<>();

            if (!result.isEmpty()) {
                for (var data : result) {
                    var item = orgHierarchies.stream()
                            .filter(x -> x.getNode().equals(data.getNode()))
                            .findFirst()
                            .orElse(data);

                    hierarchyData.add(item);
                }

                hierarchyData = Stream.concat(hierarchyData.stream(), (orgHierarchies.stream()
                        .filter(x -> result.stream()
                                .noneMatch(i -> i.getNode().equals(x.getNode())))
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
}
