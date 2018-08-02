package com.haomostudio.JuniorSpringMVCTemplate.service;

import com.haomostudio.JuniorSpringMVCTemplate.po.HmUser;

import java.util.List;
/**
 * @Description :用户管理
 * @author: gongbin
 * @date: 2018/8/2 19:32
 */
public interface HmUserService {
    /**
     * @Description :创建用户
     * @author: gongbin
     * @date: 2018/8/2 19:31
    */
    int create(HmUser item);

    /**
    * @Description :更新用户
    * @author: gongbin
    * @date: 2018/8/2 19:32
    */
    int update(HmUser item);

    HmUser get(String id);

    /**
    * 获取列表
    * @param pageNo 整数,如1
    * @param pageSize 整数,如10
    * @param sortItem 格式为"id, name"
    * @param sortOrder 格式为"asc, desc"
    * @param filters JSON字符串,格式为
    * @return 列表
    */
    List<HmUser> getListWithPagingAndFilter(Integer pageNo, Integer pageSize,
                                            String sortItem, String sortOrder,
                                            String filters);

    int delete(String id);

}
