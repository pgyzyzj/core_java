package com.nop;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Hello world!
 */
public class App {

    public static void getHostName() throws UnknownHostException {
        System.out.println(new String(InetAddress.getLocalHost().getCanonicalHostName().toString()));
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

    }



}
