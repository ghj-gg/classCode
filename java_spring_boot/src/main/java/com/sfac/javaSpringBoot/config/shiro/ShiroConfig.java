package com.sfac.javaSpringBoot.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@Component
public class ShiroConfig {

    @Autowired
    private MyRealm myRealm;

    @Bean
    public DefaultSecurityManager securityManager(){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myRealm);
        manager.setRememberMeManager(rememberMeManager());
        manager.setSessionManager(sessionManager());
        return manager;
    }

    /**
     * 配置Shiro过滤工厂
     * ------------------
     * 拦截权限
     * anon:匿名访问，无需登录
     * authc:登录后才能访问
     * user:登录过能访问
     * logout:退出
     * roles:角色过滤器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager());


        factoryBean.setLoginUrl("/account/login");
        factoryBean.setSuccessUrl("/account/dashboard");

        Map<String,String> map = new LinkedHashMap<>();

        map.put("/","anon");
        map.put("/static/**","anon");
        map.put("/js/**","anon");
        map.put("/css/**","anon");
        map.put("/plugin/**","anon");
        map.put("/account/login","anon");
        map.put("/api/login","anon");
        map.put("/account/register","anon");
        map.put("/api/user","anon");
        map.put("/account/profile","anon");

        //如果使用"记住我"功能，则采用user规则，如果必须要用户登录则采用authc规则
        //path范围越大的放越后面
        map.put("/test/**","authc");
        map.put("/**","user");

        factoryBean.setFilterChainDefinitionMap(map);

        return factoryBean;
    }

    /**
     * 注册shiro方言，让thymeleaf支持shiro标签
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    /**
     * 自动代理类，支持shiro的注解
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启Shiro注解
     */
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 记住我：rememberMeCookie
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //如果httpOnly设置为true，则客户端不会暴露给客户端脚本代码
        //使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击
        simpleCookie.setHttpOnly(true);
        //记住我cookie生效时间，单位是秒
        simpleCookie.setMaxAge(10*24*60*60);
        return simpleCookie;
    }

    /**
     * 记住我：cookie管理器
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        byte[] cipherKey = Base64.decode("wGiHplamyXlVB11UXWol8g====");
        cookieRememberMeManager.setCipherService(new AesCipherService());
        cookieRememberMeManager.setCipherKey(cipherKey);
        return cookieRememberMeManager;
    }

    /**
     * sessionCookie
     */
    @Bean
    public SimpleCookie sessionCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("shiro.session");
        simpleCookie.setPath("/");
        simpleCookie.setHttpOnly(true);
        //simpleCookie.setMaxAge(1*24*60*60);
        simpleCookie.setMaxAge(5);
        return simpleCookie;
    }

    /**
     * sessionManager
     */
    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionIdCookie(sessionCookie());
        return sessionManager;
    }

}
