package com.example.demo.designmode.abstractfactorypattern.product;

import com.example.demo.designmode.abstractfactorypattern.resoler.IResolver;

public interface IProductProducer extends IResolver<String> {

    /**
     * 业务处理
     */
    void handle();

    /**
     * 前置处理
     */
    void preHandle();

    /**
     * 后置处理
     */
    void postHandle();
}
