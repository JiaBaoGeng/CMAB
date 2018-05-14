package cn.jbg.cmab.backend.users.bean;

public class UsersDetail {

    private Users users;

    private UsersSetting usersSetting;

    private Member member;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public UsersSetting getUsersSetting() {
        return usersSetting;
    }

    public void setUsersSetting(UsersSetting usersSetting) {
        this.usersSetting = usersSetting;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
