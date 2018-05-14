package cn.jbg.cmab.wx.login.bean;

import java.io.Serializable;

/**
 * Created by jbg on 2018/3/29.
 */
public class WxLoginInfo implements Serializable {
    private static final long serialVersionUID = 12202;

    public static final String WXLOGIN_INFO = "WXLOGIN_INFO";

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 返回给用户的sessionId
     * */
    private String sessionId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
