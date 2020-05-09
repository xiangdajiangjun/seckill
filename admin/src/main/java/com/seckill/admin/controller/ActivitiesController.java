package com.seckill.admin.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.seckill.admin.dto.ActivitiesDto;
import com.seckill.admin.entity.Account;
import com.seckill.admin.entity.Activities;
import com.seckill.admin.service.AccountService;
import com.seckill.admin.service.Impl.ActivitiesService;
import com.seckill.admin.utils.PageUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/activities")
public class ActivitiesController {
    @Resource
    private ActivitiesService activitiesService;
    @RequestMapping("/list")
    public String getActivitiesList(Model model, @RequestParam(value = "page",required = false,defaultValue = "1")Integer currentPage,@RequestParam("type") Integer status) throws Exception {
        List<Activities> activitiesList=activitiesService.getActivitiesList(status);
        //时间格式显示
        activitiesList.stream().forEach(activities -> {
            String dateStr = dateToStr(activities.getStartDatetime(), "yyyy-MM-dd HH:mm:ss");
            activities.setStartDatetime(strToDate(dateStr,"yyyy-MM-dd HH:mm:ss"));
            dateStr = dateToStr(activities.getEndDatetime(), "yyyy-MM-dd HH:mm:ss");
            activities.setEndDatetime(strToDate(dateStr,"yyyy-MM-dd HH:mm:ss"));
        });
        PageUtil<Activities> pageUtil=PageUtil.createPage(currentPage);
        pageUtil.doPage(activitiesList);
        model.addAttribute("page",pageUtil);
        return "activitieslist";
    }
    @RequestMapping("/continue")
    public void continueStatus(@RequestParam("activitiesId") Integer activitiesId, HttpServletResponse httpServletResponse){
        Boolean isSuccess = activitiesService.continueStatus(activitiesId);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(204);
    }

    @GetMapping("/add")
    public String addPage(){
        return "activitiesadd";
    }
    @PostMapping("/add")
    public void addActivities(ActivitiesDto activitiesDto, HttpServletResponse httpServletResponse){
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        activitiesDto.setGoodsLimit(1);
        Activities activities=new Activities();
        activities.setStatus(1);
        activities.setCreator(username);
        activities.setShopLimit(activitiesDto.getShopLimit());
        activities.setGoodsLimit(activitiesDto.getGoodsLimit());
        activities.setShopNum(0);
        //时间处理
        Date startDate = strToDate(activitiesDto.getStartDatetime().replace('T',' '), "yyyy-MM-dd HH:mm");
        activities.setStartDatetime(startDate);
        Date endDate = strToDate(activitiesDto.getEndDatetime().replace('T',' '), "yyyy-MM-dd HH:mm");
        activities.setEndDatetime(endDate);
        Boolean isSuccess = activitiesService.addActivities(activities);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(204);
    }
    /**
     * 将字符串转换成指定格式的日期
     *
     * @param str        日期字符串.
     * @param dateFormat 日期格式. 如果为空，默认为:yyyy-MM-dd HH:mm:ss.
     * @return
     */
    public Date strToDate(final String str, String dateFormat) {
        if (str == null || str.trim().length() == 0) return null;
        try {
            if (dateFormat == null || dateFormat.length() == 0) dateFormat = "yyyy-MM-dd HH:mm:ss";
            DateFormat fmt = new SimpleDateFormat(dateFormat);
            return fmt.parse(str.trim());

        } catch (Exception ex) {
            return null;
        }
    }
    /**
     * 将日期转换成指定格式的字符串.
     *
     * @param date       日期
     * @param dateFormat 日期格式. 如果为空，默认为:yyyy-MM-dd HH:mm:ss.
     * @since 2018/9/19 14:40
     **/
    public static String dateToStr(final Date date, String dateFormat) {
        if (date == null) {
            return "";
        }
        try {
            if (dateFormat == null || dateFormat.trim().length() == 0) {
                dateFormat = "yyyy-MM-dd HH:mm:ss";
            }
            DateFormat fmt = new SimpleDateFormat(dateFormat.trim());
            return fmt.format(date);
        } catch (Exception ex) {
            return "";
        }
    }

}
