package cn.jbg.cmab.system.login.controller;

import cn.jbg.cmab.backend.admin.bean.Administrator;
import cn.jbg.cmab.backend.admin.service.AdminService;
import cn.jbg.cmab.system.login.bean.LoginInfo;
import cn.jbg.cmab.system.login.bean.LoginResponse;
import cn.jbg.cmab.system.login.service.LoginService;
import cn.jbg.cmab.system.util.ApplicationContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/LoginController")
public class LoginController {


    @Autowired
    private LoginService loginService;

    @Autowired
    private AdminService adminService;

    /* 获取当前登录信息
     * @return
     */
    @RequestMapping("/getLoginInfo")
    public LoginResponse getLoginInfo() {

        LoginResponse loginResponse = new LoginResponse();
        try{
            if(ApplicationContextUtil.getSessionAttribute(LoginInfo.LOGIN_INFO)==null){
                loginResponse.setResultCode(LoginResponse.FAIL);
            }else {
                loginResponse.setResultCode(LoginResponse.SUCCESS);
                loginResponse.setData((LoginInfo) ApplicationContextUtil.getSessionAttribute(LoginInfo.LOGIN_INFO));
            }
        }catch (Exception e){
            loginResponse.setResultCode(LoginResponse.FAIL);
            loginResponse.setResultMsg(e.getMessage());
        }

        return  loginResponse;

    }


    /**
     * 统一登录认证
     * @param
     * @return
     */
    @RequestMapping(value = "/getLogin")
    public LoginResponse login(@RequestBody Map param){

        //获取参数
        String username = (String) param.get("user");
        String loginPwd = (String) param.get("password");
        LoginResponse loginResponse = new LoginResponse();
        boolean isEncode = false;
        if(param.get("isEncode") !=null){
            isEncode = true;
        }
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(loginPwd)){
            loginResponse.setResultCode(LoginResponse.FAIL);
            loginResponse.setResultMsg("账号密码输入不能为空");
            return loginResponse;
        }
        Administrator admin = adminService.getAdminByUsersName(username);
        if(admin == null){
            loginResponse.setResultCode(LoginResponse.FAIL);
            loginResponse.setResultMsg("账号不存在");
            return loginResponse;
        }
        if(!admin.getAdminPassword().equals(loginPwd)){
            loginResponse.setResultCode(LoginResponse.FAIL);
            loginResponse.setResultMsg("密码错误");
            return loginResponse;
        }

        //到了这里表示登陆正常， 设置loginInfo 以及session
        Long adminId = admin.getAdminId();
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserName(username);
        loginInfo.setAdminId(adminId);
        try{
            ApplicationContextUtil.setSessionAttribute(LoginInfo.LOGIN_INFO,loginInfo);
            //loginService.createSessionToken((String) param.get("user"),(String) param.get("password"));
            loginResponse.setResultCode(LoginResponse.SUCCESS);
            loginResponse.setResultMsg("登陆成功");
            loginResponse.setData(loginInfo);
        }catch (Exception e){
            e.printStackTrace();
            loginResponse.setResultCode(LoginResponse.FAIL);
            loginResponse.setResultMsg(e.getMessage());
        }

        return loginResponse;
    }


    @RequestMapping(value = "/getLogout")
    public LoginResponse getLogout(){
        LoginResponse result = new LoginResponse();
        try{
            HttpSession session = ApplicationContextUtil.getSession();
            session.removeAttribute(LoginInfo.LOGIN_INFO);
            session.invalidate();
            result.setResultCode(LoginResponse.SUCCESS);
            result.setResultMsg("注销成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setResultCode(LoginResponse.FAIL);
            result.setResultMsg(e.getMessage());
        }
        return result;

    }


}
