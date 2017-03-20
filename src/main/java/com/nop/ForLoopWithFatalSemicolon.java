package com.nop;

/**
 * Created by yangzijun on 17-3-10.
 *if i=forever and you want to print i love u in a loop, but the for loop end up with the semicolon cause the for loop do nothing,
 * and run forever; so this program will never
 *
 */
public class ForLoopWithFatalSemicolon {
    public static void main(String[] args) {
        for(int i=0;i<10;i++);
        System.out.println("hehe");
    }
}
