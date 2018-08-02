package com.haomostudio.JuniorSpringMVCTemplate.controller;

import com.haomostudio.JuniorSpringMVCTemplate.common.Resp;
import com.haomostudio.JuniorSpringMVCTemplate.po.HmUser;
import com.haomostudio.JuniorSpringMVCTemplate.service.HmUserService;
import com.haomostudio.JuniorSpringMVCTemplate.service.HmUtils.Tools;
import com.alibaba.fastjson.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;


@Api(description = "用户表")
@Controller
public class HmUserController {

    @Autowired
    HmUserService hmUserService;

    @Autowired
    HttpServletResponse response;

    @ApiOperation(value = "创建'用户表'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/hm_users/new",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createHmUser(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "loginid", required = false) String loginid,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "avatar", required = false) String avatar,
            @RequestParam(value = "createTime", required = false) String createTime,
            @RequestParam(value = "lastUpdateTime", required = false) String lastUpdateTime,
            @RequestParam(value = "lastLoginTime", required = false) String lastLoginTime,
            @RequestParam(value = "departmentCode", required = false) String departmentCode,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "meetingRemindingTime", required = false) Integer meetingRemindingTime,
            @RequestParam(value = "jobTitle", required = false) String jobTitle,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "phoneNumberSpare", required = false) String phoneNumberSpare,
            @RequestParam(value = "rank", required = false) Integer rank,
            @RequestParam(value = "community", required = false) String community,
            @RequestParam(value = "enable", required = false) String enable,
            @RequestParam(value = "hide", required = false) String hide,
            @RequestParam(value = "departmentId", required = false) String departmentId,
            @RequestParam(value = "code", required = false) String code
    ) {
        HmUser item = new HmUser();
        String id = Tools.getUUID();
        item.setId(id);
        if( username != null ){
            item.setUsername(username);
        }
        if( loginid != null ){
            item.setLoginid(loginid);
        }
        if( password != null ){
            item.setPassword(password);
        }
        if( mobile != null ){
            item.setMobile(mobile);
        }
        if( email != null ){
            item.setEmail(email);
        }
        if( avatar != null ){
            item.setAvatar(avatar);
        }
            item.setCreateTime(new Date());
            item.setLastUpdateTime(new Date());
        if( lastLoginTime != null ){
            item.setLastLoginTime(Tools.convertStringToDate(lastLoginTime, "yyyy-MM-dd HH:mm:ss"));
        }
        if( departmentCode != null ){
            item.setDepartmentCode(departmentCode);
        }
        if( roleId != null ){
            item.setRoleId(roleId);
        }
        if( meetingRemindingTime != null ){
            item.setMeetingRemindingTime(meetingRemindingTime);
        }
        if( jobTitle != null ){
            item.setJobTitle(jobTitle);
        }
        if( phoneNumber != null ){
            item.setPhoneNumber(phoneNumber);
        }
        if( phoneNumberSpare != null ){
            item.setPhoneNumberSpare(phoneNumberSpare);
        }
        if( rank != null ){
            item.setRank(rank);
        }
        if( community != null ){
            item.setCommunity(community);
        }
        if( enable != null ){
            item.setEnable(enable);
        }
        if( hide != null ){
            item.setHide(hide);
        }
        if( departmentId != null ){
            item.setDepartmentId(departmentId);
        }
        if( code != null ){
            item.setCode(code);
        }
        hmUserService.create(item);

        //若request param为null，但字段定义有默认值，则需从数据库中返回结果
        return hmUserService.get(id);
        //return item;
    }
