package cn.jbg.cmab.backend.users.controller;

import cn.jbg.cmab.backend.business.bean.Business;
import cn.jbg.cmab.backend.users.bean.Member;
import cn.jbg.cmab.backend.users.bean.Users;
import cn.jbg.cmab.backend.users.bean.UsersDetail;
import cn.jbg.cmab.backend.users.bean.UsersSetting;
import cn.jbg.cmab.backend.users.service.MemberService;
import cn.jbg.cmab.backend.users.service.UsersService;
import cn.jbg.cmab.backend.users.service.UsersSettingService;
import cn.jbg.cmab.common.dao.Page;
import cn.jbg.cmab.common.mybatis.ExamplePager;
import cn.jbg.cmab.common.util.MybatisParamUtil;
import cn.jbg.cmab.common.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jbg on 2018/3/30.
 */
@RestController
@RequestMapping("/UsersController")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private UsersSettingService usersSettingService;

    @RequestMapping(value = "/getUsersList",method = RequestMethod.POST)
    public ResponseUtil getUsersList(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        Map<String, Object> resultObject = new HashMap<String, Object>();
        try{

            String pageIndex = params.get("pageIndex").toString();
            String pageSize = params.get("pageSize").toString();
            Page<Users> page = new Page<Users>(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
            page.setParams(params);
            String usersName = params.get("usersName").toString();
            //实现分页
            ExamplePager.buildByPage(page);

            List<Users> usersList = usersService.queryUsers(usersName);
            resultObject.put("usersList",usersList);
            resultObject.put("pageInfo",page.getPageInfo(page));
            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultObject(resultObject);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/getUsersById",method = RequestMethod.POST)
    public ResponseUtil getUsersById(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long usersId = Long.parseLong(params.get("usersId").toString());
            Users users = usersService.getUsersInfoById(usersId);
            if(users != null){
                responseUtil.setResultCode(ResponseUtil.SUCCESS);
                responseUtil.setResultObject(users);
            }else{
                responseUtil.setResultCode(ResponseUtil.FAIL);
                responseUtil.setResultMsg("该用户已不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/getUsersDetailById",method = RequestMethod.POST)
    public ResponseUtil getUsersDetailById(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long usersId = Long.parseLong(params.get("usersId").toString());
            UsersDetail usersDetail = usersService.getUsersDetailById(usersId);

            if(usersDetail != null){
                responseUtil.setResultCode(ResponseUtil.SUCCESS);
                responseUtil.setResultObject(usersDetail);
            }else{
                responseUtil.setResultCode(ResponseUtil.FAIL);
                responseUtil.setResultMsg("该用户已不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/addUsers",method = RequestMethod.POST)
    public ResponseUtil addUsers(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();

        try{
            Users users = MybatisParamUtil.mapToObject(params, Users.class);
            int result = usersService.addUsers(users);
            Long userId = users.getUserId();

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultObject(userId);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/updateUsers",method = RequestMethod.POST)
    public ResponseUtil updateUsers(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Users record = MybatisParamUtil.mapToObject(params, Users.class);
            int result = usersService.updateUsers(record);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/deleteUsers",method = RequestMethod.POST)
    public ResponseUtil deleteUsers(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long usersId = Long.parseLong(params.get("usersId").toString());
            int result = usersService.deleteUsers(usersId);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }
    /**
     * 根据用户ID得到用户信息
     * */
    @RequestMapping(value="/getUsersInfo",method = RequestMethod.POST)
    public ResponseUtil getUsersInfo(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        Long usersId = 0l;
        try{
            usersId = Long.parseLong(params.get("userId").toString());
            Users users = usersService.getUsersInfoById(usersId);
            //为保护用户私密信息，将用户的openId设为null
            users.setOpenid(null);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultMsg("成功");
            responseUtil.setResultObject(users);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }

        return responseUtil;
    }


    /**
     * 将从微信小程序端传过来的数据加入到用户表里
     * */
    @RequestMapping(value = "/manageUserInfo",method = RequestMethod.POST)
    public ResponseUtil manageUserInfo(@RequestBody Map param){
        ResponseUtil responseUtil = new ResponseUtil();

        try{
            Map<String, Object> userInfo = (HashMap)param.get("userInfo");
            Object userId = param.get("userId");
            userInfo.put("userId",userId);
            Users users = MybatisParamUtil.mapToObject(userInfo, Users.class);
            int resultNum = usersService.updateUsers(users);
            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultMsg("成功");

        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }

        return responseUtil;
    }

}
