package com.haomostudio.JuniorSpringMVCTemplate.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haomostudio.JuniorSpringMVCTemplate.common.Resp;
import com.haomostudio.JuniorSpringMVCTemplate.po.Menu;
import com.haomostudio.JuniorSpringMVCTemplate.service.HmUtils.Tools;
import com.haomostudio.JuniorSpringMVCTemplate.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


@Api(description = "菜单")
@Controller
public class MenuController {

    @Autowired
    MenuService menuService;

    @Autowired
    HttpServletResponse response;

    @ApiOperation(value = "创建'菜单'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/menus/new",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createMenu(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "menuLogo", required = false) String menuLogo,
            @RequestParam(value = "superiorMenuId", required = false) String superiorMenuId,
            @RequestParam(value = "grade", required = false) Integer grade,
            @RequestParam(value = "createTime", required = false) String createTime,
            @RequestParam(value = "link", required = false) String link,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "rank", required = false) Integer rank
    ) {
        Menu item = new Menu();
        String id = Tools.getUUID();
        item.setId(id);
        if( name != null ){
            item.setName(name);
        }
        if( menuLogo != null ){
            item.setMenuLogo(menuLogo);
        }
        if( superiorMenuId != null ){
            item.setSuperiorMenuId(superiorMenuId);
        }
        if( grade != null ){
            item.setGrade(grade);
        }
            item.setCreateTime(new Date());
        if( link != null ){
            item.setLink(link);
        }
        if( type != null ){
            item.setType(type);
        }
        if( rank != null ){
            item.setRank(rank);
        }
        menuService.create(item);

        //若request param为null，但字段定义有默认值，则需从数据库中返回结果
        return menuService.get(id);
        //return item;
    }

    @ApiOperation(value = "删除'菜单'表中的某条记录", notes = "根据url传入的数据id，删除整条记录。")
    @RequestMapping(value = "/menus/{menu_id}/delete",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteMenu(
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "menu_id") String id
    ) {
        Menu item = menuService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be deleted doesn't exist");
        }

        menuService.delete(id);
        return Resp.succ("delete success");
    }

    @ApiOperation(value = "修改'菜单'表中的某条记录", notes = "根据url传入的数据id，确定修改表中的某条记录，传入表中字段要修改的信息，不传代表不修改。并返回给View层")
    @RequestMapping(value = "/menus/{menu_id}/edit",
                    method = RequestMethod.POST,
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editMenu(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "menu_id") String id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "menuLogo", required = false) String menuLogo,
            @RequestParam(value = "superiorMenuId", required = false) String superiorMenuId,
            @RequestParam(value = "grade", required = false) Integer grade,
            @RequestParam(value = "link", required = false) String link,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "rank", required = false) Integer rank
    ) {
        Menu item = menuService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be edited doesn't exist");
        }

        if( name != null ){
            item.setName(name);
        }
        if( menuLogo != null ){
            item.setMenuLogo(menuLogo);
        }
        if( superiorMenuId != null ){
            item.setSuperiorMenuId(superiorMenuId);
        }
        if( grade != null ){
            item.setGrade(grade);
        }
        if( link != null ){
            item.setLink(link);
        }
        if( type != null ){
            item.setType(type);
        }
        if( rank != null ){
            item.setRank(rank);
        }
        menuService.update(item);

        return menuService.get(id);
    }

    @ApiOperation(value = "查询'菜单'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/menus/{menu_id}",
                    method = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT},
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getMenu(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "menu_id") String id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "menuLogo", required = false) String menuLogo,
            @RequestParam(value = "superiorMenuId", required = false) String superiorMenuId,
            @RequestParam(value = "grade", required = false) Integer grade,
            @RequestParam(value = "link", required = false) String link,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "rank", required = false) Integer rank
    ) {
        if(request.getMethod().equals("GET")){
            Menu item = menuService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be edited doesn't exist");
            }

            return item;
        }
        else if(request.getMethod().equals("PUT")){
            Menu item = menuService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be edited doesn't exist");
            }

            if( name != null ){
                item.setName(name);
            }
            if( menuLogo != null ){
                item.setMenuLogo(menuLogo);
            }
            if( superiorMenuId != null ){
                item.setSuperiorMenuId(superiorMenuId);
            }
            if( grade != null ){
                item.setGrade(grade);
            }
            if( link != null ){
                item.setLink(link);
            }
            if( type != null ){
                item.setType(type);
            }
            if( rank != null ){
                item.setRank(rank);
            }
            menuService.update(item);

            return menuService.get(id);
        }
        else if(request.getMethod().equals("DELETE")){
            Menu item = menuService.get(id);
            if (null == item) {
                response.setStatus(404);
                return Resp.fail("the asset to be deleted doesn't exist");
            }

            menuService.delete(id);
            return Resp.succ("delete success");
        }
        return Resp.fail("Unknown http method");
    }

    @ApiOperation(value = "查询'菜单'表中的多条记录或者新增某条记录", notes = "get传参：根据url传入的filters（过滤条件），查询对应的多条数据。数据数量取决于pageNo和pageSize；数据的先后顺序取决于sortItem，sortOrder；   post请求：新增某条记录，传入表字段对应的值")
    @RequestMapping(value = "/menus",
                    method = { RequestMethod.GET, RequestMethod.POST },
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getMenus(
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

        Long total = menuService.countListWithPagingAndFilter(filters);
        response.addHeader("total", String.valueOf(total));

        return menuService.getListWithPagingAndFilter(
                        pageNo, pageSize, sortItem, sortOrder, filters, includes, refers, relates);

    }

    @ApiOperation(value = "出入字段数组返回对应字段数据", notes = "get传参：根据url传入的filters（过滤条件），查询对应的多条数据。只返回你传进来的字段对应的数据")
    @RequestMapping(value = "/menus/columns",
                    method = { RequestMethod.GET },
                    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getMenusId(
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
        List<Menu> list = menuService.getListWithPagingAndFilter(
                pageNo, pageSize, sortItem, sortOrder, filters);

        //反射调取get方法
        Class clz = Menu.class;
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



    @ApiOperation(value = "批量创建'菜单'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/menus/create//batch",
               method = RequestMethod.POST,
               produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createsMenus(
             HttpServletRequest request,
             @RequestHeader(value = "X-Auth-Token", required = false) String token,
             @RequestParam(value = "params", required = false) String params
    ) {

             JSONArray jsonArray = JSON.parseArray(params);
             List<Menu> lists  = new ArrayList<>();

             jsonArray.stream().forEach((Object object) -> {

             JSONObject jsonObject = JSONObject.parseObject(object.toString());

             Menu item = new Menu();
             String id = Tools.getUUID();
                         item.setId(id);
             if(jsonObject.containsKey("name")   ){
            item.setName(jsonObject.get("name").toString());
        }
        if(jsonObject.containsKey("menuLogo")   ){
            item.setMenuLogo(jsonObject.get("menuLogo").toString());
        }
        if(jsonObject.containsKey("superiorMenuId")   ){
            item.setSuperiorMenuId(jsonObject.get("superiorMenuId").toString());
        }
        if(jsonObject.containsKey("grade")   ){
            item.setGrade(new Integer(jsonObject.get("grade").toString()));
        }
            item.setCreateTime(new Date());
        if(jsonObject.containsKey("link")   ){
            item.setLink(jsonObject.get("link").toString());
        }
        if(jsonObject.containsKey("type")   ){
            item.setType(jsonObject.get("type").toString());
        }
        if(jsonObject.containsKey("rank")   ){
            item.setRank(new Integer(jsonObject.get("rank").toString()));
        }
             menuService.create(item);
             lists.add(menuService.get(id));

             });
        return lists;
     }

    @ApiOperation(value = "批量删除'菜单'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
        @RequestMapping(value = "/menus/delete/batch",
                method = RequestMethod.POST,
                produces = "application/json;charset=UTF-8")
        @ResponseBody
        public Object deletesMenu(
                HttpServletRequest request,
                @RequestHeader(value = "X-Auth-Token", required = false) String token,
                @RequestParam(value = "ids", required = true) String ids
        ) {

            JSONArray jsonArray = JSON.parseArray(ids);
            jsonArray.stream().forEach(id->{

                 Menu item = menuService.get(id.toString());
                if (null != item) {
                    menuService.delete(id.toString());
                }

            });
            return Resp.succ("delete success");
        }
}
