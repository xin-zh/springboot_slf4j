package com.example.demo.designmode.abstractfactorypattern.supplier;

import com.example.demo.designmode.abstractfactorypattern.resoler.IResolver;

public interface ISupplierProducer extends IResolver<String> {
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
