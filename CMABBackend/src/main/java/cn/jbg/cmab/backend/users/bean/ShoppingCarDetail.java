package cn.jbg.cmab.backend.users.bean;

import java.math.BigDecimal;

/**
 * Created by jbg on 2018/4/1.
 * 购物车 辅助类
 */
public class ShoppingCarDetail {

    private ShoppingCar shoppingCar;

    private String itemName;

    private BigDecimal itemPrice;

    private String itemImg;

    private Integer itemInventory;

    private String itemDesc;

    public ShoppingCar getShoppingCar() {
        return shoppingCar;
    }

    public void setShoppingCar(ShoppingCar shoppingCar) {
        this.shoppingCar = shoppingCar;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    public Integer getItemInventory() {
        return itemInventory;
    }

    public void setItemInventory(Integer itemInventory) {
        this.itemInventory = itemInventory;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
}
