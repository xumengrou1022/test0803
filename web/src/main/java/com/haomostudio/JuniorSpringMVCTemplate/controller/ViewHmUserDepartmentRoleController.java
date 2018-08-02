package com.haomostudio.JuniorSpringMVCTemplate.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haomostudio.JuniorSpringMVCTemplate.common.Resp;
import com.haomostudio.JuniorSpringMVCTemplate.po.ViewHmUserDepartmentRole;
import com.haomostudio.JuniorSpringMVCTemplate.service.HmUtils.Tools;
import com.haomostudio.JuniorSpringMVCTemplate.service.ViewHmUserDepartmentRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


@Api(description = "用户部门角色视图")
@Controller
public class ViewHmUserDepartmentRoleController {

    @Autowired
    ViewHmUserDepartmentRoleService viewHmUserDepartmentRoleService;

    @Autowired
    HttpServletResponse response;

    @ApiOperation(value = "创建'用户部门角色视图'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/view_hm_user_department_roles/new",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createViewHmUserDepartmentRole(
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
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "depId", required = false) String depId,
            @RequestParam(value = "depParentDepartmentCode", required = false) String depParentDepartmentCode,
            @RequestParam(value = "depCode", required = false) String depCode,
            @RequestParam(value = "depName", required = false) String depName,
            @RequestParam(value = "depDescription", required = false) String depDescription,
            @RequestParam(value = "depCreateTime", required = false) String depCreateTime,
            @RequestParam(value = "depLastUpdateTime", required = false) String depLastUpdateTime,
            @RequestParam(value = "depSimpleName", required = false) String depSimpleName,
            @RequestParam(value = "depOrganizationName", required = false) String depOrganizationName,
            @RequestParam(value = "depPersonLiable", required = false) String depPersonLiable,
            @RequestParam(value = "depOrganizationId", required = false) String depOrganizationId,
            @RequestParam(value = "depRank", required = false) Integer depRank,
            @RequestParam(value = "depNumberReading", required = false) Integer depNumberReading,
            @RequestParam(value = "depPersonLiableName", required = false) String depPersonLiableName,
            @RequestParam(value = "rolId", required = false) String rolId,
            @RequestParam(value = "rolName", required = false) String rolName,
            @RequestParam(value = "rolDescription", required = false) String rolDescription,
            @RequestParam(value = "rolCreateTime", required = false) String rolCreateTime,
            @RequestParam(value = "rolGrade", required = false) Integer rolGrade,
            @RequestParam(value = "rolAbbreviation", required = false) String rolAbbreviation,
            @RequestParam(value = "rolLastUpdateTime", required = false) String rolLastUpdateTime,
            @RequestParam(value = "rolRank", required = false) Integer rolRank
    ) {
        ViewHmUserDepartmentRole item = new ViewHmUserDepartmentRole();
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
        if( depId != null ){
            item.setDepId(depId);
        }
        if( depParentDepartmentCode != null ){
            item.setDepParentDepartmentCode(depParentDepartmentCode);
        }
        if( depCode != null ){
            item.setDepCode(depCode);
        }
        if( depName != null ){
            item.setDepName(depName);
        }
        if( depDescription != null ){
            item.setDepDescription(depDescription);
        }
        if( depCreateTime != null ){
            item.setDepCreateTime(Tools.convertStringToDate(depCreateTime, "yyyy-MM-dd HH:mm:ss"));
        }
        if( depLastUpdateTime != null ){
            item.setDepLastUpdateTime(Tools.convertStringToDate(depLastUpdateTime, "yyyy-MM-dd HH:mm:ss"));
        }
        if( depSimpleName != null ){
            item.setDepSimpleName(depSimpleName);
        }
        if( depOrganizationName != null ){
            item.setDepOrganizationName(depOrganizationName);
        }
        if( depPersonLiable != null ){
            item.setDepPersonLiable(depPersonLiable);
        }
        if( depOrganizationId != null ){
            item.setDepOrganizationId(depOrganizationId);
        }
        if( depRank != null ){
            item.setDepRank(depRank);
        }
        if( depNumberReading != null ){
            item.setDepNumberReading(depNumberReading);
        }
        if( depPersonLiableName != null ){
            item.setDepPersonLiableName(depPersonLiableName);
        }
        if( rolId != null ){
            item.setRolId(rolId);
        }
        if( rolName != null ){
            item.setRolName(rolName);
        }
        if( rolDescription != null ){
            item.setRolDescription(rolDescription);
        }
        if( rolCreateTime != null ){
            item.setRolCreateTime(Tools.convertStringToDate(rolCreateTime, "yyyy-MM-dd HH:mm:ss"));
        }
        if( rolGrade != null ){
            item.setRolGrade(rolGrade);
        }
        if( rolAbbreviation != null ){
            item.setRolAbbreviation(rolAbbreviation);
        }
        if( rolLastUpdateTime != null ){
            item.setRolLastUpdateTime(Tools.convertStringToDate(rolLastUpdateTime, "yyyy-MM-dd HH:mm:ss"));
        }
        if( rolRank != null ){
            item.setRolRank(rolRank);
        }
        viewHmUserDepartmentRoleService.create(item);

        //若request param为null，但字段定义有默认值，则需从数据库中返回结果
        return viewHmUserDepartmentRoleService.get(id);
        //return item;
    }

