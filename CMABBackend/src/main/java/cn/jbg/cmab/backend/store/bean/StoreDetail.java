package cn.jbg.cmab.backend.store.bean;

public class StoreDetail {

    private Store store;

    /**
     * 门店所属商家的名称
     * */
    private String belongName;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getBelongName() {
        return belongName;
    }

    public void setBelongName(String belongName) {
        this.belongName = belongName;
    }
}
