package com.seckill.purchase.config;

import com.seckill.purchase.entity.Account;
import com.seckill.purchase.service.AccountService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class MyRealm extends AuthorizingRealm {
    @Resource
    private AccountService accountService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        //从数据库取出
        Account account = accountService.findByUsername(username);

        //参数：取出的实体/用户名、数据库对应密码、[数据库对应的盐值]、realm名
        //return new SimpleAuthenticationInfo(username,account.getPassword(), ByteSource.Util.bytes(account.getSalt()),getName());
        return new SimpleAuthenticationInfo(account,account.getPassword(),getName());
    }
}
