package com.nop;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void getHostName() throws UnknownHostException {
        System.out.println(new String(InetAddress.getLocalHost().getCanonicalHostName().toString()));
    }
    public static void main( String[] args )
    {
        try {
            getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
