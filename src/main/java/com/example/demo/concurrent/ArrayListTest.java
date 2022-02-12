package com.example.demo.concurrent;

import org.springframework.util.StopWatch;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Created by 25790 on 2021/11/28.
 * CopyOnWriteArrayList与加锁的ArrayList性能比较
 */
public class ArrayListTest {

    public static void main(String[] args) {
        writeCompare();
        readCompare();
    }

    /**
     * 测试并发写的性能
     */
    private static void writeCompare(){
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        StopWatch stopWatch=new StopWatch();
        int loopCount = 100000;

        stopWatch.start("write copyOnWriteArrayList");
        //循环1000次随机往copyOnWriteArrayList里写数据
        IntStream.rangeClosed(1,loopCount).parallel().forEach(t->copyOnWriteArrayList.add(ThreadLocalRandom.current().nextInt(loopCount)));
        stopWatch.stop();

        stopWatch.start("write synchronizedList");
        //循环1000次随机往synchronizedList里写数据
        IntStream.rangeClosed(1,loopCount).parallel().forEach(t->synchronizedList.add(ThreadLocalRandom.current().nextInt(loopCount)));
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());

    }

    /**
     * 测试并发读的性能
     */
    private static void readCompare(){
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        addAll(copyOnWriteArrayList);
        addAll(synchronizedList);

        StopWatch stopWatch=new StopWatch();
        int loopCount=1000000;
        int count=copyOnWriteArrayList.size();
        stopWatch.start("read copyOnWriteArrayList");
        //循环1000000次并发从CopyOnWriteArrayList随机查询元素
        IntStream.rangeClosed(1,loopCount).parallel().forEach(t->copyOnWriteArrayList.get(ThreadLocalRandom.current().nextInt(count)));
        stopWatch.stop();

        stopWatch.start("read synchronizedList");
        IntStream.rangeClosed(1,loopCount).parallel().forEach(t->synchronizedList.get(ThreadLocalRandom.current().nextInt(count)));
         stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    private static void addAll(List<Integer> list){
        list.addAll(IntStream.rangeClosed(1,1000).boxed().collect(Collectors.toList()));
    }
}
