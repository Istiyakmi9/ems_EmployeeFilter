package com.bot.employeeFilter.db.utils;

public class TypedParameter {
    public TypedParameter(){ }
    public TypedParameter(String key, Object value, int dataType) {
        this.dataType = dataType;
        this.key = key;
        this.value = value;
    }
    String key;
    Object value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    int dataType;
}
