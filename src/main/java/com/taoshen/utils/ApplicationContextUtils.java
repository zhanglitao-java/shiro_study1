package com.taoshen.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    //spring工厂的上下文context对象
    private static ApplicationContext applicationContext;

    @Override
    //ApplicationContextAware提供的函数
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //根据bean的名字 获取工厂中指定bean对象 向外暴露 就可以想要容器中什么bean都有了
    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }
}
