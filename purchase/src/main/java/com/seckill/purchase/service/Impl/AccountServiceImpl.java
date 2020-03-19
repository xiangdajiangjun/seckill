package com.seckill.purchase.service.Impl;

import com.seckill.purchase.dao.AccountDao;
import com.seckill.purchase.dao.UserDao;
import com.seckill.purchase.dto.RegisterDto;
import com.seckill.purchase.entity.Account;
import com.seckill.purchase.entity.User;
import com.seckill.purchase.service.AccountService;
import com.seckill.purchase.service.exception.SginException;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.omg.CORBA.UnknownUserException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountDao accountDao;
    @Resource
    private UserDao userDao;

    @Override
    public Account getAccount(String username) {
        Account account = accountDao.findAccountByUsername(username);
        if (account==null)
            throw new UnknownAccountException();
        return account;
    }

    @Override
    public String registerAccount(RegisterDto registerDto) {
        //检测用户名是否已存在
        if(accountDao.findAccountByUsername(registerDto.getUsername())!=null)
            return "用户名已存在";
        if(userDao.findUserByEmail(registerDto.getEmail())!=null)
            return "邮箱已被注册。";

        //新增账户
        Account account =new Account();
        //设置盐值
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        salt=salt+registerDto.getEmail();
        String newPassword =new SimpleHash("MD5",registerDto.getPassword(),ByteSource.Util.bytes(salt),3).toHex();
        account.setUsername(registerDto.getUsername());
        account.setType("buyer");
        account.setSalt(salt);
        account.setState(0);
        account.setPassword(newPassword);
        accountDao.save(account);

        //新增用户（买家）
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        userDao.save(user);
        return "success";
    }

    /**
     * 加密算法
     * @param account
     * @return 加密密码
     */
    @Override
    public String encryptor(Account account) {
        String newPassword = new Md5Hash(account.getPassword(), ByteSource.Util.bytes(account.getSalt()),3).toString();
        return newPassword;
    }
}