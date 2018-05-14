package cn.jbg.cmab.backend.users.controller;

import cn.jbg.cmab.backend.business.service.BusinessService;
import cn.jbg.cmab.backend.mall.bean.Item;
import cn.jbg.cmab.backend.mall.service.ItemService;
import cn.jbg.cmab.backend.users.bean.Orders;
import cn.jbg.cmab.backend.users.bean.OrdersDetail;
import cn.jbg.cmab.backend.users.bean.Users;
import cn.jbg.cmab.backend.users.service.OrdersService;
import cn.jbg.cmab.backend.users.service.ShoppingCarService;
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

import java.util.*;

/**
 * Created by jbg on 2018/4/5.
 */
@RestController
@RequestMapping("/OrdersController")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ShoppingCarService shoppingCarService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private UsersService usersService;

    /**
     * 根据用户提交的信息，生成订单
     * 需要1. 确认物品的库存量满足条件
     *     2. 删除购物车中的信息
     *     3. 需要减小该物品的库存量
     *     4. 插入订单信息
     * */
    @RequestMapping(value = "/generateOrdersFromShC", method = RequestMethod.POST)
    public ResponseUtil generateOrdersFromShC(@RequestBody Map param){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            List<Long> shcIds = (ArrayList)param.get("shcIds");
            List<Map<String,Object>> ordersList = (ArrayList)param.get("orders");
            boolean allItemIsMatch = true;
            String toastString = "您购买的";  //存在物品数量不足时的提示语
            //需要一个一个物品的进行判断
            for(int i=0; i<ordersList.size(); i++){
                //1. 确认物品的库存量满足条件
                Orders orders = MybatisParamUtil.mapToObject(ordersList.get(i),Orders.class);
                Long itemId = orders.getItemId();
                int itemNum = orders.getItemNum();
                Item item = itemService.getItemByItemId(itemId);
                int itemInventory = item.getItemInventory();
                if(itemNum > itemInventory){ //数量大于库存量， 不满足条件
                    allItemIsMatch = false;
                    toastString += item.getItemName() +"与";
                }else{  //满足数量条件， 可以继续进行后续操作
                    //2. 删除购物车中的相关信息
                    Long shcId = Long.parseLong(String.valueOf(shcIds.get(i)));
                    if( shcId != 0L){
                        int shcResult = shoppingCarService.deleteShcItem(shcId);
                    }
                    // 3. 需要减小该物品的库存量
                    int newItemInventory = itemInventory -itemNum;
                    int itemResult = itemService.updateItemInventory(itemId, newItemInventory);

                    // 4. 插入订单信息
                    orders.setOrderTag(Constants.ORDER_TAG_PROCESSING);
                    orders.setOrderTime(new Date());
                    ordersService.addOrders(orders);
                }
            }
            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            if(allItemIsMatch){
                responseUtil.setResultMsg("提交订单成功");
            }else{
                responseUtil.setResultObject("成功一半");
                responseUtil.setResultMsg(toastString + "库存量不足！请修改购买数量");
            }
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg("异常："+e.getMessage());
        }

        return  responseUtil;
    }

    @RequestMapping(value = "/getOrdersList",method = RequestMethod.POST)
    public ResponseUtil getOrdersList(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        Map<String, Object> resultObject = new HashMap<String, Object>();
        try{
            String pageIndex = params.get("pageIndex").toString();
            String pageSize = params.get("pageSize").toString();
            Page<Orders> page = new Page<Orders>(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
            page.setParams(params);
            //实现分页
            ExamplePager.buildByPage(page);

            List<Orders> ordersList = ordersService.queryOrders();
            resultObject.put("ordersList",ordersList);
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

    @RequestMapping(value = "/getOrdersById",method = RequestMethod.POST)
    public ResponseUtil getOrdersById(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long ordersId = Long.parseLong(params.get("ordersId").toString());
            Orders orders = ordersService.getOrdersById(ordersId);
            if(orders != null){
                responseUtil.setResultCode(ResponseUtil.SUCCESS);
                responseUtil.setResultObject(orders);
            }else{
                responseUtil.setResultCode(ResponseUtil.FAIL);
                responseUtil.setResultMsg("该订单已不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }
        return responseUtil;
    }

    @RequestMapping(value = "/getOrdersDetailById",method = RequestMethod.POST)
    public ResponseUtil getOrdersDetailById(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long orderId = Long.parseLong(params.get("orderId").toString());
            Orders orders = ordersService.getOrdersById(orderId);
            if(orders != null){
                OrdersDetail ordersDetail = getOrderDetailFromOrder(orders);

                responseUtil.setResultCode(ResponseUtil.SUCCESS);
                responseUtil.setResultObject(ordersDetail);
            }else{
                responseUtil.setResultCode(ResponseUtil.FAIL);
                responseUtil.setResultMsg("该订单已不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }
        return responseUtil;
    }

    @RequestMapping(value = "/getOrdersByUserId",method = RequestMethod.POST)
    public ResponseUtil getOrdersByUserId(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long userId = Long.parseLong(params.get("userId").toString());
            List<Orders> ordersList= ordersService.getOrdersByUserId(userId);
            List<OrdersDetail> ordersDetails = new ArrayList<>();
            for(Orders orders: ordersList){
                if(orders != null){
                    OrdersDetail ordersDetail = getOrderDetailFromOrder(orders);
                    ordersDetails.add(ordersDetail);
                }
            }
            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultObject(ordersDetails);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }
        return responseUtil;
    }

    private OrdersDetail getOrderDetailFromOrder(Orders orders){
        Long userId = orders.getUserId();
        String userName = usersService.getUsersInfoById(userId).getNickName();

        Long itemId = orders.getItemId();
        Item item = itemService.getItemByItemId(itemId);
        String itemName = item.getItemName();


        Long itemBelong = item.getItemBelong();
        String businessName = businessService.getBusinessById(itemBelong).getBusinessName();

        OrdersDetail ordersDetail = new OrdersDetail();
        ordersDetail.setOrders(orders);
        ordersDetail.setItemName(itemName);
        ordersDetail.setUserName(userName);
        ordersDetail.setItemBelong(itemBelong);
        ordersDetail.setBusinessName(businessName);
        ordersDetail.setItem(item);

        return ordersDetail;
    }

    @RequestMapping(value = "/addOrders",method = RequestMethod.POST)
    public ResponseUtil addOrders(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Orders record = MybatisParamUtil.mapToObject(params, Orders.class);
            int result = ordersService.addOrders(record);
            //Long itemId = record.getItemId();
            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            //responseUtil.setResultObject(itemId);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/updateOrders",method = RequestMethod.POST)
    public ResponseUtil updateOrders(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Orders record = MybatisParamUtil.mapToObject(params, Orders.class);
            int result = ordersService.updateOrders(record);
            responseUtil.setResultCode(ResponseUtil.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/deleteOrders",method = RequestMethod.POST)
    public ResponseUtil deleteOrders(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long ordersId = Long.parseLong(params.get("ordersId").toString());
            int result = ordersService.deleteOrders(ordersId);
            responseUtil.setResultCode(ResponseUtil.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

}
