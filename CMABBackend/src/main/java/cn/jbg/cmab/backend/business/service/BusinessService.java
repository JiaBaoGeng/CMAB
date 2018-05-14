package cn.jbg.cmab.backend.business.service;

import cn.jbg.cmab.backend.business.bean.Business;
import cn.jbg.cmab.backend.business.bean.BusinessExample;
import cn.jbg.cmab.backend.business.dao.BusinessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jbg on 2018/3/16.
 */
@Service
public class BusinessService {
    @Autowired
    private BusinessMapper businessMapper;

    public List<Business> queryBusiness(String businessName){

        BusinessExample example = new BusinessExample();
        BusinessExample.Criteria criteria = example.createCriteria();

        if(businessName!=null && !businessName.equals("")){
            businessName = "%" + businessName + "%";
            criteria.andBusinessNameLike(businessName);
        }
        return businessMapper.selectByExample(example);
    }

    public Business getBusinessById(Long businessId){
        return businessMapper.selectByPrimaryKey(businessId);
    }
    public int addBusiness(Business record){
        return businessMapper.insert(record);
    }

    public int updateBusiness(Business record){
        return businessMapper.updateByPrimaryKeySelective(record);
    }

    public int deleteBusiness(Long businessId){
        return businessMapper.deleteByPrimaryKey(businessId);
    }
}
