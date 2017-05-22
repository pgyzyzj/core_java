package com.nop.javase;

/**
 * Created by yangzijun on 17-4-19.
 */
public enum Color {
    RED(1), BLUE(2), GREEN(3);
    private int code;

    private Color(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
