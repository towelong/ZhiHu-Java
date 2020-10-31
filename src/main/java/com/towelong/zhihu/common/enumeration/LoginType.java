package com.towelong.zhihu.common.enumeration;

public enum LoginType {
    USER_PASSWORD(0),
    MINI(1);

    private int typeCode;

    LoginType(int typeCode) {
        this.typeCode = typeCode;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }

    public static LoginType getTypeByValue(Integer value){
        for (LoginType loginType:LoginType.values()){
            if (loginType.getTypeCode()==value){
                return loginType;
            }
        }
        return null;
    }
}
