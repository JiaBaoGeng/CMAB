define([ 'hbs!../templates/admin.html',
         '../actions/adminAction',
         '../../system/actions/SysUtilAction',
         '../../system/common/CommonUtil'

        ],
		function(temp, adminAction, SysUtilAction, CommonUtil) {
	var adminView = fish.View.extend({
		el : false,
		template : temp,
		pageSize : 5,
		pageIndex  : 1,
		editStatus : '',
		saveType : '',
		$selectStateCdBox : '',

		events : {
            'click #searchAdminByName': 'searchAdminByName',
            'click #btn-add-admin' : 'addAdmin',
            'click .editAdmin' : 'editAdmin',
            'click .deleteAdmin': 'deleteAdmin',
            'click #btn-admin-save': 'saveAdmin',
            'click #btn-admin-cancel': 'cancelAdmin',
		},

		initialize : function() {

		},

		render: function () {
            this.$el.html(this.template());
        },

		afterRender : function() {
			var that = this;
            that.editStatus = false;
            //that.initInput();
            that.getAdminList_();
            $("#tabs").tabs();
            $("#admin_info_form").form('disable');
		},

		 //初始化静态数据
        initInput : function(){
        	var that = this;
            var options = {
                placeholder: '请选择配置状态',
                dataTextField: 'displayValue',
                dataValueField: 'code',
                dataSource: [
                {displayValue : '有效', code : '00A'},
                {displayValue : '无效', code : '00X'}
                ]
            };
            that.$selectStateCdBox = $('#stateCd').combobox(options);
        },

        getAdminList_: function(){
        	var that = this;
        	that.$('#adminListPager').pagination('destroy');
        	that.getAdminList(null);
        },

		//查询商品信息，根据商品名称
        getAdminList: function(params){
            $("#adminListGrid").grid('destroy');
			var that = this;
			if(params == null || params == '' || params == undefined){
				var params = {};
				params.pageIndex = that.pageIndex;
				params.pageSize = that.pageSize;
			}
			params.adminName = $.trim($("#searchAdminName").val());
			$.blockUI({
	            message: '请稍后'
	        });
			adminAction.getAdminList(params, function(result){
				$.unblockUI();
				if(result && result.resultCode == "0"){
					if(result.resultObject.adminList.length > 0){
						that.$("#adminListGrid").html("");
						that.initData(result);
						//默认查看第一条信息详情
						that.$("#adminListGrid table tr.jqgrow:first").trigger('click');
					}else{
						that.$("#adminListGrid").html("");
						var html = '<img src="marketing/css/base/images/none-1.png" style="height:100px;width:124px;text-align:center;margin:20px 0">';
	 					html += '<p style="width:100%;text-align:center;color:#d0d5e0;">抱歉！暂无数据</p>';
						that.$('#adminListGrid').append(html);
						that.$('#adminListPager').pagination('destroy');
					}

				}
			});
		},

        //商品信息表格渲染
        initData: function(result){
            var that = this;
            $("#adminListGrid").grid('destroy');
            that.$adminListGrid = $("#adminListGrid").grid({
              data: result.resultObject.adminList,
              height: 200,
              rowNum : 5,
              rowList : [5,10,20],
              multiselect:true,
              colModel: [
                  { name: 'idKey', label: '商品id',hidden:true, key:true,
                              formatter: function(cellval, opts, rwdat, _act) {
                                    return  rwdat.id;
                              }
                   },
                  { name: 'itemName', label:'商品名称',sortable: false},
                  { name: 'itemPrice', label: '商品价格', sortable:false},
                   { name: 'itemImg', label: '商品图画', sortable: false, hidden:true},
                  { name: 'itemSales', label: '商品销量', sortable: false},
                  { name: 'itemInventory', label: '商品库存量', sortable: false},
                  { name: 'itemDesc', label: '商品描述', sortable: false, hidden:true},
                  { name: 'itemRate', label: '商品评分', sortable: false, hidden:true},
                  { name: 'itemCredit', label: '商品提供积分', sortable: false, hidden:true},
                  { name: 'itemLevel',label:'操作',sortable: false,
                      formatter: function (cellval, opts, rwdat, _act) {
                          var html = '<div class="btn-group">' +
                                  '<button type="button" class="btn btn-link js-edit editItem">修改</button>' +
                                  '</div>' ;
                          html += '<div class="btn-group">' +
                                  '<button type="button" class="btn btn-link js-delete delItem">删除</button>' +
                                  '</div>';
                          return html;
                      },
                  }
              ],
                onCellSelect: function (e, status) {//点击头部的checkbox时触发的事件
                    var selarrrow = $("#adminListGrid").grid("getCheckRows");
                    //单选
                    if(selarrrow.length>=1){
                        var sel = [selarrrow[0].adminId] ;
                        $("#adminListGrid").grid("setCheckRows",sel,false);
                    }
                },
                beforeSelectRow: function (e, rowid) {  /*选中行之前事件*/
                    if (that.isAdminInfoEdit()) {
                        return false;
                    }
                    return true;
                },
                onSelectRow: function (e, rowid, state, checked) {//选中行事件
                  //当前选中的商品
                  var data = that.$adminListGrid.grid('getSelection');
                  that.showItemValues(data);  //显示账号详细信息
              },
          });
            that.initDataPagination(result.resultObject.pageInfo);
        },

        // 是否正在编辑中
        isAdminInfoEdit: function () {
            if (this.editStatus) {
                fish.warn('账号信息编辑中，请先保存或者放弃再进行此操作');
                return true;
            }
            return false;
        },

		//账号信息分页
		initDataPagination: function(pageInfo){
			var that = this;
        	$('#adminListPager').pagination({
        		 total:pageInfo.pageCount,
    	         records:pageInfo.total,
    	         displayNum:5,
    	         rowNum:pageInfo.pageSize,
    	         pgInput :true,
    	         pgRecText:true,
    	         pgTotal:true,
    	         rowtext:null,
    	         onPageClick: function (e, eventData) {
    	        	 if(that.isAdminInfoEdit()) return false;
    	        	 $('#page-content').text(eventData.eventType+' '+eventData.rowNum+' '+eventData.page);
                     var params = {};
                     params.pageIndex = eventData.page;
                     params.pageSize = that.pageSize;
                     that.getAdminList(params);
                 }
    	     });
		},

		//赋值
		showItemValues: function(ScData){
			var that = this;
			that.$("#itemId").val(ScData.itemId);
			that.$("#itemName").val(ScData.itemName);
			that.$("#itemPrice").val(ScData.itemPrice);
			that.$("#itemImg").val(ScData.itemImg);
			that.$("#itemSales").val(ScData.itemSales)
			that.$("#itemInventory").val(ScData.itemInventory);
			that.$("#itemRate").val(ScData.itemRate);
			that.$("#itemCredit").val(ScData.itemCredit);
			that.$("#itemDesc").val(ScData.itemDesc);
			/*that.$selectStateCdBox.combobox('value', ScData.stateCd);
			that.$selectBRRelaBox.combobox('value', bornRuleData.zmgrClusterComponentBornrule.bornruleRela);
			that.$selectBRStatusBox.combobox('value',bornRuleData.zmgrClusterComponentBornrule.statusCd);*/
		},

		//新增商品信息
		addAdmin: function(){
            var that = this;
            if (that.isAdminInfoEdit()) {
                return ;
            }
            that.editStatus = true;
            //that.initInput();
            that.$("#admin_info_form").form('enable');
            that.$("#admin_info_form").form('clear');
            that.$(".js-admin-btns").show();
            that.$("#itemSales").attr('disabled', true);
            that.$("#itemRate").attr('disabled', true);
            //that.$("#stateCd").prev().find("input").attr("readonly", true);
            //that.$selectStateCdBox.combobox('value', '00A');
            that.saveType = 'add';
		},

		//编辑系统规则信息弹窗
		editItem: function(){
            var that = this;
            if (that.isAdminInfoEdit()) {
                return false;
            };
            that.editStatus = true;
            that.$("#admin_info_form").form('enable');
            that.$(".js-admin-btns").show();
            that.$("#itemSales").attr('disabled', true);
            that.$("#itemRate").attr('disabled', true);
            //that.$("#stateCd").prev().find("input").attr("readonly", true);
            that.saveType = 'edit';
		},

		//保存账号信息
		saveItem: function(){
			var that = this;
            if(!$("admin_info_form").isValid()){
                return false;
            }
            var params = that.$("#admin_info_form").form('value');
            $.blockUI({
                message: '请稍后'
            });
            if(that.saveType == 'add'){
                adminAction.addAdmin(params, function(result){
                    $.unblockUI();
                    if(result && result.resultCode == '0'){
                        that.cancelAdmin();
                        that.editStatus = false;
                        that.getAdminList_();
                        fish.success('新增成功！',function(){});
                    }else{
                        fish.error('新增失败: '+result.resultMsg);
                    }
                });
            }else if(that.saveType == 'edit'){
                adminAction.updateAdmin(params, function(result){
                    $.unblockUI();
                    if(result && result.resultCode == '0'){
                        that.editStatus = false;
                        that.cancelAdmin();
                        that.getAdminList_();
                        //that.$("#"+params.id).trigger('click');
                        fish.success('修改成功！',function(){ });
                    }else{
                        fish.error('修改失败: '+result.resultMsg);
                    }
                });
            }
		},

		//取消保存
		cancelAdmin: function(){
			var that = this;
			$(".js-admin-btns").hide();
			$("#admin_info_form").form('disable');
			$("#admin_info_form").form('clear');
            $('#admin_info_form').resetValid();
            that.editStatus = false;
			CommonUtil.removeValiStyle(that.$('#admin_info_form'));
			//默认查看第一条信息详情
			$("#adminListGrid table tr.jqgrow:first").trigger('click');
		},

		delItem: function(){
			var that = this;
			var adminId = that.$("#adminListGrid").grid("getSelection").adminId;
	        fish.confirm('确定删除该账号？删除该账号后，将不能再使用该账号登录系统！', function(){
                var params = {};
                params.adminId = adminId;
                adminAction.deleteAdmin(params, function(result){
                    if(result && result.resultCode == '0'){
                        that.getAdminList_();
                        fish.success("账号删除成功！");
                    }else{
                        fish.error("删除失败："+result.resultMsg);
                    };
                });
	       	});
		},

		//名称查询
		searchAdminByName: function(){
			var that = this;
			if(that.isAdminInfoEdit()){
			    return false;
			}
			that.getAdminList_();
		},
	});
	return adminView;

})
