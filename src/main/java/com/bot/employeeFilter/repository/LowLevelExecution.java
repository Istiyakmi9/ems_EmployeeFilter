package com.bot.employeeFilter.repository;

import com.bot.employeeFilter.model.DbParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class LowLevelExecution {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public <T> Map<String, Object> executeProcedure(String procedureName, List<DbParameters> sqlParams) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName(procedureName);

        Map<String, Object> paramSet = new HashMap<>();
        for (DbParameters dbParameters : sqlParams) {
            paramSet.put(dbParameters.parameter, dbParameters.value);
            simpleJdbcCall.addDeclaredParameter(
                    new SqlParameter(
                            dbParameters.parameter,
                            dbParameters.type
                    ));
        }

        return simpleJdbcCall.execute(paramSet);
    }
}
