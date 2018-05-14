package cn.jbg.cmab.backend.mall.controller;

import cn.jbg.cmab.backend.mall.bean.Item;
import cn.jbg.cmab.backend.mall.bean.UsedGoods;
import cn.jbg.cmab.backend.mall.bean.UsedGoodsDetail;
import cn.jbg.cmab.backend.mall.service.UsedGoodsService;
import cn.jbg.cmab.backend.users.bean.UsersDetail;
import cn.jbg.cmab.backend.users.service.UsersService;
import cn.jbg.cmab.common.dao.Page;
import cn.jbg.cmab.common.mybatis.ExamplePager;
import cn.jbg.cmab.common.util.Constants;
import cn.jbg.cmab.common.util.MybatisParamUtil;
import cn.jbg.cmab.common.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jbg on 2018/4/5.
 * 二手商品
 */
@RestController
@RequestMapping("/UsedGoodsController")
public class UsedGoodsController {

    @Autowired
    private UsedGoodsService usedGoodsService;

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/getGoodsList",method = RequestMethod.POST)
    public ResponseUtil getGoodsList(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        Map<String, Object> resultObject = new HashMap<String, Object>();
        try{

            String pageIndex = params.get("pageIndex").toString();
            String pageSize = params.get("pageSize").toString();
            Page<UsedGoods> page = new Page<UsedGoods>(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
            page.setParams(params);
            String goodsName = params.get("goodsName").toString();
            //实现分页
            ExamplePager.buildByPage(page);

            boolean fromUser = false;
            if(params.get("fromUser") != null){
                fromUser = true;
            }
            List<UsedGoods> usedGoodsList = usedGoodsService.queryGoods(goodsName,fromUser);
            resultObject.put("goodsList",usedGoodsList);
            resultObject.put("pageInfo",page.getPageInfo(page));
            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultObject(resultObject);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }

        return responseUtil;
    }


    @RequestMapping(value = "/getUsedGoodsById",method = RequestMethod.POST)
    public ResponseUtil getUsedGoodsById(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long usedGoodsId = Long.parseLong(params.get("usedGoodsId").toString());
            UsedGoods usedGoods = usedGoodsService.getUsedGoodsById(usedGoodsId);
            if(usedGoods != null){
                responseUtil.setResultCode(ResponseUtil.SUCCESS);
                responseUtil.setResultObject(usedGoods);
            }else{
                responseUtil.setResultCode(ResponseUtil.FAIL);
                responseUtil.setResultMsg("该二手商品已不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }
        return responseUtil;
    }

    @RequestMapping(value = "/getUsedGoodsDetailById",method = RequestMethod.POST)
    public ResponseUtil getUsedGoodsDetailById(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long usedGoodsId = Long.parseLong(params.get("usedGoodsId").toString());
            UsedGoods usedGoods = usedGoodsService.getUsedGoodsById(usedGoodsId);
            UsedGoodsDetail usedGoodsDetail = new UsedGoodsDetail();

            if(usedGoods != null){
                Long userId = usedGoods.getUserId();
                Long buyerId = usedGoods.getBuyerId();
                UsersDetail usersDetail = usersService.getUsersDetailById(userId);
                String userName = usersService.getUsersInfoById(userId).getNickName();
                String buyerName = "";
                if(buyerId != null){
                    buyerName = usersService.getUsersInfoById(buyerId).getNickName();
                }else{
                    buyerName = "暂未被购买";
                }

                usedGoodsDetail.setUsedGoods(usedGoods);
                usedGoodsDetail.setUsersDetail(usersDetail);
                usedGoodsDetail.setUserName(userName);
                usedGoodsDetail.setBuyerName(buyerName);


                responseUtil.setResultCode(ResponseUtil.SUCCESS);
                responseUtil.setResultObject(usedGoodsDetail);
            }else{
                responseUtil.setResultCode(ResponseUtil.FAIL);
                responseUtil.setResultMsg("该二手商品真的已不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }
        return responseUtil;
    }

    @RequestMapping(value = "/addUsedGoods",method = RequestMethod.POST)
    public ResponseUtil addUsedGoods(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();

        try{
            UsedGoods record = MybatisParamUtil.mapToObject(params, UsedGoods.class);
            record.setOnsaleTime(new Date());
            record.setCheckoutTag(Constants.CHECKOUT_TAG_WAIT);
            record.setGoodsTag(Constants.GOODS_TAG_WAIT);
            int result = usedGoodsService.addUsedGoods(record);
            Long goodsId = record.getGoodsId();

            //修改图片的存储字符串
            //修改存在数据库中的图片路径
            String goodsImg = record.getGoodsImg();
            goodsImg = goodsImg.replaceAll("tmp", ""+goodsId);
            //itemImg.replaceAll()
            record.setGoodsImg(goodsImg);
            int resultTmp = usedGoodsService.updateUsedGoods(record);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultObject(goodsId);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/updateUsedGoods",method = RequestMethod.POST)
    public ResponseUtil updateUsedGoods(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            UsedGoods record = MybatisParamUtil.mapToObject(params, UsedGoods.class);
            int result = usedGoodsService.updateUsedGoods(record);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/deleteUsedGoods",method = RequestMethod.POST)
    public ResponseUtil deleteUsedGoods(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long usedGoodsId = Long.parseLong(params.get("usedGoodsId").toString());
            int result = usedGoodsService.deleteUsedGoods(usedGoodsId);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }
}
