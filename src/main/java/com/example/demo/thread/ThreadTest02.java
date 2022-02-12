package com.example.demo.thread;

import com.alibaba.fastjson.JSON;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by 25790 on 2021/11/28.
 * 使用ConcurrentHashMap来统计key出现的次数
 */
public class ThreadTest02 {
    private static int LOOP_COUNT=10000000;
    private static int THREAD_COUNT=9;
    private static int ITEM_COUNT=10;

    public static void main(String[] args) throws InterruptedException {
//        Map<String, Long> normalMap = normal();
//        Map<String, Long> goodMap = goodUse();
//        System.out.println(JSON.toJSONString(normalMap));
//        System.out.println(JSON.toJSONString(goodMap));

        String compare = compare();
        System.out.println(compare);
    }

    /**
     * 数据比较
     * @return
     * @throws InterruptedException
     */
    private static String  compare() throws InterruptedException {
        StopWatch stopWatch=new StopWatch("统计方法耗时");
        stopWatch.start("normal");
        Map<String, Long> normal = normal();
        stopWatch.stop();
        //断言校验元素数量
        Assert.isTrue(normal.size()==ITEM_COUNT,"normalUse is error");
        Assert.isTrue(normal.entrySet().stream().mapToLong(item->item.getValue()).reduce(0,Long::sum)==LOOP_COUNT,"normal count is error");

        stopWatch.start("goodUse");
        Map<String, Long> goodsUse = goodUse();
        stopWatch.stop();
        //断言校验元素数量
        Assert.isTrue(goodsUse.size()==ITEM_COUNT,"normalUse is error");
        Assert.isTrue(goodsUse.entrySet().stream().mapToLong(item->item.getValue()).reduce(0,Long::sum)==LOOP_COUNT,"normal count is error");

        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.shortSummary());

        return "ok";
    }


    /**
     * 常规的统计方法
     * @return
     * @throws InterruptedException
     */
    private static Map<String,Long> normal() throws InterruptedException {
        ConcurrentHashMap<String, Long> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
                    //获得一个随机的Key
                    String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
                    synchronized (freqs) {
                        if (freqs.containsKey(key)) {
                            //Key存在则+1
                            freqs.put(key, freqs.get(key) + 1);
                        } else {
                            //Key不存在则初始化为1
                            freqs.put(key, 1L);
                        }
                    }
                }
        ));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        return freqs;
    }

    /**
     * 改进后的代码
     * @return
     */
    private static   Map<String,Long> goodUse() throws InterruptedException {
        ConcurrentHashMap<String,LongAdder> freqs=new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
                    String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
                    //利用computeIfAbsent()方法来实例化LongAdder，然后利用LongAdder来进行线程安全计数
            //该方法为原子性方法，LongAdder是一个线程安全的累加器，所以可以之间调increment方法做累加
                    freqs.computeIfAbsent(key, k -> new LongAdder()).increment();
                }
        ));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        //因为我们的Value是LongAdder而不是Long，所以需要做一次转换才能返回
        return freqs.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue().longValue())
                );

    }
}
