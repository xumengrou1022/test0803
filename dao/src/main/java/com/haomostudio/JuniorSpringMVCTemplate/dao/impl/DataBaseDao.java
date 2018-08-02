package com.haomostudio.JuniorSpringMVCTemplate.dao.impl;

import java.util.List;
import java.util.Map;


public interface DataBaseDao {

    String START = "start";

    String LIMIT = "limit";

    String UPDATE = "UPDATE";

    String INSERT = "INSERT";

    String DELETE = "DELETE";

    int queryForInt(String sql, List<Object> params);

    boolean executeSQL(String sql);

    int update(String tableNamePrefix, String tableName, Map<String, Object> map);

    int insert(String tableNamePrefix, String tableName, Map<String, Object> map);

    int inserts(String tableNamePrefix, String tableName, List<Map<String, Object>> maps);

    int delete(String tableNamePrefix, String tableName, Map<String, Object> map);

    boolean executeSQL(String sql, List<Object> params);

    Map<String, Object> executeQuerySQL(String sql);

    List<Map<String, Object>> executeListSQL(String sql);

    void executeSingleSQL(String sql);

    List<Map<String, Object>> getTableData(String tableNamePrefix, String tableName, Map<String, Object> params);

    int getTableDataCount(String tableNamePrefix, String tableName, Map<String, Object> params);

    int getColumnNotNullCount(String tableNamePrefix, String tableName, String columnName);

    int getColumnNotZeroCount(String tableNamePrefix, String tableName, String columnName);

}
