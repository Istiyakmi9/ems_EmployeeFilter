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

    public OrgHierarchyModel(Integer roleId, Integer parentNode, String roleName, String email, String imageUrl, String companyId, Boolean isActive, Boolean isDepartment) {
        this.roleId = roleId;
        this.parentNode = parentNode;
        this.roleName = roleName;
        this.email = email;
        this.imageUrl = imageUrl;
        this.companyId = companyId;
        this.isActive = isActive;
        this.isDepartment = isDepartment;
    }

    @JsonProperty("RoleId")
    @Column(name = "RoleId")
    @Id
    Integer roleId;
    @JsonProperty("ParentNode")
    @Column(name = "ParentNode")
    Integer parentNode;
    @JsonProperty("RoleName")
    @Column(name = "RoleName")
    String roleName;
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
    Boolean isActive;

    @JsonProperty("IsDepartment")
    @Column(name = "IsDepartment")
    Boolean isDepartment;
}
