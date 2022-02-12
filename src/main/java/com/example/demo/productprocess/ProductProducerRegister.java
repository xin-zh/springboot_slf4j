package com.example.demo.productprocess;

import com.example.demo.resolver.AbstractResolverRegister;
import org.springframework.stereotype.Service;

/**
 * Created by 25790 on 2021/12/4.
 */
@Service
public class ProductProducerRegister extends AbstractResolverRegister<String,IProductProducer> {
    @Override
    protected Class<IProductProducer> getResolverClass() {
        return IProductProducer.class;
    }
}
