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
        for (int i = 0; i < 10; ++i) {
            System.out.println(Math.abs("c.jd.id".hashCode()));
        }

        System.out.println(1%2!=0);

        System.out.println("促销升级");


    }

}
