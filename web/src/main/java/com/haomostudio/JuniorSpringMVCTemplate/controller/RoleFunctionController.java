package com.haomostudio.JuniorSpringMVCTemplate.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haomostudio.JuniorSpringMVCTemplate.common.Resp;
import com.haomostudio.JuniorSpringMVCTemplate.po.RoleFunction;
import com.haomostudio.JuniorSpringMVCTemplate.service.HmUtils.Tools;
import com.haomostudio.JuniorSpringMVCTemplate.service.RoleFunctionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


@Api(description = "角色功能关系表")
@Controller
public class RoleFunctionController {

    @Autowired
    RoleFunctionService roleFunctionService;

    @Autowired
    HttpServletResponse response;

    @ApiOperation(value = "创建'角色功能关系表'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/role_functions/new",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createRoleFunction(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "functionId", required = false) String functionId,
            @RequestParam(value = "createTime", required = false) String createTime,
            @RequestParam(value = "lastUpdateTime", required = false) String lastUpdateTime
    ) {
        RoleFunction item = new RoleFunction();
        String id = Tools.getUUID();
        item.setId(id);
        if( roleId != null ){
            item.setRoleId(roleId);
        }
        if( functionId != null ){
            item.setFunctionId(functionId);
        }
            item.setCreateTime(new Date());
            item.setLastUpdateTime(new Date());
        roleFunctionService.create(item);

        //若request param为null，但字段定义有默认值，则需从数据库中返回结果
        return roleFunctionService.get(id);
        //return item;
    }

    @ApiOperation(value = "删除'角色功能关系表'表中的某条记录", notes = "根据url传入的数据id，删除整条记录。")
    @RequestMapping(value = "/role_functions/{role_function_id}/delete",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteRoleFunction(
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "role_function_id") String id
    ) {
        RoleFunction item = roleFunctionService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be deleted doesn't exist");
        }

