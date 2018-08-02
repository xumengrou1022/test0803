package com.haomostudio.JuniorSpringMVCTemplate.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CaseFormat;
import com.haomostudio.JuniorSpringMVCTemplate.dao.*;
import com.haomostudio.JuniorSpringMVCTemplate.po.*;
import com.haomostudio.JuniorSpringMVCTemplate.vo.*;
import com.haomostudio.JuniorSpringMVCTemplate.service.*;
import com.haomostudio.JuniorSpringMVCTemplate.service.HmUtils.MybatisExampleHelper;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.haomo.plugin.Page;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.stream.Collectors;


@Service("functionAuthorityService")
public class FunctionAuthorityServiceImpl implements FunctionAuthorityService{
    protected static final Logger LOG = LoggerFactory.getLogger(FunctionAuthorityServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    OrganizationMapper organizationMapper;

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    HmUserMapper hmUserMapper;

    @Autowired
    AuthTokenMapper authTokenMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RoleMenuMapper roleMenuMapper;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    FunctionAuthorityMapper functionAuthorityMapper;

    @Autowired
    RoleFunctionMapper roleFunctionMapper;

    @Autowired
    CcNotificationSettingMapper ccNotificationSettingMapper;

    @Autowired
    ViewHmUserDepartmentRoleMapper viewHmUserDepartmentRoleMapper;

    @Autowired
    HmLogMapper hmLogMapper;

    @Autowired
    CcLoggingMapper ccLoggingMapper;


    @Override
    public int create(FunctionAuthority item) {
        return functionAuthorityMapper.insertSelective(item);
    }

    @Override
    public int delete(String id) {
        return functionAuthorityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(FunctionAuthority item) {
        return functionAuthorityMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public FunctionAuthority get(String id) {
        return functionAuthorityMapper.selectByPrimaryKey(id);
    }

    private void assignRelates(String relates, List<FunctionAuthorityVO> modelVOList){
        JSONObject relatesObj = JSON.parseObject(relates);
        for (String table: relatesObj.keySet()) {
            for(FunctionAuthorityVO modelVO: modelVOList){
                List<String> joinCols = (List<String>) relatesObj.get(table);
                FunctionAuthorityExample tableExampleObj = new FunctionAuthorityExample();
                Map<String, Map<String, Map<String, Object>>> tableFilters = new HashMap<>();
                for(String joinCol: joinCols){
                    Map<String, Map<String, Object>> column = new HashMap<>();
                    Method m = MybatisExampleHelper.getMethod(modelVO.getSuperior(),
                        "get"+ CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, joinCol));
                    Map<String, Object> condition = new HashMap();
                    try{
                        if (m.invoke(modelVO.getSuperior())==null){
                            return;
                        }
                        condition.put("equalTo", (String)JSON.parse(JSON.toJSONString(m.invoke(modelVO.getSuperior()))));
                        column.put(joinCol, condition);
                        tableFilters.put(table, column);
                    }
                    catch (InvocationTargetException e) {
                        LOG.error(e.toString());
                    }
                    catch (IllegalAccessException e){
                        LOG.error(e.toString());
                    }
                }

                MybatisExampleHelper.assignWhereClause(tableExampleObj, tableExampleObj.or(), table, JSON.toJSONString(tableFilters));
                tableExampleObj.setOrderByClause(MybatisExampleHelper.createOrderClause("id", "asc"));

                // 获取到关联表的列表
                try{
                    Field mapperFieldDef = this.getClass()
                    .getDeclaredField(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, table)+"Mapper");
                    mapperFieldDef.setAccessible(true);
                    Object fieldValue = mapperFieldDef.get(this);
                    MapperProxy proxy = (MapperProxy) Proxy.getInvocationHandler(fieldValue);
                    Method sbewr = MybatisExampleHelper.getMethod(fieldValue, "selectByExample");
                    Page page = new Page(0,1000);
                    tableExampleObj.setPage(page);
                    Object[] args = new Object[]{tableExampleObj};
                    modelVO.getRelates().put(table, (List<Object>)(proxy.invoke(proxy, sbewr, args)));
                }
                catch (Throwable e){
                    LOG.error(e.toString());
                }
            }
        }
    }

    @Override
    public List<FunctionAuthority> getListWithPagingAndFilter(
        Integer pageNo, Integer pageSize,
        String sortItem, String sortOrder,
        String filters){
        FunctionAuthorityExample exampleObj = new FunctionAuthorityExample();
        Page page = new Page((pageNo - 1) * pageSize, pageSize);
        exampleObj.setPage(page);
        MybatisExampleHelper.assignWhereClause(exampleObj, exampleObj.or(), "FunctionAuthority", filters);
        exampleObj.setOrderByClause(MybatisExampleHelper.createOrderClause(sortItem, sortOrder));

        return functionAuthorityMapper.selectByExample(exampleObj);
    }

    @Override
    public Object getListWithPagingAndFilter(
        Integer pageNo, Integer pageSize,
        String sortItem, String sortOrder,
        String filters, String includes, String refers, String relates){
        FunctionAuthorityExample exampleObj = new FunctionAuthorityExample();
        Page page = new Page((pageNo - 1) * pageSize, pageSize);
        exampleObj.setPage(page);
        MybatisExampleHelper.assignWhereClause(exampleObj, exampleObj.or(), "FunctionAuthority", filters);
        exampleObj.setOrderByClause(MybatisExampleHelper.createOrderClause(sortItem, sortOrder));

        List<FunctionAuthority> modelList = functionAuthorityMapper.selectByExample(exampleObj);

        List<FunctionAuthorityVO> modelVOList = modelList.stream().map(model -> new FunctionAuthorityVO(model)).collect(Collectors.toList());

         // 处理includes的外链字段  处理refers的关联表
        if((includes != null || refers != null)&& modelList.size() > 0){
            //对Vo层数据循环处理外链表数据
            modelVOList.stream().forEach(status->{
                if (includes != null){
                    this.assignIncludes(includes,status);
                }
                if (refers != null){
                    this.assignRefers(refers,status);
                }
            });
        }

        // 处理join的关联表
        if(relates != null && modelList.size() > 0){
            this.assignRelates(relates, modelVOList);
        }

        if(includes == null && refers == null && relates == null){
            return modelList;
        }
        else{
            return modelVOList;
        }
    }

    @Override
    public Long countListWithPagingAndFilter(String filters){
        FunctionAuthorityExample exampleObj = new FunctionAuthorityExample();
        MybatisExampleHelper.assignWhereClause(exampleObj, exampleObj.or(), "FunctionAuthority", filters);
        return functionAuthorityMapper.countByExample(exampleObj);
    }
    
    private void assignIncludes(String includes, FunctionAuthorityVO status){
            JSONObject includesObj = JSON.parseObject(includes);
            //定义容器存储所有外链表数据
            Map<String,Object> map = new HashMap();
            // 循环解析每一个键值对
            if (includesObj!=null && includesObj.keySet().size() >0){
                includesObj.keySet().stream().forEach(table->{
                    //每一个key都是一个po实体类名
                    Object className = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table);
                    JSONObject jsonObject = includesObj.getJSONObject(
                            CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, table));
                    //对内层的includes数据进行解析
                    JSONArray columnCondition = jsonObject.getJSONArray("includes");
                    if (columnCondition != null && columnCondition.toArray().length >0){
                        //循环数组
                        Arrays.stream(columnCondition.toArray()).forEach(obj -> {
                            //反射机制将要执行的方法名
                            String ffName = "get"+CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, obj.toString());
                            //获取Method的对象
                            Method m = MybatisExampleHelper.getMethod(status.getSuperior(), ffName);
                            MybatisExampleHelper.dealSearchIncludes(m,status.getSuperior(),
                                    className,map,table,this.getClass().getPackage().getName());
    
                        });
                    }
                });
            }
            status.setIncludes(map);
        }
    
        private void assignRefers(String refers, FunctionAuthorityVO status){
            //解析includes字符串为JSONObject
            JSONObject refersObj = JSON.parseObject(refers);
            //定义容器存储所有外链表数据
            Map<String,List<Object>> map = new HashMap();
            if (refersObj!=null && refersObj.keySet().size() > 0){
                // 循环解析每一个键值对
                refersObj.keySet().stream().forEach(table -> {
                    //每一个key都是一个po实体类名
                    JSONObject jsonObject = refersObj.getJSONObject(
                            CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, table));
                    //对内层的includes数据进行解析
                    JSONArray columnCondition = jsonObject.getJSONArray("includes");
                    if (columnCondition != null && columnCondition.toArray().length > 0){
                        //循环数组
                        Arrays.stream(columnCondition.toArray()).forEach(obj -> {
                            MybatisExampleHelper.dealSearchRefers(status.getSuperior().getId(),
                                    table,map,obj,CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table),
                                    this.getClass().getPackage().getName());
                        });
                    }
                });
            }
    
            status.setRefers(map);
        }
}
