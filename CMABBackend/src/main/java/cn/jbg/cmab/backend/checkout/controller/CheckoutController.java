package cn.jbg.cmab.backend.checkout.controller;

import cn.jbg.cmab.backend.checkout.bean.Checkout;
import cn.jbg.cmab.backend.checkout.bean.Message;
import cn.jbg.cmab.backend.checkout.service.CheckoutService;
import cn.jbg.cmab.backend.checkout.service.MessageService;
import cn.jbg.cmab.backend.mall.service.ItemService;
import cn.jbg.cmab.backend.mall.service.UsedGoodsService;
import cn.jbg.cmab.backend.store.service.StoreService;
import cn.jbg.cmab.common.dao.Page;
import cn.jbg.cmab.common.mybatis.ExamplePager;
import cn.jbg.cmab.common.util.Constants;
import cn.jbg.cmab.common.util.ResponseUtil;
import cn.jbg.cmab.system.util.SpringContextUtil;
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
 * Created by jbg on 2018/3/16.
 */
@RestController
@RequestMapping("/CheckoutController")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/getCheckoutListByType",method = RequestMethod.POST)
    public ResponseUtil getCheckoutListByType(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        Map<String, Object> resultObject = new HashMap<String, Object>();
        try{

            String pageIndex = params.get("pageIndex").toString();
            String pageSize = params.get("pageSize").toString();
            Integer checkoutType = Integer.parseInt(params.get("checkoutType").toString());
            Page<Checkout> page = new Page<Checkout>(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
            page.setParams(params);
            //实现分页
            ExamplePager.buildByPage(page);

            List<Checkout> checkoutList = checkoutService.queryCheckoutByType(checkoutType);
            resultObject.put("checkoutList",checkoutList);
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

    //删除审核信息
    @RequestMapping(value = "/deleteCheckout",method = RequestMethod.POST)
    public ResponseUtil deleteCheckout(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long checkoutId = Long.parseLong(params.get("checkoutId").toString());
            int result = checkoutService.deleteCheckout(checkoutId);
            responseUtil.setResultCode(ResponseUtil.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

    /**
     * 审核完成：
     * 1.改变被审核项的状态（被审核项所在表）
     * 2.向发布对象者发送“成功” 或 “失败” 信息（在消息表中插入一条信息）
     * 3.审核操作完成后，删除审核信息
     * */
    @RequestMapping(value = "/completeCheckout",method = RequestMethod.POST)
    public ResponseUtil completeCheckout(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            //1.改变被审核项的状态（被审核项所在表）
            Long checkoutId = Long.parseLong(params.get("checkoutId").toString());
            Checkout checkout = checkoutService.getCheckoutInfoById(checkoutId);
            //被审核项的类型
            Integer checkoutType = checkout.getCheckoutType();
            //被审核项的ID
            Long auditId = checkout.getAuditId();
            //审核通过还是不通过
            int checkoutTag = Integer.parseInt(params.get("checkoutTag").toString());
            if(checkoutType == Constants.CHECKOUT_TYPE_SYSTEM){ //系统

            }else if(checkoutType == Constants.CHECKOUT_TYPE_USEDGOODS){ //二手商品
                UsedGoodsService usedGoodsService = (UsedGoodsService)
                        SpringContextUtil.getBean("UsedGoodsService",UsedGoodsService.class);
                usedGoodsService.updateUsedGoodsCheckoutTag(auditId,checkoutTag);
            }else if(checkoutType == Constants.CHECKOUT_TYPE_ITEM){  //商品
                ItemService itemService = (ItemService)
                        SpringContextUtil.getBean("ItemService",ItemService.class);
                itemService.updateItemCheckoutTag(auditId,checkoutTag);
            }else if(checkoutType == Constants.CHECKOUT_TYPE_STORE){ //门店
                StoreService storeService = (StoreService)
                        SpringContextUtil.getBean("StoreService",StoreService.class);
                storeService.updateStoreCheckoutTag(auditId,checkoutTag);
            }

            //2.向发布对象者发送成功或者失败信息（在消息表中插入一条信息）
            //被审核项的发布人员的类型
            Integer peopleType = checkout.getPeopleType();
            Long peopleId = checkout.getPeopleId();
            Integer messageType = checkoutTag==Constants.CHECKOUT_TAG_SUCCESS?
                    Constants.MESSAGE_TYPE_SUCCESS: Constants.MESSAGE_TYPE_FAIL;
            Message message = new Message();
            message.setMessageContent("恭喜您成功了！");
            message.setMessageType(messageType);
            message.setMessageTime(new Date());
            message.setPeopleType(peopleType);
            message.setPeopleId(peopleId);
            messageService.addMessage(message);

            //3.审核操作完成后，删除审核信息
            int result = checkoutService.deleteCheckout(checkoutId);
            responseUtil.setResultCode(ResponseUtil.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

}