//
    @ApiOperation(value = "删除'用户表'表中的某条记录", notes = "根据url传入的数据id，删除整条记录。")
    @RequestMapping(value = "/hm_users/{hm_user_id}/delete",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteHmUser(
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "hm_user_id") String id
    ) {
        HmUser item = hmUserService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be deleted doesn't exist");
        }

        hmUserService.delete(id);
        return Resp.succ("delete success");
    }

    @ApiOperation(value = "修改'用户表'表中的某条记录", notes = "根据url传入的数据id，确定修改表中的某条记录，传入表中字段要修改的信息，不传代表不修改。并返回给View层")
    @RequestMapping(value = "/hm_users/{hm_user_id}/edit",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editHmUser(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "hm_user_id") String id,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "loginid", required = false) String loginid,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "avatar", required = false) String avatar,
            @RequestParam(value = "lastLoginTime", required = false) String lastLoginTime,
            @RequestParam(value = "departmentCode", required = false) String departmentCode,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "meetingRemindingTime", required = false) Integer meetingRemindingTime,
            @RequestParam(value = "jobTitle", required = false) String jobTitle,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "phoneNumberSpare", required = false) String phoneNumberSpare,
            @RequestParam(value = "rank", required = false) Integer rank,
            @RequestParam(value = "community", required = false) String community,
            @RequestParam(value = "enable", required = false) String enable,
            @RequestParam(value = "hide", required = false) String hide,
            @RequestParam(value = "departmentId", required = false) String departmentId,
            @RequestParam(value = "code", required = false) String code
    ) {
        HmUser item = hmUserService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be edited doesn't exist");
        }

        if( username != null ){
            item.setUsername(username);
        }
        if( loginid != null ){
            item.setLoginid(loginid);
        }
        if( password != null ){
            item.setPassword(password);
        }
        if( mobile != null ){
            item.setMobile(mobile);
        }
        if( email != null ){
            item.setEmail(email);
        }
        if( avatar != null ){
            item.setAvatar(avatar);
        }
            item.setLastUpdateTime(new Date());
        if( lastLoginTime != null ){
            item.setLastLoginTime(Tools.convertStringToDate(lastLoginTime, "yyyy-MM-dd HH:mm:ss"));
        }
        if( departmentCode != null ){
            item.setDepartmentCode(departmentCode);
        }
        if( roleId != null ){
            item.setRoleId(roleId);
        }
        if( meetingRemindingTime != null ){
            item.setMeetingRemindingTime(meetingRemindingTime);
        }
        if( jobTitle != null ){
            item.setJobTitle(jobTitle);
        }
        if( phoneNumber != null ){
            item.setPhoneNumber(phoneNumber);
        }
        if( phoneNumberSpare != null ){
            item.setPhoneNumberSpare(phoneNumberSpare);
        }
        if( rank != null ){
            item.setRank(rank);
        }
        if( community != null ){
            item.setCommunity(community);
        }
        if( enable != null ){
            item.setEnable(enable);
        }
        if( hide != null ){
            item.setHide(hide);
        }
        if( departmentId != null ){
            item.setDepartmentId(departmentId);
        }
        if( code != null ){
            item.setCode(code);
        }
        hmUserService.update(item);

        return hmUserService.get(id);
    }

    @ApiOperation(value = "查询'用户表'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/hm_users/{hm_user_id}",
                    method = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT},
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getHmUser(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "hm_user_id") String id,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "loginid", required = false) String loginid,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "avatar", required = false) String avatar,
            @RequestParam(value = "lastLoginTime", required = false) String lastLoginTime,
            @RequestParam(value = "departmentCode", required = false) String departmentCode,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "meetingRemindingTime", required = false) Integer meetingRemindingTime,
            @RequestParam(value = "jobTitle", required = false) String jobTitle,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "phoneNumberSpare", required = false) String phoneNumberSpare,
            @RequestParam(value = "rank", required = false) Integer rank,
            @RequestParam(value = "community", required = false) String community,
            @RequestParam(value = "enable", required = false) String enable,
            @RequestParam(value = "hide", required = false) String hide,
            @RequestParam(value = "departmentId", required = false) String departmentId,
            @RequestParam(value = "code", required = false) String code
    ) {
        if(request.getMethod().equals("GET")){
            HmUser item = hmUserService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be edited doesn't exist");
            }

            return item;
        }
        else if(request.getMethod().equals("PUT")){
            HmUser item = hmUserService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be edited doesn't exist");
            }

            if( username != null ){
                item.setUsername(username);
            }
            if( loginid != null ){
                item.setLoginid(loginid);
            }
            if( password != null ){
                item.setPassword(password);
            }
            if( mobile != null ){
                item.setMobile(mobile);
            }
            if( email != null ){
                item.setEmail(email);
            }
            if( avatar != null ){
                item.setAvatar(avatar);
            }
                item.setLastUpdateTime(new Date());
            if( lastLoginTime != null ){
                item.setLastLoginTime(Tools.convertStringToDate(lastLoginTime, "yyyy-MM-dd HH:mm:ss"));
            }
            if( departmentCode != null ){
                item.setDepartmentCode(departmentCode);
            }
            if( roleId != null ){
                item.setRoleId(roleId);
            }
            if( meetingRemindingTime != null ){
                item.setMeetingRemindingTime(meetingRemindingTime);
            }
            if( jobTitle != null ){
                item.setJobTitle(jobTitle);
            }
            if( phoneNumber != null ){
                item.setPhoneNumber(phoneNumber);
            }
            if( phoneNumberSpare != null ){
                item.setPhoneNumberSpare(phoneNumberSpare);
            }
            if( rank != null ){
                item.setRank(rank);
            }
            if( community != null ){
                item.setCommunity(community);
            }
            if( enable != null ){
                item.setEnable(enable);
            }
            if( hide != null ){
                item.setHide(hide);
            }
            if( departmentId != null ){
                item.setDepartmentId(departmentId);
            }
            if( code != null ){
                item.setCode(code);
            }
            hmUserService.update(item);

            return hmUserService.get(id);
        }
        else if(request.getMethod().equals("DELETE")){
            HmUser item = hmUserService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be deleted doesn't exist");
            }

            hmUserService.delete(id);
            return Resp.succ("delete success");
        }
        return Resp.fail("Unknown http method");
    }

    @ApiOperation(value = "查询'用户表'表中的多条记录或者新增某条记录", notes = "get传参：根据url传入的filters（过滤条件），查询对应的多条数据。数据数量取决于pageNo和pageSize；数据的先后顺序取决于sortItem，sortOrder；   post请求：新增某条记录，传入表字段对应的值")
    @RequestMapping(value = "/hm_users",
                    method = { RequestMethod.GET, RequestMethod.POST },
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getHmUsers(
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

        Long total = hmUserService.countListWithPagingAndFilter(filters);
        response.addHeader("total", String.valueOf(total));

        return hmUserService.getListWithPagingAndFilter(
                        pageNo, pageSize, sortItem, sortOrder, filters, includes, refers, relates);

    }

    @ApiOperation(value = "出入字段数组返回对应字段数据", notes = "get传参：根据url传入的filters（过滤条件），查询对应的多条数据。只返回你传进来的字段对应的数据")
    @RequestMapping(value = "/hm_users/columns",
                    method = { RequestMethod.GET },
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getHmUsersId(
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
        List<HmUser> list = hmUserService.getListWithPagingAndFilter(
                pageNo, pageSize, sortItem, sortOrder, filters);

        //反射调取get方法
        Class clz = HmUser.class;
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



    @ApiOperation(value = "批量创建'用户表'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/hm_users/create//batch",
               method = RequestMethod.POST,
               produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createsHmUsers(
             HttpServletRequest request,
             @RequestHeader(value = "X-Auth-Token", required = false) String token,
             @RequestParam(value = "params", required = false) String params
    ) {

             JSONArray jsonArray = JSON.parseArray(params);
             List<HmUser> lists  = new ArrayList<>();

             jsonArray.stream().forEach((Object object) -> {

             JSONObject jsonObject = JSONObject.parseObject(object.toString());

             HmUser item = new HmUser();
             String id = Tools.getUUID();
                         item.setId(id);
             if(jsonObject.containsKey("username")   ){
            item.setUsername(jsonObject.get("username").toString());
        }
        if(jsonObject.containsKey("loginid")   ){
            item.setLoginid(jsonObject.get("loginid").toString());
        }
        if(jsonObject.containsKey("password")   ){
            item.setPassword(jsonObject.get("password").toString());
        }
        if(jsonObject.containsKey("mobile")   ){
            item.setMobile(jsonObject.get("mobile").toString());
        }
        if(jsonObject.containsKey("email")   ){
            item.setEmail(jsonObject.get("email").toString());
        }
        if(jsonObject.containsKey("avatar")   ){
            item.setAvatar(jsonObject.get("avatar").toString());
        }
            item.setCreateTime(new Date());
            item.setLastUpdateTime(new Date());
        if(jsonObject.containsKey("lastLoginTime")   ){
            item.setLastLoginTime(Tools.convertStringToDate(jsonObject.get("lastLoginTime").toString(), "yyyy-MM-dd HH:mm:ss"));
        }
        if(jsonObject.containsKey("departmentCode")   ){
            item.setDepartmentCode(jsonObject.get("departmentCode").toString());
        }
        if(jsonObject.containsKey("roleId")   ){
            item.setRoleId(jsonObject.get("roleId").toString());
        }
        if(jsonObject.containsKey("meetingRemindingTime")   ){
            item.setMeetingRemindingTime(new Integer(jsonObject.get("meetingRemindingTime").toString()));
        }
        if(jsonObject.containsKey("jobTitle")   ){
            item.setJobTitle(jsonObject.get("jobTitle").toString());
        }
        if(jsonObject.containsKey("phoneNumber")   ){
            item.setPhoneNumber(jsonObject.get("phoneNumber").toString());
        }
        if(jsonObject.containsKey("phoneNumberSpare")   ){
            item.setPhoneNumberSpare(jsonObject.get("phoneNumberSpare").toString());
        }
        if(jsonObject.containsKey("rank")   ){
            item.setRank(new Integer(jsonObject.get("rank").toString()));
        }
        if(jsonObject.containsKey("community")   ){
            item.setCommunity(jsonObject.get("community").toString());
        }
        if(jsonObject.containsKey("enable")   ){
            item.setEnable(jsonObject.get("enable").toString());
        }
        if(jsonObject.containsKey("hide")   ){
            item.setHide(jsonObject.get("hide").toString());
        }
        if(jsonObject.containsKey("departmentId")   ){
            item.setDepartmentId(jsonObject.get("departmentId").toString());
        }
        if(jsonObject.containsKey("code")   ){
            item.setCode(jsonObject.get("code").toString());
        }
             hmUserService.create(item);
             lists.add(hmUserService.get(id));

             });
        return lists;
     }

    @ApiOperation(value = "批量删除'用户表'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
        @RequestMapping(value = "/hm_users/delete/batch",
                method = RequestMethod.POST,
                produces = "application/json;charset=UTF-8")
        @ResponseBody
        public Object deletesHmUser(
                HttpServletRequest request,
                @RequestHeader(value = "X-Auth-Token", required = false) String token,
                @RequestParam(value = "ids", required = true) String ids
        ) {

            JSONArray jsonArray = JSON.parseArray(ids);
            jsonArray.stream().forEach(id->{

                 HmUser item = hmUserService.get(id.toString());
                if (null != item) {
                    hmUserService.delete(id.toString());
                }

            });
            return Resp.succ("delete success");
        }
}
