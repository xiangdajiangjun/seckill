package com.seckill.admin.controller;

import com.seckill.admin.entity.GoodType;
import com.seckill.admin.service.Impl.TypeService;
import com.seckill.admin.utils.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/type")
public class TypeController {
    @Resource
    private TypeService typeService;

    @RequestMapping("/add")
    public String addPage(){
        return "typeadd";
    }
    @RequestMapping("/insert")
    public void addType(HttpServletResponse httpServletResponse,@RequestParam("tag") String tag){
        Boolean isSuccess = typeService.addType(tag);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(204);
    }

    @RequestMapping("/list")
    public String getTypeList(Model model,@RequestParam(value = "page",required = false,defaultValue = "1")Integer currentPage) throws Exception {
        List<GoodType> typeList = typeService.getTypeList();
        PageUtil<GoodType> page = PageUtil.createPage(currentPage);
        page.doPage(typeList);
        model.addAttribute("page",page);
        return "typelist";
    }

    @RequestMapping("/delete")
    public void delType(@RequestParam("typeId") Integer typeId, HttpServletResponse httpServletResponse){
        Boolean isSuccess = typeService.delType(typeId);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(204);

    }

    @RequestMapping("/status")
    public void changeStatus(@RequestParam("typeId") Integer typeId, HttpServletResponse httpServletResponse){
        Boolean isSuccess = typeService.changeStatus(typeId);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(204);
    }
}
