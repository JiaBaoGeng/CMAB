package cn.jbg.cmab.backend.store.service;

import cn.jbg.cmab.backend.store.bean.Store;
import cn.jbg.cmab.backend.store.bean.StoreExample;
import cn.jbg.cmab.backend.store.dao.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jbg on 2018/3/16.
 */
@Service
public class StoreService {
    @Autowired
    private StoreMapper storeMapper;

    public List<Store> queryStore(String storeName){

        StoreExample example = new StoreExample();
        StoreExample.Criteria criteria = example.createCriteria();

        if(storeName!=null && !storeName.equals("")){
            storeName = "%" + storeName + "%";
            criteria.andStoreNameLike(storeName);
        }
        return storeMapper.selectByExample(example);
    }

    public Store getStoreDetailById(Long storeId){
        return storeMapper.selectByPrimaryKey(storeId);
    }

    public int addStore(Store record){
        return storeMapper.insert(record);
    }

    public int updateStore(Store record){
        return storeMapper.updateByPrimaryKeySelective(record);
    }

    public int deleteStore(Long storeId){
        return storeMapper.deleteByPrimaryKey(storeId);
    }

    //改变门店信息的审核状态
    public int updateStoreCheckoutTag(Long storeId, Integer tag){
        Store record = new Store();
        record.setStoreId(storeId);
        record.setCheckoutTag(tag);
        return storeMapper.updateByPrimaryKeySelective(record);
    }

}
