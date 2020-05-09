package com.seckill.seller.controller;

import com.seckill.seller.config.ShiroConfig;
import com.seckill.seller.dao.ShopDao;
import com.seckill.seller.entity.Activities;
import com.seckill.seller.entity.ActivitiesGoods;
import com.seckill.seller.entity.ActivitiesShop;
import com.seckill.seller.entity.Goods;
import com.seckill.seller.service.AccountService;
import com.seckill.seller.service.Impl.ActivitiesService;
import com.seckill.seller.service.Impl.ActivitiesShopService;
import com.seckill.seller.service.PurchaseRemote;
import com.seckill.seller.utils.PageUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/activities")
public class ActivitiesController {
    @Resource
    private ActivitiesService activitiesService;
    @Resource
    private ActivitiesShopService activitiesShopService;
    @Resource
    private AccountService accountService;
    @Resource
    private ShopDao shopDao;
    @Resource
    private PurchaseRemote purchaseRemote;


    @RequestMapping("/list")
    public String getActivitiesList(Model model, @RequestParam(value = "page",required = false,defaultValue = "1")Integer currentPage) throws Exception {
        List<Activities> activitiesList=activitiesService.getActivitiesList(2);
        PageUtil<Activities> pageUtil=PageUtil.createPage(currentPage);
        pageUtil.doPage(activitiesList);
        model.addAttribute("page",pageUtil);
        return "activitieslist";
    }
    @RequestMapping("/join")
    public void joinActivities(@RequestParam("activitiesId") Integer activitiesId,HttpServletResponse httpServletResponse){
        //根据本账号用户查出店铺id
        String keeperName = (String) SecurityUtils.getSubject().getPrincipal();
        int userId = accountService.getUserByUserName(keeperName).getId();
        Integer shopId = shopDao.findByKeepperId(userId).getId();
        Boolean isSuccess = activitiesService.joinActivities(shopId,activitiesId);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(204);

    }

    /**
     * 上架阶段的本店参与的活动列表
     * @param model
     * @param currentPage
     * @return
     * @throws Exception
     */
    @RequestMapping("/upperlist")
    public String  upperPage(Model model, @RequestParam(value = "page",required = false,defaultValue = "1")Integer currentPage) throws Exception {
        List<Activities> activitiesList=activitiesService.getActivitiesList(3);
        PageUtil<Activities> pageUtil=PageUtil.createPage(currentPage);
        pageUtil.doPage(activitiesList);
        model.addAttribute("page",pageUtil);
        return "activitiesupper";
    }

    /**
     * 本店某活动中上架商品列表
     * @param activitiesId
     * @param model
     * @return
     */
    @GetMapping("/uppergoodslist")
    public String upperListPage(@RequestParam("activitiesId") Integer activitiesId, @RequestParam(value = "page",required = false,defaultValue = "1")Integer currentPage,Model model) throws Exception {
        //根据本账号用户查出店铺id
        String keeperName = (String) SecurityUtils.getSubject().getPrincipal();
        int userId = accountService.getUserByUserName(keeperName).getId();
        Integer shopId = shopDao.findByKeepperId(userId).getId();
        //得到本店所有商品
        List<Goods> allGoods = purchaseRemote.getAllGoods(shopId);
        Map<Integer, String> goodsIdToName = allGoods.stream().collect(Collectors.toMap(Goods::getId, Goods::getName, (k1, k2) -> k1));
        Map<Integer, Double> goodsIdToPrice = allGoods.stream().collect(Collectors.toMap(Goods::getId, Goods::getPrice, (k1, k2) -> k1));
        List<Integer> goodsIdList = allGoods.stream().map(Goods::getId).collect(Collectors.toList());
        //得到秒杀活动上架的商品
        List<ActivitiesGoods> activitiesGoodsList= activitiesService.getActivitiesGoodsList(activitiesId);
        //筛选本店商品
        activitiesGoodsList=activitiesGoodsList.stream().filter(activitiesGoods -> goodsIdList.contains(activitiesGoods.getGoodsId())).collect(Collectors.toList());
        //补上商品名字
        activitiesGoodsList.forEach(activitiesGoods -> {
            activitiesGoods.setName(goodsIdToName.get(activitiesGoods.getGoodsId()));
            activitiesGoods.setOriginalPrice(goodsIdToPrice.get(activitiesGoods.getGoodsId()));
        });
        //封装
        PageUtil<ActivitiesGoods> pageUtil=PageUtil.createPage(currentPage);
        pageUtil.doPage(activitiesGoodsList);
        model.addAttribute("page",pageUtil);
        return "uppergoodslist";

    }

    /**
     * 上架秒杀商品的页面
     * @return
     */
    @GetMapping("/upper")
    public String upperPage(Model model){
        //根据本账号用户查出店铺id
        String keeperName = (String) SecurityUtils.getSubject().getPrincipal();
        int userId = accountService.getUserByUserName(keeperName).getId();
        Integer shopId = shopDao.findByKeepperId(userId).getId();
        //得到本店所有商品
        List<Goods> allGoods = purchaseRemote.getAllGoods(shopId).stream().filter(Goods::getIsSell).collect(Collectors.toList());
        //可上架的所有活动
        List<Activities> activitiesList=activitiesService.getActivitiesList(3);

        model.addAttribute("allGoods",allGoods);
        model.addAttribute("activitiesList",activitiesList.stream().map(Activities::getId).collect(Collectors.toList()));


        return "upperpage";
    }

    /**
     * 提交秒杀商品
     */
    @PostMapping("/upper")
    @ResponseBody
    public String upperGoods(ActivitiesGoods activitiesGoods,Model model){
        try {
            Integer activitiesId=activitiesGoods.getActivitiesId();
            //根据本账号用户查出店铺id
            String keeperName = (String) SecurityUtils.getSubject().getPrincipal();
            int userId = accountService.getUserByUserName(keeperName).getId();
            Integer shopId = shopDao.findByKeepperId(userId).getId();
            //1.确定本店在本活动中还有上架名额
            Activities activities = activitiesService.getActivitiesById(activitiesId);
            ActivitiesShop activitiesShop =activitiesShopService.getActivitiesShop(activitiesId,shopId);
            if (activities.getGoodsLimit()<=activitiesShop.getGoodsNum()){
                return"本活动商品名额已满";
            }
            //2.确定本商品未在本次活动中
            //得到秒杀活动上架的商品
            List<ActivitiesGoods> activitiesGoodsList= activitiesService.getActivitiesGoodsList(activitiesId);
            List<Integer> goodsList = activitiesGoodsList.stream().map(ActivitiesGoods::getGoodsId).collect(Collectors.toList());
            if (goodsList.contains(activitiesGoods.getGoodsId())){
                return"商品已在本活动上架";
            }
            //2.5 确定秒杀价低于原价
            Goods goods = purchaseRemote.getGoods(activitiesGoods.getGoodsId());
            if (goods.getPrice()<activitiesGoods.getPresentPrice()){
                return "秒杀价应该低于原价";
            }
            else {
                activitiesGoods.setOriginalPrice(goods.getPrice());
                activitiesGoods.setName(goods.getName());
            }
            //3.无问题，保存上架
            activitiesGoods.setShopId(shopId);
            activitiesGoods.setBuyerList("");
            Boolean isSuccess = activitiesService.saveActivitiesGoods(activitiesGoods);
            return "上架成功";
        }catch (Exception e){
            return "发生错误";
        }
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
