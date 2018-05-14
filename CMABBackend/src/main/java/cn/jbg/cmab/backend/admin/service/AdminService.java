package cn.jbg.cmab.backend.admin.service;


import cn.jbg.cmab.backend.admin.bean.Administrator;
import cn.jbg.cmab.backend.admin.bean.AdministratorExample;
import cn.jbg.cmab.backend.admin.dao.AdministratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jbg on 2018/3/25.
 */
@Service
public class AdminService {

    @Autowired
    private AdministratorMapper administratorMapper;

    public Administrator getAdminByUsersName(String username){
        AdministratorExample example = new AdministratorExample();
        example.createCriteria().andUsernameEqualTo(username);

        List<Administrator> adminLists = administratorMapper.selectByExample(example);

        if(adminLists.size() == 0){
            return null;
        }
        return adminLists.get(0);
    }

    public Administrator getAdminById(Long adminId){
        return administratorMapper.selectByPrimaryKey(adminId);
    }

    public int changePassword(Long adminId, String newPwd){
        Administrator record = new Administrator();
        record.setAdminId(adminId);
        record.setAdminPassword(newPwd);
        return administratorMapper.updateByPrimaryKeySelective(record);
    }
}
