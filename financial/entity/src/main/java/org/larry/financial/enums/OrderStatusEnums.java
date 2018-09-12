package org.larry.financial.enums;

public enum  OrderStatusEnums {


    APPLAY("1","申购"),
    REDEEM("2","赎回") ;

    private String statusCode ;

    private String desc ;

    public String getStatusCode() {
        return statusCode;
    }

    public String getDesc() {
        return desc;
    }

    OrderStatusEnums(String statusCode, String desc) {
        this.statusCode = statusCode;
        this.desc = desc;
    }
}
