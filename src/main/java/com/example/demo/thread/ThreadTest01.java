package com.example.demo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Created by 25790 on 2021/11/28.
 * ConcurrentHashMap:并发容器
 */
public class ThreadTest01 {
    private final static Logger logger = LoggerFactory.getLogger(ThreadTest01.class);
    private static final Integer THREAD_COUNT=10;

    private static final Integer ITEM_COUNT=1000;

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    /**
     * 多个线程测试
     */
    private static void test1(){
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i=0;i<5;i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        threadTest();
                        threadTest2()   ;
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage(),e);
                    }
                }
            });
        }
    }

    /**
     * 并发环境下加锁处理
     */
    private static void threadTest2() throws InterruptedException {
        //初始化900个元素
        ConcurrentHashMap<String, Long> concurrentHashMap = getData(ITEM_COUNT - 100);
        logger.info("synchronized init size:{}",concurrentHashMap.size());
        //使用线程池并发处理逻辑
        ForkJoinPool forkJoinPool=new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(()-> IntStream.rangeClosed(1,10).parallel().forEach(i->{
            synchronized (concurrentHashMap) {
                //查询还需补充多少个元素
                int gap = ITEM_COUNT - concurrentHashMap.size();
                logger.info("synchronized gap size:{}", gap);
                concurrentHashMap.putAll(getData(gap));
            }
        }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        logger.info("synchronized finish size:{}", concurrentHashMap.size());
    }

    /**
     * 不加锁处理,并发情况下一些方法可能存在中间状态，导致值不一致
     */
    private static void  threadTest() throws InterruptedException {
        //初始化900个元素
        ConcurrentHashMap<String, Long> concurrentHashMap = getData(ITEM_COUNT - 100);
        logger.info("init size:{}",concurrentHashMap.size());
        //使用线程池并发处理逻辑
        ForkJoinPool forkJoinPool=new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(()-> IntStream.rangeClosed(1,10).parallel().forEach(i->{
            //查询还需补充多少个元素
            int gap= ITEM_COUNT-concurrentHashMap.size();
            logger.info("gap size:{}",gap);
            concurrentHashMap.putAll(getData(gap));
        }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        logger.info("finish size:{}", concurrentHashMap.size());
    }

    /**
     * 用于获取一个指定元素数量的数量模拟器的ConcurrentHashMap
     * @param count
     * @return
     */
    private static ConcurrentHashMap<String,Long> getData(int count){
        return LongStream.rangeClosed(1,count)
                .boxed()
                .collect(Collectors.toConcurrentMap(i-> UUID.randomUUID().toString(), Function.identity(),
                        (o1,o2)->o1,ConcurrentHashMap::new));
    }

}
