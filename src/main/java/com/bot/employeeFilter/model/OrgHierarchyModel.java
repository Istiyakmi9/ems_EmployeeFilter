package com.bot.employeeFilter.model;

import com.bot.employeeFilter.db.annotations.Column;
import com.bot.employeeFilter.db.annotations.Id;
import com.bot.employeeFilter.db.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@Table(name = "org_hierarchy")
public class OrgHierarchyModel {
    public OrgHierarchyModel() {
    }

    public OrgHierarchyModel(Integer node, Integer parentNode, String name, String email, String imageUrl, String companyId, boolean isActive) {
        this.node = node;
        this.parentNode = parentNode;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.companyId = companyId;
        this.isActive = isActive;
    }

    @JsonProperty("Node")
    @Column(name = "Node")
    @Id
    Integer node;
    @JsonProperty("ParentNode")
    @Column(name = "ParentNode")
    Integer parentNode;
    @JsonProperty("Name")
    @Column(name = "Name")
    String name;
    @JsonProperty("Email")
    @Column(name = "Email")
    String email;
    @JsonProperty("ImageUrl")
    @Column(name = "ImageUrl")
    String imageUrl;

    @JsonProperty("CompanyId")
    @Column(name = "CompanyId")
    String companyId;

    @JsonProperty("IsActive")
    @Column(name = "IsActive")
    boolean isActive;
}
