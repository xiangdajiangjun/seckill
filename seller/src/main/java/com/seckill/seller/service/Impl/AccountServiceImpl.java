package com.seckill.seller.service.Impl;

import com.seckill.seller.dao.AccountDao;
import com.seckill.seller.dao.GoodTypeDao;
import com.seckill.seller.dao.ShopDao;
import com.seckill.seller.dao.UserDao;
import com.seckill.seller.dto.RegisterDto;
import com.seckill.seller.entity.*;
import com.seckill.seller.service.AccountService;
import com.seckill.seller.utils.exception.AccountTypeException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountDao accountDao;
    @Resource
    private UserDao userDao;
    @Resource
    private GoodTypeDao goodTypeDao;
    @Resource
    private ShopDao shopDao;

    @Override
    public Account getAccount(String username) {
        Account account = accountDao.findAccountByUsername(username);
        if (account==null||(!account.getType().equals("2")))
            throw new UnknownAccountException();
        return account;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String registerAccount(RegisterDto registerDto) {
        //检测用户名是否已存在
        if(accountDao.findAccountByUsername(registerDto.getUsername())!=null)
            return "用户名已存在";
        if(userDao.findUserByEmail(registerDto.getEmail())!=null)
            return "邮箱已被注册。";

        try{
            //新增账户
            Account account =new Account();
            //设置盐值
            String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
            salt=salt+registerDto.getEmail();
            String newPassword =new SimpleHash("MD5",registerDto.getPassword(),ByteSource.Util.bytes(salt),3).toHex();
            account.setUsername(registerDto.getUsername());
            account.setType("2");
            account.setSalt(salt);
            account.setState(0);
            account.setPassword(newPassword);
            accountDao.save(account);

            //新增用户
            User user = new User();
            user.setUsername(registerDto.getUsername());
            user.setEmail(registerDto.getEmail());
            userDao.save(user);

            //新增店铺
            Shop shop =new Shop();
            shop.setName(registerDto.getShopName());
            shop.setShopLevel(0);
            shop.setKeepperId(user.getId());
            shopDao.save(shop);
        }catch (Exception e){
            return "注册出现错误";
        }


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

    @Override
    public Map<Role, List<Permission>> getRoleAndPermission(Account account) {
        Map<Role, List<Permission>> roleListMap = new HashMap<>();
        List<Role> roleList = account.getRoleList();
        List<Permission> permissionList;
        for (Role role:roleList){
            permissionList = new ArrayList<>();

        }
        return null;
    }

    @Override
    public List<GoodType> getGoodType() {

        return goodTypeDao.findAll().stream().filter(GoodType::getIsAvailable).collect(Collectors.toList());
    }

    @Override
    public User getUserByUserName(String userName) {

        return userDao.findByUsername(userName);
    }
}
