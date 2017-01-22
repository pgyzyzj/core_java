package com.nop.concurrent.threadsafe;

import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;

/**
 * Created by yangzijun on 17-1-22.
 * The <b>IsThreadSafe</b> class demonstrated easy thread safe condition..
 *
 */
public class IsThreadSafe {
    public int i=0;

    public void increment(){
        int before=i;
        System.out.println("thread:"+Thread.currentThread().getName()+",before="+before);
        int after=before+1;
        System.out.println("thread:"+Thread.currentThread().getName()+",end="+after);
        i=after;
    }

    class WorkerThread implements Runnable{
        private IsThreadSafe isThreadSafe;
        public WorkerThread(IsThreadSafe i){
            this.isThreadSafe=i;
        }

        public void run() {
            isThreadSafe.increment();
        }
    }

    private void getResult(){
        for (int j=0;j<1000;j++){
            WorkerThread workerThread=new WorkerThread(this);
            Thread worker=new Thread(workerThread);
            worker.start();
            //if uncommented, the worker thread will finish it's job one by one
/*            try {
                while(worker.isAlive())
                worker.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

        }
    }

    public static void main(String[] args) throws InterruptedException {
        IsThreadSafe isThreadSafe=new IsThreadSafe();
        isThreadSafe.getResult();
        Thread.sleep(1000);
        System.out.println("All worker threads have been done.,final i="+isThreadSafe.i);

    }
}