        roleFunctionService.delete(id);
        return Resp.succ("delete success");
    }

    @ApiOperation(value = "修改'角色功能关系表'表中的某条记录", notes = "根据url传入的数据id，确定修改表中的某条记录，传入表中字段要修改的信息，不传代表不修改。并返回给View层")
    @RequestMapping(value = "/role_functions/{role_function_id}/edit",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editRoleFunction(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "role_function_id") String id,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "functionId", required = false) String functionId
    ) {
        RoleFunction item = roleFunctionService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be edited doesn't exist");
        }

        if( roleId != null ){
            item.setRoleId(roleId);
        }
        if( functionId != null ){
            item.setFunctionId(functionId);
        }
            item.setLastUpdateTime(new Date());
        roleFunctionService.update(item);

        return roleFunctionService.get(id);
    }

    @ApiOperation(value = "查询'角色功能关系表'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/role_functions/{role_function_id}",
                    method = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT},
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getRoleFunction(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "role_function_id") String id,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "functionId", required = false) String functionId
    ) {
        if(request.getMethod().equals("GET")){
            RoleFunction item = roleFunctionService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be edited doesn't exist");
            }

            return item;
        }
        else if(request.getMethod().equals("PUT")){
            RoleFunction item = roleFunctionService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be edited doesn't exist");
            }

            if( roleId != null ){
                item.setRoleId(roleId);
            }
            if( functionId != null ){
                item.setFunctionId(functionId);
            }
                item.setLastUpdateTime(new Date());
            roleFunctionService.update(item);

            return roleFunctionService.get(id);
        }
        else if(request.getMethod().equals("DELETE")){
            RoleFunction item = roleFunctionService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be deleted doesn't exist");
            }

            roleFunctionService.delete(id);
            return Resp.succ("delete success");
        }
        return Resp.fail("Unknown http method");
    }

    @ApiOperation(value = "查询'角色功能关系表'表中的多条记录或者新增某条记录", notes = "get传参：根据url传入的filters（过滤条件），查询对应的多条数据。数据数量取决于pageNo和pageSize；数据的先后顺序取决于sortItem，sortOrder；   post请求：新增某条记录，传入表字段对应的值")
    @RequestMapping(value = "/role_functions",
                    method = { RequestMethod.GET, RequestMethod.POST },
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getRoleFunctions(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token", required = false) String token,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "sortItem", required = false) String sortItem,
            @RequestParam(value = "sortOrder", required = false) String sortOrder,
            @RequestParam(value = "filters", required = false) String filters,
            @RequestParam(value = "includes", required = false) String includes,
            @RequestParam(value = "refers", required = false) String refers,
            @RequestParam(value = "relates", required = false) String relates
    ) {
        if(pageNo == null){
            pageNo = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        if(sortItem == null){
            sortItem = "id";
        }
        if(sortOrder == null){
            sortOrder = "asc";
        }
        if(filters == null){
            filters = "{}";
        }

        Long total = roleFunctionService.countListWithPagingAndFilter(filters);
        response.addHeader("total", String.valueOf(total));

        return roleFunctionService.getListWithPagingAndFilter(
                        pageNo, pageSize, sortItem, sortOrder, filters, includes, refers, relates);

    }

    @ApiOperation(value = "出入字段数组返回对应字段数据", notes = "get传参：根据url传入的filters（过滤条件），查询对应的多条数据。只返回你传进来的字段对应的数据")
    @RequestMapping(value = "/role_functions/columns",
                    method = { RequestMethod.GET },
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getRoleFunctionsId(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token", required = false) String token,
            @RequestParam(value = "filters", required = true) String filters,
            @RequestParam(value = "columns", required = true) String columns,
            @RequestParam(value = "sortOrder", required = false) String sortOrder,
            @RequestParam(value = "sortItem", required = false) String sortItem,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        if(pageNo == null){
            pageNo = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        if(sortOrder==null){
            sortOrder = "asc";
        }
        if(sortItem==null){
            sortItem = "id";
        }
        JSONArray jsonArray = JSON.parseArray(columns);
        List<Map<String,Object>> lists  = new ArrayList<>();
        List<RoleFunction> list = roleFunctionService.getListWithPagingAndFilter(
                pageNo, pageSize, sortItem, sortOrder, filters);

        //反射调取get方法
        Class clz = RoleFunction.class;
        list.stream().forEach(lot->{
            Map<String,Object> map = new HashMap<>();
            for (Object j : jsonArray){
                String s = (String) j;
                s = s.replaceFirst(s.substring(0,1),s.substring(0,1).toUpperCase());
                try {
                    if (clz.getMethod("get"+s).invoke(lot) !=null){
                        map.put((String) j,clz.getMethod("get"+s).invoke(lot));
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            lists.add(map);
        });

        return lists;
     }



    @ApiOperation(value = "批量创建'角色功能关系表'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/role_functions/create//batch",
               method = RequestMethod.POST,
               produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createsRoleFunctions(
             HttpServletRequest request,
             @RequestHeader(value = "X-Auth-Token", required = false) String token,
             @RequestParam(value = "params", required = false) String params
    ) {

             JSONArray jsonArray = JSON.parseArray(params);
             List<RoleFunction> lists  = new ArrayList<>();

             jsonArray.stream().forEach((Object object) -> {

             JSONObject jsonObject = JSONObject.parseObject(object.toString());

             RoleFunction item = new RoleFunction();
             String id = Tools.getUUID();
                         item.setId(id);
             if(jsonObject.containsKey("roleId")   ){
            item.setRoleId(jsonObject.get("roleId").toString());
        }
        if(jsonObject.containsKey("functionId")   ){
            item.setFunctionId(jsonObject.get("functionId").toString());
        }
            item.setCreateTime(new Date());
            item.setLastUpdateTime(new Date());
             roleFunctionService.create(item);
             lists.add(roleFunctionService.get(id));

             });
        return lists;
     }

    @ApiOperation(value = "批量删除'角色功能关系表'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
        @RequestMapping(value = "/role_functions/delete/batch",
                method = RequestMethod.POST,
                produces = "application/json;charset=UTF-8")
        @ResponseBody
        public Object deletesRoleFunction(
                HttpServletRequest request,
                @RequestHeader(value = "X-Auth-Token", required = false) String token,
                @RequestParam(value = "ids", required = true) String ids
        ) {

            JSONArray jsonArray = JSON.parseArray(ids);
            jsonArray.stream().forEach(id->{

                 RoleFunction item = roleFunctionService.get(id.toString());
                if (null != item) {
                    roleFunctionService.delete(id.toString());
                }

            });
            return Resp.succ("delete success");
        }
}
