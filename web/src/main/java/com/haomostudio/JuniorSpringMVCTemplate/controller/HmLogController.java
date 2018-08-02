package com.haomostudio.JuniorSpringMVCTemplate.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haomostudio.JuniorSpringMVCTemplate.common.Resp;
import com.haomostudio.JuniorSpringMVCTemplate.po.HmLog;
import com.haomostudio.JuniorSpringMVCTemplate.service.HmLogService;
import com.haomostudio.JuniorSpringMVCTemplate.service.HmUtils.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


@Api(description = "用户日志")
@Controller
public class HmLogController {

    @Autowired
    HmLogService hmLogService;

    @Autowired
    HttpServletResponse response;

    @ApiOperation(value = "创建'用户日志'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/hm_logs/new",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createHmLog(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "operatingTime", required = false) String operatingTime,
            @RequestParam(value = "auxiliaryInspection", required = false) String auxiliaryInspection,
            @RequestParam(value = "createTime", required = false) String createTime,
            @RequestParam(value = "lastUpdateTime", required = false) String lastUpdateTime,
            @RequestParam(value = "updateUserId", required = false) String updateUserId
    ) {
        HmLog item = new HmLog();
        String id = Tools.getUUID();
        item.setId(id);
        if( userId != null ){
            item.setUserId(userId);
        }
        if( content != null ){
            item.setContent(content);
        }
        if( operatingTime != null ){
            item.setOperatingTime(Tools.convertStringToDate(operatingTime, "yyyy-MM-dd HH:mm:ss"));
        }
        if( auxiliaryInspection != null ){
            item.setAuxiliaryInspection(auxiliaryInspection);
        }
            item.setCreateTime(new Date());
            item.setLastUpdateTime(new Date());
        if( updateUserId != null ){
            item.setUpdateUserId(updateUserId);
        }
        hmLogService.create(item);

        //若request param为null，但字段定义有默认值，则需从数据库中返回结果
        return hmLogService.get(id);
        //return item;
    }

    @ApiOperation(value = "删除'用户日志'表中的某条记录", notes = "根据url传入的数据id，删除整条记录。")
    @RequestMapping(value = "/hm_logs/{hm_log_id}/delete",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteHmLog(
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "hm_log_id") String id
    ) {
        HmLog item = hmLogService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be deleted doesn't exist");
        }

