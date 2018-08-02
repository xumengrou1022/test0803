package com.haomostudio.JuniorSpringMVCTemplate.dao.impl;


import com.haomostudio.JuniorSpringMVCTemplate.dao.common.Constants;
import com.haomostudio.JuniorSpringMVCTemplate.dao.common.util.DataBaseOperationUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository(value = "databaseDao")
public class OracleDataBaseDaoImpl implements DataBaseDao {

    public static final String PAGINATION_PREFIX = "select * from (select row_.*, rownum rownum_ from(";

    public static final String PAGINATION_SUFFIX = ")row_ where rownum <= ?) where rownum_ > ?";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataSource dataSource;

    public List<Map<String, Object>> queryForList(String sql, List<Object> params) {
        try {
            List e = params.size() > 0 ? this.getJdbcTemplate().queryForList(sql, params.toArray()) :
                    this.getJdbcTemplate().queryForList(sql);
            return e;
        } catch (Exception var4) {
            var4.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public int queryForInt(String sql, List<Object> params) {
        try {
            if (null == params) {
                params = new ArrayList<>();
            }
            logger.debug("executeSQL:{}\r\nparams:{}", sql, params);
            return params.size() > 0 ? this.getJdbcTemplate().queryForObject(sql, params.toArray(),Integer.class) :
                    this.getJdbcTemplate().queryForObject(sql,Integer.class);
        } catch (Exception var4) {
            var4.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean executeSQL(String sql) {
        logger.debug("executeSQL:{}", sql);
        String[] sqlList = sql.split(";");
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate();
        for (String s : sqlList) {
            jdbcTemplate.execute(s);
        }
        return true;
    }

    @Override
    public int update(String tableNamePrefix, String tableName, Map<String, Object> map) {
        try {
            //保证ID是最后一个字段
            String id = (String) map.get(Constants.COLUMN_ID);
            map.remove(Constants.COLUMN_ID);
            map.put(Constants.COLUMN_ID, id);
            String sql = "UPDATE " + DataBaseOperationUtils.getOracleTableName(tableNamePrefix, tableName) + " SET "
                    + DataBaseOperationUtils.GetNames(map, UPDATE, DataBaseOperationUtils.DB_TYPE_ORACLE);
            logger.debug("update sql:{}\r\nparams:{}", sql, map);
            int row = this.getJdbcTemplate().update(sql, DataBaseOperationUtils.GetObjectArgs(map, DataBaseOperationUtils.DB_TYPE_ORACLE));
            return row;
        } catch (Exception var4) {
            var4.printStackTrace();
            throw var4;
        }
    }


    public int insert(String tableNamePrefix, String tableName, Map<String, Object> map) {
        try {
            String sql = "INSERT INTO " + DataBaseOperationUtils.getOracleTableName(tableNamePrefix, tableName) + " "
                    + DataBaseOperationUtils.GetNames(map, INSERT, DataBaseOperationUtils.DB_TYPE_ORACLE);
            logger.debug("insert sql:{}\r\nparams:{}", sql, map);
            int ID = this.getJdbcTemplate().update(sql, DataBaseOperationUtils.GetObjectArgs(map, DataBaseOperationUtils.DB_TYPE_ORACLE));
            return ID;
        } catch (Exception var4) {
            var4.printStackTrace();
            throw var4;
        }
    }

    public int inserts(String tableNamePrefix, String tableName, List<Map<String, Object>> maps) {
        try {
            String sql = "INSERT INTO " + DataBaseOperationUtils.getOracleTableName(tableNamePrefix, tableName) + " "
                    + DataBaseOperationUtils.GetNames(maps.get(0), INSERT, DataBaseOperationUtils.DB_TYPE_ORACLE);
            logger.debug("inserts sql:{}\r\nparams:{}", sql, maps);
            int[] ints = this.getJdbcTemplate().batchUpdate(sql, DataBaseOperationUtils.GetObjectArgs(maps, DataBaseOperationUtils.DB_TYPE_ORACLE));
            return 0;
        } catch (Exception var4) {
            var4.printStackTrace();
            throw var4;
        }
    }

    @Override
    public int delete(String tableNamePrefix, String tableName, Map<String, Object> map) {
        try {
            String sql = "DELETE FROM " + DataBaseOperationUtils.getOracleTableName(tableNamePrefix, tableName) + " WHERE "
                    + DataBaseOperationUtils.GetNames(map, DELETE, DataBaseOperationUtils.DB_TYPE_ORACLE);
            logger.debug("delete sql:{}\r\nparams:{}", sql, map);
            int rows = this.getJdbcTemplate().update(sql, DataBaseOperationUtils.GetObjectArgs(map, DataBaseOperationUtils.DB_TYPE_ORACLE));
            return rows;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean executeSQL(String sql, List<Object> params) {
        logger.debug("executeSQL:{}\r\nparams:{}", sql, params);
        int status = this.getJdbcTemplate().update(sql, params.toArray());
        return status > 0;
    }

    @Override
    public Map<String, Object> executeQuerySQL(String sql) {
        logger.debug("executeQuerySQL:{}", sql);
        return this.getJdbcTemplate().queryForMap(sql);
    }

    public List<Map<String, Object>> executeListSQL(String sql) {
        logger.debug("executeListSQL:{}", sql);
        return this.getJdbcTemplate().queryForList(sql);
    }

    @Override
    public void executeSingleSQL(String sql) {
        logger.debug("executeSingleSQL:{}", sql);
        this.getJdbcTemplate().execute(sql);
    }

    @Override
    public List<Map<String, Object>> getTableData(String tableNamePrefix, String tableName, Map<String, Object> params) {
        return null;
    }

    @Override
    public int getTableDataCount(String tableNamePrefix, String tableName, Map<String, Object> params) {
        return 0;
    }

    @Override
    public int getColumnNotNullCount(String tableNamePrefix, String tableName, String columnName) {
        return 0;
    }

    @Override
    public int getColumnNotZeroCount(String tableNamePrefix, String tableName, String columnName) {
        return 0;
    }

    private JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }
}

