package com.taoshen.config;

import com.taoshen.shiro.realms.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来整合shiro框架相关的配置类
 * 因为之前使用各种东西的时候都是ssm整合框架 还没SpringBoot注解开发的优雅 所以各种配置都是配置文件去完成 然后在SpringMVC项目
 * 中的web.xml设置监听器 从而实现各种框架之间配置文件的整合  所以在springboot应用中 就搞一个config文件夹 然后使用@Configuration
 * 去写一些配置文件类  再来@Bean注解将shiro的jar包api导入容器中 从而完成配置文件  某种意义上来说 也完成SpringBoot整合shiro
 */
@Configuration//该类为IOC容器配置类
public class ShiroConfig {
    //创建shiroFilter  负责拦截所有请求
    @Bean//函数返回值类型为IOC的bean对象
    //通过形参从IOC容器中去找bean注入DefaultWebSecurityManager
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        //ShiroFilter过滤器工厂的Bean 产生的ShiroFilter自然帮助拦截请求
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //给filter设置安全管理器 也就是认证授权的核心机制SecurityManager
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //配置系统受限资源
        //配置系统公共资源
        Map<String, String> map = new HashMap();

        //authc 表示请求这个资源需要认证和授权
        map.put("/user/login", "anon");//设置为公共资源
        map.put("/user/register", "anon");//设置为公共资源
        map.put("/register.jsp", "anon");//设置为公共资源
        map.put("/**", "authc");//受限资源

        //默认认证界面路径 如果不设置 也就是默认去找login.jsp 所以说shiro对jsp还是比较友好的
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");

        //给ShiroFilter设置上 资源管理的map集合
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    //创建安全管理器
    @Bean
    //因为这里环境是一个web项目 所以显然是不能使用最原始的SecurityManager了 而是web相关的Manager 给他一个Realm数据源
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm) {
        //web环境下的 DefaultWebSecurityManager
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        //给安全管理器设置realm数据源
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    //创建自定义realm
    @Bean
    //返回一个数据源Realm对象 这里也是我们自定义Realm
    public Realm getRealm() {
        CustomerRealm customerRealm = new CustomerRealm();
        //修改凭证校验匹配器  告诉其我们使用的md5加密 散列次数为1024
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置加密算法为md5
        credentialsMatcher.setHashAlgorithmName("md5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);

        //将凭证校验匹配器设置给realm
        customerRealm.setCredentialsMatcher(credentialsMatcher);
        return customerRealm;
    }

}
