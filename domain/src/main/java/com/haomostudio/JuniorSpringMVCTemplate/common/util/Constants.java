package com.haomostudio.JuniorSpringMVCTemplate.common.util;


public class Constants {

    public static final char UNDERLINE = '_';
    public final static String SCRIPT_PATH = "/scripts";
    public final static String FORMFILE_PATH = "/form";
    public final static String AVATAR_PATH = "/upload/user/avatar";
    public final static String SEAFILE_COMMON_LIB = "common";
    public final static String AFTER_SALES_SEAFILE_LIB = "/afterSales";
    public final static String SEAFILE_LOGISTICS_DIR = "/logistics";
    public final static String FILING_TENDER_SEAFILE_LIB = "/filingTender";

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_SCENE_INSTANCE_ID = "SCENE_INSTANCE_ID";

    public static final String COLUMN_FORM_INSTANCE_ID = "FORM_INSTANCE_ID";

    public static final String COLUMN_CREATE_TIME = "CREATE_TIME";

    public static final String COLUMN_UPDATE_TIME = "UPDATE_TIME";

    /**
     * 表单引擎自定义表名前缀
     */
    public static final String TABLE_NAME_PREFIX_FORM_ENGINE = "T_";

    /**
     * 用户自定义表名前缀
     */
    public static final String TABLE_NAME_PREFIX_USER = "U_";

    /**
     * 部门自定义表名前缀
     */
    public static final String TABLE_NAME_PREFIX_DEPARTMENT = "D_";

    /**
     * 客户自定义表名前缀
     */
    public static final String TABLE_NAME_PREFIX_CUSTOMER = "C_";

    /**
     * 物料自定义表名前缀
     */
    public static final String TABLE_NAME_PREFIX_MATERIAL = "M_";

    /**
     * 业务场景自定义表名前缀
     */
    public static final String TABLE_NAME_PREFIX_SCENE = "S_";

    /**
     * 报修单的编号标识，和编号长度
     */
    public static final String REPAIR_ORDER_NO_TYPE = "Repair_OrderNo_Type";
    public static final int REPAIR_ORDER_NO_LENGTH = 4;

    // 报备编号
    public static final String FILING_NO_TYPE = "FILING_NO_TYPE";

    public static class UserQueryConditionType {
        public static final String afterSalesCallCenterQuery = "afterSalesCallCenterQuery";
    }

    public static class FilingHandleStatus {
        // 打开
        public static final String Active = "A";
        // 确定（可以被合同使用）
        public static final String Confirm = "C";
        // 关闭/办结(手动作废)
        public static final String End = "E";
        // 超时
        public static final String Overtime = "O";
        // 待办
        public static final String Wait = "W";
        // 已办
        public static final String Pass = "P";
        // 跳过
        public static final String Skip = "S";
        // 中标
        public static final String ZhongBiao = "O";
        // 未中标
        public static final String WeizhongBiao = "X";
    }

}
