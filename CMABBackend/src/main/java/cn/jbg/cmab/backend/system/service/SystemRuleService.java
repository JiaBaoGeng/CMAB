package cn.jbg.cmab.backend.system.service;

import cn.jbg.cmab.backend.system.bean.SystemRule;
import cn.jbg.cmab.backend.system.bean.SystemRuleExample;
import cn.jbg.cmab.backend.system.dao.SystemRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jbg on 2018/3/16.
 */
@Service
public class SystemRuleService {

    @Autowired
    private SystemRuleMapper systemRuleMapper;

    public List<SystemRule> querySystemRuleByType(String ruleType){

        SystemRuleExample example = new SystemRuleExample();
        SystemRuleExample.Criteria criteria = example.createCriteria();

        if(ruleType!=null && !ruleType.equals("")){
            //ruleType = "%" + ruleType + "%";
            criteria.andRuleTypeEqualTo(ruleType);
        }
        return systemRuleMapper.selectByExample(example);
    }

    /*public Store getStoreDetailById(Long storeId){
        return systemRuleMapper.selectByPrimaryKey(storeId);
    }*/

    public int addSystemRule(SystemRule record){
        return systemRuleMapper.insert(record);
    }

    public int updateSystemRule(SystemRule record){
        return systemRuleMapper.updateByPrimaryKeySelective(record);
    }

    public int deleteSystemRule(Long systemRuleId){
        return systemRuleMapper.deleteByPrimaryKey(systemRuleId);
    }
}
