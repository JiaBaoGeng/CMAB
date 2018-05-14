package cn.jbg.cmab.common.util;

/**
 * Created by jbg on 2018/3/29.
 */
public class Constants {

    /****************************
     *  小程序用户登录使用 */
    public static final String WXSERVER_URL = "https://api.weixin.qq.com/sns/jscode2session?" ;
    //"appid=APPID&secret=SECRET&js_code=JSCODE&grant_type="

    /**
    * 小程序的APPID
    * */
    public static final String APPID = "wxb66691cf22531416";

    /**
    * 小程序的APPSECRET
    * */
    public static final String APP_SECRET = "f9537f8587b537bced3f64b1394b71a1";
    public static final String GRANT_TYPE = "authorization_code";
    public static final String WXLOGIN_SESSIONID = "sessionId";


    /****************************************
     * 订单状态常量*/
    public static final Integer ORDER_TAG_PROCESSING = 0; // processing  处理中
    public static final Integer ORDER_TAG_SHIPPED = 1;  // shipped 已发货
    public static final Integer ORDER_TAG_LOGISTICS = 2;  // logistics 物流中
    public static final Integer ORDER_TAG_COMPLETE = 3;  // complete 交易完成
    public static final Integer ORDER_TAG_REJECTION = 4;  //rejection 客户拒收
    public static final Integer ORDER_TAG_RETURN = 5;  // return 返货物流中
    public static final Integer ORDER_TAG_RETURN_COMPLETE = 6;  // return_complete 已返货

    /*****************************************
     * 二手商品 状态常量
     */
    public static final Integer GOODS_TAG_ONSALE = 0; // onsale 上架
    public static final Integer GOODS_TAG_OFFSALE = 1;  // offsale 下架
    public static final Integer GOODS_TAG_TRADING = 2;  // trading 交易中
    public static final Integer GOODS_TAG_TRADED = 3;  // traded 交易完成
    public static final Integer GOODS_TAG_REJECTION = 4;  //rejection 客户拒收
    public static final Integer GOODS_TAG_RETURNING = 5;  // returning 返货物流中
    public static final Integer GOODS_TAG_RETURN_COMPLETE = 6;  // return_complete 已返货成功
    public static final Integer GOODS_TAG_WAIT = 7;  // wait 等待审核

    /*****************************************
     * 二手商品、商品、门店的审核状态常量：待审核、审核通过、审核未通过
     */
    public static final Integer CHECKOUT_TAG_WAIT = 0; // 待审核
    public static final Integer CHECKOUT_TAG_SUCCESS = 1;  // 审核通过
    public static final Integer CHECKOUT_TAG_FAIL= 2;  // 审核未通过

    /*****************************************
     * 审核类型 ：系统、二手商品、商品、门店、
     * */
    public static final Integer CHECKOUT_TYPE_SYSTEM = 0; // 系统
    public static final Integer CHECKOUT_TYPE_USEDGOODS = 1;  // 二手商品
    public static final Integer CHECKOUT_TYPE_ITEM= 2;  // 商品
    public static final Integer CHECKOUT_TYPE_STORE= 3;  // 门店

    /***********************************
     * 审核管理：被审核项的发布人员的类型
     * */
    public static final Integer CHECKOUT_PEOPLE_ADMIN = 0; // 系统管理人员
    public static final Integer CHECKOUT_PEOPLE_USERS = 1;  // 用户
    public static final Integer CHECKOUT_PEOPLE_BUSINESS= 2;  // 商家

    /***********************************
     * 消息通知： 被通知人类型
     * */
    public static final Integer MESSAGE_TO_PEOPLE_ADMIN = 0; // 系统管理人员
    public static final Integer MESSAGE_TO_PEOPLE_USERS = 1;  // 用户
    public static final Integer MESSAGE_TO_PEOPLE_BUSINESS= 2;  // 商家

    /***********************************
     * 消息通知：消息类型，成功或失败
     * */
    public static final Integer MESSAGE_TYPE_SUCCESS = 0; // 成功
    public static final Integer MESSAGE_TYPE_FAIL = 1;  // 失败

    /************************************
     * 门店服务类型 1：检测维修 2：美容保养 3：汽车防护 4：呼叫拖车
     * */
    public enum STORE_SERVICE_TYPE {
        MAINTENANCE, BEAUTY, PROTECT, TRAILER
    }
}
