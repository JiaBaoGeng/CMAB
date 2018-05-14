package cn.jbg.cmab.backend.users.controller;

import cn.jbg.cmab.backend.users.bean.UsersSetting;
import cn.jbg.cmab.backend.users.service.UsersSettingService;
import cn.jbg.cmab.common.util.MybatisParamUtil;
import cn.jbg.cmab.common.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jbg on 2018/4/4.
 */
@RestController
@RequestMapping("/UsersSettingController")
public class UsersSettingController {

    @Autowired
    private UsersSettingService usersSettingService;

    @RequestMapping(value = "/getSettingByUserId", method = RequestMethod.POST)
    public ResponseUtil getSettingByUserId(@RequestBody Map param) {
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long userId = Long.parseLong(param.get("userId").toString());
            UsersSetting setting = usersSettingService.getSettingByUserId(userId);
            if(setting != null){
                responseUtil.setResultCode(ResponseUtil.SUCCESS);
                responseUtil.setResultObject(setting);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }

        return responseUtil;
    }


    @RequestMapping(value = "/updateSettingBySettingId", method = RequestMethod.POST)
    public ResponseUtil updateSettingBySettingId(@RequestBody Map param) {
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Map settingMap = (HashMap) param.get("setting");
            UsersSetting setting = MybatisParamUtil.mapToObject(settingMap, UsersSetting.class);
            if(setting != null){
                int result = usersSettingService.updateSettingBySettingId(setting);
                if(result == 1){
                    responseUtil.setResultCode(ResponseUtil.SUCCESS);
                    responseUtil.setResultMsg("修改成功");
                }else{
                    responseUtil.setResultCode(ResponseUtil.FAIL);
                    responseUtil.setResultMsg("修改失败");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }

        return responseUtil;
    }
}
