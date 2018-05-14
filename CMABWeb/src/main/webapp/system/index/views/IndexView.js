define(['i18n!system/index/i18n/index',
        'public/portal/portal'], function (i18n) {
    var IndexView = fish.View.extend({

        initialize: function () {
        	//监听登录状态改变
        	portal.appGlobal.on("change:currentStatus", this.currentStatusChange, this);
        },

        index: function () {
            if(window.portal.localajax==true){
                var status = 'running';
                portal.appGlobal.set('currentStatus', status); // 设置登陆状态
                portal.appGlobal.set('loginInfo', result.data); // 设置登陆人信息loginInfo
            }else{
                fish.post('backend:LoginController/getLoginInfo', {}, function(result){
                    if(result){
                        if(result.resultCode == '0'){
                            var status = 'running';
                            portal.appGlobal.set('currentStatus', status); // 设置登陆状态
                            portal.appGlobal.set('loginInfo', result.data); // 设置登陆人信息loginInfo
                        }else {
                            status="login";
                        }
                        portal.appGlobal.set("currentStatus", status);
                    }
                });
            }
        },

        // 如果已经登录了，则修改成main IndexView，否则变成LoginView
        currentStatusChange: function () { //登录状态改变
            if ("login" == portal.appGlobal.get("currentStatus")) {
                this.requireView('system/login/views/LoginView');
            } else if ("running" == portal.appGlobal.get("currentStatus")) {
        		this.requireView('system/main/views/MainView');
            } else if ("sessionTimeOut" == portal.appGlobal.get("currentStatus")) {
            	fish.store.set("reLogin", i18n.SESSION_TIME_OUT_REASON);
            	this.requireView('system/login/views/LoginView');
            } else if ("beenKickedFromLogin" == portal.appGlobal.get("currentStatus")) {
            	fish.store.set("reLogin", i18n.SESSION_TIME_OUT_BEEN_KICKED);
            	this.requireView('system/login/views/LoginView');
            }
        }
    });

    return IndexView;
});