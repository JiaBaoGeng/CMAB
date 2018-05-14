package cn.jbg.cmab.wx.login.controller;

import cn.jbg.cmab.backend.users.service.UsersService;
import cn.jbg.cmab.system.util.ApplicationContextUtil;
import cn.jbg.cmab.wx.login.bean.WxLoginInfo;
import cn.jbg.cmab.wx.login.bean.WxLoginResponse;
import cn.jbg.cmab.wx.login.service.WxLoginService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 小程序端需要用的的登陆接口
 * Created by jbg on 2018/3/29.
 */
@RestController
@RequestMapping("/WxLoginController")
public class WxLoginController {

    @Autowired
    private WxLoginService wxLoginService;

    @Autowired
    private UsersService usersService;

    /**
    * 微信小程序登陆
    * */
    @RequestMapping(value = "/wxLogin")
    public WxLoginResponse login(@RequestBody Map param){
        WxLoginResponse wxLoginResponse = new WxLoginResponse();

        JSONObject openIdAndSessionKey = null;
        String code = param.get("code").toString();
        if(code != null && !code.equals("")){
            openIdAndSessionKey = wxLoginService.getSessionFromWxServer(code);
        }

        if(openIdAndSessionKey == null || openIdAndSessionKey.get("openid") == null){
            wxLoginResponse.setResultCode(WxLoginResponse.FAIL);
            wxLoginResponse.setResultMsg("服务器端错误");

            return wxLoginResponse;
        }

        //从微信服务器 取回数据成功
        //会话秘钥
        String sessionKey = openIdAndSessionKey.get("session_key").toString();
        //openId 可作为用户的唯一标识 得到openId后 通过openId得到用户的usersId
        String openId = openIdAndSessionKey.get("openid").toString();
        Long usersId = usersService.getUsersIdByOpenId(openId);

        // 设置Session
        WxLoginInfo wxLoginInfo = new WxLoginInfo();
        wxLoginInfo.setUserId(usersId);
        try{
            ApplicationContextUtil.setSessionAttribute(WxLoginInfo.WXLOGIN_INFO, wxLoginInfo);
            wxLoginInfo.setSessionId(ApplicationContextUtil.getSession().getId());
            //向小程序返回数据
            wxLoginResponse.setResultCode(WxLoginResponse.SUCCESS);
            wxLoginResponse.setResultMsg("登陆成功");
            wxLoginResponse.setWxLoginInfo(wxLoginInfo);
        }catch (Exception e){
            e.printStackTrace();
            wxLoginResponse.setResultCode(WxLoginResponse.FAIL);
        }

        return wxLoginResponse;
    }

    /**
     *
     * */

    @RequestMapping(value = "/wxLoginInfo")
    public WxLoginResponse getLoginInfo(@RequestBody Map param){
        WxLoginResponse wxLoginResponse = new WxLoginResponse();

        try{
            if(ApplicationContextUtil.getSessionAttribute(WxLoginInfo.WXLOGIN_INFO) != null){ //服务器端的session仍然有效
                wxLoginResponse.setResultCode(WxLoginResponse.SUCCESS);
                wxLoginResponse.setResultMsg("wxLoginInfo成功");
                wxLoginResponse.setWxLoginInfo((WxLoginInfo) ApplicationContextUtil.getSessionAttribute(WxLoginInfo.WXLOGIN_INFO));
            }else{
                wxLoginResponse.setResultCode(WxLoginResponse.FAIL);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return wxLoginResponse;
    }
}
