package com.example.demo.designmode.abstractfactorypattern.product;

import com.example.demo.designmode.abstractfactorypattern.resoler.AbstractResolverRegister;

public class ProductRegister extends AbstractResolverRegister<String, IProductProducer> {
    @Override
    protected Class<IProductProducer> getResolverClass() {
        return IProductProducer.class;
    }
}
