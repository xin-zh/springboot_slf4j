package com.example.demo.concurrent;

import com.example.demo.utils.HttpClientUtil;
import sun.net.www.http.HttpClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by 25790 on 2021/12/12.
 * 同时并发请求
 */
public class LatchTest {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = new Runnable() {
            //线程安全的计数器
           private int  iCounter=ThreadLocalRandom.current().nextInt();

//            int iCounter
            public void run() {
                for (int i = 0; i < 5; i++) {
                    //发起请求
                    String s = HttpClientUtil.doPost("http://www.baidu.com/","hpv");
                    System.out.println(s);
                    iCounter++;
                    System.out.println(System.nanoTime() + "[" + Thread.currentThread().getName() + "] iCounter=" + iCounter);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        LatchTest latchTest=new LatchTest();
        latchTest.startTaskAllInOnce(10,runnable);


    }

    public long startTaskAllInOnce(int threadNums,final Runnable task) throws InterruptedException {
        final CountDownLatch startGate=new CountDownLatch(1);
        final CountDownLatch endGate=new CountDownLatch(threadNums);
        for (int i=0;i<threadNums;i++){
            Thread t=new Thread(){
                @Override
                public void run() {
                    try{
                        //使线程在此等待，当开始门打开时，请求一起涌入
                        startGate.await();
                        try{
                            task.run();
                        }finally {
                            //将结束门减1，减到0时，就可以开启结束门了
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }
        long startTime=System.nanoTime();
        System.out.println(startTime + " [" + Thread.currentThread() + "] All thread is ready, concurrent going...");
        //开始门打开，所有线程请求一起到达
        startGate.countDown();
//结束门开启
        endGate.await();
        long endTime=System.nanoTime();
        System.out.println(endTime + " [" + Thread.currentThread() + "] All thread is completed.");
        return endTime-startTime;
    }
}
