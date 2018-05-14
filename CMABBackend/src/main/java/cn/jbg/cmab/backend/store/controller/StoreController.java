package cn.jbg.cmab.backend.store.controller;

import cn.jbg.cmab.backend.business.bean.Business;
import cn.jbg.cmab.backend.business.service.BusinessService;
import cn.jbg.cmab.backend.store.bean.Store;
import cn.jbg.cmab.backend.store.bean.StoreDetail;
import cn.jbg.cmab.backend.store.service.StoreService;
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
@RequestMapping("/StoreController")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private BusinessService businessService;

    @RequestMapping(value = "/getStoreList",method = RequestMethod.POST)
    public ResponseUtil getStoreList(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        Map<String, Object> resultObject = new HashMap<String, Object>();
        try{
            String pageIndex = new String();
            String pageSize = new String();
            if(params.get("pageIndex") != null){
                pageIndex = params.get("pageIndex").toString();
                pageSize = params.get("pageSize").toString();
            }else{
                pageIndex = "1";
                pageSize = "100";
            }
            Page<Store> page = new Page<Store>(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
            page.setParams(params);
            //实现分页
            ExamplePager.buildByPage(page);

            String storeName = null;
            if(params.get("storeName") != null){
                storeName = params.get("storeName").toString();
            }

            List<Store> storeList = storeService.queryStore(storeName);
            resultObject.put("storeList",storeList);
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

    @RequestMapping(value = "/getStoreDetailById",method = RequestMethod.POST)
    public ResponseUtil getStoreDetailById(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long storeId = Long.parseLong(params.get("storeId").toString());
            Store store = storeService.getStoreDetailById(storeId);

            if(store != null){
                Long storeBelongId = store.getStoreBelong();
                StoreDetail storeDetail = new StoreDetail();
                storeDetail.setStore(store);
                if(storeBelongId != 0){
                    Business business = businessService.getBusinessById(storeBelongId);
                    storeDetail.setBelongName(business.getBusinessName());
                }else{
                    storeDetail.setBelongName("平台");
                }
                responseUtil.setResultCode(ResponseUtil.SUCCESS);
                responseUtil.setResultObject(storeDetail);
            }else{
                responseUtil.setResultCode(ResponseUtil.FAIL);
                responseUtil.setResultMsg("该门店不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/addStore",method = RequestMethod.POST)
    public ResponseUtil addStore(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Store record = MybatisParamUtil.mapToObject(params, Store.class);
            int result = storeService.addStore(record);
            Long storeId = record.getStoreId();

            //修改存在数据库中的图片路径
            String storeImg = record.getStoreImg();
            storeImg = storeImg.replaceAll("tmp", ""+storeId);
            record.setStoreImg(storeImg);
            int resultTmp = storeService.updateStore(record);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultObject(storeId);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/updateStore",method = RequestMethod.POST)
    public ResponseUtil updateStore(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Store record = MybatisParamUtil.mapToObject(params, Store.class);
            int result = storeService.updateStore(record);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/deleteStore",method = RequestMethod.POST)
    public ResponseUtil deleteStore(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long storeId = Long.parseLong(params.get("storeId").toString());
            int result = storeService.deleteStore(storeId);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

}