package com.example.demo.productprocess;

import org.springframework.stereotype.Service;

/**
 * Created by 25790 on 2021/12/4.
 */
@Service
public class ProductProcessor9000 implements IProductProducer {
    @Override
    public String getKey() {
        return "9000";
    }
}
