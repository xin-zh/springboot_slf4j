package com.example.demo.classloader;

import com.example.demo.concurrent.ArrayListTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 25790 on 2021/12/12.
 */
public class Main {

    static {
        System.out.println("初始化主类");
    }

    public static void main(String[] args) {
//        //调用主类的静态变量，会加载主类的静态代码块
//        System.out.println(SubClass.a);
//
//        System.out.println("============");
//
//        //调用主类的静态方法，会加载主类的静态代码块
//        SubClass.method();
//
//        System.out.println("==================");
//
//        //初始化子类，加载顺序：主类静态代码块->子类静态代码块->主类构造->子类构造
//        new SubClass();

        //通过数组创建对象,不会加载对象信息，
//        List<SubClass> list=new ArrayList();

        //调用final修饰的变量，不会触发类的初始化
        System.out.println(SubClass.b);
    }


}
