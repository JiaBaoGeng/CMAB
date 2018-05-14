define(['hbs!../templates/main.html',
        '../actions/MainAction',
        'system/login/actions/LoginAction',
        '../../actions/SysUtilAction'
        ],
    function (indexTemplate, MainAction, LoginAction,SysUtilAction) {
        var MainView = fish.View.extend({
            template: indexTemplate,
            className: "container-main",
            menuArr: [],
            firstMenuId: '',
            openFlag: true,
            hasRightModules:[],   //用户已有权限的模块
            events: {
                "click .js-all-menu": "openMenus", // 打开系统菜单div
                "click .menu-close": "hideMenus", // 隐藏系统菜单div
                "click .sidebar-fold": "openSider", // 打开或隐藏常用菜单div
                "click .topbar-product-all-item>li.ng-item": "openView", // 全部菜单位置点开，打开一个新view
                "click .js-home": "onClickHome", //点击tab的home
                "click .topbar-user-info": "showUserInfo", // 基本信息
                "click .topbar-user-modPwd": "changePwd", // 修改密码
                "click .logout": "uLoginout" // 退出系统,注销
            },
            initialize: function () {
            },
            render: function () {
                this.$el.html(this.template());
            },
            // 页面首次加载
            afterRender: function () {
            	var that = this;
            	var menu = {id: 'tabs-home', isRemote: false, hasNav: false};
        		this.menuArr.push(menu);
            	// 处理登陆信息
            	if(typeof(portal.appGlobal.changed.loginInfo) != "undefined"){
            		var loginInfo = portal.appGlobal.get('loginInfo');
            		if(loginInfo ==null || loginInfo =='null'){
            			return;
            		}
            		var info = loginInfo.username;
            		$(".topbar-info .name").text(info);
                	// 加载菜单
                	that.loadMenus();
                	// 加载首页homeview
                    //that.loadHomeView();
            	}else{
            		that.getLoginInfo();
            	}

            },
            // 加载菜单
            loadMenus: function () {
            	var that = this;
            	var loginInfo = portal.appGlobal.get('loginInfo');
            	var params = {
            	    adminId : loginInfo.adminId
            	};
            	MainAction.getCommonMenus(params, function(result){
            		if(result){
            			that.$("#menu-slidebar").slimscroll({
        					height: window.innerHeight-64-40-16+'px',
        					color: '#fff',
        					positionY: 'left'
        				});
            			if('0' == result.resultCode){
            				var modules = result.resultObject;
            				if(modules==null || modules=="undefined"){
            					return;
            				}
            				//加载已有权限的模块
            				that.hasRightModules = that.hasRightModules.concat(modules);
            				// 加载到制定的div里面
            				$(".js-general-menu li:not(#js-up-down-common)").remove();
            				for(var i=0; i<modules.length; i++){
            					var html ='<li menu_id="'+modules[i].moduleId+'" menu_name="'+modules[i].moduleName+'" menu_url="'+modules[i].moduleUrl + '" >'+
            					'<a href="javascript:void(0);" >'+
	            					'<span class="nav-icon"><i class="iconfont '+modules[i].moduleIcon+'"></i></span>'+
	            					'<span class="nav-title">'+modules[i].moduleName+'</span>'+
            					'</a></li>';
            					$(".js-general-menu ul").append(html);
            				}
            			}
            			$(".js-general-menu li a").each(function(){
            				var ele=this;
    						$(this).click(function(){
    							that.openView(ele);
    						});
    					});
            			$(".js-general-menu li[par_menu_id='-1'] a").click(function(){
            				if($(this).parent().siblings().is(":hidden")){
            					var menuId = $(".js-general-menu li[par_menu_id='-1']").attr("menu_id");
            					$(this).parent().siblings("[par_menu_id="+menuId+"]").slideDown();
            				}
            				else{
            					$(this).parent().siblings().slideUp();
            				}
    					});
            		}
            	});
            },

            // 加载home页面
            loadHomeView: function() {
                var that = this;
                var loginInfo = portal.appGlobal.get('loginInfo');
            	var params = {};
            	//URL是查询得到的
            	MainAction.qrySystemPost(params,function(result){
            		if(result.resultCode == "0"){
            			var data = result.resultObject;
            			//if(data != null&&data.hasOwnProperty('systemPost')){
						if(data != null){
            				var post = result.resultObject;
            				var myurl = post.indexView;
            				if(myurl != null){
            				}else{
            					fish.error('岗位对应URL为空');
            					return;
            				}
            			}else{
            				return;
            			}
            		}else{
            			fish.error(result.resultMsg);
            		}
            	});
            },

            // 获取登录信息
            getLoginInfo: function(){
            	var that = this;
            	var params = {};
            	LoginAction.getLoginInfo(params, function(result){
		 			if(result){
		 				if(result.resultCode == '0'){
		 					var status = 'running';
		 					portal.appGlobal.set('currentStatus', status); // 设置登陆状态
		 					portal.appGlobal.set('loginInfo', result.data); // 设置登陆信息
		 					var loginInfo = portal.appGlobal.get('loginInfo');
		 					var info = loginInfo.username;
		            		$(".topbar-info .name").text(info);

		                	// 加载菜单
		                	that.loadMenus();
		                	// 加载首页homeview
		                    //that.loadHomeView();
		 				}else {
		 					fish.error(result.resultMsg || '登录超时');
		 					portal.appGlobal.set('currentStatus', "login"); // 设置登陆状态
		 				}
		 			}
		 		});
            },

            // 点击系统菜单打开/关系菜单显示div
            openMenus: function (e) {
            	var that = this;
            	var aobj = $(e.target);
            	if (aobj.hasClass("active")) {
            		aobj.removeClass("active");
            		this.$(".topbar-dropdown-box").hide();
            	} else {
            		aobj.addClass("active");
            		this.$(".topbar-dropdown-box").show();
            		that.loadAllMenus(); // 根据A-Z加载全部菜单
            	}
            },
            // 点击隐藏系统菜单div
            hideMenus: function () {
            	this.$(".js-all-menu").removeClass("active");
            	this.$(".topbar-dropdown-box").hide();
            },
            // 点击打开隐藏常用菜单div
            openSider: function () {
                var siderb = this.$(".js-sider-fold");
                var commonj = this.$(".js-common-menu");
                if (siderb.hasClass("view-sidebar-close")) {
                    siderb.removeClass("view-sidebar-close");
                    commonj.html('常用菜单<span><p id="js-up-down-common" class="glyphicon glyphicon-collapse-up"></li></span>');
                }
                else {
                    siderb.addClass("view-sidebar-close")
                    commonj.html('常用菜单<span><li id="js-up-down-common" class="glyphicon glyphicon-collapse-up"></li></span>');
                }
            },

            // 加载全部菜单，但员工只可以点击自己有权限的模块
            //这里是 已经保存了用户已有权限的模块， 再查询用户没有权限的模块
            loadAllMenus: function(){
            	var that = this;
            	var loginInfo = portal.appGlobal.get('loginInfo');
            	var params = {};
            	params.menuId=that.firstMenuId;
                params.adminId = loginInfo.adminId;
                params.notHasRight = 'true';
            	MainAction.getCommonMenus(params, function(result){
    				if(result && '0' == result.resultCode){
						var otherModules = result.resultObject;
						// 左侧第一级菜单展示
						/*if(''==that.firstMenuId || null==that.firstMenuId){
							that.firstMenus(result.firstMenus);
						}*/
						// 加载到制定的div里面
						$(".topbar-product-all-item .pull-left ul").remove();
						var html = '<ul class="col-xs-12" style="margin-top:15px;">';
                        html += '<li class="ng-scope active"><span>已有权限</span></li>';
                        let hasRightModules = that.hasRightModules;
                        for(var i=0; i<hasRightModules.length; i++){
                            html += '<li class="ng-item active" menu_id="'+hasRightModules[i].moduleId+'" menu_name="'+hasRightModules[i].moduleName+'" menu_url="'+hasRightModules[i].moduleUrl+'" style="width: 200px; margin-left: 25px">'+
                                '<a href="javascript:void(0);">'+hasRightModules[i].moduleName+'</a></li>' +
                                '<li class="collection star-unselect" menu_id="'+hasRightModules[i].moduleId+'" style="margin-left: 0px"></li>';
                        }
                        html += '</ul>';
                        $(".topbar-product-all-item .pull-left").append(html);

						if(otherModules==null || otherModules=="undefined"){
        					return;
        				}

						var html2 = '<ul class="col-xs-12" style="margin-top:30px;">';
                        html2 += '<li class="ng-scope active"><span>未获权限</span></li>';
						for(var i=0; i<otherModules.length; i++){
                            html2 += '<li class="ng-item active" menu_id="'+otherModules[i].moduleId+'" menu_name="'+otherModules[i].moduleName+'" menu_url="undefined" style="width: 200px; margin-left: 25px">'+
                                '<a href="javascript:void(0);">'+otherModules[i].moduleName+'</a></li>' +
                                '<li class="collection star-unselect" menu_id="'+otherModules[i].moduleId+'" style="margin-left: 0px"></li>';
						}
						html2 += '</ul>';
                        $(".topbar-product-all-item .pull-left").append(html2);
					}
					// 打开一个新view
					$(".topbar-product-all-item .ng-item:not([menu_url='undefined'])").each(function(index,eve){
						$(this).click(function(eve){
							var ele=eve.target;
							that.openView(ele);
						});
					});
    			});
            },
            // 第一级菜单
            firstMenus: function(params){
            	var that = this;
            	if(params==null || params=="undefined"){
					return;
				}
            	// 加载到制定的div里面
				$(".js-search-menu .general-list li").remove();
				var html ='<li class="active"><a>全部<i class="pull-right icon iconfont icon-arrow"></i></a></li>';
				$(".js-search-menu .general-list").append(html);
				for(var i=0; i<params.length; i++){
					html = '<li  menu_id="'+params[i].menuId+'" menu_level="'+params[i].menuLevel+'" >';
					html += '<a>'+params[i].menuName+'<i class="pull-right icon iconfont icon-arrow"></i></a>';
					
					html += '</li>';
					$(".js-search-menu .general-list").append(html);
				}
				//一级菜单点击事件
				that.selectedFirstMenu();
            },
            //一级菜单点击事件
            selectedFirstMenu: function(){
            	var that = this;
            	that.$(".js-search-menu .general-menu-box").find("li").each(function(index, ele){
					that.$(ele).click(function(){
						that.$(".js-search-menu .general-menu-box").find("li").each(function(index2,el){
							$(el).removeClass("active");
						});
						that.$(ele).addClass("active");
		            	that.firstMenuId=that.$(ele).attr("menu_id");
		            	that.loadAllMenus();
					});
				});
            },
            // 点击菜单打开菜单对应的view
            openView: function (e) {
            	var that = this;
            	var $li = $(e).parent();
            	var menu_id = $li.attr("menu_id");
            	var menu_name = $li.attr("menu_name");
            	var menu_url = $li.attr("menu_url");
            	var isRemote = $li.attr("isRemote");
            	var hasNav = $li.attr("hasNav");
            	that.$(".topbar-dropdown-box").hide();
            	isRemote = "true";
            	//保存菜单ID至全局变量，功能按钮权限控制需使用
            	portal.appGlobal.set(SysUtilAction.keys.coorsystem_openMenuId,menu_id);
            	//切换列表选择active
            	/*$li.parents().find(".active").removeClass("active");*/
            	$('.js-all-menu').removeClass("active");
            	$li.addClass("active");
            	if (menu_id && menu_name && menu_url) {
                    that.$("#tabs-border").tabs("option", "tabCanCloseTemplate", 
                        "\
                            <li>\
                              <a href='#{href}'>\
                                <button type='button' class='ui-tabs-close ui-tabs-main-close close' role='button'>\
                                  <span aria-hidden='true' title='close' class='iconfont icon-close'></span>\
                                </button>\
                                <div style='float: left; '>#{label}</div>\
                              </a>\
                            </li>\
                        "
                    );
            		that.$("#tabs-border").tabs("option", "canClose", true);
            		that.$("#tabs-border").tabs("add", {
                        id: menu_id, 
                        label: menu_name, 
                        active: true,
                     });
            		that.$("#tabs-border").tabs({activate: function (e, ui) {
                        var panelid = ui.newPanel.attr("id");
                        $.each(that.menuArr, function (index, menu) {
                            if (menu && menu.id === panelid) {
                                that.openFlag = !menu.isRemote;
                                that.onClickHome();
                            }
                        });
                    },
                    beforeRemove: function (e, ui) {
                        var panelid = ui.panel.attr("id");
                        //移除view，触发cleanup事件
                        that.removeView("#" + panelid);
                    }});
            		var menu = {id: menu_id, isRemote: isRemote === 'true', hasNav: hasNav === 'true'};
            		this.menuArr.push(menu);
            		that.openFlag = !menu.isRemote;
            		that.onClickHome();
            		that.$(".js-all-menu").removeClass("active");
            		that.requireView({
            			selector: "#" + menu_id,
            			url: menu_url,
            			callback: function () {
            				if ($li.attr("hasNav")) { //如果子菜单内含有导航控件，需要特殊处理
            					that.$("#" + menu_id).height(screen.height - 200);
            					that.$(".view-framework-product-body").css("overflow-y", "hidden");
            				} else {
            					that.$(".view-framework-product-body").slimscroll({
            						height: window.innerHeight-64-40+'px'
            					});
            					that.$('.view-framework-product-body').css('width','auto');
            					that.$('.view-framework-product-body').parent('.slimScrollDiv').css('width','auto');
            				}
            			}
            		});
            	}
            },
            // 点击home
            onClickHome: function () {
                var siderb = this.$(".js-sider-fold");
                if (this.openFlag === true) {
                    siderb.removeClass("view-sidebar-none");
                } else {
                    if (!siderb.hasClass("view-sidebar-none")) {
                        siderb.addClass("view-sidebar-none");
                    }
                }
                this.openFlag = true;
            },

            // 点击其他按钮打开新的标签页
            openNewView: function(params){
            	var that = this;
            	var menu_id = params.tabId;
            	var menu_name = params.title;
            	var menu_url = params.url;
            	var isRemote = 'true';
                var hasNav = '';
            	if (menu_id && menu_name && menu_url) {
	                 that.$("#tabs-border").tabs("option", "canClose", true);
	                 that.$("#tabs-border").tabs("add", {id: menu_id, label: menu_name, active: true});
	                 var menu = {id: menu_id, isRemote: isRemote === 'true', hasNav: hasNav === 'true'};
	                 this.menuArr.push(menu);
	                 that.openFlag = !menu.isRemote;
	                 that.onClickHome();
	                 that.$(".js-all-menu").removeClass("active");
	                 that.requireView({
	                     selector: "#" + menu_id,
	                     url: menu_url,
	                     callback: function () {
	                    	 that.$(".view-framework-product-body").css("overflow-y", "auto");
	                     }
	                 });
            	}
            },
            
            // 点击人员打开人员信息div
            openUser: function (f) {
                var userj = $(f.target);
                if (userj.hasClass("active")) {
                    userj.removeClass("active");
                    this.$(".topbar-info-dropdown-memu").hide();
                } else {
                    userj.addClass("active");
                    this.$(".topbar-info-dropdown-memu").show();
                }
            },
            // 基本资料
            showUserInfo: function(){
            	var that = this;
                fish.popupView({
                    url: 'marketing/modules/main/views/BaseDataView',
                    height: 400,
                    width: "80%",
                    close:function(){
                    },
                    callback:function(){
                    }
                });
            },
            // 修改密码
            changePwd: function(){
            	var that = this;
                fish.popupView({
                    url: 'system/main/views/changePasswordView',
                    height: 450,
                    width: "40%",
                    close:function(){
                    },
                    callback:function(){
                    }
                });
            },
            // 退出系统
            uLoginout: function(){
            	var that = this;
            	var params = {};
            	LoginAction.logout(params, function(result){
    				if(result){
    					if('0' == result.resultCode){
    						var status = 'login';
    	 					portal.appGlobal.set('currentStatus', status);
    					}
    				}
            	});
            },
            
        });
        return MainView;
    });