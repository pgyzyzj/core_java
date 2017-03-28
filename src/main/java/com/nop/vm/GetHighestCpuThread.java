package com.nop.vm;

/**
 * Created by yangzijun on 17-3-27.
 * 1: top
 * 2:top -H -p pid ,from the out put get nid(thread's pid),and then change to hex
 * 3:jstack -l pid > pid.txt
 * 4: grep hexadecimal nid in pid.txt
 */
public class GetHighestCpuThread {
    public static void main(String[] args) {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

                }
            }
        });
        t.start();
    }
}
