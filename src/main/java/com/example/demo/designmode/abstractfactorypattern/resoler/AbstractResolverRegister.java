package com.example.demo.designmode.abstractfactorypattern.resoler;

import java.util.*;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class AbstractResolverRegister<K, V extends IResolver<K>> implements ApplicationContextAware {
    private final static Logger logger = LoggerFactory.getLogger(AbstractResolverRegister.class);

    public ApplicationContext context;

    private Map<K, V> resolverMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public V getResolvers(K k) {
        return resolverMap.get(k);
    }

    protected abstract Class<V> getResolverClass();

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        Class<V> clazz = getResolverClass();
        Collection<V> list = context.getBeansOfType(clazz).values();
        if (CollectionUtils.isNotEmpty(list)) {
            for (V v1 : list) {
                if (v1.getKey() instanceof String) {
                    String str = (String)v1.getKey();
                    if (StringUtils.isEmpty(str)) {
                        logger.warn("配置未空，采用默认处理器，处理器类为：{}", v1);
                        resolverMap.put(v1.getKey(), v1);
                    } else {
                        List<String> strings = Arrays.asList(str.split(","));
                        for (String string : strings) {
                            logger.info("处理器code：{}，处理器类：{}", string, v1);
                            resolverMap.put((K)string, v1);
                        }
                    }
                } else {
                    if (v1.getKey() == null) {
                        logger.warn("配置为空，采用默认处理器：{}", v1);
                    } else {
                        logger.info("非String类型，处理器code：{}，处理器类：{}", v1.getKey(), v1);
                    }
                    resolverMap.put(v1.getKey(), v1);
                }
            }
        }
        logger.info("初始化classType：{}，初始化对象length：{}", clazz, resolverMap.size());
    }
}
