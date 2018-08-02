package com.haomostudio.JuniorSpringMVCTemplate.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haomostudio.JuniorSpringMVCTemplate.common.Resp;
import com.haomostudio.JuniorSpringMVCTemplate.po.CcLogging;
import com.haomostudio.JuniorSpringMVCTemplate.service.CcLoggingService;
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


@Api(description = "日志记录")
@Controller
public class CcLoggingController {

    @Autowired
    CcLoggingService ccLoggingService;

    @Autowired
    HttpServletResponse response;

    @ApiOperation(value = "创建'日志记录'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/cc_loggings/new",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createCcLogging(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "operator", required = false) String operator,
            @RequestParam(value = "loginTimePeriod", required = false) String loginTimePeriod,
            @RequestParam(value = "operatingTimePeriod", required = false) String operatingTimePeriod,
            @RequestParam(value = "loginType", required = false) String loginType,
            @RequestParam(value = "typeOperation", required = false) String typeOperation,
            @RequestParam(value = "operatingModule", required = false) String operatingModule,
            @RequestParam(value = "affiliation", required = false) String affiliation,
            @RequestParam(value = "theirDepartment", required = false) String theirDepartment,
            @RequestParam(value = "logContent", required = false) String logContent,
            @RequestParam(value = "createTime", required = false) String createTime,
            @RequestParam(value = "lastUpdateTime", required = false) String lastUpdateTime,
            @RequestParam(value = "operatorId", required = false) String operatorId
    ) {
        CcLogging item = new CcLogging();
        String id = Tools.getUUID();
        item.setId(id);
        if( operator != null ){
            item.setOperator(operator);
        }
        if( loginTimePeriod != null ){
            item.setLoginTimePeriod(Tools.convertStringToDate(loginTimePeriod, "yyyy-MM-dd HH:mm:ss"));
        }
        if( operatingTimePeriod != null ){
            item.setOperatingTimePeriod(Tools.convertStringToDate(operatingTimePeriod, "yyyy-MM-dd HH:mm:ss"));
        }
        if( loginType != null ){
            item.setLoginType(loginType);
        }
        if( typeOperation != null ){
            item.setTypeOperation(typeOperation);
        }
        if( operatingModule != null ){
            item.setOperatingModule(operatingModule);
        }
        if( affiliation != null ){
            item.setAffiliation(affiliation);
        }
        if( theirDepartment != null ){
            item.setTheirDepartment(theirDepartment);
        }
        if( logContent != null ){
            item.setLogContent(logContent);
        }
            item.setCreateTime(new Date());
            item.setLastUpdateTime(new Date());
        if( operatorId != null ){
            item.setOperatorId(operatorId);
        }
        ccLoggingService.create(item);

        //若request param为null，但字段定义有默认值，则需从数据库中返回结果
        return ccLoggingService.get(id);
        //return item;
    }

    @ApiOperation(value = "删除'日志记录'表中的某条记录", notes = "根据url传入的数据id，删除整条记录。")
    @RequestMapping(value = "/cc_loggings/{cc_logging_id}/delete",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteCcLogging(
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "cc_logging_id") String id
    ) {
        CcLogging item = ccLoggingService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be deleted doesn't exist");
        }

