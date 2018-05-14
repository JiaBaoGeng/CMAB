package cn.jbg.cmab.backend.business.controller;

import cn.jbg.cmab.backend.business.bean.Business;
import cn.jbg.cmab.backend.business.service.BusinessService;
import cn.jbg.cmab.backend.mall.bean.Item;
import cn.jbg.cmab.backend.mall.service.ItemService;
import cn.jbg.cmab.common.dao.Page;
import cn.jbg.cmab.common.mybatis.ExamplePager;
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
@RequestMapping("/BusinessController")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @RequestMapping(value = "/getBusinessList",method = RequestMethod.POST)
    public ResponseUtil getBusinessList(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        Map<String, Object> resultObject = new HashMap<String, Object>();
        try{

            String pageIndex = params.get("pageIndex").toString();
            String pageSize = params.get("pageSize").toString();
            Page<Business> page = new Page<Business>(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
            page.setParams(params);
            String businessName = params.get("businessName").toString();
            //实现分页
            ExamplePager.buildByPage(page);

            List<Business> businesses = businessService.queryBusiness(businessName);
            resultObject.put("businessList",businesses);
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

    @RequestMapping(value = "/getBusinessById",method = RequestMethod.POST)
    public ResponseUtil getBusinessById(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long businessId = Long.parseLong(params.get("businessId").toString());
            Business business = businessService.getBusinessById(businessId);
            if(business != null){
                responseUtil.setResultCode(ResponseUtil.SUCCESS);
                responseUtil.setResultObject(business);
            }else{
                responseUtil.setResultCode(ResponseUtil.FAIL);
                responseUtil.setResultMsg("该商家已不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }

        return responseUtil;

    }

    @RequestMapping(value = "/addBusiness",method = RequestMethod.POST)
    public ResponseUtil addBusiness(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();

        try{
            Business business = MybatisParamUtil.mapToObject(params, Business.class);
            int result = businessService.addBusiness(business);
            Long businessId = business.getBusinessId();

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultObject(businessId);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/updateBusiness",method = RequestMethod.POST)
    public ResponseUtil updateBusiness(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Business record = MybatisParamUtil.mapToObject(params, Business.class);
            int result = businessService.updateBusiness(record);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/deleteBusiness",method = RequestMethod.POST)
    public ResponseUtil deleteBusiness(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long businessId = Long.parseLong(params.get("businessId").toString());
            int result = businessService.deleteBusiness(businessId);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

}