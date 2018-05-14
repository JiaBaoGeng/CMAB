package cn.jbg.cmab.common.util;

/**
 * Created by jbg on 2018/3/25.
 */

public class ResponseUtil {
    public static String SUCCESS = "0";
    public static String FAIL = "-1";

    public ResponseUtil(){
        resultCode = SUCCESS;
        resultMsg = "";
    }

    public Object resultObject;

    private String resultCode;

    private String resultMsg;

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

    public Object getResultObject() {
        return resultObject;
    }

    public void setResultObject(Object resultObject) {
        this.resultObject = resultObject;
    }
}