        ccLoggingService.delete(id);
        return Resp.succ("delete success");
    }

    @ApiOperation(value = "修改'日志记录'表中的某条记录", notes = "根据url传入的数据id，确定修改表中的某条记录，传入表中字段要修改的信息，不传代表不修改。并返回给View层")
    @RequestMapping(value = "/cc_loggings/{cc_logging_id}/edit",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editCcLogging(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "cc_logging_id") String id,
            @RequestParam(value = "operator", required = false) String operator,
            @RequestParam(value = "loginTimePeriod", required = false) String loginTimePeriod,
            @RequestParam(value = "operatingTimePeriod", required = false) String operatingTimePeriod,
            @RequestParam(value = "loginType", required = false) String loginType,
            @RequestParam(value = "typeOperation", required = false) String typeOperation,
            @RequestParam(value = "operatingModule", required = false) String operatingModule,
            @RequestParam(value = "affiliation", required = false) String affiliation,
            @RequestParam(value = "theirDepartment", required = false) String theirDepartment,
            @RequestParam(value = "logContent", required = false) String logContent,
            @RequestParam(value = "operatorId", required = false) String operatorId
    ) {
        CcLogging item = ccLoggingService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be edited doesn't exist");
        }

        if( operator != null ){
            item.setOperator(operator);
        }
        if( loginTimePeriod != null ){
            item.setLoginTimePeriod(Tools.convertStringToDate(loginTimePeriod, "yyyy-MM-dd HH:mm:ss"));
        }
        if( operatingTimePeriod != null ){
            item.setOperatingTimePeriod(Tools.convertStringToDate(operatingTimePeriod, "yyyy-MM-dd HH:mm:ss"));
        }
        if( loginType != null ){
            item.setLoginType(loginType);
        }
        if( typeOperation != null ){
            item.setTypeOperation(typeOperation);
        }
        if( operatingModule != null ){
            item.setOperatingModule(operatingModule);
        }
        if( affiliation != null ){
            item.setAffiliation(affiliation);
        }
        if( theirDepartment != null ){
            item.setTheirDepartment(theirDepartment);
        }
        if( logContent != null ){
            item.setLogContent(logContent);
        }
            item.setLastUpdateTime(new Date());
        if( operatorId != null ){
            item.setOperatorId(operatorId);
        }
        ccLoggingService.update(item);

        return ccLoggingService.get(id);
    }

    @ApiOperation(value = "查询'日志记录'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/cc_loggings/{cc_logging_id}",
                    method = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT},
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getCcLogging(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "cc_logging_id") String id,
            @RequestParam(value = "operator", required = false) String operator,
            @RequestParam(value = "loginTimePeriod", required = false) String loginTimePeriod,
            @RequestParam(value = "operatingTimePeriod", required = false) String operatingTimePeriod,
            @RequestParam(value = "loginType", required = false) String loginType,
            @RequestParam(value = "typeOperation", required = false) String typeOperation,
            @RequestParam(value = "operatingModule", required = false) String operatingModule,
            @RequestParam(value = "affiliation", required = false) String affiliation,
            @RequestParam(value = "theirDepartment", required = false) String theirDepartment,
            @RequestParam(value = "logContent", required = false) String logContent,
            @RequestParam(value = "operatorId", required = false) String operatorId
    ) {
        if(request.getMethod().equals("GET")){
            CcLogging item = ccLoggingService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be edited doesn't exist");
            }

            return item;
        }
        else if(request.getMethod().equals("PUT")){
            CcLogging item = ccLoggingService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be edited doesn't exist");
            }

            if( operator != null ){
                item.setOperator(operator);
            }
            if( loginTimePeriod != null ){
                item.setLoginTimePeriod(Tools.convertStringToDate(loginTimePeriod, "yyyy-MM-dd HH:mm:ss"));
            }
            if( operatingTimePeriod != null ){
                item.setOperatingTimePeriod(Tools.convertStringToDate(operatingTimePeriod, "yyyy-MM-dd HH:mm:ss"));
            }
            if( loginType != null ){
                item.setLoginType(loginType);
            }
            if( typeOperation != null ){
                item.setTypeOperation(typeOperation);
            }
            if( operatingModule != null ){
                item.setOperatingModule(operatingModule);
            }
            if( affiliation != null ){
                item.setAffiliation(affiliation);
            }
            if( theirDepartment != null ){
                item.setTheirDepartment(theirDepartment);
            }
            if( logContent != null ){
                item.setLogContent(logContent);
            }
                item.setLastUpdateTime(new Date());
            if( operatorId != null ){
                item.setOperatorId(operatorId);
            }
            ccLoggingService.update(item);

            return ccLoggingService.get(id);
        }
        else if(request.getMethod().equals("DELETE")){
            CcLogging item = ccLoggingService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be deleted doesn't exist");
            }

            ccLoggingService.delete(id);
            return Resp.succ("delete success");
        }
        return Resp.fail("Unknown http method");
    }

    @ApiOperation(value = "查询'日志记录'表中的多条记录或者新增某条记录", notes = "get传参：根据url传入的filters（过滤条件），查询对应的多条数据。数据数量取决于pageNo和pageSize；数据的先后顺序取决于sortItem，sortOrder；   post请求：新增某条记录，传入表字段对应的值")
    @RequestMapping(value = "/cc_loggings",
                    method = { RequestMethod.GET, RequestMethod.POST },
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getCcLoggings(
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

        Long total = ccLoggingService.countListWithPagingAndFilter(filters);
        response.addHeader("total", String.valueOf(total));

        return ccLoggingService.getListWithPagingAndFilter(
                        pageNo, pageSize, sortItem, sortOrder, filters, includes, refers, relates);

    }

    @ApiOperation(value = "出入字段数组返回对应字段数据", notes = "get传参：根据url传入的filters（过滤条件），查询对应的多条数据。只返回你传进来的字段对应的数据")
    @RequestMapping(value = "/cc_loggings/columns",
                    method = { RequestMethod.GET },
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getCcLoggingsId(
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
        List<CcLogging> list = ccLoggingService.getListWithPagingAndFilter(
                pageNo, pageSize, sortItem, sortOrder, filters);

        //反射调取get方法
        Class clz = CcLogging.class;
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



    @ApiOperation(value = "批量创建'日志记录'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/cc_loggings/create//batch",
               method = RequestMethod.POST,
               produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createsCcLoggings(
             HttpServletRequest request,
             @RequestHeader(value = "X-Auth-Token", required = false) String token,
             @RequestParam(value = "params", required = false) String params
    ) {

             JSONArray jsonArray = JSON.parseArray(params);
             List<CcLogging> lists  = new ArrayList<>();

             jsonArray.stream().forEach((Object object) -> {

             JSONObject jsonObject = JSONObject.parseObject(object.toString());

             CcLogging item = new CcLogging();
             String id = Tools.getUUID();
                         item.setId(id);
             if(jsonObject.containsKey("operator")   ){
            item.setOperator(jsonObject.get("operator").toString());
        }
        if(jsonObject.containsKey("loginTimePeriod")   ){
            item.setLoginTimePeriod(Tools.convertStringToDate(jsonObject.get("loginTimePeriod").toString(), "yyyy-MM-dd HH:mm:ss"));
        }
        if(jsonObject.containsKey("operatingTimePeriod")   ){
            item.setOperatingTimePeriod(Tools.convertStringToDate(jsonObject.get("operatingTimePeriod").toString(), "yyyy-MM-dd HH:mm:ss"));
        }
        if(jsonObject.containsKey("loginType")   ){
            item.setLoginType(jsonObject.get("loginType").toString());
        }
        if(jsonObject.containsKey("typeOperation")   ){
            item.setTypeOperation(jsonObject.get("typeOperation").toString());
        }
        if(jsonObject.containsKey("operatingModule")   ){
            item.setOperatingModule(jsonObject.get("operatingModule").toString());
        }
        if(jsonObject.containsKey("affiliation")   ){
            item.setAffiliation(jsonObject.get("affiliation").toString());
        }
        if(jsonObject.containsKey("theirDepartment")   ){
            item.setTheirDepartment(jsonObject.get("theirDepartment").toString());
        }
        if(jsonObject.containsKey("logContent")   ){
            item.setLogContent(jsonObject.get("logContent").toString());
        }
            item.setCreateTime(new Date());
            item.setLastUpdateTime(new Date());
        if(jsonObject.containsKey("operatorId")   ){
            item.setOperatorId(jsonObject.get("operatorId").toString());
        }
             ccLoggingService.create(item);
             lists.add(ccLoggingService.get(id));

             });
        return lists;
     }

    @ApiOperation(value = "批量删除'日志记录'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
        @RequestMapping(value = "/cc_loggings/delete/batch",
                method = RequestMethod.POST,
                produces = "application/json;charset=UTF-8")
        @ResponseBody
        public Object deletesCcLogging(
                HttpServletRequest request,
                @RequestHeader(value = "X-Auth-Token", required = false) String token,
                @RequestParam(value = "ids", required = true) String ids
        ) {

            JSONArray jsonArray = JSON.parseArray(ids);
            jsonArray.stream().forEach(id->{

                 CcLogging item = ccLoggingService.get(id.toString());
                if (null != item) {
                    ccLoggingService.delete(id.toString());
                }

            });
            return Resp.succ("delete success");
        }
}
