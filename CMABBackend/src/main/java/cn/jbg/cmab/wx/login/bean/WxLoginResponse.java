package cn.jbg.cmab.wx.login.bean;

/**
 * Created by jbg on 2018/3/29.
 */
public class WxLoginResponse {
    public static String SUCCESS ="0";
    public static String FAIL = "-1";

    private WxLoginInfo wxLoginInfo;

    private String resultCode;

    private String resultMsg;

    public WxLoginInfo getWxLoginInfo() {
        return wxLoginInfo;
    }

    public void setWxLoginInfo(WxLoginInfo wxLoginInfo) {
        this.wxLoginInfo = wxLoginInfo;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }


    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }


}
