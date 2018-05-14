package cn.jbg.cmab.backend.users.service;

import cn.jbg.cmab.backend.users.bean.*;
import cn.jbg.cmab.backend.users.dao.MemberMapper;
import cn.jbg.cmab.backend.users.dao.UsersMapper;
import cn.jbg.cmab.backend.users.dao.UsersSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jbg on 2018/3/30.
 */
@Service
public class UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private UsersSettingMapper usersSettingMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private UsersSettingService usersSettingService;

    public List<Users> queryUsers(String usersName){

        UsersExample example = new UsersExample();
        UsersExample.Criteria criteria = example.createCriteria();

        if(usersName!=null && !usersName.equals("")){
            usersName = "%" + usersName + "%";
            //criteria.andUserNsersNameLike(businessName);
        }
        return usersMapper.selectByExample(example);
    }

    public int addUsers(Users record){
        return usersMapper.insert(record);
    }

    public int updateUsers(Users record){
        return usersMapper.updateByPrimaryKeySelective(record);
    }

    public int deleteUsers(Long usersId){
        return usersMapper.deleteByPrimaryKey(usersId);
    }



    /**
     * method 根据openId得到用户Id
     * param 微信官方给出的用户唯一标识 openId
     * return 用户ID
     */
    public long getUsersIdByOpenId(String openId){
        // 首先需要判断用户是否是新用户
        //是： 添加用户数据， 并得到Id；否： 直接从users表中取出UsersId
        Long usersId = 0l;
        UsersExample example = new UsersExample();
        UsersExample.Criteria criteria =example.createCriteria();
        criteria.andOpenidEqualTo(openId);

        List<Users> usersList = usersMapper.selectByExample(example);

        if(usersList.size() == 0) {
            Users record = new Users();
            record.setOpenid(openId);
            int res = usersMapper.insertSelective(record);
            usersId = record.getUserId();

            //用户是新用户,也需要向 member(会员)和setting(设置)表中插入无信息的数据
            Member member = new Member();
            member.setUserId(usersId);
            memberMapper.insertSelective(member);
            UsersSetting usersSetting = new UsersSetting();
            usersSetting.setUserId(usersId);
            usersSettingMapper.insertSelective(usersSetting);
        }else{
            usersId = usersList.get(0).getUserId();
        }

        return usersId;
    }

    public Users getUsersInfoById(Long usersId){
        return usersMapper.selectByPrimaryKey(usersId);
    }

    public UsersDetail getUsersDetailById(Long usersId){
        Users users = getUsersInfoById(usersId);
        UsersDetail usersDetail = new UsersDetail();
        if(users != null){
            Member member = memberService.getMemberInfoByUserId(usersId);
            UsersSetting usersSetting = usersSettingService.getSettingByUserId(usersId);

            usersDetail.setUsers(users);
            usersDetail.setMember(member);
            usersDetail.setUsersSetting(usersSetting);
        }else{
           return null;
        }

        return usersDetail;
    }
}
