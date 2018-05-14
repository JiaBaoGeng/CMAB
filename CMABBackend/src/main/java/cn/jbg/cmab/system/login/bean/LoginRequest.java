package cn.jbg.cmab.system.login.bean;

public class LoginRequest {

    private String staffCode = "";
    private String password = "";

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
