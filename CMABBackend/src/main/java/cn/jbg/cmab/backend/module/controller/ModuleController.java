package cn.jbg.cmab.backend.module.controller;

import cn.jbg.cmab.backend.module.bean.Module;
import cn.jbg.cmab.backend.module.service.ModuleService;
import cn.jbg.cmab.common.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by jbg on 2018/3/25.
 */
@RestController
@RequestMapping("/ModuleController")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @RequestMapping("/getModulesByAdmin")
    public ResponseUtil getModulesByAdmin(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        Long adminId = Long.parseLong(params.get("adminId").toString());

        //设置是否是加载已有权限
        boolean hasRight = true;
        if(params.get("notHasRight") != null){
            hasRight = false;
        }
        try{
            List<Module> modules = moduleService.getModulesByAdmin(adminId,hasRight);
            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultObject(modules);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }

        return responseUtil;
    }

}
