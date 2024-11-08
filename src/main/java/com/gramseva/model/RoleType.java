package com.gramseva.model;

import lombok.Getter;

@Getter
public enum RoleType {
    ADMIN("ADMIN","Admin"),SUB_ADMIN("SUB_ADMIN","Sub-admin"),RELATIONSHIP_MANAGER("RELATIONSHIP_MANAGER","Relationship Manager");

    private final String key;
    private final String value;

    RoleType(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
