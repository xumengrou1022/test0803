package com.haomostudio.JuniorSpringMVCTemplate.service.common;

import java.util.List;
import java.util.Map;


public class Response {

    private Integer code;

    private Map<String, List<String>> headers;

    private String body;

    private Integer pageNo;

    private Integer pageSize;

    private Integer total;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getPageNo() {

        return getPaginationValueFromHeader("page_no");
    }

    public Integer getPageSize() {
        return getPaginationValueFromHeader("page_size");
    }

    public Integer getTotal() {
        return getPaginationValueFromHeader("total");
    }


    /**
     * 获得分页的整形数值：page_no, page_size, total
     * @param key key
     * @return Integer
     */
    private Integer getPaginationValueFromHeader(String key) {

        List<String> list = this.headers.get(key);
        if (list.size() > 0) {
            return Integer.valueOf(list.get(0));
        }
        return null;
    }


    @Override
    public String toString() {

        StringBuffer buffer = new StringBuffer();
        buffer.append("Code:").append(this.code).append("\nBody:").append(this.body);
        if (null != this.getHeaders()) {
            for (Map.Entry<String, List<String>> entry : this.getHeaders().entrySet()) {
                buffer.append("\n").append(entry.getKey()).append(":");
                if (null != entry.getValue()) {
                    for (String value : entry.getValue()) {
                        buffer.append(value).append(" ");
                    }
                }
            }
        }
        return buffer.toString();
    }
}
