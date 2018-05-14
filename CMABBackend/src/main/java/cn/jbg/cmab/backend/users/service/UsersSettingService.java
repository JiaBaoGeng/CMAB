package cn.jbg.cmab.backend.users.service;

import cn.jbg.cmab.backend.users.bean.UsersSetting;
import cn.jbg.cmab.backend.users.bean.UsersSettingExample;
import cn.jbg.cmab.backend.users.dao.UsersSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jbg on 2018/4/4.
 */
@Service
public class UsersSettingService {

    @Autowired
    private UsersSettingMapper usersSettingMapper;

    public UsersSetting getSettingByUserId(Long userId){
        UsersSettingExample example = new UsersSettingExample();
        example.createCriteria().andUserIdEqualTo(userId);

        List<UsersSetting> settings = usersSettingMapper.selectByExample(example);

        if(settings.size() > 0){
            return settings.get(0);
        }

        return null;
    }

    public int updateSettingBySettingId(UsersSetting setting){
        return usersSettingMapper.updateByPrimaryKeySelective(setting);
    }
}
