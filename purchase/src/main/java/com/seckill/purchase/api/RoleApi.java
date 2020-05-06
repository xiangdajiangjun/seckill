package com.seckill.purchase.api;

import com.seckill.purchase.dao.AccountDao;
import com.seckill.purchase.dao.RoleDao;
import com.seckill.purchase.entity.Account;
import com.seckill.purchase.entity.Role;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/api/role")
public class RoleApi {
    @Resource
    private RoleDao roleDao;
    @Resource
    private AccountDao accountDao;

    @RequestMapping("/list")
    @ResponseBody
    List<Role> getRoleList(){
        return roleDao.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/distribute")
    @ResponseBody
    Boolean roleDistribute(@RequestParam("username") String username, @RequestParam("roleId") Integer roleId){
        Account account = accountDao.findAccountByUsername(username);
        List<Role> roleList = account.getRoleList();
        Role role = roleDao.findById(roleId);
        if (roleList.contains(role)){
            if (!roleList.remove(role))
                return false;
        }
        else{
            roleList.add(role);
        }
        accountDao.save(account);
        //可以测试了，看看改变角色能不能改到数据库表单，以及失败会不会报错
        return true;
    }

}
