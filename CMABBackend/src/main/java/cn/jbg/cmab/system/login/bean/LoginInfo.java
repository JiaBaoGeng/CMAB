package cn.jbg.cmab.system.login.bean;


import java.io.Serializable;

/**
 * Created by jbg.
 */
public class LoginInfo implements Serializable {

    private static final long serialVersionUID = 1220l;

    public static final String LOGIN_INFO = "LOGIN_INFO";

    public static final String CURRENT_TENENT = "CURRENT_TENANT";
    /**
     * 用户名.
     */
    private String username;
    /**
     * 登录IP.
     */
    private String loginIp;
    /**
     * 用户权限ID
     * */
    private long adminId;

    public String getUsername() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }


    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }
}