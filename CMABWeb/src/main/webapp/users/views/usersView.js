define([ 'hbs!../templates/usersTemplate.html',
         '../actions/usersAction',
         '../../system/actions/SysUtilAction',
         '../../system/common/CommonUtil'

        ],
		function(temp, usersAction, SysUtilAction, CommonUtil) {
	var usersView = fish.View.extend({
		el : false,
		template : temp,
		pageSize : 5,
		pageIndex  : 1,
		editStatus : '',
		saveType : '',
		$genderBox : '',

		events : {
            'click #searchUsersByName': 'searchUsersByName',
            'click #btn-add-users' : 'addUsers',
            'click .editUsers' : 'editUsers',
            'click .delUsers': 'delUsers',
            'click #btn-users-save': 'saveUsers',
            'click #btn-users-cancel': 'cancelUsers',
		},

		initialize : function() {

		},

		render: function () {
            this.$el.html(this.template());
        },

		afterRender : function() {
			var that = this;
            that.editStatus = false;
            that.initInput();
            that.getUsersList_();
            $("#tabs-users").tabs();
            $("#users_info_form").form('disable');
		},

		 //初始化静态数据
        initInput : function(){
        	var that = this;
            var options = {
                placeholder: '',
                dataTextField: 'displayValue',
                dataValueField: 'code',
                dataSource: [
                    {displayValue : '未知', code : '0'},
                    {displayValue : '男', code : '1'},
                    {displayValue : '女', code : '2'},
                ]
            };
            that.$genderBox = $('#gender').combobox(options);
        },

        getUsersList_: function(){
        	var that = this;
        	that.$('#usersListPager').pagination('destroy');
        	that.getUsersList(null);
        },

		//查询用户信息，根据商品昵称
        getUsersList: function(params){
            $("#usersListGrid").grid('destroy');
			var that = this;
			if(params == null || params == '' || params == undefined){
				var params = {};
				params.pageIndex = that.pageIndex;
				params.pageSize = that.pageSize;
			}
			params.usersName = $.trim($("#searchUsersName").val());
			$.blockUI({
	            message: '请稍后'
	        });
			usersAction.getUsersList(params, function(result){
				$.unblockUI();
				if(result && result.resultCode == "0"){
					if(result.resultObject.usersList.length > 0){
						that.$("#usersListGrid").html("");
						that.initData(result);
						//默认查看第一条信息详情
						that.$("#usersListGrid table tr.jqgrow:first").trigger('click');
					}else{
						that.$("#usersListGrid").html("");
						var html = '<img src="marketing/css/base/images/none-1.png" style="height:100px;width:124px;text-align:center;margin:20px 0">';
	 					html += '<p style="width:100%;text-align:center;color:#d0d5e0;">抱歉！暂无数据</p>';
						that.$('#usersListGrid').append(html);
						that.$('#usersListPager').pagination('destroy');
					}

				}
			});
		},

        //用户信息表格渲染
        initData: function(result){
            var that = this;
            $("#usersListGrid").grid('destroy');
            that.$usersListGrid = $("#usersListGrid").grid({
              data: result.resultObject.usersList,
              height: 200,
              rowNum : 5,
              rowList : [5,10,20],
              multiselect:true,
              colModel: [
                  { name: 'idKey', label: '用户id',hidden:true, key:true,
                          formatter: function(cellval, opts, rwdat, _act) {
                                return  cellval;
                          }
                   },
                  { name: 'nickName', label:'昵称',sortable: false},
                  { name: 'gender', label: '性别', sortable:false,
                      formatter: function(cellval, opts, rwdat, _act) {
                         if(cellval == '1'){
                             return '男';
                         }else if(cellval == '2'){
                             return '女';
                         }else{
                             return '未知';
                         }
                      }
                  },
                  { name: 'city', label: '城市', sortable: false},
                  { name: 'usersLevel',label:'操作',sortable: false,
                      formatter: function (cellval, opts, rwdat, _act) {
                          var html = '<div class="btn-group">' +
                                  '<button type="button" class="btn btn-link js-edit editUsers">修改</button>' +
                                  '</div>' ;
                          html += '<div class="btn-group">' +
                                  '<button type="button" class="btn btn-link js-delete delUsers">删除</button>' +
                                  '</div>';
                          return html;
                      },
                  }
              ],
                onCellSelect: function (e, status) {//点击头部的checkbox时触发的事件
                    var selarrrow = $("#usersListGrid").grid("getCheckRows");
                    //单选
                    if(selarrrow.length>=1){
                        var sel = [selarrrow[0].userId] ;
                        $("#usersListGrid").grid("setCheckRows",sel,false);
                    }
                },
                beforeSelectRow: function (e, rowid) {  /*选中行之前事件*/
                    if (that.isUsersInfoEdit()) {
                        return false;
                    }
                    return true;
                },
                onSelectRow: function (e, rowid, state, checked) {//选中行事件
                  that.showUsersValues();  //显示用户详细信息
              },
          });
            that.initDataPagination(result.resultObject.pageInfo);
        },

        // 是否正在编辑中
        isUsersInfoEdit: function () {
            if (this.editStatus) {
                fish.warn('用户信息编辑中，请先保存或者放弃再进行此操作');
                return true;
            }
            return false;
        },

		//用户信息分页
		initDataPagination: function(pageInfo){
			var that = this;
        	$('#usersListPager').pagination({
        		 total:pageInfo.pageCount,
    	         records:pageInfo.total,
    	         displayNum:5,
    	         rowNum:pageInfo.pageSize,
    	         pgInput :true,
    	         pgRecText:true,
    	         pgTotal:true,
    	         rowtext:null,
    	         onPageClick: function (e, eventData) {
    	        	 if(that.isUsersInfoEdit()) return false;
    	        	 $('#page-content').text(eventData.eventType+' '+eventData.rowNum+' '+eventData.page);
                     var params = {};
                     params.pageIndex = eventData.page;
                     params.pageSize = that.pageSize;
                     that.getUsersList(params);
                 }
    	     });
		},

		//赋值
		showUsersValues: function(ScData){
            var that = this;
            var usersId = that.$usersListGrid.grid('getSelection').userId;
            var params = {
                usersId : usersId,
            };
            usersAction.getUsersDetail(params, function(result){
                if(result && result.resultCode == "0"){
                    var usersDetail = result.resultObject;
                    that.$("#userId").val(usersDetail.users.userId);
                    that.$("#nickName").val(usersDetail.users.nickName);
                    that.$("#city").val(usersDetail.users.city);
                    that.$("#phoneNumber").val(usersDetail.usersSetting.phoneNumber);
                    that.$("#email").val(usersDetail.usersSetting.email);
                    that.$("#memberLevel").val(usersDetail.member.memberLevel);
                    that.$("#memberCredit").val(usersDetail.member.memberCredit);
                    that.$("#userDesc").val(usersDetail.users.userDesc);
                    that.$genderBox.combobox('value', usersDetail.users.gender);
                    /*that.$selectBRRelaBox.combobox('value', bornRuleData.zmgrClusterComponentBornrule.bornruleRela);
                    that.$selectBRStatusBox.combobox('value',bornRuleData.zmgrClusterComponentBornrule.statusCd);*/
                }
            });
		},

		//新增用户信息
		addUsers: function(){
            var that = this;
            if (that.isUsersInfoEdit()) {
                return ;
            }
            that.editStatus = true;
            //that.initInput();
            that.$("#users_info_form").form('enable');
            that.$("#users_info_form").form('clear');
            that.$(".js-users-btns").show();
            //that.$("#usersSales").attr('disabled', true);
            //that.$("#usersRate").attr('disabled', true);
            //that.$("#stateCd").prev().find("input").attr("readonly", true);
            //that.$selectStateCdBox.combobox('value', '00A');
            that.saveType = 'add';
		},

		//编辑用户信息弹窗
		editUsers: function(){
            var that = this;
            if (that.isUsersInfoEdit()) {
                return false;
            };
            that.editStatus = true;
            that.$("#users_info_form").form('enable');
            that.$(".js-users-btns").show();
            //that.$("#UsersSales").attr('disabled', true);
            //that.$("#UsersRate").attr('disabled', true);
            //that.$("#stateCd").prev().find("input").attr("readonly", true);
            that.saveType = 'edit';
		},

		//保存用户信息
		saveUsers: function(){
			var that = this;
            if(!$("users_info_form").isValid()){
                return false;
            }
            var params = that.$("#users_info_form").form('value');
            $.blockUI({
                message: '请稍后'
            });
            if(that.saveType == 'add'){
                usersAction.addUsers(params, function(result){
                    $.unblockUI();
                    if(result && result.resultCode == '0'){
                        that.cancelUsers();
                        that.editStatus = false;
                        that.getUsersList_();
                        fish.success('新增成功！',function(){});
                    }else{
                        fish.error('新增失败: '+result.resultMsg);
                    }
                });
            }else if(that.saveType == 'edit'){
                usersAction.updateUsers(params, function(result){
                    $.unblockUI();
                    if(result && result.resultCode == '0'){
                        that.editStatus = false;
                        that.cancelUsers();
                        that.getUsersList_();
                        //that.$("#"+params.id).trigger('click');
                        fish.success('编辑成功！',function(){ });
                    }else{
                        fish.error('编辑失败: '+result.resultMsg);
                    }
                });
            }
		},

		//取消保存
		cancelUsers: function(){
			var that = this;
			$(".js-users-btns").hide();
			$("#users_info_form").form('disable');
			$("#users_info_form").form('clear');
            $('#users_info_form').resetValid();
            that.editStatus = false;
			CommonUtil.removeValiStyle(that.$('#users_info_form'));
			//默认查看第一条信息详情
			$("#usersListGrid table tr.jqgrow:first").trigger('click');
		},

		delUsers: function(){
			var that = this;
			var usersId = that.$("#usersListGrid").grid("getSelection").userId;
	        fish.confirm('确定删除该用户所有信息？', function(){
                var params = {};
                params.usersId = usersId;
                usersAction.deleteUsers(params, function(result){
                    if(result && result.resultCode == '0'){
                        that.getUsersList_();
                        fish.success("用户信息删除成功！");
                    }else{
                        fish.error("删除失败："+result.resultMsg);
                    };
                });
	       	});
		},

		//名称查询
		searchUsersByName: function(){
			var that = this;
			if(that.isUsersInfoEdit()){
			    return false;
			}
			that.getUsersList_();
		},
	});
	return usersView;

})
