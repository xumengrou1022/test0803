package com.haomostudio.JuniorSpringMVCTemplate.common.domain;


public enum ColumnOperator {
    equal("eq", "="),
    neq("neq", "<>"),
    lt("lt", "<"),
    lte("lte", "<="),
    gt("gt", ">"),
    gte("gte", ">="),
    like("like", "like"),
    isNull("is null", "is null"),
    isNotNull("is not null", "is not null");

    private String operatorName;
    private String dbOperator;

    ColumnOperator(String operatorName, String dbOperator) {
        this.operatorName = operatorName;
        this.dbOperator = dbOperator;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getDbOperator() {
        return dbOperator;
    }

    public void setDbOperator(String dbOperator) {
        this.dbOperator = dbOperator;
    }
}