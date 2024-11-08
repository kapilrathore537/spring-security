package com.gramseva.model;

import lombok.Getter;

@Getter
public enum PermissionTitle {


    USERS("USERS","Users");  // and many more

    private final String key;
    private final String value;

    PermissionTitle(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
}
