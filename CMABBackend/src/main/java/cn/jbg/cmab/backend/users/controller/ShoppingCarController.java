package cn.jbg.cmab.backend.users.controller;

import cn.jbg.cmab.backend.mall.bean.Item;
import cn.jbg.cmab.backend.mall.service.ItemService;
import cn.jbg.cmab.backend.users.bean.ShoppingCar;
import cn.jbg.cmab.backend.users.bean.ShoppingCarDetail;
import cn.jbg.cmab.backend.users.service.ShoppingCarService;
import cn.jbg.cmab.common.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jbg on 2018/4/1.
 */
@RestController
@RequestMapping("/ShoppingCarController")
public class ShoppingCarController {

    @Autowired
    private ShoppingCarService shoppingCarService;

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/getShoppingCarDetailByUserId", method = RequestMethod.POST)
    public ResponseUtil getShoppingCarDetailByUserId(@RequestBody Map param){
        ResponseUtil responseUtil = new ResponseUtil();
        List<ShoppingCarDetail>  shoppingCarDetails = new ArrayList<ShoppingCarDetail>();
        try{
            Long userId = Long.parseLong(param.get("userId").toString());
            List<ShoppingCar> shoppingCarList = shoppingCarService.getShoppingCarByUserId(userId);
            for(ShoppingCar shoppingCar : shoppingCarList){
                shoppingCarDetails.add(shoppingCarToDetail(shoppingCar));
            }
            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultObject(shoppingCarDetails);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }

        return responseUtil;
    }

    private ShoppingCarDetail shoppingCarToDetail(ShoppingCar shoppingCar){
        ShoppingCarDetail shoppingCarDetail = new ShoppingCarDetail();

        Long itemId = shoppingCar.getItemId();
        Item item = itemService.getItemByItemId(itemId);

        shoppingCarDetail.setShoppingCar(shoppingCar);
        shoppingCarDetail.setItemName(item.getItemName());
        shoppingCarDetail.setItemImg(item.getItemImg());
        shoppingCarDetail.setItemPrice(item.getItemPrice());
        shoppingCarDetail.setItemDesc(item.getItemDesc());
        shoppingCarDetail.setItemInventory(item.getItemInventory());
        return shoppingCarDetail;
    }


    @RequestMapping(value = "/addItemToShopCar", method = RequestMethod.POST)
    public ResponseUtil addItemToShopCar(@RequestBody Map param){
        ResponseUtil responseUtil = new ResponseUtil();


        try{
            Long userId = Long.parseLong(param.get("userId").toString());
            Long itemId = Long.parseLong(param.get("itemId").toString());
            int result = shoppingCarService.queryShcByUserAndItem(userId, itemId);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultMsg("加入购物车成功");
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg("加入失败"+e.getMessage());
        }

        return responseUtil;
    }

    /**
     * 根据主键，删除购物车信息
     * */
    @RequestMapping(value = "/deleteShcItem", method = RequestMethod.POST)
    public ResponseUtil deleteShcItem(@RequestBody Map param){
        ResponseUtil responseUtil = new ResponseUtil();

        try{
            Long shcId = Long.parseLong(param.get("shcId").toString());
            int result = shoppingCarService.deleteShcItem(shcId);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultMsg("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg("删除失败"+e.getMessage());
        }

        return responseUtil;
    }

    /**
     * 购物车中的商品数量改变，进行更改
     * */
    @RequestMapping(value = "/updateShcItemNum", method = RequestMethod.POST)
    public ResponseUtil updateShcItemNum(@RequestBody Map param){
        ResponseUtil responseUtil = new ResponseUtil();

        try{
            Long shcId = Long.parseLong(param.get("shcId").toString());
            Integer itemNum = Integer.parseInt(param.get("itemNum").toString());
            int result = shoppingCarService.updateShcItemNum(shcId,itemNum);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultMsg("更改成功");
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg("更改失败"+e.getMessage());
        }

        return responseUtil;
    }

    /**
     * 根据购物车的ids 取出购物车的信息
     * */
    @RequestMapping(value = "/getShcListByIds", method = RequestMethod.POST)
    public ResponseUtil getShcListByIds(@RequestBody Map param){
        ResponseUtil responseUtil = new ResponseUtil();

        try{
            String shcIdsStr = param.get("shcIds").toString();
            String[] shcIdsStrArr = shcIdsStr.split("-");
            List<Long> shcIds = new ArrayList<Long>();
            for(String shcIdStr : shcIdsStrArr){
                Long shcId = Long.parseLong(shcIdStr);
                shcIds.add(shcId);
            }
            List<ShoppingCar> shoppingCarList =  shoppingCarService.getShcListByIds(shcIds);
            List<ShoppingCarDetail>  shoppingCarDetails = new ArrayList<ShoppingCarDetail>();
            for(ShoppingCar shoppingCar : shoppingCarList){
                shoppingCarDetails.add(shoppingCarToDetail(shoppingCar));
            }
            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultObject(shoppingCarDetails);
            responseUtil.setResultMsg("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg("查询失败"+e.getMessage());
        }

        return responseUtil;
    }
}
