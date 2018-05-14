package cn.jbg.cmab.backend.users.service;
;
import cn.jbg.cmab.backend.mall.bean.Item;
import cn.jbg.cmab.backend.users.bean.Orders;
import cn.jbg.cmab.backend.users.bean.OrdersDetail;
import cn.jbg.cmab.backend.users.bean.OrdersExample;
import cn.jbg.cmab.backend.users.dao.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jbg on 2018/4/5.
 */
@Service
public class OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;



    public List<Orders> queryOrders(){

        OrdersExample example = new OrdersExample();
        OrdersExample.Criteria criteria = example.createCriteria();

        /*if(itemName!=null && !itemName.equals("")){
            itemName = "%" + itemName + "%";
            criteria.andItemNameLike(itemName);
        }*/
        return ordersMapper.selectByExample(example);
    }

    public Orders getOrdersById(Long ordersId){
        return ordersMapper.selectByPrimaryKey(ordersId);
    }

    public List<Orders> getOrdersByUserId(Long userId){

        OrdersExample example = new OrdersExample();
        OrdersExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);

        return ordersMapper.selectByExample(example);
    }


    public int addOrders(Orders record){
        return ordersMapper.insert(record);
    }

    public int updateOrders(Orders record){
        return ordersMapper.updateByPrimaryKeySelective(record);
    }

    public int deleteOrders(Long ordersId){
        return ordersMapper.deleteByPrimaryKey(ordersId);
    }

}
