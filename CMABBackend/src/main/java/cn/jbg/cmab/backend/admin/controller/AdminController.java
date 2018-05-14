package cn.jbg.cmab.backend.admin.controller;

import cn.jbg.cmab.backend.admin.bean.Administrator;
import cn.jbg.cmab.backend.admin.service.AdminService;
import cn.jbg.cmab.common.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by jbg on 2018/3/25.
 */
@RestController
@RequestMapping("/AdminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/changePassword")
    public ResponseUtil changePassword(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();

        Long adminId = Long.parseLong(params.get("adminId").toString());
        Administrator administrator= adminService.getAdminById(adminId);
        if(administrator == null){
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg("登录超时，请重新登陆");
            return responseUtil;
        }

        String pwd = params.get("pwd").toString();
        if(pwd.equals(administrator.getAdminPassword())){ //验证通过
            String newPwd = params.get("newPwd").toString();
            int resultNum = adminService.changePassword(adminId, newPwd);
            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultMsg("修改成功");
        }else{
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg("原始密码错误");
        }

        return responseUtil;
    }


}
