package cn.jbg.cmab.backend.system.controller;

import cn.jbg.cmab.backend.system.bean.SystemRule;
import cn.jbg.cmab.backend.system.service.SystemRuleService;
import cn.jbg.cmab.common.util.MybatisParamUtil;
import cn.jbg.cmab.common.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jbg on 2018/3/16.
 */
@RestController
@RequestMapping("/SystemRuleController")
public class SystemRuleController {

    @Autowired
    private SystemRuleService systemRuleService;

    @RequestMapping(value = "/getSystemRuleByType",method = RequestMethod.POST)
    public ResponseUtil getSystemRuleByType(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        Map<String, Object> resultObject = new HashMap<String, Object>();
        try{
            String ruleType = params.get("ruleType").toString();
            List<SystemRule> systemRuleList = systemRuleService.querySystemRuleByType(ruleType);
            resultObject.put("systemRuleList",systemRuleList);
            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultObject(resultObject);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }

        return responseUtil;

    }

    @RequestMapping(value = "/addSystemRule",method = RequestMethod.POST)
    public ResponseUtil addSystemRule(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            SystemRule record = MybatisParamUtil.mapToObject(params, SystemRule.class);
            int result = systemRuleService.addSystemRule(record);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            //responseUtil.setResultObject(itemId);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/updateSystemRule",method = RequestMethod.POST)
    public ResponseUtil updateSystemRule(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            SystemRule record = MybatisParamUtil.mapToObject(params, SystemRule.class);
            int result = systemRuleService.updateSystemRule(record);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/deleteSystemRule",method = RequestMethod.POST)
    public ResponseUtil deleteSystemRule(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long systemRuleId = Long.parseLong(params.get("systemRuleId").toString());
            int result = systemRuleService.deleteSystemRule(systemRuleId);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

}