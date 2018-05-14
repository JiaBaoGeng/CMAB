package cn.jbg.cmab.backend.module.service;


import cn.jbg.cmab.backend.admin.bean.Authority;
import cn.jbg.cmab.backend.admin.bean.AuthorityExample;
import cn.jbg.cmab.backend.admin.dao.AuthorityMapper;
import cn.jbg.cmab.backend.module.bean.Module;
import cn.jbg.cmab.backend.module.bean.ModuleExample;
import cn.jbg.cmab.backend.module.dao.ModuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jbg on 2018/3/25.
 */
@Service
public class ModuleService {

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private AuthorityMapper authorityMapper;

    /**
     * param hasRight:加载的是否是已有权限的模块
     * */
    public List<Module> getModulesByAdmin(Long adminId, boolean hasRight){
        //首先通过 adminId 找到所有关联的 moduleId
        AuthorityExample example = new AuthorityExample();
        example.createCriteria().andAdminIdEqualTo(adminId);

        List<Authority> authorities = authorityMapper.selectByExample(example);
        List<Long> moduleIds = new ArrayList<Long>();
        for(Authority authority : authorities){
            moduleIds.add(authority.getModuleId());
        }

        //再根据moduleIds 找出对应的modules
        ModuleExample moduleExample = new ModuleExample();
        if(hasRight){
            moduleExample.createCriteria().andModuleIdIn(moduleIds);
        }else{
            moduleExample.createCriteria().andModuleIdNotIn(moduleIds);
        }

        return moduleMapper.selectByExample(moduleExample);
    }

}
