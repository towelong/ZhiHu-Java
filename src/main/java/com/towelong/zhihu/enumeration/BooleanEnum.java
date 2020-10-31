package com.towelong.zhihu.enumeration;

public enum BooleanEnum {

    TRUE(1),
    FALSE(0);

    private Integer typeCode;

    BooleanEnum(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }
}
