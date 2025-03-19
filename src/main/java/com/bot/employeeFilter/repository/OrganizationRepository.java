package com.bot.employeeFilter.repository;

import com.bot.employeeFilter.db.utils.LowLevelExecution;
import com.bot.employeeFilter.model.DbParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrganizationRepository {
    @Autowired
    LowLevelExecution lowLevelExecution;

    public void deleteOrganizationTreeRepository(int roleId) throws Exception {
        List<DbParameters> dbParameters = new ArrayList<>();
        dbParameters.add(new DbParameters("_RoleId", roleId, Types.INTEGER));

        lowLevelExecution.executeProcedure("sp_org_hierarchy_delete_by_roleId", dbParameters);
    }
}