package com.haomostudio.JuniorSpringMVCTemplate.dao.common.util;


import com.haomostudio.JuniorSpringMVCTemplate.dao.impl.DataBaseDao;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;


public class DataBaseOperationUtils {

    public static final String DB_TYPE_ORACLE = "oracle";

    public static final String DB_TYPE_MYSQL = "mysql";

    public static final String DATA_TYPE_VARCHAR2 = "varchar2";

    public static final String DATA_TYPE_TIMESTAMP = "timestamp";

    public static final String DATA_TYPE_INT = "number(11, 0)";

    public static final String DATA_TYPE_FLOAT = "FLOAT";

    public static final String DATA_TYPE_DOUBLE = "FLOAT";

    public static final String DATA_TYPE_NUMBER = "NUMBER";

    public static final SimpleDateFormat ORACLE_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static List<String> getPropertyName(Object obj) {
        ArrayList list = new ArrayList();
        Field[] fs = obj.getClass().getDeclaredFields();
        Field[] var7 = fs;
        int var6 = fs.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            Field f = var7[var5];
            list.add(f.getName());
        }

        return list;
    }

    public static Object[] GetObjectArgs(Map<String, Object> map, String dbType) {

        Map<String, Object> params = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (DB_TYPE_ORACLE.equals(dbType) && (value instanceof Date)) {
                value = ORACLE_SDF.format((Date)value);
            }
            params.put(key, value);
        }
        return params.values().toArray();
    }

    public static List<Object[]> GetObjectArgs(List<Map<String, Object>> maps, String dbType) {
        List<Object[]> rets = new ArrayList<>();
        Map<String, Object> params = new LinkedHashMap<>();

        for (Map<String, Object> map : maps) {

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (DB_TYPE_ORACLE.equals(dbType) && (value instanceof Date)) {
                    value = ORACLE_SDF.format((Date) value);
                }
                params.put(key, value);
            }
            rets.add(params.values().toArray());
        }
        return rets;
    }

    public static String GetNames(Map<String, Object> map, String operation, String dbType) {
        String columns = "";

        try {
            if(DataBaseDao.INSERT.equals(operation)) {
                String params = "(";
                columns = "(";

                for(Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    columns = columns + (DB_TYPE_ORACLE.equals(dbType)? key.toUpperCase() + ",":"`" + key + "`,");
                    if (DB_TYPE_ORACLE.equals(dbType) && (value instanceof Date)) {
                        params = params + "to_date(?,'YYYY-MM-DD HH24:MI:SS'),";
                    } else {
                        params = params + "?,";
                    }
                }

                if(!"(".equals(columns)) {
                    columns = columns.substring(0, columns.length() - 1);
                }

                if(!"(".equals(params)) {
                    params = params.substring(0, params.length() - 1);
                }

                columns = columns + ")";
                params = params + ")";
                return columns + " VALUES" + params;
            }

            if(DataBaseDao.UPDATE.equals(operation)) {
                for(Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if ("ID".equals(key)) {
                        continue;
                    }
                    columns = columns + (DB_TYPE_ORACLE.equals(dbType)? key.toUpperCase() + "":"`" + key + "`");
                    if (DB_TYPE_ORACLE.equals(dbType) && (value instanceof Date)) {
                        columns = columns + "=to_date(?,'YYYY-MM-DD HH24:MI:SS'),";
                    } else {
                        columns = columns + "=?,";
                    }
                }

                if(columns.substring(columns.length() - 1, columns.length()).equals(",")) {
                    columns = columns.substring(0, columns.length() - 1);
                }

                columns = columns + " WHERE ID=?";
            }

            if (DataBaseDao.DELETE.equals(operation)) {
                for(String key : map.keySet()) {
                    columns = columns + (DB_TYPE_ORACLE.equals(dbType)? key.toUpperCase() + "=? AND ":"`" + key + "`=? AND ");
                }

                if(columns.endsWith(" AND ")) {
                    columns = columns.substring(0, columns.length() - 5);
                }

            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return columns;
    }

    public static String getInSQL(String params, String flag) {
        String tempStr = params.replace(flag, ",");
        String[] tempStrArr = tempStr.split(",");
        String InSQL = "";

        for(int i = 0; i < tempStrArr.length; ++i) {
            InSQL = InSQL + "?,";
        }

        if(!"".equals(InSQL)) {
            InSQL = InSQL.substring(0, InSQL.length() - 1);
        }

        return InSQL;
    }

    public static List<Object> getInArgs(String params, String flag, String dataType) {
        String tempStr = params.replace(flag, ",");
        String[] tempStrArr = tempStr.split(",");
        ArrayList obj = new ArrayList();
        int i;
        if("varchar".equalsIgnoreCase(dataType) || "varchar2".equalsIgnoreCase(dataType) || "blob".equalsIgnoreCase(dataType)) {
            for(i = 0; i < tempStrArr.length; ++i) {
                obj.add(tempStrArr[i]);
            }
        } else if("integer".equals(dataType)) {
            for(i = 0; i < tempStrArr.length; ++i) {
                obj.add(Integer.valueOf(Integer.parseInt(tempStrArr[i])));
            }
        }

        return obj;
    }


    private static String getDataType(int type) {

        switch (type) {
            case 1:
                return DATA_TYPE_VARCHAR2;
            case 2:
                return DATA_TYPE_VARCHAR2;
            case 3:
                return DATA_TYPE_TIMESTAMP;
            case 4:
                return DATA_TYPE_TIMESTAMP;
            case 5:
                return DATA_TYPE_VARCHAR2;
            case 6:
                return DATA_TYPE_VARCHAR2;
            case 7:
                return DATA_TYPE_VARCHAR2;
            case 8:
                return DATA_TYPE_VARCHAR2;
            case 9:
                return DATA_TYPE_INT;
            case 10:
//                return DATA_TYPE_DOUBLE;
                return DATA_TYPE_NUMBER;
            case 11:
                return DATA_TYPE_VARCHAR2;
            case 12:
                return DATA_TYPE_VARCHAR2;
            default:
                return DATA_TYPE_VARCHAR2;
        }
    }


    public static String getMySQLTableName(String prefix, String tableName) {

        return "`" + getFinalTableName(prefix, tableName) + "`";
    }

    public static String getOracleTableName(String prefix, String tableName) {
        return getFinalTableName(prefix, tableName).toUpperCase();
    }

    private static String getFinalTableName(String prefix, String tableName) {
        String finalName = "";
        if (!StringUtils.isEmpty(prefix)) {
            finalName += prefix.trim();
            finalName += tableName.trim().toUpperCase();
        } else {
            finalName += tableName.trim().toLowerCase();
        }
        return finalName;
    }
}

