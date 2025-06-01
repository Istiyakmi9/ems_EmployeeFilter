package com.bot.employeeFilter.service;

import com.bot.employeeFilter.db.service.DbManager;
import com.bot.employeeFilter.interfaces.IOrganizationTreeService;
import com.bot.employeeFilter.model.CurrentSession;
import com.bot.employeeFilter.model.DeleteOrgTreeNode;
import com.bot.employeeFilter.model.OrgHierarchyModel;
import com.bot.employeeFilter.repository.OrganizationRepository;
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
    CurrentSession currentSession;
    @Autowired
    OrganizationRepository organizationRepository;

    @Override
    public List<OrgHierarchyModel> getOrganizationHierarchyService(int companyId) throws Exception {
        String query = "select * from org_hierarchy where CompanyId = " + companyId;
        var result = dbManager.queryList(query, OrgHierarchyModel.class);
        return result.stream().sorted(Comparator.comparing(OrgHierarchyModel::getRoleId)).toList();
    }

    @Override
    public List<OrgHierarchyModel> addOrganizationHierarchyService(List<OrgHierarchyModel> orgHierarchies) throws Exception {
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
        } catch (Exception ex) {
            throw new Exception((ex.getMessage()));
        }

        return getOrganizationHierarchyService(currentSession.getCompanyId());
    }

    public List<OrgHierarchyModel> getOrgTreeByRoleService(int companyId, int roleId) throws Exception {
        String query = "select * from org_hierarchy where CompanyId = " + companyId;
        List<OrgHierarchyModel> records = dbManager.queryList(query, OrgHierarchyModel.class);

        var result = buildFilteredTree(records, new ArrayList<>(), roleId);
        return result.stream().sorted(Comparator.comparing(OrgHierarchyModel::getRoleId)).toList();
    }

    public List<OrgHierarchyModel> deleteOrganizationHierarchyService(DeleteOrgTreeNode deleteOrgTreeNode) throws Exception {
        try {
            List<OrgHierarchyModel> orgTree = new ArrayList<>(getOrganizationHierarchyService(currentSession.getCompanyId()));
            if (!orgTree.isEmpty() && orgTree.size() > 0) {
                if (deleteOrgTreeNode.isDeleteAllNode()) {
                    List<OrgHierarchyModel> removeNode = orgTree.stream().filter(x -> x.getRoleId() == deleteOrgTreeNode.getRoleId() ||
                                    x.getParentNode() == deleteOrgTreeNode.getRoleId())
                            .toList();

                    for (OrgHierarchyModel orgHierarchyModel : removeNode) {
                        organizationRepository.deleteOrganizationTreeRepository(orgHierarchyModel.getRoleId());
                    }
                } else {
                    var removeNode = orgTree.stream().filter(x -> x.getRoleId() == deleteOrgTreeNode.getRoleId()).findFirst()
                            .orElseThrow(() -> new Exception("Hierarchy node not found"));
                    organizationRepository.deleteOrganizationTreeRepository(removeNode.getRoleId());
                    
                    var childNodes = orgTree.stream().filter(x -> x.getParentNode() == deleteOrgTreeNode.getRoleId()).toList();
                    if (!childNodes.isEmpty() && childNodes.size() > 0) {
                        for (OrgHierarchyModel childNode : childNodes) {
                            childNode.setParentNode(deleteOrgTreeNode.getNewParentNode());
                        }
                        dbManager.saveAll(childNodes, OrgHierarchyModel.class);
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return getOrganizationHierarchyService(currentSession.getCompanyId());
    }

    private List<OrgHierarchyModel> buildFilteredTree(List<OrgHierarchyModel> itemTree, List<OrgHierarchyModel> newTree, int roleId) throws Exception {
        try {
            Optional<OrgHierarchyModel> item = itemTree.stream().filter(i -> i.getRoleId() == roleId).findFirst();
            if (item.isPresent()) {
                buildFilteredTree(itemTree, newTree, item.get().getParentNode());
                newTree.add(item.get());
            }

            return newTree;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

    }
}
