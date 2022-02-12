package com.example.demo.classloader;

/**
 * Created by 25790 on 2021/12/12.
 * class loader:父类静态代码块>子类静态代码块>父类构造>子类构造
 */
public class SubClass  extends Parent{
    public SubClass(){
        System.out.println("SubClass Constructor....");
    }

    static {
        System.out.println("SubClass static div");
    }

    public static void main(String[] args) {
        new SubClass();
    }
}
