package com.nop;

/**
 * Created by yangzijun on 17-3-13.
 */
public class EasyToWrong {

    private boolean isOk;

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public static void main(String[] args) {
        EasyToWrong easyToWrong=new EasyToWrong();
        Boolean ok=null;
        easyToWrong.setOk(ok);
    }
}
