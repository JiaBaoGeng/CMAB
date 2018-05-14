package cn.jbg.cmab.backend.mall.service;

import cn.jbg.cmab.backend.mall.bean.Item;
import cn.jbg.cmab.backend.mall.bean.ItemExample;
import cn.jbg.cmab.backend.mall.dao.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jbg on 2018/3/16.
 */
@Service
public class ItemService {
    @Autowired
    private ItemMapper itemMapper;

    public List<Item> queryItems(String itemName){

        ItemExample example = new ItemExample();
        ItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemInventoryGreaterThan(0);

        if(itemName!=null && !itemName.equals("")){
            itemName = "%" + itemName + "%";
            criteria.andItemNameLike(itemName);
        }
        return itemMapper.selectByExample(example);
    }

    public Item getItemByItemId(Long itemId){
        return itemMapper.selectByPrimaryKey(itemId);
    }

    public int addItem(Item record){
        return itemMapper.insert(record);
    }

    public int updateItem(Item record){
        return itemMapper.updateByPrimaryKeySelective(record);
    }

    public int deleteItem(Long itemId){
        return itemMapper.deleteByPrimaryKey(itemId);
    }

    public int getInventoryByItemId(Long itemId){
        return getItemByItemId(itemId).getItemInventory();
    }

    public int updateItemInventory(Long itemId, int newItemInventory){
        Item item = new Item();
        item.setItemId(itemId);
        item.setItemInventory(newItemInventory);

        return updateItem(item);
    }

    //改变商品的审核状态
    public int updateItemCheckoutTag(Long itemId, Integer tag){
        Item record = new Item();
        record.setItemId(itemId);
        record.setCheckoutTag(tag);
        return itemMapper.updateByPrimaryKeySelective(record);
    }
}
