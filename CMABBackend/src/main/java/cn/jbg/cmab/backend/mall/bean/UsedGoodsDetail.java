package cn.jbg.cmab.backend.mall.bean;

import cn.jbg.cmab.backend.users.bean.UsersDetail;

public class UsedGoodsDetail {

    private UsedGoods usedGoods;

    private UsersDetail usersDetail;

    private String userName;

    private String buyerName;

    public UsedGoods getUsedGoods() {
        return usedGoods;
    }

    public void setUsedGoods(UsedGoods usedGoods) {
        this.usedGoods = usedGoods;
    }

    public UsersDetail getUsersDetail() {
        return usersDetail;
    }

    public void setUsersDetail(UsersDetail usersDetail) {
        this.usersDetail = usersDetail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }
}
