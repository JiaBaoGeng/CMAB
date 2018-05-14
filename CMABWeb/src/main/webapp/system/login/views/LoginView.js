define(['hbs!../templates/login.html',
        '../actions/LoginAction'],
    function(loginTemplate, LoginAction) {
	    var loginView = fish.View.extend({
	        template: loginTemplate,
	        className: "login",
		    events:{
		    	"click .js-login":"onLogin"
	        },
	        
	        render: function () {
	        	this.$el.html(this.template());
	        },
	
	        afterRender: function(){
				$("#userCode").focus();
	        	var that = this;
	        	//名称输入框回车事件
            	that.$("#password").keydown(function(event) {
                    if (event.keyCode == "13") {
                        that.onLogin();
                    }
                });
			},
			
			//登录方法
			onLogin: function(){
		 		var params = {};
		 		params.user = $("#userCode").val();
		        params.password = $("#password").val();
		        $.blockUI({
		            message: '登录中, 请稍后'
		        });
		        LoginAction.login(params, function(result){
		 			$.unblockUI();
		 			if(result){
		 				if(result.resultCode == '0'){
		 					var status = 'running';
		 					portal.appGlobal.set('currentStatus', status); // 设置登陆状态
		 					portal.appGlobal.set('loginInfo', result.data); // 设置登陆信息
		 				}else {
		 					fish.error(result.resultMsg || '登录失败');
		 				}
		 			}
		 		});
			}

		});
	    return loginView;
});