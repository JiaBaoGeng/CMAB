package cn.jbg.cmab.backend.users.service;

import cn.jbg.cmab.backend.users.bean.ShoppingCar;
import cn.jbg.cmab.backend.users.bean.ShoppingCarExample;
import cn.jbg.cmab.backend.users.dao.ShoppingCarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jbg on 2018/4/1.
 */
@Service
public class ShoppingCarService {

    @Autowired
    private ShoppingCarMapper shoppingCarMapper;

    public List<ShoppingCar> getShoppingCarByUserId(Long userId){
        ShoppingCarExample example = new ShoppingCarExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return shoppingCarMapper.selectByExample(example);
    }

    public int addItemToShopCar(ShoppingCar shoppingCar){
        return shoppingCarMapper.insertSelective(shoppingCar);
    }

    public int queryShcByUserAndItem(Long userId, Long itemId){
        ShoppingCarExample example = new ShoppingCarExample();
        example.createCriteria().andUserIdEqualTo(userId)
            .andItemIdEqualTo(itemId);

        List<ShoppingCar> shoppingCarList = shoppingCarMapper.selectByExample(example);
        //加入购物车库表之前，需要先判断原购物车有没有相同的物品,
        //有：进行更新  没有：进行插入
        if(shoppingCarList != null && shoppingCarList.size() > 0){ //存在物品， 进行数量的更新
            ShoppingCar shoppingCar = shoppingCarList.get(0);
            shoppingCar.setItemNum(shoppingCar.getItemNum() + 1);

            shoppingCarMapper.updateByPrimaryKeySelective(shoppingCar);
        }else{ //不存在物品 进行数据的插入
            ShoppingCar shoppingCar = new ShoppingCar();
            shoppingCar.setItemId(itemId);
            shoppingCar.setItemNum(1);
            shoppingCar.setUserId(userId);

            shoppingCarMapper.insertSelective(shoppingCar);
        }

        return shoppingCarList.size();
    }

    /**
     * 根据主键 删除商品信息
     * */
    public int deleteShcItem(Long shcId){
        return shoppingCarMapper.deleteByPrimaryKey(shcId);
    }
    public int deleteShcItems(List<Long> shcIds){
        ShoppingCarExample example = new ShoppingCarExample();
        example.createCriteria().andShcIdIn(shcIds);

        return shoppingCarMapper.deleteByExample(example);

    }
    /**
     * 更改 购物车中的商品数量
     * */
    public int updateShcItemNum(Long shcId, Integer itemNum){
        ShoppingCar shoppingCar = new ShoppingCar();
        shoppingCar.setShcId(shcId);
        shoppingCar.setItemNum(itemNum);
        return shoppingCarMapper.updateByPrimaryKeySelective(shoppingCar);
    }

    public List<ShoppingCar> getShcListByIds(List<Long> shcIds){
        ShoppingCarExample example = new ShoppingCarExample();
        example.createCriteria().andShcIdIn(shcIds);

        return shoppingCarMapper.selectByExample(example);
    }
}
