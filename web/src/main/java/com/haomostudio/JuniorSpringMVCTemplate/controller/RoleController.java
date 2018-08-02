package com.haomostudio.JuniorSpringMVCTemplate.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haomostudio.JuniorSpringMVCTemplate.common.Resp;
import com.haomostudio.JuniorSpringMVCTemplate.po.Role;
import com.haomostudio.JuniorSpringMVCTemplate.service.HmUtils.Tools;
import com.haomostudio.JuniorSpringMVCTemplate.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


@Api(description = "角色")
@Controller
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    HttpServletResponse response;

    @ApiOperation(value = "创建'角色'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/roles/new",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createRole(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "createTime", required = false) String createTime,
            @RequestParam(value = "grade", required = false) Integer grade,
            @RequestParam(value = "abbreviation", required = false) String abbreviation,
            @RequestParam(value = "lastUpdateTime", required = false) String lastUpdateTime,
            @RequestParam(value = "rank", required = false) Integer rank
    ) {
        Role item = new Role();
        String id = Tools.getUUID();
        item.setId(id);
        if( name != null ){
            item.setName(name);
        }
        if( description != null ){
            item.setDescription(description);
        }
            item.setCreateTime(new Date());
        if( grade != null ){
            item.setGrade(grade);
        }
        if( abbreviation != null ){
            item.setAbbreviation(abbreviation);
        }
            item.setLastUpdateTime(new Date());
        if( rank != null ){
            item.setRank(rank);
        }
        roleService.create(item);

        //若request param为null，但字段定义有默认值，则需从数据库中返回结果
        return roleService.get(id);
        //return item;
    }

    @ApiOperation(value = "删除'角色'表中的某条记录", notes = "根据url传入的数据id，删除整条记录。")
    @RequestMapping(value = "/roles/{role_id}/delete",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteRole(
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "role_id") String id
    ) {
        Role item = roleService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be deleted doesn't exist");
        }

        roleService.delete(id);
        return Resp.succ("delete success");
    }

    @ApiOperation(value = "修改'角色'表中的某条记录", notes = "根据url传入的数据id，确定修改表中的某条记录，传入表中字段要修改的信息，不传代表不修改。并返回给View层")
    @RequestMapping(value = "/roles/{role_id}/edit",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editRole(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "role_id") String id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "grade", required = false) Integer grade,
            @RequestParam(value = "abbreviation", required = false) String abbreviation,
            @RequestParam(value = "rank", required = false) Integer rank
    ) {
        Role item = roleService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be edited doesn't exist");
        }

        if( name != null ){
            item.setName(name);
        }
        if( description != null ){
            item.setDescription(description);
        }
        if( grade != null ){
            item.setGrade(grade);
        }
        if( abbreviation != null ){
            item.setAbbreviation(abbreviation);
        }
            item.setLastUpdateTime(new Date());
        if( rank != null ){
            item.setRank(rank);
        }
        roleService.update(item);

        return roleService.get(id);
    }

    @ApiOperation(value = "查询'角色'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/roles/{role_id}",
                    method = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT},
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getRole(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "role_id") String id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "grade", required = false) Integer grade,
            @RequestParam(value = "abbreviation", required = false) String abbreviation,
            @RequestParam(value = "rank", required = false) Integer rank
    ) {
        if(request.getMethod().equals("GET")){
            Role item = roleService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be edited doesn't exist");
            }

            return item;
        }
        else if(request.getMethod().equals("PUT")){
            Role item = roleService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be edited doesn't exist");
            }

            if( name != null ){
                item.setName(name);
            }
            if( description != null ){
                item.setDescription(description);
            }
            if( grade != null ){
                item.setGrade(grade);
            }
            if( abbreviation != null ){
                item.setAbbreviation(abbreviation);
            }
                item.setLastUpdateTime(new Date());
            if( rank != null ){
                item.setRank(rank);
            }
            roleService.update(item);

            return roleService.get(id);
        }
        else if(request.getMethod().equals("DELETE")){
            Role item = roleService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be deleted doesn't exist");
            }

            roleService.delete(id);
            return Resp.succ("delete success");
        }
        return Resp.fail("Unknown http method");
    }

    @ApiOperation(value = "查询'角色'表中的多条记录或者新增某条记录", notes = "get传参：根据url传入的filters（过滤条件），查询对应的多条数据。数据数量取决于pageNo和pageSize；数据的先后顺序取决于sortItem，sortOrder；   post请求：新增某条记录，传入表字段对应的值")
    @RequestMapping(value = "/roles",
                    method = { RequestMethod.GET, RequestMethod.POST },
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getRoles(
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

        Long total = roleService.countListWithPagingAndFilter(filters);
        response.addHeader("total", String.valueOf(total));

        return roleService.getListWithPagingAndFilter(
                        pageNo, pageSize, sortItem, sortOrder, filters, includes, refers, relates);

    }

    @ApiOperation(value = "出入字段数组返回对应字段数据", notes = "get传参：根据url传入的filters（过滤条件），查询对应的多条数据。只返回你传进来的字段对应的数据")
    @RequestMapping(value = "/roles/columns",
                    method = { RequestMethod.GET },
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getRolesId(
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
        List<Role> list = roleService.getListWithPagingAndFilter(
                pageNo, pageSize, sortItem, sortOrder, filters);

        //反射调取get方法
        Class clz = Role.class;
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



    @ApiOperation(value = "批量创建'角色'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/roles/create//batch",
               method = RequestMethod.POST,
               produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createsRoles(
             HttpServletRequest request,
             @RequestHeader(value = "X-Auth-Token", required = false) String token,
             @RequestParam(value = "params", required = false) String params
    ) {

             JSONArray jsonArray = JSON.parseArray(params);
             List<Role> lists  = new ArrayList<>();

             jsonArray.stream().forEach((Object object) -> {

             JSONObject jsonObject = JSONObject.parseObject(object.toString());

             Role item = new Role();
             String id = Tools.getUUID();
                         item.setId(id);
             if(jsonObject.containsKey("name")   ){
            item.setName(jsonObject.get("name").toString());
        }
        if(jsonObject.containsKey("description")   ){
            item.setDescription(jsonObject.get("description").toString());
        }
            item.setCreateTime(new Date());
        if(jsonObject.containsKey("grade")   ){
            item.setGrade(new Integer(jsonObject.get("grade").toString()));
        }
        if(jsonObject.containsKey("abbreviation")   ){
            item.setAbbreviation(jsonObject.get("abbreviation").toString());
        }
            item.setLastUpdateTime(new Date());
        if(jsonObject.containsKey("rank")   ){
            item.setRank(new Integer(jsonObject.get("rank").toString()));
        }
             roleService.create(item);
             lists.add(roleService.get(id));

             });
        return lists;
     }

    @ApiOperation(value = "批量删除'角色'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
        @RequestMapping(value = "/roles/delete/batch",
                method = RequestMethod.POST,
                produces = "application/json;charset=UTF-8")
        @ResponseBody
        public Object deletesRole(
                HttpServletRequest request,
                @RequestHeader(value = "X-Auth-Token", required = false) String token,
                @RequestParam(value = "ids", required = true) String ids
        ) {

            JSONArray jsonArray = JSON.parseArray(ids);
            jsonArray.stream().forEach(id->{

                 Role item = roleService.get(id.toString());
                if (null != item) {
                    roleService.delete(id.toString());
                }

            });
            return Resp.succ("delete success");
        }
}
