package com.buraktuysuz.n11.finalproject.utility.enums;

public enum EnumApplicationResult {
    APPROVED(1,"BAŞVURU ONAYLANDI"),
    NOTAPPROVED(2,"BAŞVURU ONAYLANMADI");

    private int value;
    private String display;

    EnumApplicationResult(int value, String display) {
        this.value = value;
        this.display = display;
    }

    public int getValue() {
        return value;
    }

    public String getDisplay() {
        return display;
    }
}
