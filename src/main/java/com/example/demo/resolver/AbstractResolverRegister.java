package com.example.demo.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by 25790 on 2021/12/4.
 * 同类型解析器抽象类
 */
public abstract class AbstractResolverRegister<K,V extends IResolver<K>> implements ApplicationContextAware {
    private final static Logger logger = LoggerFactory.getLogger(AbstractResolverRegister.class);

    private  ApplicationContext ctx;

    private Map<K,V> resolverMap=new HashMap<>();
    @Override
    public void setApplicationContext(ApplicationContext args) throws BeansException {
        ctx=args;
    }

    public V getResolver(K k1){
        return resolverMap.get(k1);
    }

    /**
     * @PostConstruct 当一个class被注解为一个bean时，这个class里面被@PostConstruct注解标记的方法会在程序启动时执行
     *
     */
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init(){
        Class<V> clazz = getResolverClass();
        Collection<V> list = ctx.getBeansOfType(clazz).values();
        if (!CollectionUtils.isEmpty(list)){
            list.forEach((v1)->{
                if (v1.getKey() instanceof String){
                    String str = (String) v1.getKey();
                    if (StringUtils.isEmpty(str)){
                        logger.warn("配置未空，采用默认处理器，处理器类：{}",v1);
                        resolverMap.put(v1.getKey(),v1);
                    }else{
                        List<String> strs = Arrays.asList(str.split(","));
                        for (String v:strs){
                            logger.info("处理器code：{},处理器类：{}",v,v1);
                            resolverMap.put((K)v,v1);
                        }
                    }
                }else {
                    if (v1.getKey()==null){
                        logger.info("配置为空，采用默认处理器，处理器类：{}",v1);
                    }else{
                        logger.info("非String类型的，业务处理器code：{}，业务处理器：{}",v1.getKey(),v1);
                    }
                    resolverMap.put(v1.getKey(),v1);
                }

            });
            logger.info("初始化classType：{}，resolverRegister.length:{}",clazz,resolverMap.size());
        }
    }

    /**
     * 注册容器的类型
     * @return
     */
    protected abstract Class<V> getResolverClass();
}
