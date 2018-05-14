package cn.jbg.cmab.backend.checkout.service;

import cn.jbg.cmab.backend.checkout.bean.Checkout;
import cn.jbg.cmab.backend.checkout.bean.CheckoutExample;
import cn.jbg.cmab.backend.checkout.dao.CheckoutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jbg on 2018/3/16.
 */
@Service
public class CheckoutService {
    @Autowired
    private CheckoutMapper checkoutMapper;

    public List<Checkout> queryCheckoutByType(Integer checkoutType){

        CheckoutExample example = new CheckoutExample();
        CheckoutExample.Criteria criteria = example.createCriteria();

        // 根据审核类型 查询待审核的项
        if(checkoutType!=null){
            criteria.andCheckoutTypeEqualTo(checkoutType);
        }
        return checkoutMapper.selectByExample(example);
    }

    public Checkout getCheckoutInfoById(Long checkoutId){
        return checkoutMapper.selectByPrimaryKey(checkoutId);
    }

    public int addCheckout(Checkout record){
        return checkoutMapper.insert(record);
    }

    public int updateCheckoute(Checkout record){
        return checkoutMapper.updateByPrimaryKeySelective(record);
    }

    public int deleteCheckout(Long checkoutId){
        return checkoutMapper.deleteByPrimaryKey(checkoutId);
    }

}
