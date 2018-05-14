package cn.jbg.cmab.backend.checkout.controller;

import cn.jbg.cmab.backend.checkout.bean.Message;
import cn.jbg.cmab.backend.checkout.service.MessageService;
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
 * 进行消息的 增删改查
 * 用户阅读消息后，删除消息。
 */
@RestController
@RequestMapping("/MessageController")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/getMessageList",method = RequestMethod.POST)
    public ResponseUtil getMessageList(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        Map<String, Object> resultObject = new HashMap<String, Object>();
        try{
            String pageIndex = params.get("pageIndex").toString();
            String pageSize = params.get("pageSize").toString();
            Page<Message> page = new Page<Message>(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
            page.setParams(params);
            //实现分页
            ExamplePager.buildByPage(page);

            List<Message> messageList = messageService.queryMessage();
            resultObject.put("messageList",messageList);
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

    @RequestMapping(value = "/getMessageById",method = RequestMethod.POST)
    public ResponseUtil getMessageById(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long messageId = Long.parseLong(params.get("messageId").toString());
            Message message = messageService.getMessageById(messageId);
            if(message != null){
                responseUtil.setResultCode(ResponseUtil.SUCCESS);
                responseUtil.setResultObject(message);
            }else{
                responseUtil.setResultCode(ResponseUtil.FAIL);
                responseUtil.setResultMsg("该消息已不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/addMessage",method = RequestMethod.POST)
    public ResponseUtil addMessage(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();

        try{
            Message record = MybatisParamUtil.mapToObject(params, Message.class);
            int result = messageService.addMessage(record);
            //Long itemId = record.getItemId();

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            //responseUtil.setResultObject(itemId);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/updateMessage",method = RequestMethod.POST)
    public ResponseUtil updateMessage(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Message record = MybatisParamUtil.mapToObject(params, Message.class);
            int result = messageService.updateMessage(record);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

    @RequestMapping(value = "/deleteMessage",method = RequestMethod.POST)
    public ResponseUtil deleteMessage(@RequestBody Map params){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long messageId = Long.parseLong(params.get("messageId").toString());
            int result = messageService.deleteMessage(messageId);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultObject(e.getMessage());
        }

        return responseUtil;
    }

}