package com.seckill.purchase.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    //第一个配置（总配置）
     /**
     *  配置shiroFilterFactoryBean
     *  配置基本的过滤规则
     * @param securityManager 在后续步骤有配置并注入
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //配置管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //配置:登陆\登陆成功页面\登陆失败页面（如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面）
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        //配置基本的策略规则（过滤器）,
        //支持Ant风格
        /**
         * Shiro内置过滤器，可以实现权限相关的拦截器，哪些可以直接访问 ，哪些需要权限  ，按照顺序,先配置的起作用
         *    常用的过滤器：
         *       anon: 无需认证（登录）可以访问（所有 url 都都可以匿名访问）
         *       authc: 必须认证才可以访问
         *       user: 如果使用rememberMe的功能可以直接访问（配置记住我或认证通过可以访问）
         *       perms： 该资源必须得到资源权限才可以访问
         *       role: 该资源必须得到角色权限才可以访问
         */
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/api/**", "anon");
//        filterChainDefinitionMap.put("/good/**", "anon");
//        filterChainDefinitionMap.put("/user/**", "anon");
        filterChainDefinitionMap.put("/test/**", "anon");
//        filterChainDefinitionMap.put("/cart/**", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/**/image/**", "anon");
        filterChainDefinitionMap.put("/**/css/**", "anon");
        filterChainDefinitionMap.put("/**/js/**", "anon");
        filterChainDefinitionMap.put("/**/fonts/**", "anon");
        filterChainDefinitionMap.put("**.ico", "anon");
        //配置注销的URL
        filterChainDefinitionMap.put("/logout", "logout");


        //权限配置
        filterChainDefinitionMap.put("/good/**","perms[see_goods]");
        filterChainDefinitionMap.put("/user/**","perms[user_info]");
        filterChainDefinitionMap.put("/cart/**","perms[see_cart]");
        filterChainDefinitionMap.put("/order/**","perms[see_order]");
        filterChainDefinitionMap.put("/seckill/**","perms[into_seckill]");
        //似乎不配置就默认无需认证
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    //第二个配置：配置Cache，此配置可选，暂时不配

    //第三个配置：securityManager的相关配置
    //3.1配置一个或多个realm备用
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(3);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    @Bean
    public MyRealm myRealm(HashedCredentialsMatcher hashedCredentialsMatcher){
        MyRealm myRealm =new MyRealm();
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myRealm;
    }
    //3.2配置Authenticator认证器,认证什么情况下算用户认证通过了，即reaml的使用规则，使用多个还是一个，需要通过几个（有默认暂略）


    //3.3 配置securityManager
    @Bean
    public SecurityManager securityManager(MyRealm myRealm){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm);
        return securityManager;
    }


    //其他
    //方言支持
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }


}
