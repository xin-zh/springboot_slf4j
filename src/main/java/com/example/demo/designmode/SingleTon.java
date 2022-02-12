package com.example.demo.designmode;

import java.util.stream.IntStream;

/**
 * Created by 25790 on 2021/12/12.
 */
public class SingleTon {
    private volatile static SingleTon singleTon;

//    static {
//        singleTon=new SingleTon();
//    }

    private static int cnt1=1;
    private static int cnt2=0;

    private SingleTon(){
        cnt1++;
        cnt2++;
//        System.out.println("init cnt1:"+cnt1);
//        System.out.println("init cnt2:"+cnt2);
    }

    /**
     * 加双重锁获取单例对象
     * @return
     */
    public static SingleTon getSingleTon(){
        if (singleTon==null){
            synchronized (SingleTon.class){
                if (singleTon==null){
                    singleTon=new SingleTon();
                }
            }
        }
        return singleTon;
    }

    /**
     * 不加锁获取对象
     * @return
     */
    public static SingleTon getSingleTon2(){
        if (singleTon==null){
            singleTon=new SingleTon();
        }
        return singleTon;
    }

    public static SingleTon getSingleTon3(){
        return new SingleTon();
    }

    public static void main(String[] args) {

//        SingleTon singleTon = getSingleTon();
//        System.out.println("cnt1:"+SingleTon.cnt1);
//        System.out.println("cnt2:"+SingleTon.cnt2);

        concurrentGetSingleTon();

    }

    /**
     * 并发请求获取实例信息
     */
    public static void concurrentGetSingleTon(){
        IntStream.rangeClosed(1,10).parallel().forEach(t->{
            SingleTon singleTon = getSingleTon();
            if (singleTon==null){
                System.out.println("singleTon is null");
            }else {
                System.out.println(singleTon);
            }
        });

        System.out.println("=============================");

        IntStream.rangeClosed(1,10).parallel().forEach(t->{
            SingleTon singleTon = getSingleTon2();
            if (singleTon==null){
                System.out.println("singleTon is null");
            }else {
                System.out.println(singleTon);
            }
        });

        System.out.println("=============================");

        IntStream.rangeClosed(1,10).parallel().forEach(t->{
            SingleTon singleTon = getSingleTon3();
            if (singleTon==null){
                System.out.println("singleTon is null");
            }else {
                System.out.println(singleTon);
            }
        });
    }

}
