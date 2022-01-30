package com.buraktuysuz.n11.finalproject.utility.enums;

public enum EnumMessageType {

    SMS(1),
    GMAIL(2),
    OFFICE365(3);


    private int value;

    EnumMessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
