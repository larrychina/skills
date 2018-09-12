package org.larry.financial.enums;

public enum OrderTypeEnums {

    INIT("0","初始化"),
    DEALING("1","处理中 "),
    SUCCESS("2","处理成功"),
    FAILED("3","处理失败");

    private String typeCode ;

    private String desc ;

    OrderTypeEnums(String typeCode, String desc) {
        this.typeCode = typeCode;
        this.desc = desc;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public String getDesc() {
        return desc;
    }
}
