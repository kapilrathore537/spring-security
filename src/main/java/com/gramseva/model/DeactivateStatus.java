package com.gramseva.model;

import lombok.Getter;

@Getter
public enum DeactivateStatus {
    IS_ACTIVE("IS_ACTIVE","Active"),SEVEN_DAYS("SEVEN_DAYS", "Seven Days"), FOURTEEN_DAYS("FOURTEEN_DAYS", "Fourteen Days"),LIFETIME("LIFETIME","Lifetime");


    private final String key;
    private final String value;

    DeactivateStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
