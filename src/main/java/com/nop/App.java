package com.nop;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

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
            for(int i=0;i<100;i++){
                System.out.println(new Random().nextInt(1000));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
