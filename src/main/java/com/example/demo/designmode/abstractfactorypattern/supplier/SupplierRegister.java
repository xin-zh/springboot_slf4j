package com.example.demo.designmode.abstractfactorypattern.supplier;

import com.example.demo.designmode.abstractfactorypattern.resoler.AbstractResolverRegister;

public class SupplierRegister extends AbstractResolverRegister<String, ISupplierProducer> {

    @Override
    protected Class<ISupplierProducer> getResolverClass() {
        return ISupplierProducer.class;
    }
}
