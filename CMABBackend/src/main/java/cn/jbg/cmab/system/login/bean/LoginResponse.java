package cn.jbg.cmab.system.login.bean;


public class LoginResponse {

    public static String SUCCESS ="0";
    public static String FAIL = "-1";

    private LoginInfo data;

    private String resultCode;

    private String resultMsg;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }


    public LoginInfo getData() {
        return data;
    }

    public void setData(LoginInfo data) {
        this.data = data;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
