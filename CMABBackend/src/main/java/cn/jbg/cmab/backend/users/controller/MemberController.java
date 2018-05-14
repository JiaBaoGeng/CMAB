package cn.jbg.cmab.backend.users.controller;

import cn.jbg.cmab.backend.users.bean.Member;
import cn.jbg.cmab.backend.users.service.MemberService;
import cn.jbg.cmab.common.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by jbg on 2018/3/30.
 */
@RestController
@RequestMapping("/MemberController")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/getMemberInfoByUserId", method = RequestMethod.POST)
    public ResponseUtil getMemberInfoByUserId(@RequestBody Map param){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Long userId = Long.parseLong(param.get("userId").toString());
            Member member = memberService.getMemberInfoByUserId(userId);

            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultObject(member);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg("异常："+e.getMessage());
        }

        return  responseUtil;
    }

}
