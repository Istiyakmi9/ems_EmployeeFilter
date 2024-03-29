package com.bot.employeeFilter.db.service;

import com.bot.employeeFilter.db.utils.DatabaseConfiguration;
import com.bot.employeeFilter.db.utils.Template;
import com.bot.employeeFilter.db.utils.TypeConverter;
import com.bot.employeeFilter.db.utils.TypedParameter;
import com.bot.employeeFilter.model.DbParameters;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DbManager {
    @Autowired
    DbUtils dbUtils;
    @Autowired
    ObjectMapper mapper;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DbManager(DatabaseConfiguration databaseConfiguration) {
        Template template = new Template();
        jdbcTemplate = template.getTemplate(databaseConfiguration);
    }

    public <T> void save(T instance) throws Exception {
        String query = dbUtils.save(instance);
        jdbcTemplate.execute(query);
    }

    public <T> void saveAll(List<T> instance, Class<T> type) throws Exception {
        String query = dbUtils.saveAll(instance, type);
        jdbcTemplate.execute(query);
    }

    public <T> List<DbParameters> getProcedureParameters(List<T> instance, Class<T> type) throws Exception {
        return dbUtils.getProcedureParameters(instance, type);
    }

    public <T> List<TypedParameter> getParameters(T instance, Class<T> type) throws Exception {
        return dbUtils.getParameters(instance, type);
    }

    public <T> int nextIntPrimaryKey(Class<T> instance) throws Exception {
        int index = 0;
        String lastIndexQuery = dbUtils.lastPrimaryKey(instance);
        try {
            String lastIndex = jdbcTemplate.queryForObject(lastIndexQuery, String.class);
            if (lastIndex != null && !lastIndex.isEmpty()) {
                index = Integer.parseInt(lastIndex);
            }
        } catch (EmptyResultDataAccessException e) {
            index = 0;
        }

        return index + 1;
    }

    public <T> long nextLongPrimaryKey(Class<T> instance) throws Exception {
        long index = 0;
        String lastIndexQuery = dbUtils.lastPrimaryKey(instance);
        try {
            String lastIndex = jdbcTemplate.queryForObject(lastIndexQuery, String.class);
            if (lastIndex != null && !lastIndex.isEmpty()) {
                index = Integer.parseInt(lastIndex);
            }
        } catch (EmptyResultDataAccessException e) {
            index = 0;
        }

        return index + 1;
    }

    public <T> List<T> get(Class<T> type) throws Exception {
        String query = dbUtils.get(type);
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        return mapper.convertValue(result, new TypeReference<List<T>>() {
        });
    }

    public <T> List<T> getAllByIntKeys(List<Integer> keys, Class<T> type) throws Exception {
        String query = dbUtils.getAllByIntKeys(keys, type);
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        var converter = new TypeConverter<T>(type);
        return converter.convert(result);
    }

    public <T> List<T> getAllByStringKeys(List<String> keys, Class<T> type) throws Exception {
        String query = dbUtils.getAllByStringKeys(keys, type);
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        return mapper.convertValue(result, new TypeReference<List<T>>() {
        });
    }

    public <T> T getById(long id, Class<T> type) throws Exception {
        String query = dbUtils.getById(id, type);
        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(query);
            return mapper.convertValue(result, type);
        } catch (Exception e) {
            return null;
        }
    }

    public <T> T getById(int id, Class<T> type) throws Exception {
        String query = dbUtils.getById(id, type);
        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(query);
            return mapper.convertValue(result, type);
        } catch (Exception e) {
            return null;
        }
    }

    public <T> T queryRaw(String query, Class<T> type) {
        Map<String, Object> result = jdbcTemplate.queryForMap(query);
        return mapper.convertValue(result, type);
    }

    public <T> List<T> queryList(String query, Class<T> type) throws Exception {
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        var converter = new TypeConverter<T>(type);
        return converter.convert(result);
    }

    public void execute(String query) {
        jdbcTemplate.execute(query);
    }
}
