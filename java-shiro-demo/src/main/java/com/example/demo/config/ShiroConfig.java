package com.example.demo.config;

import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.PermissionService;
import com.example.demo.service.RealmService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Autowired
    private RealmService realmService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 通过配置的方式注入, 可以设置一些属性
     * @return
     */
    @Bean
    public Realm realm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        return shiroRealm;
    }

    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager(@Autowired Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // securityManager.setRealm(realm());
        securityManager.setRealm(realm);
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        /* -----------------路由规则-------------------- */

        // 过滤器是由顺序的,这里使用LinkedHashMap
        Map<String, String> map = new LinkedHashMap<>();
        // 从数据库动态获取到需要权限才可以访问的资源
        List<Permission> permissions = permissionService.findAll();
        if (null != permissions) {
            permissions.forEach(permission -> map.put(permission.getUrl(), String.format("perms[%s]", permission.getName())));
        }

        // 未登录可访问首页
        map.put("/", "anon");
        map.put("/index", "anon");
        map.put("/index.html", "anon");
        map.put("/login", "anon");
        map.put("/login.html", "anon");
        // 登入 登出
        map.put("/api/realm/login", "anon");
        map.put("/api/realm/logout", "logout");
        // 其他资源访问需要登录
        map.put("/*", "authc");
        map.put("/**", "authc");

        /* -----------------路由规则-------------------- */

        // 登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功 前后端分离不用设置
         shiroFilterFactoryBean.setSuccessUrl("/index");
        // 错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/api/realm/noPermission");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    // 不加这个注解不生效，具体不详
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    // 加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 自定义Realm,注入到bean容器,在配置类中再注入,可以设置一些属性
     */
    public class ShiroRealm extends AuthorizingRealm {

        /**
         * 授权
         * @param principalCollection
         * @return
         */
        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
            //从认证时传入的user获取登录用户名
            User user = (User) principalCollection.getPrimaryPrincipal();
            //添加角色和权限
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            for (Role role : user.getRoles()) {
                //添加角色
                simpleAuthorizationInfo.addRole(role.getName());
                //添加权限
                for (Permission permissions : role.getPermissions()) {
                    simpleAuthorizationInfo.addStringPermission(permissions.getName());
                }
            }
            return simpleAuthorizationInfo;
        }

        /**
         * 认证
         * @param authenticationToken
         * @return
         * @throws AuthenticationException
         */
        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
            // 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
            if (authenticationToken.getPrincipal() == null) {
                return null;
            }
            // 获取用户信息
            String name = authenticationToken.getPrincipal().toString();
            User user = realmService.getUserByName(name);
            if (user == null) {
                // 这里返回后会报出对应异常
                return null;
            } else {
                // 将user放入principal,则在上边可以拿到
                // 这里验证authenticationToken和simpleAuthenticationInfo的信息 使用用户输入的(UsernamePasswordToken)authenticationToken与数据库账号密码进行比较
                return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            }
        }
    }
}
