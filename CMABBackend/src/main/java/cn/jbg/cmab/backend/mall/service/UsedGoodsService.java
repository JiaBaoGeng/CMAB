package cn.jbg.cmab.backend.mall.service;

import cn.jbg.cmab.backend.mall.bean.UsedGoods;
import cn.jbg.cmab.backend.mall.bean.UsedGoodsExample;
import cn.jbg.cmab.backend.mall.dao.UsedGoodsMapper;
import cn.jbg.cmab.common.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jbg on 2018/4/5.
 */
@Service
public class UsedGoodsService {

    @Autowired
    private UsedGoodsMapper usedGoodsMapper;

    public List<UsedGoods> queryGoods(String goodsName, boolean fromUser){
        UsedGoodsExample example = new UsedGoodsExample();
        UsedGoodsExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsNumGreaterThan(0);

        if(goodsName != null && !goodsName.equals("")){
            goodsName = "%" + goodsName + "%";
            criteria.andGoodsNameLike(goodsName);
        }

        //来自用户 保证： 审核状态通过；物品为上架状态
        if(fromUser){
            criteria.andCheckoutTagEqualTo(Constants.CHECKOUT_TAG_SUCCESS);
            criteria.andGoodsTagEqualTo(Constants.GOODS_TAG_ONSALE);
        }

        return usedGoodsMapper.selectByExample(example);
    }
    public UsedGoods getUsedGoodsById(Long usedGoodsId){
        return usedGoodsMapper.selectByPrimaryKey(usedGoodsId);
    }

    public int addUsedGoods(UsedGoods record){
        return usedGoodsMapper.insert(record);
    }

    public int updateUsedGoods(UsedGoods record){
        return usedGoodsMapper.updateByPrimaryKeySelective(record);
    }

    public int deleteUsedGoods(Long usedGoodsId){
        return usedGoodsMapper.deleteByPrimaryKey(usedGoodsId);
    }

    //改变二手货物的审核状态
    public int updateUsedGoodsCheckoutTag(Long usedGoodsId, Integer tag){
        UsedGoods record = new UsedGoods();
        record.setGoodsId(usedGoodsId);
        record.setCheckoutTag(tag);
        return usedGoodsMapper.updateByPrimaryKeySelective(record);
    }
}
