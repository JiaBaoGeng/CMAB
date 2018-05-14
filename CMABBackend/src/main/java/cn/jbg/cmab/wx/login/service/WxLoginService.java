package cn.jbg.cmab.wx.login.service;

import cn.jbg.cmab.common.util.Constants;
import cn.jbg.cmab.common.util.NetReqUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

/**
 * Created by jbg on 2018/3/29.
 */
@Service
public class WxLoginService {


    public JSONObject getSessionFromWxServer(String code){
        //拼接 请求微信服务器的URL
        String toWXUrl = "";
        toWXUrl = Constants.WXSERVER_URL + "appid=" + Constants.APPID + "&secret=" +
                Constants.APP_SECRET + "&js_code=" + code + "&grant_type=" +Constants.GRANT_TYPE;
        JSONObject jsonObject = null;
        try{
            jsonObject = NetReqUtil.get(toWXUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  jsonObject;
    }

}
