package com.haomostudio.JuniorSpringMVCTemplate.service.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public abstract class GenericService {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final int MAX_PAGE_SIZE = 10000;

    public static final Integer TABLE_TYPE_DEFAULT = 2;

    public static final String TABLE_NAME_CUSTOMER = "CUSTOMER";

    public static final String OFFSET = "offset";


    public static final String LIMIT = "rows";

    /**
     * 日志
     */
    public Log log = LogFactory.getLog(getClass());

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }


    public void addPageNoPageSize2Condition(Map<String, Object> condition, Integer pageNo, Integer pageSize) {
        condition.put(OFFSET, (pageNo - 1) * pageSize);
        condition.put(LIMIT, pageSize);
    }

    public Date parseDate(String dateStr) {
        if (StringUtils.isNotEmpty(dateStr)) {
            try {
                return this.sdf.parse(dateStr);
            } catch (Exception e) {
                log.error("日期转换错误");
            }
        }
        return null;
    }
}