        hmLogService.delete(id);
        return Resp.succ("delete success");
    }

    @ApiOperation(value = "修改'用户日志'表中的某条记录", notes = "根据url传入的数据id，确定修改表中的某条记录，传入表中字段要修改的信息，不传代表不修改。并返回给View层")
    @RequestMapping(value = "/hm_logs/{hm_log_id}/edit",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editHmLog(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "hm_log_id") String id,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "operatingTime", required = false) String operatingTime,
            @RequestParam(value = "auxiliaryInspection", required = false) String auxiliaryInspection,
            @RequestParam(value = "updateUserId", required = false) String updateUserId
    ) {
        HmLog item = hmLogService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be edited doesn't exist");
        }

        if( userId != null ){
            item.setUserId(userId);
        }
        if( content != null ){
            item.setContent(content);
        }
        if( operatingTime != null ){
            item.setOperatingTime(Tools.convertStringToDate(operatingTime, "yyyy-MM-dd HH:mm:ss"));
        }
        if( auxiliaryInspection != null ){
            item.setAuxiliaryInspection(auxiliaryInspection);
        }
            item.setLastUpdateTime(new Date());
        if( updateUserId != null ){
            item.setUpdateUserId(updateUserId);
        }
        hmLogService.update(item);

        return hmLogService.get(id);
    }

    @ApiOperation(value = "查询'用户日志'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/hm_logs/{hm_log_id}",
                    method = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT},
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getHmLog(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "hm_log_id") String id,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "operatingTime", required = false) String operatingTime,
            @RequestParam(value = "auxiliaryInspection", required = false) String auxiliaryInspection,
            @RequestParam(value = "updateUserId", required = false) String updateUserId
    ) {
        if(request.getMethod().equals("GET")){
            HmLog item = hmLogService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be edited doesn't exist");
            }

            return item;
        }
        else if(request.getMethod().equals("PUT")){
            HmLog item = hmLogService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be edited doesn't exist");
            }

            if( userId != null ){
                item.setUserId(userId);
            }
            if( content != null ){
                item.setContent(content);
            }
            if( operatingTime != null ){
                item.setOperatingTime(Tools.convertStringToDate(operatingTime, "yyyy-MM-dd HH:mm:ss"));
            }
            if( auxiliaryInspection != null ){
                item.setAuxiliaryInspection(auxiliaryInspection);
            }
                item.setLastUpdateTime(new Date());
            if( updateUserId != null ){
                item.setUpdateUserId(updateUserId);
            }
            hmLogService.update(item);

            return hmLogService.get(id);
        }
        else if(request.getMethod().equals("DELETE")){
            HmLog item = hmLogService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be deleted doesn't exist");
            }

            hmLogService.delete(id);
            return Resp.succ("delete success");
        }
        return Resp.fail("Unknown http method");
    }

    @ApiOperation(value = "查询'用户日志'表中的多条记录或者新增某条记录", notes = "get传参：根据url传入的filters（过滤条件），查询对应的多条数据。数据数量取决于pageNo和pageSize；数据的先后顺序取决于sortItem，sortOrder；   post请求：新增某条记录，传入表字段对应的值")
    @RequestMapping(value = "/hm_logs",
                    method = { RequestMethod.GET, RequestMethod.POST },
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getHmLogs(
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

        Long total = hmLogService.countListWithPagingAndFilter(filters);
        response.addHeader("total", String.valueOf(total));

        return hmLogService.getListWithPagingAndFilter(
                        pageNo, pageSize, sortItem, sortOrder, filters, includes, refers, relates);

    }

    @ApiOperation(value = "出入字段数组返回对应字段数据", notes = "get传参：根据url传入的filters（过滤条件），查询对应的多条数据。只返回你传进来的字段对应的数据")
    @RequestMapping(value = "/hm_logs/columns",
                    method = { RequestMethod.GET },
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getHmLogsId(
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
        List<HmLog> list = hmLogService.getListWithPagingAndFilter(
                pageNo, pageSize, sortItem, sortOrder, filters);

        //反射调取get方法
        Class clz = HmLog.class;
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



    @ApiOperation(value = "批量创建'用户日志'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/hm_logs/create//batch",
               method = RequestMethod.POST,
               produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createsHmLogs(
             HttpServletRequest request,
             @RequestHeader(value = "X-Auth-Token", required = false) String token,
             @RequestParam(value = "params", required = false) String params
    ) {

             JSONArray jsonArray = JSON.parseArray(params);
             List<HmLog> lists  = new ArrayList<>();

             jsonArray.stream().forEach((Object object) -> {

             JSONObject jsonObject = JSONObject.parseObject(object.toString());

             HmLog item = new HmLog();
             String id = Tools.getUUID();
                         item.setId(id);
             if(jsonObject.containsKey("userId")   ){
            item.setUserId(jsonObject.get("userId").toString());
        }
        if(jsonObject.containsKey("content")   ){
            item.setContent(jsonObject.get("content").toString());
        }
        if(jsonObject.containsKey("operatingTime")   ){
            item.setOperatingTime(Tools.convertStringToDate(jsonObject.get("operatingTime").toString(), "yyyy-MM-dd HH:mm:ss"));
        }
        if(jsonObject.containsKey("auxiliaryInspection")   ){
            item.setAuxiliaryInspection(jsonObject.get("auxiliaryInspection").toString());
        }
            item.setCreateTime(new Date());
            item.setLastUpdateTime(new Date());
        if(jsonObject.containsKey("updateUserId")   ){
            item.setUpdateUserId(jsonObject.get("updateUserId").toString());
        }
             hmLogService.create(item);
             lists.add(hmLogService.get(id));

             });
        return lists;
     }

    @ApiOperation(value = "批量删除'用户日志'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
        @RequestMapping(value = "/hm_logs/delete/batch",
                method = RequestMethod.POST,
                produces = "application/json;charset=UTF-8")
        @ResponseBody
        public Object deletesHmLog(
                HttpServletRequest request,
                @RequestHeader(value = "X-Auth-Token", required = false) String token,
                @RequestParam(value = "ids", required = true) String ids
        ) {

            JSONArray jsonArray = JSON.parseArray(ids);
            jsonArray.stream().forEach(id->{

                 HmLog item = hmLogService.get(id.toString());
                if (null != item) {
                    hmLogService.delete(id.toString());
                }

            });
            return Resp.succ("delete success");
        }
}
