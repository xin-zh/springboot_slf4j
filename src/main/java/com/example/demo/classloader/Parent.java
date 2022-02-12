package com.example.demo.classloader;

/**
 * Created by 25790 on 2021/12/12.
 * 父类
 */
public class Parent {

    public static int a=10;

    public final static int  b=20;

    public Parent(){
        System.out.println("Constructor....");
    }
    static{
        System.out.println("static div....");
    }

    public static void method(){
        System.out.println("static method....");
    }

}
