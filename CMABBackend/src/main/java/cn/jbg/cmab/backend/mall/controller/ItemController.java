package cn.jbg.cmab.backend.mall.controller;

import cn.jbg.cmab.backend.business.bean.Business;
import cn.jbg.cmab.backend.business.service.BusinessService;
import cn.jbg.cmab.backend.mall.bean.Item;
import cn.jbg.cmab.backend.mall.bean.ItemDetail;
import cn.jbg.cmab.backend.mall.service.ItemService;
import cn.jbg.cmab.common.dao.Page;
import cn.jbg.cmab.common.mybatis.ExamplePager;
import cn.jbg.cmab.common.util.MybatisParamUtil;
import cn.jbg.cmab.common.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jbg on 2018/3/16.
 */
@RestController
@RequestMapping("/ItemController")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private BusinessService businessService;

    @RequestMapping(value = "/getItemList",method = RequestMethod.POST)
    public ResponseUtil getItemList(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        Map<String, Object> resultObject = new HashMap<String, Object>();
        try{
            String pageIndex = params.get("pageIndex").toString();
            String pageSize = params.get("pageSize").toString();
            Page<Item> page = new Page<Item>(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
            page.setParams(params);
            String itemName = params.get("itemName").toString();
            //实现分页
            ExamplePager.buildByPage(page);

            List<Item> items = itemService.queryItems(itemName);
            resultObject.put("itemList",items);
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

    @RequestMapping(value = "/getItemByItemId",method = RequestMethod.POST)
    public ResponseUtil getItemByItemId(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long itemId = Long.parseLong(params.get("itemId").toString());
            Item item = itemService.getItemByItemId(itemId);
            if(item != null){
                responseUtil.setResultCode(ResponseUtil.SUCCESS);
                responseUtil.setResultObject(item);
            }else{
                responseUtil.setResultCode(ResponseUtil.FAIL);
                responseUtil.setResultMsg("该商品不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/getItemDetailByItemId",method = RequestMethod.POST)
    public ResponseUtil getItemDetailByItemId(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long itemId = Long.parseLong(params.get("itemId").toString());
            Item item = itemService.getItemByItemId(itemId);

            Long itemBelong = item.getItemBelong();
            ItemDetail itemDetail = new ItemDetail();
            itemDetail.setItem(item);
            if(itemBelong != 0){
                Business business = businessService.getBusinessById(itemBelong);
                itemDetail.setItemBelongName(business.getBusinessName());
            }else{
                itemDetail.setItemBelongName("平台");
            }

            if(item != null){
                responseUtil.setResultCode(ResponseUtil.SUCCESS);
                responseUtil.setResultObject(itemDetail);
            }else{
                responseUtil.setResultMsg("该商品不存在");
                responseUtil.setResultCode(ResponseUtil.FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/addItem",method = RequestMethod.POST)
    public ResponseUtil addItem(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();

        try{
            Item record = MybatisParamUtil.mapToObject(params, Item.class);
            int result = itemService.addItem(record);
            Long itemId = record.getItemId();

            //修改存在数据库中的图片路径
            String itemImg = record.getItemImg();
            itemImg = itemImg.replaceAll("tmp", ""+itemId);
            //itemImg.replaceAll()
            record.setItemImg(itemImg);
            int resultTmp = itemService.updateItem(record);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultObject(itemId);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/updateItem",method = RequestMethod.POST)
    public ResponseUtil updateItem(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Item record = MybatisParamUtil.mapToObject(params, Item.class);
            int result = itemService.updateItem(record);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/deleteItem",method = RequestMethod.POST)
    public ResponseUtil deleteItem(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long itemId = Long.parseLong(params.get("itemId").toString());
            int result = itemService.deleteItem(itemId);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

}