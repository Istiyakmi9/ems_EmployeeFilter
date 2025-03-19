package com.bot.employeeFilter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteOrgTreeNode {
    @JsonProperty("RoleId")
    int roleId;

    @JsonProperty("IsDeleteAllNode")
    boolean isDeleteAllNode;

    @JsonProperty("NewParentNode")
    int newParentNode;
}
