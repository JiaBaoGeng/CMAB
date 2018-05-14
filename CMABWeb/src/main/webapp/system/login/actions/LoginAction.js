define(function () {
    var LoginAction =  {
        
    	// 登陆
        login: function (params, success) {
            var result = fish.post('backend:LoginController/getLogin', params, success);
            return result;
        },
        
        // 登出
        logout: function(params, success) {
        	var result = fish.post('backend:LoginController/getLogout', params, success);
            return result;
        },
        
        // 获取登录信息
        getLoginInfo: function(params, success) {
        	var result = fish.post('backend:LoginController/getLoginInfo', params, success);
            return result;
        }
        

    };

    return LoginAction;
});