package com.haomostudio.JuniorSpringMVCTemplate.service.HmUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CaseFormat;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class MybatisExampleHelper {
    /**
     * 创建mybatis的函数setOrderByClause支持的排序格式
     * @param sortItem 格式为: 'id, name'
     * @param sortOrder 格式为: 'asc, desc'
     * @return
     */
    public static String createOrderClause(String sortItem, String sortOrder){
        List<String> items = Arrays.asList(sortItem.split(",\\s*"));
        List<String> orders = Arrays.asList(sortOrder.split(",\\s*"));
        if(items.size() != orders.size()){
            return null;
        }
        else{
            List<String> itemOrder = new ArrayList<>();
            for(int i=0;i<items.size();i++){
                itemOrder.add(items.get(i) + " " + orders.get(i));
            }
            return String.join(", ", itemOrder);
        }
    }

    /**
     * 获取mybatis generator自动生成的过滤函数的名称
     * @param column
     * @param condition
     * @return
     */
    public static String getFilterFuncName(String column, String condition){
        if(column.contains("_")){
            return "and"
                    + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, column)
                    + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, condition);
        }
        else{
            return "and"
                    + column.substring(0,1).toUpperCase() + column.substring(1)
                    + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, condition);
        }
    }

    public static Method getMethod(Object obj, String methodName){
        for(Method m: obj.getClass().getDeclaredMethods()){
            if(m.getName().equals(methodName)){
                return m;
            }
        }

        return null;
    }

    public static Method getMethodNumber(Object obj, String methodName,int parameterNuber){
        for(Method m: obj.getClass().getDeclaredMethods()){
            if(m.getName().equals(methodName) && m.getParameterTypes().length == parameterNuber){
                return m;
            }
        }

        return null;
    }

    /**
     * 给mybatis附加上某个字段的条件过滤
     * @param exampleObj
     * @param exampleObjCriteria
     * @param column
     * @param condition
     * @param value
     * @return
     */
    public static Object assignCondition(Object exampleObj, Object exampleObjCriteria,
                                         String column ,String condition, Object value){
        String funcName = getFilterFuncName(column, condition);
        Method m = MybatisExampleHelper.getMethod(exampleObjCriteria, funcName);

        switch (condition){
            case "fieldsValuesOr":
                try {
                    m = MybatisExampleHelper.getMethod(exampleObjCriteria, condition);
                    m.invoke(exampleObjCriteria,(List<String>)((Map<String,Object>)value).get("values"),(List<String>)((Map<String,Object>)value).get("fields"),column);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                break;
            case "isNull":
            case "isNotNull":
                try{
                    m.invoke(exampleObjCriteria);
                }
                catch (ReflectiveOperationException e){
                    throw new RuntimeException(e);
                }
                break;
            // 需要考虑时间和Number等字段
            case "between":
            case "notBetween":
                try{
                    if(m.getParameterTypes()[0].getName().contains("Date")){
                        m.invoke(exampleObjCriteria,
                                Tools.convertStringToDate(((List<String>) value).get(0), "yyyy-MM-dd HH:mm:ss"),
                                Tools.convertStringToDate(((List<String>) value).get(1), "yyyy-MM-dd HH:mm:ss"));
                    } else if(m.getParameterTypes()[0].getName().contains("Long")){
                        m.invoke(exampleObjCriteria,
                                ((List<Long>) value).get(0),
                                ((List<Long>) value).get(1));
                    } else{
                        m.invoke(exampleObjCriteria,
                                ((List<Integer>) value).get(0),
                                ((List<Integer>) value).get(1));
                    }
                }
                catch (ReflectiveOperationException e){
                    throw new RuntimeException(e);
                }
                break;

            default:
                try{
                    if(m.getParameterTypes()[0].getName().contains("Date")){
                        m.invoke(exampleObjCriteria,
                                Tools.convertStringToDate((String)value, "yyyy-MM-dd HH:mm:ss"));
                    } else if(m.getParameterTypes()[0].getName().contains("Long")){
                        m.invoke(exampleObjCriteria, Long.valueOf(String.valueOf(value)));
                    } else if(m.getParameterTypes()[0].getName().contains("Integer")){
                        m.invoke(exampleObjCriteria, Integer.valueOf((String)value));

                    } else if(m.getParameterTypes()[0].getName().contains("int")){
                        m.invoke(exampleObjCriteria, (int)value);
                    } else {
                        m.invoke(exampleObjCriteria, value);
                    }
                }
                catch (ReflectiveOperationException e){
                    throw new RuntimeException(e);
                }
                break;
        }
        return exampleObj;
    }

    /**
     * 给mybatis的Example对象附加上
     * @param exampleObj
     * @param exampleObjCriteria
     * @param filters JSON字符串,格式为
     *                {
     *                  table:
     *                  {
     *                    column1: {
     *                      like: '%abc%',
     *                      notLike: ''
     *                      between: [1, 10],
     *                      notBetween: [1, 10]
     *                      isNull: true,   // 只能为true
     *                      isNotNull: true,    // 只能为true
     *                      equalTo: "abc",
     *                      notEqualTo: "abc",
     *                      greaterThan: 10,
     *                      greaterThanOrEqualTo: 10,
     *                      lessThan: 10,
     *                      lessThanOrEqualTo: 10,
     *                      in: [],
     *                      notIn: []
     *                    }
     *                  }
     *
     *                }
     * @return
     */
    public static Object assignWhereClause (Object exampleObj,
                                           Object exampleObjCriteria,
                                           String className,
                                           String filters){
        JSONObject filterObj = JSON.parseObject(filters);
        if(!filterObj.containsKey(
                CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, className)
        )){
            return exampleObjCriteria;
        }

        JSONObject columnCondition = filterObj.getJSONObject(
                CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, className));

        Set<Map.Entry<String, Object>> entrySet = columnCondition.entrySet();

        for(Map.Entry<String, Object> entry: entrySet){
            String column = entry.getKey();
            JSONObject conditionObj = (JSONObject) entry.getValue();
            Set<Map.Entry<String, Object>> conditionObjEntrySet
                    = conditionObj.entrySet();

            for(Map.Entry<String, Object> coes: conditionObjEntrySet){
                exampleObj = assignCondition(
                        exampleObj, exampleObjCriteria,
                        column, coes.getKey(), coes.getValue());
            }
        }

        return exampleObjCriteria;
    }

    public static String getSimpleFilter(String table, String column, String oper, Object value){
        return JSON.toJSONString(new HashMap(){{
            put(table, new HashMap(){{
                put(column, new HashMap(){{
                    put(oper, value);
                }});
            }});
        }});
    }


    /**
     *
     * @param m          java反射类中的方法
     * @param superior   实体类对象
     * @param className  类名（用于反射时对应的ServiceImpl类）
     * @param map        用于存储数据的Map
     * @param table      关联表的名字
     */
    public static void dealSearchIncludes(Method m,Object superior,Object className,Map<String,Object> map,String table,String packageName){
        try {
            if (m != null){
                //外联表的主键值
                Object id = m.invoke(superior);
                //根据主键取出整条数据
                if (id != null){
                    WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
                    //反射类名
                    Class onwClass = Class.forName(packageName+"."+className+"ServiceImpl");
                    Object impl = onwClass.newInstance();
                    //获取Method的对象
                    String get = "get";
                    if (className.equals("CrmUser")){
                        get = "getUserById";
                    }
                    Method m1 = MybatisExampleHelper.getMethod(impl, get);
                    if (m1 != null){
                        //外联表的整条数据
                        Object obj2 = m1.invoke(wac.getBean(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, table)+"Service"),id);
                        //取出来的数据返回
                        map.put(table.toString(),obj2);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


    public static void dealSearchRefers(Object id,String table,Map<String,List<Object>> map,Object obj,Object className,String packageName){
        try {
            //根据主键取出整条数据
            if (id != null) {
                WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
                //DivStattisTabService service = (DivStattisTabService)
                //反射类名
                Class onwClass = Class.forName(packageName+"."+className+"ServiceImpl");
                Object impl = onwClass.newInstance();
                //获取Method的对象
                Method m1 = MybatisExampleHelper.getMethodNumber(impl, "getListWithPagingAndFilter",5);
                //filters的字符串拼接
                StringBuffer buffer = new StringBuffer();
                buffer.append("{");
                buffer.append("\""+table+"\"");
                buffer.append(":{");
                buffer.append("\""+obj.toString()+"\"");
                buffer.append(":{");
                buffer.append("\"equalTo\": \""+id.toString()+"\"");
                buffer.append("} } }");
                //执行getListWithPagingAndFilter方法的参数数组
                Object[] methodObject = new Object[]{1,1000,"id","asc",buffer.toString()};

                if (m1 != null){
                    //外联表的整条数据
                    List<Object> obj2 = (List<Object>)m1.invoke(wac.getBean(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, table)+"Service"),1,1000,"id","asc",buffer.toString());
                    //取出来的数据返回
                    map.put(table.toString(),obj2);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


}
