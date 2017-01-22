package com.nop;

/**
 * Created by yangzijun on 17-1-16.
 */
public class NMain {
    public static void main(String[] args) {
        String vopOrder="[48492890141, 49266066953]";
        int idx = vopOrder.indexOf("[");
        int endIdx = vopOrder.lastIndexOf("]");
        System.out.println(vopOrder.substring(idx + 1, endIdx));

    }
}
