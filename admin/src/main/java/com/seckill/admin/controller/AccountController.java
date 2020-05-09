package com.seckill.admin.controller;

import com.seckill.admin.entity.Account;
import com.seckill.admin.entity.Role;
import com.seckill.admin.service.AccountService;
import com.seckill.admin.utils.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Resource
    private AccountService accountService;


    @RequestMapping("/role/distribute")
    public void roleDistribute(@RequestParam("username") String username,@RequestParam("roleId") Integer roleId,HttpServletResponse httpServletResponse){
        Boolean isSuccess = accountService.roleDistribute(username,roleId);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(500);
    }
    @RequestMapping("/rolelist")
    public String accountRole(Model model,@RequestParam(value = "page",required = false,defaultValue = "1")Integer currentPage) throws Exception {
        //账户列表
        List<Account> accountList = accountService.getAccountList();
        PageUtil<Account> pageUtil=PageUtil.createPage(currentPage);
        pageUtil.doPage(accountList);
        model.addAttribute("page",pageUtil);
        //角色信息
        List<Role> roleList = accountService.getRoleList();
        model.addAttribute("roleList",roleList);
        return "account_role";
    }

    @RequestMapping("/list")
    public String getAccountList(Model model,@RequestParam(value = "page",required = false,defaultValue = "1")Integer currentPage) throws Exception {
        List<Account> accountList = accountService.getAccountList();
        PageUtil<Account> pageUtil=PageUtil.createPage(currentPage);
        pageUtil.doPage(accountList);
        model.addAttribute("page",pageUtil);
        return "accountlist";
    }

    @RequestMapping("/status")
    public void changeStatus(@RequestParam("username") String username, HttpServletResponse httpServletResponse){
        Boolean isSuccess = accountService.changeStatus(username);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(204);
    }
}
