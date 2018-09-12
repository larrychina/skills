package org.larry.financial.enums;

public enum ProductStatusEnums {

    AUDITING("1","审核中"),
    IN_SALE("2","销售中"),
    STOP_SALE("3","暂停销售"),
    ENDED("4","已结束") ;


    private String statusCode ;

    private String desc ;


    public String getStatusCode() {
        return statusCode;
    }

    public String getDesc() {
        return desc;
    }

    ProductStatusEnums(String statusCode, String desc) {
        this.statusCode = statusCode;
        this.desc = desc;
    }
}