    @ApiOperation(value = "删除'用户部门角色视图'表中的某条记录", notes = "根据url传入的数据id，删除整条记录。")
    @RequestMapping(value = "/view_hm_user_department_roles/{view_hm_user_department_role_id}/delete",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteViewHmUserDepartmentRole(
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "view_hm_user_department_role_id") String id
    ) {
        ViewHmUserDepartmentRole item = viewHmUserDepartmentRoleService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be deleted doesn't exist");
        }

        viewHmUserDepartmentRoleService.delete(id);
        return Resp.succ("delete success");
    }

    @ApiOperation(value = "修改'用户部门角色视图'表中的某条记录", notes = "根据url传入的数据id，确定修改表中的某条记录，传入表中字段要修改的信息，不传代表不修改。并返回给View层")
    @RequestMapping(value = "/view_hm_user_department_roles/{view_hm_user_department_role_id}/edit",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editViewHmUserDepartmentRole(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "view_hm_user_department_role_id") String id,
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
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "depId", required = false) String depId,
            @RequestParam(value = "depParentDepartmentCode", required = false) String depParentDepartmentCode,
            @RequestParam(value = "depCode", required = false) String depCode,
            @RequestParam(value = "depName", required = false) String depName,
            @RequestParam(value = "depDescription", required = false) String depDescription,
            @RequestParam(value = "depCreateTime", required = false) String depCreateTime,
            @RequestParam(value = "depLastUpdateTime", required = false) String depLastUpdateTime,
            @RequestParam(value = "depSimpleName", required = false) String depSimpleName,
            @RequestParam(value = "depOrganizationName", required = false) String depOrganizationName,
            @RequestParam(value = "depPersonLiable", required = false) String depPersonLiable,
            @RequestParam(value = "depOrganizationId", required = false) String depOrganizationId,
            @RequestParam(value = "depRank", required = false) Integer depRank,
            @RequestParam(value = "depNumberReading", required = false) Integer depNumberReading,
            @RequestParam(value = "depPersonLiableName", required = false) String depPersonLiableName,
            @RequestParam(value = "rolId", required = false) String rolId,
            @RequestParam(value = "rolName", required = false) String rolName,
            @RequestParam(value = "rolDescription", required = false) String rolDescription,
            @RequestParam(value = "rolCreateTime", required = false) String rolCreateTime,
            @RequestParam(value = "rolGrade", required = false) Integer rolGrade,
            @RequestParam(value = "rolAbbreviation", required = false) String rolAbbreviation,
            @RequestParam(value = "rolLastUpdateTime", required = false) String rolLastUpdateTime,
            @RequestParam(value = "rolRank", required = false) Integer rolRank
    ) {
        ViewHmUserDepartmentRole item = viewHmUserDepartmentRoleService.get(id);
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
        if( depId != null ){
            item.setDepId(depId);
        }
        if( depParentDepartmentCode != null ){
            item.setDepParentDepartmentCode(depParentDepartmentCode);
        }
        if( depCode != null ){
            item.setDepCode(depCode);
        }
        if( depName != null ){
            item.setDepName(depName);
        }
        if( depDescription != null ){
            item.setDepDescription(depDescription);
        }
        if( depCreateTime != null ){
            item.setDepCreateTime(Tools.convertStringToDate(depCreateTime, "yyyy-MM-dd HH:mm:ss"));
        }
        if( depLastUpdateTime != null ){
            item.setDepLastUpdateTime(Tools.convertStringToDate(depLastUpdateTime, "yyyy-MM-dd HH:mm:ss"));
        }
        if( depSimpleName != null ){
            item.setDepSimpleName(depSimpleName);
        }
        if( depOrganizationName != null ){
            item.setDepOrganizationName(depOrganizationName);
        }
        if( depPersonLiable != null ){
            item.setDepPersonLiable(depPersonLiable);
        }
        if( depOrganizationId != null ){
            item.setDepOrganizationId(depOrganizationId);
        }
        if( depRank != null ){
            item.setDepRank(depRank);
        }
        if( depNumberReading != null ){
            item.setDepNumberReading(depNumberReading);
        }
        if( depPersonLiableName != null ){
            item.setDepPersonLiableName(depPersonLiableName);
        }
        if( rolId != null ){
            item.setRolId(rolId);
        }
        if( rolName != null ){
            item.setRolName(rolName);
        }
        if( rolDescription != null ){
            item.setRolDescription(rolDescription);
        }
        if( rolCreateTime != null ){
            item.setRolCreateTime(Tools.convertStringToDate(rolCreateTime, "yyyy-MM-dd HH:mm:ss"));
        }
        if( rolGrade != null ){
            item.setRolGrade(rolGrade);
        }
        if( rolAbbreviation != null ){
            item.setRolAbbreviation(rolAbbreviation);
        }
        if( rolLastUpdateTime != null ){
            item.setRolLastUpdateTime(Tools.convertStringToDate(rolLastUpdateTime, "yyyy-MM-dd HH:mm:ss"));
        }
        if( rolRank != null ){
            item.setRolRank(rolRank);
        }
        viewHmUserDepartmentRoleService.update(item);

        return viewHmUserDepartmentRoleService.get(id);
    }

    @ApiOperation(value = "查询'用户部门角色视图'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/view_hm_user_department_roles/{view_hm_user_department_role_id}",
                    method = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT},
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getViewHmUserDepartmentRole(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "view_hm_user_department_role_id") String id,
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
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "depId", required = false) String depId,
            @RequestParam(value = "depParentDepartmentCode", required = false) String depParentDepartmentCode,
            @RequestParam(value = "depCode", required = false) String depCode,
            @RequestParam(value = "depName", required = false) String depName,
            @RequestParam(value = "depDescription", required = false) String depDescription,
            @RequestParam(value = "depCreateTime", required = false) String depCreateTime,
            @RequestParam(value = "depLastUpdateTime", required = false) String depLastUpdateTime,
            @RequestParam(value = "depSimpleName", required = false) String depSimpleName,
            @RequestParam(value = "depOrganizationName", required = false) String depOrganizationName,
            @RequestParam(value = "depPersonLiable", required = false) String depPersonLiable,
            @RequestParam(value = "depOrganizationId", required = false) String depOrganizationId,
            @RequestParam(value = "depRank", required = false) Integer depRank,
            @RequestParam(value = "depNumberReading", required = false) Integer depNumberReading,
            @RequestParam(value = "depPersonLiableName", required = false) String depPersonLiableName,
            @RequestParam(value = "rolId", required = false) String rolId,
            @RequestParam(value = "rolName", required = false) String rolName,
            @RequestParam(value = "rolDescription", required = false) String rolDescription,
            @RequestParam(value = "rolCreateTime", required = false) String rolCreateTime,
            @RequestParam(value = "rolGrade", required = false) Integer rolGrade,
            @RequestParam(value = "rolAbbreviation", required = false) String rolAbbreviation,
            @RequestParam(value = "rolLastUpdateTime", required = false) String rolLastUpdateTime,
            @RequestParam(value = "rolRank", required = false) Integer rolRank
    ) {
        if(request.getMethod().equals("GET")){
            ViewHmUserDepartmentRole item = viewHmUserDepartmentRoleService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be edited doesn't exist");
            }

            return item;
        }
        else if(request.getMethod().equals("PUT")){
            ViewHmUserDepartmentRole item = viewHmUserDepartmentRoleService.get(id);
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
            if( depId != null ){
                item.setDepId(depId);
            }
            if( depParentDepartmentCode != null ){
                item.setDepParentDepartmentCode(depParentDepartmentCode);
            }
            if( depCode != null ){
                item.setDepCode(depCode);
            }
            if( depName != null ){
                item.setDepName(depName);
            }
            if( depDescription != null ){
                item.setDepDescription(depDescription);
            }
            if( depCreateTime != null ){
                item.setDepCreateTime(Tools.convertStringToDate(depCreateTime, "yyyy-MM-dd HH:mm:ss"));
            }
            if( depLastUpdateTime != null ){
                item.setDepLastUpdateTime(Tools.convertStringToDate(depLastUpdateTime, "yyyy-MM-dd HH:mm:ss"));
            }
            if( depSimpleName != null ){
                item.setDepSimpleName(depSimpleName);
            }
            if( depOrganizationName != null ){
                item.setDepOrganizationName(depOrganizationName);
            }
            if( depPersonLiable != null ){
                item.setDepPersonLiable(depPersonLiable);
            }
            if( depOrganizationId != null ){
                item.setDepOrganizationId(depOrganizationId);
            }
            if( depRank != null ){
                item.setDepRank(depRank);
            }
            if( depNumberReading != null ){
                item.setDepNumberReading(depNumberReading);
            }
            if( depPersonLiableName != null ){
                item.setDepPersonLiableName(depPersonLiableName);
            }
            if( rolId != null ){
                item.setRolId(rolId);
            }
            if( rolName != null ){
                item.setRolName(rolName);
            }
            if( rolDescription != null ){
                item.setRolDescription(rolDescription);
            }
            if( rolCreateTime != null ){
                item.setRolCreateTime(Tools.convertStringToDate(rolCreateTime, "yyyy-MM-dd HH:mm:ss"));
            }
            if( rolGrade != null ){
                item.setRolGrade(rolGrade);
            }
            if( rolAbbreviation != null ){
                item.setRolAbbreviation(rolAbbreviation);
            }
            if( rolLastUpdateTime != null ){
                item.setRolLastUpdateTime(Tools.convertStringToDate(rolLastUpdateTime, "yyyy-MM-dd HH:mm:ss"));
            }
            if( rolRank != null ){
                item.setRolRank(rolRank);
            }
            viewHmUserDepartmentRoleService.update(item);

            return viewHmUserDepartmentRoleService.get(id);
        }
        else if(request.getMethod().equals("DELETE")){
            ViewHmUserDepartmentRole item = viewHmUserDepartmentRoleService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be deleted doesn't exist");
            }

            viewHmUserDepartmentRoleService.delete(id);
            return Resp.succ("delete success");
        }
        return Resp.fail("Unknown http method");
    }

    @ApiOperation(value = "查询'用户部门角色视图'表中的多条记录或者新增某条记录", notes = "get传参：根据url传入的filters（过滤条件），查询对应的多条数据。数据数量取决于pageNo和pageSize；数据的先后顺序取决于sortItem，sortOrder；   post请求：新增某条记录，传入表字段对应的值")
    @RequestMapping(value = "/view_hm_user_department_roles",
                    method = { RequestMethod.GET, RequestMethod.POST },
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getViewHmUserDepartmentRoles(
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

        Long total = viewHmUserDepartmentRoleService.countListWithPagingAndFilter(filters);
        response.addHeader("total", String.valueOf(total));

        return viewHmUserDepartmentRoleService.getListWithPagingAndFilter(
                        pageNo, pageSize, sortItem, sortOrder, filters, includes, refers, relates);

    }

    @ApiOperation(value = "出入字段数组返回对应字段数据", notes = "get传参：根据url传入的filters（过滤条件），查询对应的多条数据。只返回你传进来的字段对应的数据")
    @RequestMapping(value = "/view_hm_user_department_roles/columns",
                    method = { RequestMethod.GET },
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getViewHmUserDepartmentRolesId(
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
        List<ViewHmUserDepartmentRole> list = viewHmUserDepartmentRoleService.getListWithPagingAndFilter(
                pageNo, pageSize, sortItem, sortOrder, filters);

        //反射调取get方法
        Class clz = ViewHmUserDepartmentRole.class;
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



    @ApiOperation(value = "批量创建'用户部门角色视图'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/view_hm_user_department_roles/create//batch",
               method = RequestMethod.POST,
               produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createsViewHmUserDepartmentRoles(
             HttpServletRequest request,
             @RequestHeader(value = "X-Auth-Token", required = false) String token,
             @RequestParam(value = "params", required = false) String params
    ) {

             JSONArray jsonArray = JSON.parseArray(params);
             List<ViewHmUserDepartmentRole> lists  = new ArrayList<>();

             jsonArray.stream().forEach((Object object) -> {

             JSONObject jsonObject = JSONObject.parseObject(object.toString());

             ViewHmUserDepartmentRole item = new ViewHmUserDepartmentRole();
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
        if(jsonObject.containsKey("depId")   ){
            item.setDepId(jsonObject.get("depId").toString());
        }
        if(jsonObject.containsKey("depParentDepartmentCode")   ){
            item.setDepParentDepartmentCode(jsonObject.get("depParentDepartmentCode").toString());
        }
        if(jsonObject.containsKey("depCode")   ){
            item.setDepCode(jsonObject.get("depCode").toString());
        }
        if(jsonObject.containsKey("depName")   ){
            item.setDepName(jsonObject.get("depName").toString());
        }
        if(jsonObject.containsKey("depDescription")   ){
            item.setDepDescription(jsonObject.get("depDescription").toString());
        }
        if(jsonObject.containsKey("depCreateTime")   ){
            item.setDepCreateTime(Tools.convertStringToDate(jsonObject.get("depCreateTime").toString(), "yyyy-MM-dd HH:mm:ss"));
        }
        if(jsonObject.containsKey("depLastUpdateTime")   ){
            item.setDepLastUpdateTime(Tools.convertStringToDate(jsonObject.get("depLastUpdateTime").toString(), "yyyy-MM-dd HH:mm:ss"));
        }
        if(jsonObject.containsKey("depSimpleName")   ){
            item.setDepSimpleName(jsonObject.get("depSimpleName").toString());
        }
        if(jsonObject.containsKey("depOrganizationName")   ){
            item.setDepOrganizationName(jsonObject.get("depOrganizationName").toString());
        }
        if(jsonObject.containsKey("depPersonLiable")   ){
            item.setDepPersonLiable(jsonObject.get("depPersonLiable").toString());
        }
        if(jsonObject.containsKey("depOrganizationId")   ){
            item.setDepOrganizationId(jsonObject.get("depOrganizationId").toString());
        }
        if(jsonObject.containsKey("depRank")   ){
            item.setDepRank(new Integer(jsonObject.get("depRank").toString()));
        }
        if(jsonObject.containsKey("depNumberReading")   ){
            item.setDepNumberReading(new Integer(jsonObject.get("depNumberReading").toString()));
        }
        if(jsonObject.containsKey("depPersonLiableName")   ){
            item.setDepPersonLiableName(jsonObject.get("depPersonLiableName").toString());
        }
        if(jsonObject.containsKey("rolId")   ){
            item.setRolId(jsonObject.get("rolId").toString());
        }
        if(jsonObject.containsKey("rolName")   ){
            item.setRolName(jsonObject.get("rolName").toString());
        }
        if(jsonObject.containsKey("rolDescription")   ){
            item.setRolDescription(jsonObject.get("rolDescription").toString());
        }
        if(jsonObject.containsKey("rolCreateTime")   ){
            item.setRolCreateTime(Tools.convertStringToDate(jsonObject.get("rolCreateTime").toString(), "yyyy-MM-dd HH:mm:ss"));
        }
        if(jsonObject.containsKey("rolGrade")   ){
            item.setRolGrade(new Integer(jsonObject.get("rolGrade").toString()));
        }
        if(jsonObject.containsKey("rolAbbreviation")   ){
            item.setRolAbbreviation(jsonObject.get("rolAbbreviation").toString());
        }
        if(jsonObject.containsKey("rolLastUpdateTime")   ){
            item.setRolLastUpdateTime(Tools.convertStringToDate(jsonObject.get("rolLastUpdateTime").toString(), "yyyy-MM-dd HH:mm:ss"));
        }
        if(jsonObject.containsKey("rolRank")   ){
            item.setRolRank(new Integer(jsonObject.get("rolRank").toString()));
        }
             viewHmUserDepartmentRoleService.create(item);
             lists.add(viewHmUserDepartmentRoleService.get(id));

             });
        return lists;
     }

    @ApiOperation(value = "批量删除'用户部门角色视图'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
        @RequestMapping(value = "/view_hm_user_department_roles/delete/batch",
                method = RequestMethod.POST,
                produces = "application/json;charset=UTF-8")
        @ResponseBody
        public Object deletesViewHmUserDepartmentRole(
                HttpServletRequest request,
                @RequestHeader(value = "X-Auth-Token", required = false) String token,
                @RequestParam(value = "ids", required = true) String ids
        ) {

            JSONArray jsonArray = JSON.parseArray(ids);
            jsonArray.stream().forEach(id->{

                 ViewHmUserDepartmentRole item = viewHmUserDepartmentRoleService.get(id.toString());
                if (null != item) {
                    viewHmUserDepartmentRoleService.delete(id.toString());
                }

            });
            return Resp.succ("delete success");
        }
}
