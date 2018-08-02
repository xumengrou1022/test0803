package com.haomostudio.JuniorSpringMVCTemplate.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haomostudio.JuniorSpringMVCTemplate.common.Resp;
import com.haomostudio.JuniorSpringMVCTemplate.po.Organization;
import com.haomostudio.JuniorSpringMVCTemplate.service.HmUtils.Tools;
import com.haomostudio.JuniorSpringMVCTemplate.service.OrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


@Api(description = "组织社区")
@Controller
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    @Autowired
    HttpServletResponse response;

    @ApiOperation(value = "创建'组织社区'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/organizations/new",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createOrganization(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "createTime", required = false) String createTime,
            @RequestParam(value = "lastUpdateTime", required = false) String lastUpdateTime,
            @RequestParam(value = "rank", required = false) Integer rank,
            @RequestParam(value = "createPeple", required = false) String createPeple,
            @RequestParam(value = "linkToken", required = false) String linkToken,
            @RequestParam(value = "whetherInside", required = false) String whetherInside,
            @RequestParam(value = "communityId", required = false) String communityId
    ) {
        Organization item = new Organization();
        String id = Tools.getUUID();
        item.setId(id);
        if( name != null ){
            item.setName(name);
        }
            item.setCreateTime(new Date());
            item.setLastUpdateTime(new Date());
        if( rank != null ){
            item.setRank(rank);
        }
        if( createPeple != null ){
            item.setCreatePeple(createPeple);
        }
        if( linkToken != null ){
            item.setLinkToken(linkToken);
        }
        if( whetherInside != null ){
            item.setWhetherInside(whetherInside);
        }
        if( communityId != null ){
            item.setCommunityId(communityId);
        }
        organizationService.create(item);

        //若request param为null，但字段定义有默认值，则需从数据库中返回结果
        return organizationService.get(id);
        //return item;
    }

    @ApiOperation(value = "删除'组织社区'表中的某条记录", notes = "根据url传入的数据id，删除整条记录。")
    @RequestMapping(value = "/organizations/{organization_id}/delete",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteOrganization(
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "organization_id") String id
    ) {
        Organization item = organizationService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be deleted doesn't exist");
        }

        organizationService.delete(id);
        return Resp.succ("delete success");
    }

    @ApiOperation(value = "修改'组织社区'表中的某条记录", notes = "根据url传入的数据id，确定修改表中的某条记录，传入表中字段要修改的信息，不传代表不修改。并返回给View层")
    @RequestMapping(value = "/organizations/{organization_id}/edit",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editOrganization(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "organization_id") String id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "rank", required = false) Integer rank,
            @RequestParam(value = "createPeple", required = false) String createPeple,
            @RequestParam(value = "linkToken", required = false) String linkToken,
            @RequestParam(value = "whetherInside", required = false) String whetherInside,
            @RequestParam(value = "communityId", required = false) String communityId
    ) {
        Organization item = organizationService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be edited doesn't exist");
        }

        if( name != null ){
            item.setName(name);
        }
            item.setLastUpdateTime(new Date());
        if( rank != null ){
            item.setRank(rank);
        }
        if( createPeple != null ){
            item.setCreatePeple(createPeple);
        }
        if( linkToken != null ){
            item.setLinkToken(linkToken);
        }
        if( whetherInside != null ){
            item.setWhetherInside(whetherInside);
        }
        if( communityId != null ){
            item.setCommunityId(communityId);
        }
        organizationService.update(item);

        return organizationService.get(id);
    }

    @ApiOperation(value = "查询'组织社区'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/organizations/{organization_id}",
                    method = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT},
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getOrganization(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "organization_id") String id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "rank", required = false) Integer rank,
            @RequestParam(value = "createPeple", required = false) String createPeple,
            @RequestParam(value = "linkToken", required = false) String linkToken,
            @RequestParam(value = "whetherInside", required = false) String whetherInside,
            @RequestParam(value = "communityId", required = false) String communityId
    ) {
        if(request.getMethod().equals("GET")){
            Organization item = organizationService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be edited doesn't exist");
            }

            return item;
        }
        else if(request.getMethod().equals("PUT")){
            Organization item = organizationService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be edited doesn't exist");
            }

            if( name != null ){
                item.setName(name);
            }
                item.setLastUpdateTime(new Date());
            if( rank != null ){
                item.setRank(rank);
            }
            if( createPeple != null ){
                item.setCreatePeple(createPeple);
            }
            if( linkToken != null ){
                item.setLinkToken(linkToken);
            }
            if( whetherInside != null ){
                item.setWhetherInside(whetherInside);
            }
            if( communityId != null ){
                item.setCommunityId(communityId);
            }
            organizationService.update(item);

            return organizationService.get(id);
        }
        else if(request.getMethod().equals("DELETE")){
            Organization item = organizationService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be deleted doesn't exist");
            }

            organizationService.delete(id);
            return Resp.succ("delete success");
        }
        return Resp.fail("Unknown http method");
    }

    @ApiOperation(value = "查询'组织社区'表中的多条记录或者新增某条记录", notes = "get传参：根据url传入的filters（过滤条件），查询对应的多条数据。数据数量取决于pageNo和pageSize；数据的先后顺序取决于sortItem，sortOrder；   post请求：新增某条记录，传入表字段对应的值")
    @RequestMapping(value = "/organizations",
                    method = { RequestMethod.GET, RequestMethod.POST },
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getOrganizations(
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

        Long total = organizationService.countListWithPagingAndFilter(filters);
        response.addHeader("total", String.valueOf(total));

        return organizationService.getListWithPagingAndFilter(
                        pageNo, pageSize, sortItem, sortOrder, filters, includes, refers, relates);

    }

    @ApiOperation(value = "出入字段数组返回对应字段数据", notes = "get传参：根据url传入的filters（过滤条件），查询对应的多条数据。只返回你传进来的字段对应的数据")
    @RequestMapping(value = "/organizations/columns",
                    method = { RequestMethod.GET },
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getOrganizationsId(
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
        List<Organization> list = organizationService.getListWithPagingAndFilter(
                pageNo, pageSize, sortItem, sortOrder, filters);

        //反射调取get方法
        Class clz = Organization.class;
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



    @ApiOperation(value = "批量创建'组织社区'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/organizations/create//batch",
               method = RequestMethod.POST,
               produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createsOrganizations(
             HttpServletRequest request,
             @RequestHeader(value = "X-Auth-Token", required = false) String token,
             @RequestParam(value = "params", required = false) String params
    ) {

             JSONArray jsonArray = JSON.parseArray(params);
             List<Organization> lists  = new ArrayList<>();

             jsonArray.stream().forEach((Object object) -> {

             JSONObject jsonObject = JSONObject.parseObject(object.toString());

             Organization item = new Organization();
             String id = Tools.getUUID();
                         item.setId(id);
             if(jsonObject.containsKey("name")   ){
            item.setName(jsonObject.get("name").toString());
        }
            item.setCreateTime(new Date());
            item.setLastUpdateTime(new Date());
        if(jsonObject.containsKey("rank")   ){
            item.setRank(new Integer(jsonObject.get("rank").toString()));
        }
        if(jsonObject.containsKey("createPeple")   ){
            item.setCreatePeple(jsonObject.get("createPeple").toString());
        }
        if(jsonObject.containsKey("linkToken")   ){
            item.setLinkToken(jsonObject.get("linkToken").toString());
        }
        if(jsonObject.containsKey("whetherInside")   ){
            item.setWhetherInside(jsonObject.get("whetherInside").toString());
        }
        if(jsonObject.containsKey("communityId")   ){
            item.setCommunityId(jsonObject.get("communityId").toString());
        }
             organizationService.create(item);
             lists.add(organizationService.get(id));

             });
        return lists;
     }

    @ApiOperation(value = "批量删除'组织社区'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
        @RequestMapping(value = "/organizations/delete/batch",
                method = RequestMethod.POST,
                produces = "application/json;charset=UTF-8")
        @ResponseBody
        public Object deletesOrganization(
                HttpServletRequest request,
                @RequestHeader(value = "X-Auth-Token", required = false) String token,
                @RequestParam(value = "ids", required = true) String ids
        ) {

            JSONArray jsonArray = JSON.parseArray(ids);
            jsonArray.stream().forEach(id->{

                 Organization item = organizationService.get(id.toString());
                if (null != item) {
                    organizationService.delete(id.toString());
                }

            });
            return Resp.succ("delete success");
        }
}
