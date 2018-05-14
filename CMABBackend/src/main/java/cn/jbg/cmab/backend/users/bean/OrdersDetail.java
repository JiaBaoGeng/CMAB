package cn.jbg.cmab.backend.users.bean;

import cn.jbg.cmab.backend.mall.bean.Item;

public class OrdersDetail {

    private Orders orders;

    private String userName;

    private String itemName;

    private Item item;

    private Long itemBelong;

    private String businessName;

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getItemBelong() {
        return itemBelong;
    }

    public void setItemBelong(Long itemBelong) {
        this.itemBelong = itemBelong;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
