package com.bot.employeeFilter.service;

import com.bot.employeeFilter.controller.OrganizationTreeController;
import com.bot.employeeFilter.db.service.DbManager;
import com.bot.employeeFilter.db.utils.LowLevelExecution;
import com.bot.employeeFilter.interfaces.IOrganizationTreeService;
import com.bot.employeeFilter.model.OrgHierarchyModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrganizationTreeService implements IOrganizationTreeService {
    @Autowired
    DbManager dbManager;
    @Autowired
    LowLevelExecution lowLevelExecution;
    @Autowired
    ObjectMapper mapper;

    @Override
    public List<OrgHierarchyModel> getOrganizationHierarchyService(int companyId) throws Exception {
        String query = "select * from org_hierarchy where CompanyId = " + companyId;
        var result = dbManager.queryList(query, OrgHierarchyModel.class);
        return result.stream().sorted(Comparator.comparing(OrgHierarchyModel::getRoleId)).toList();
    }

    @Override
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

    public List<OrgHierarchyModel> getOrgTreeByRoleService(int companyId, int roleId) throws Exception {
        String query = "select * from org_hierarchy where CompanyId = " + companyId;
        List<OrgHierarchyModel> records = dbManager.queryList(query, OrgHierarchyModel.class);

        var result = buildFilteredTree(records, new ArrayList<>(), roleId);
        return result.stream().sorted(Comparator.comparing(OrgHierarchyModel::getRoleId)).toList();
    }

    private List<OrgHierarchyModel> buildFilteredTree(List<OrgHierarchyModel> itemTree, List<OrgHierarchyModel> newTree, int roleId) throws Exception {
        Optional<OrgHierarchyModel> item = itemTree.stream().filter(i -> i.getRoleId() == roleId).findFirst();
        if (item.isPresent()) {
            buildFilteredTree(itemTree, newTree, item.get().getParentNode());
            newTree.add(item.get());
        }

        return newTree;
    }
}
