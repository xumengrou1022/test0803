package com.haomostudio.JuniorSpringMVCTemplate.controller;

import com.haomostudio.JuniorSpringMVCTemplate.common.Resp;
import com.haomostudio.JuniorSpringMVCTemplate.po.CcNotificationSetting;
import com.haomostudio.JuniorSpringMVCTemplate.service.CcNotificationSettingService;
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


@Api(description = "通知设置")
@Controller
public class CcNotificationSettingController {

    @Autowired
    CcNotificationSettingService ccNotificationSettingService;

    @Autowired
    HttpServletResponse response;

    @ApiOperation(value = "创建'通知设置'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/cc_notification_settings/new",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createCcNotificationSetting(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "remindingTime", required = false) Integer remindingTime,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "createTime", required = false) String createTime,
            @RequestParam(value = "whetherReminding", required = false) String whetherReminding,
            @RequestParam(value = "reminderModule", required = false) String reminderModule,
            @RequestParam(value = "people", required = false) String people,
            @RequestParam(value = "rank", required = false) Integer rank
    ) {
        CcNotificationSetting item = new CcNotificationSetting();
        String id = Tools.getUUID();
        item.setId(id);
        if( name != null ){
            item.setName(name);
        }
        if( remindingTime != null ){
            item.setRemindingTime(remindingTime);
        }
        if( type != null ){
            item.setType(type);
        }
            item.setCreateTime(new Date());
        if( whetherReminding != null ){
            item.setWhetherReminding(whetherReminding);
        }
        if( reminderModule != null ){
            item.setReminderModule(reminderModule);
        }
        if( people != null ){
            item.setPeople(people);
        }
        if( rank != null ){
            item.setRank(rank);
        }
        ccNotificationSettingService.create(item);

        //若request param为null，但字段定义有默认值，则需从数据库中返回结果
        return ccNotificationSettingService.get(id);
        //return item;
    }

    @ApiOperation(value = "删除'通知设置'表中的某条记录", notes = "根据url传入的数据id，删除整条记录。")
    @RequestMapping(value = "/cc_notification_settings/{cc_notification_setting_id}/delete",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteCcNotificationSetting(
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "cc_notification_setting_id") String id
    ) {
        CcNotificationSetting item = ccNotificationSettingService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be deleted doesn't exist");
        }

        ccNotificationSettingService.delete(id);
        return Resp.succ("delete success");
    }

    @ApiOperation(value = "修改'通知设置'表中的某条记录", notes = "根据url传入的数据id，确定修改表中的某条记录，传入表中字段要修改的信息，不传代表不修改。并返回给View层")
    @RequestMapping(value = "/cc_notification_settings/{cc_notification_setting_id}/edit",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editCcNotificationSetting(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "cc_notification_setting_id") String id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "remindingTime", required = false) Integer remindingTime,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "whetherReminding", required = false) String whetherReminding,
            @RequestParam(value = "reminderModule", required = false) String reminderModule,
            @RequestParam(value = "people", required = false) String people,
            @RequestParam(value = "rank", required = false) Integer rank
    ) {
        CcNotificationSetting item = ccNotificationSettingService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be edited doesn't exist");
        }

        if( name != null ){
            item.setName(name);
        }
        if( remindingTime != null ){
            item.setRemindingTime(remindingTime);
        }
        if( type != null ){
            item.setType(type);
        }
        if( whetherReminding != null ){
            item.setWhetherReminding(whetherReminding);
        }
        if( reminderModule != null ){
            item.setReminderModule(reminderModule);
        }
        if( people != null ){
            item.setPeople(people);
        }
        if( rank != null ){
            item.setRank(rank);
        }
        ccNotificationSettingService.update(item);

        return ccNotificationSettingService.get(id);
    }

    @ApiOperation(value = "查询'通知设置'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/cc_notification_settings/{cc_notification_setting_id}",
                    method = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT},
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getCcNotificationSetting(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "cc_notification_setting_id") String id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "remindingTime", required = false) Integer remindingTime,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "whetherReminding", required = false) String whetherReminding,
            @RequestParam(value = "reminderModule", required = false) String reminderModule,
            @RequestParam(value = "people", required = false) String people,
            @RequestParam(value = "rank", required = false) Integer rank
    ) {
        if(request.getMethod().equals("GET")){
            CcNotificationSetting item = ccNotificationSettingService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be edited doesn't exist");
            }

            return item;
        }
        else if(request.getMethod().equals("PUT")){
            CcNotificationSetting item = ccNotificationSettingService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be edited doesn't exist");
            }

            if( name != null ){
                item.setName(name);
            }
            if( remindingTime != null ){
                item.setRemindingTime(remindingTime);
            }
            if( type != null ){
                item.setType(type);
            }
            if( whetherReminding != null ){
                item.setWhetherReminding(whetherReminding);
            }
            if( reminderModule != null ){
                item.setReminderModule(reminderModule);
            }
            if( people != null ){
                item.setPeople(people);
            }
            if( rank != null ){
                item.setRank(rank);
            }
            ccNotificationSettingService.update(item);

            return ccNotificationSettingService.get(id);
        }
        else if(request.getMethod().equals("DELETE")){
            CcNotificationSetting item = ccNotificationSettingService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be deleted doesn't exist");
            }

            ccNotificationSettingService.delete(id);
            return Resp.succ("delete success");
        }
        return Resp.fail("Unknown http method");
    }

    @ApiOperation(value = "查询'通知设置'表中的多条记录或者新增某条记录", notes = "get传参：根据url传入的filters（过滤条件），查询对应的多条数据。数据数量取决于pageNo和pageSize；数据的先后顺序取决于sortItem，sortOrder；   post请求：新增某条记录，传入表字段对应的值")
    @RequestMapping(value = "/cc_notification_settings",
                    method = { RequestMethod.GET, RequestMethod.POST },
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getCcNotificationSettings(
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

        Long total = ccNotificationSettingService.countListWithPagingAndFilter(filters);
        response.addHeader("total", String.valueOf(total));

        return ccNotificationSettingService.getListWithPagingAndFilter(
                        pageNo, pageSize, sortItem, sortOrder, filters, includes, refers, relates);

    }

    @ApiOperation(value = "出入字段数组返回对应字段数据", notes = "get传参：根据url传入的filters（过滤条件），查询对应的多条数据。只返回你传进来的字段对应的数据")
    @RequestMapping(value = "/cc_notification_settings/columns",
                    method = { RequestMethod.GET },
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getCcNotificationSettingsId(
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
        List<CcNotificationSetting> list = ccNotificationSettingService.getListWithPagingAndFilter(
                pageNo, pageSize, sortItem, sortOrder, filters);

        //反射调取get方法
        Class clz = CcNotificationSetting.class;
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



    @ApiOperation(value = "批量创建'通知设置'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/cc_notification_settings/create//batch",
               method = RequestMethod.POST,
               produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createsCcNotificationSettings(
             HttpServletRequest request,
             @RequestHeader(value = "X-Auth-Token", required = false) String token,
             @RequestParam(value = "params", required = false) String params
    ) {

             JSONArray jsonArray = JSON.parseArray(params);
             List<CcNotificationSetting> lists  = new ArrayList<>();

             jsonArray.stream().forEach((Object object) -> {

             JSONObject jsonObject = JSONObject.parseObject(object.toString());

             CcNotificationSetting item = new CcNotificationSetting();
             String id = Tools.getUUID();
                         item.setId(id);
             if(jsonObject.containsKey("name")   ){
            item.setName(jsonObject.get("name").toString());
        }
        if(jsonObject.containsKey("remindingTime")   ){
            item.setRemindingTime(new Integer(jsonObject.get("remindingTime").toString()));
        }
        if(jsonObject.containsKey("type")   ){
            item.setType(jsonObject.get("type").toString());
        }
            item.setCreateTime(new Date());
        if(jsonObject.containsKey("whetherReminding")   ){
            item.setWhetherReminding(jsonObject.get("whetherReminding").toString());
        }
        if(jsonObject.containsKey("reminderModule")   ){
            item.setReminderModule(jsonObject.get("reminderModule").toString());
        }
        if(jsonObject.containsKey("people")   ){
            item.setPeople(jsonObject.get("people").toString());
        }
        if(jsonObject.containsKey("rank")   ){
            item.setRank(new Integer(jsonObject.get("rank").toString()));
        }
             ccNotificationSettingService.create(item);
             lists.add(ccNotificationSettingService.get(id));

             });
        return lists;
     }

    @ApiOperation(value = "批量删除'通知设置'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
        @RequestMapping(value = "/cc_notification_settings/delete/batch",
                method = RequestMethod.POST,
                produces = "application/json;charset=UTF-8")
        @ResponseBody
        public Object deletesCcNotificationSetting(
                HttpServletRequest request,
                @RequestHeader(value = "X-Auth-Token", required = false) String token,
                @RequestParam(value = "ids", required = true) String ids
        ) {

            JSONArray jsonArray = JSON.parseArray(ids);
            jsonArray.stream().forEach(id->{

                 CcNotificationSetting item = ccNotificationSettingService.get(id.toString());
                if (null != item) {
                    ccNotificationSettingService.delete(id.toString());
                }

            });
            return Resp.succ("delete success");
        }
}
