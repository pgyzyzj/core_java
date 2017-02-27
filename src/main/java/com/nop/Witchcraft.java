package com.nop;

/**
 * Created by yangzijun on 17-2-24.
 */
public class Witchcraft {

    /**
     * 要报错，如果三元运算符的内部是使用Integer.parseInt(s),Integer.value(s)都是报NumberFormatException...
     * 求解报错的本质原因是啥？
     * 就是java的自动装箱与拆箱引起的问题
     */
    public static void uMayWrong(){
        Integer s=null;
        s.intValue();
        Integer a=s!=null?new Integer(0):s;

    }



    public static void main(String[] args) {
        uMayWrong();
    }
}
