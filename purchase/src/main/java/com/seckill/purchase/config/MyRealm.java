package com.seckill.purchase.config;

import com.seckill.purchase.entity.Account;
import com.seckill.purchase.service.AccountService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
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
        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken)token;
        //token是待验证的，Principal是唯一主体（如用户名/邮箱），crxxx是证书（如密码,然而并不一样。。）；
        // 返回的AuthenticationInfo是从数据库取出的信息，用于比对
        String username = (String)token.getPrincipal();
        //从数据库取出对应数据
        Account account = accountService.getAccount(username);
        //参数：取出的实体/用户名、数据库对应密码、[数据库对应的盐值]、realm名
        return new SimpleAuthenticationInfo(username,account.getPassword(), ByteSource.Util.bytes(account.getSalt()),getName());
        //return new SimpleAuthenticationInfo(username,account.getPassword(),getName());
    }
}
