package com.haomostudio.JuniorSpringMVCTemplate.test;

import com.guanpb.poi.excel.ExcelExportUtil;
import com.guanpb.poi.excel.ExcelImportUtil;
import com.haomostudio.JuniorSpringMVCTemplate.pojo.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Test {

    public static void main(String[] args) {

        /**
         * Mock数据，Java对象列表
         */
        List<User> userList = new ArrayList();
        for (int i = 0; i < 100; i++) {
            User user = new User("用户"+i,  i, true,  (long) (10000+i), new Date());
            userList.add(user);
        }
        String filePath = "/Users/liuranran/Downloads/test-user.xlsx";

        /**
         * Excel导出：Object 转换为 Excel
         */
        ExcelExportUtil.exportToFile(filePath, userList, userList,userList);

        /**
         * Excel导入：Excel 转换为 Object
          */
        List<Object> list = ExcelImportUtil.importExcel(User.class, filePath);

        System.out.println(list);

    }

}
