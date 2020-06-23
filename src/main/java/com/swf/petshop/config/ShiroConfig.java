package com.swf.petshop.config;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro的配置类
 */
@Configuration
public class ShiroConfig {

    /**
     * realm对象
     * @return
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * cookie对象;
     * 设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //记住我cookie生效时间30天 ,单位秒
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * cookie管理对象
     * 生成rememberMe管理器
     * @return
     */
    @Bean(name = "rememberMeManager")
    public CookieRememberMeManager rememberMeManager(@Qualifier("rememberMeCookie") SimpleCookie rememberMeCookie){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //关联cookie对象
        cookieRememberMeManager.setCookie(rememberMeCookie);
        //rememberMe cookie加密的密钥 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    /**
     * 安全管理器
     * @param userRealm
     * @param rememberMeManager
     * @return
     */
    @Bean(name = "webSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm,
                                                                  @Qualifier("rememberMeManager") CookieRememberMeManager rememberMeManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        defaultWebSecurityManager.setRealm(userRealm);
        //注入记住我管理器
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager);
        return defaultWebSecurityManager;
    }

    /**
     * 过滤器工厂
     * @param webSecurityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("webSecurityManager") DefaultWebSecurityManager webSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 配置安全管理器
        shiroFilterFactoryBean.setSecurityManager(webSecurityManager);
        //拦截
        Map<String,String> filterMap = new LinkedHashMap<>();
        //授权
        /*
            anno:无需认证
            authc：必须认证才可
            user：必须有记住我功能才能访问
            perms：拥有某个权限
            role：拥有某个角色权限
        */
        filterMap.put("/**","authc");
        /*
            授予访问不同页面需要用户的不同权限
            如：
                filterMap.put("/user/add","perms[user:add]");
        */
        //注入拦截器
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        //设置登录的请求
        shiroFilterFactoryBean.setLoginUrl("/login");
        //设置无权访问的请求
        shiroFilterFactoryBean.setUnauthorizedUrl("/noPermission");
        return shiroFilterFactoryBean;
    }

}
