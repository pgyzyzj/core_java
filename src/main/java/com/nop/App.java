package com.nop;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * Hello world!
 */
public class App {

    public static void getHostName() throws UnknownHostException {
        System.out.println(new String(InetAddress.getLocalHost().getCanonicalHostName().toString()));
    }

    public static void main(String[] args) {
        System.out.println("br_v01加了点内容");
        for (int i = 0; i < 10; ++i) {
            System.out.println(Math.abs("c.jd.id".hashCode()));
        }

        System.out.println(1%2!=0);

    }
}
