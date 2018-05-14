define([ 'hbs!../templates/businessTemplate.html',
         '../actions/businessAction',
         '../../system/actions/SysUtilAction',
         '../../system/common/CommonUtil'

        ],
		function(temp, businessAction, SysUtilAction, CommonUtil) {
	var businessView = fish.View.extend({
		el : false,
		template : temp,
		pageSize : 5,
		pageIndex  : 1,
		editStatus : '',
		saveType : '',

		events : {
            'click #searchBusinessByName': 'searchBusinessByName',
            'click #btn-add-business' : 'addBusiness',
            'click .editBusiness' : 'editBusiness',
            'click .deleteBusiness': 'deleteBusiness',
            'click #btn-business-save': 'saveBusiness',
            'click #btn-business-cancel': 'cancelBusiness',
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
            that.getBusinessList_();
            $("#tabsBusiness").tabs();
            $("#business_info_form").form('disable');
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

        getBusinessList_: function(){
        	var that = this;
        	that.$('#businessListPager').pagination('destroy');
        	that.getBusinessList(null);
        },

		//查询商家信息，根据商家名称
        getBusinessList: function(params){
            $("#businessListGrid").grid('destroy');
			var that = this;
			if(params == null || params == '' || params == undefined){
				var params = {};
				params.pageIndex = that.pageIndex;
				params.pageSize = that.pageSize;
			}
			params.businessName = $.trim($("#searchBusinessName").val());
			$.blockUI({
	            message: '请稍后'
	        });
			businessAction.getBusinessList(params, function(result){
			    $.unblockUI();
				if(result && result.resultCode == "0"){
					if(result.resultObject.businessList.length > 0){
						that.$("#businessListGrid").html("");
						that.initData(result);
						//默认查看第一条信息详情
						that.$("#businessListGrid table tr.jqgrow:first").trigger('click');
					}else{
						that.$("#businessListGrid").html("");
						var html = '<img src="marketing/css/base/images/none-1.png" style="height:100px;width:124px;text-align:center;margin:20px 0">';
	 					html += '<p style="width:100%;text-align:center;color:#d0d5e0;">抱歉！暂无数据</p>';
						that.$('#businessListGrid').append(html);
						that.$('#businessListPager').pagination('destroy');
					}
				}

			});
		},

        //商家信息表格渲染
        initData: function(result){
            var that = this;
            $("#businessListGrid").grid('destroy');
            that.$businessListGrid = $("#businessListGrid").grid({
              data: result.resultObject.businessList,
              height: 200,
              rowNum : 5,
              rowList : [5,10,20],
              multiselect:true,
              colModel: [
                  { name: 'idKey', label: '商家id',hidden:true, key:true,
                              formatter: function(cellval, opts, rwdat, _act) {
                                    return  rwdat.businessId;
                              }
                   },
                  { name: 'businessName', label:'商家名称',sortable: false},
                  { name: 'businessAddr', label: '商家地址', sortable:false},
                   { name: 'businessManager', label: '负责人', sortable: false, hidden:true},
                  { name: 'businessPhone', label: '手机号码', sortable: false},
                  { name: 'businessLevel',label:'操作',sortable: false,
                      formatter: function (cellval, opts, rwdat, _act) {
                          var html = '<div class="btn-group">' +
                                  '<button type="button" class="btn btn-link js-edit editBusiness">修改</button>' +
                                  '</div>' ;
                          html += '<div class="btn-group">' +
                                  '<button type="button" class="btn btn-link js-delete deleteBusiness">删除</button>' +
                                  '</div>';
                          return html;
                      },
                  }
              ],
                onCellSelect: function (e, status) {//点击头部的checkbox时触发的事件
                    var selarrrow = $("#businessListGrid").grid("getCheckRows");
                    //单选
                    if(selarrrow.length>=1){
                        var sel = [selarrrow[0].businessId] ;
                        $("#businessListGrid").grid("setCheckRows",sel,false);
                    }
                },
                beforeSelectRow: function (e, rowid) {  /*选中行之前事件*/
                    if (that.isBusinessInfoEdit()) {
                        return false;
                    }
                    return true;
                },
                onSelectRow: function (e, rowid, state, checked) {//选中行事件
                  //当前选中的商品
                  var data = that.$businessListGrid.grid('getSelection');
                  that.showBusinessInfo(data);  //显示商家详细信息
              },
          });
            that.initDataPagination(result.resultObject.pageInfo);
        },

        // 是否正在编辑中
        isBusinessInfoEdit: function () {
            if (this.editStatus) {
                fish.warn('商家信息编辑中，请先保存或者放弃再进行此操作');
                return true;
            }
            return false;
        },

		//商家信息分页
		initDataPagination: function(pageInfo){
			var that = this;
        	$('#businessListPager').pagination({
        		 total:pageInfo.pageCount,
    	         records:pageInfo.total,
    	         displayNum:5,
    	         rowNum:pageInfo.pageSize,
    	         pgInput :true,
    	         pgRecText:true,
    	         pgTotal:true,
    	         rowtext:null,
    	         onPageClick: function (e, eventData) {
    	        	 if(that.isBusinessInfoEdit()) return false;
    	        	 $('#page-content').text(eventData.eventType+' '+eventData.rowNum+' '+eventData.page);
                     var params = {};
                     params.pageIndex = eventData.page;
                     params.pageSize = that.pageSize;
                     that.getBusinessList(params);
                 }
    	     });
		},

		//赋值
		showBusinessInfo: function(ScData){
			var that = this;
			that.$("#businessId").val(ScData.businessId);
			that.$("#businessName").val(ScData.businessName);
			that.$("#businessAddr").val(ScData.businessAddr);
			that.$("#businessManager").val(ScData.businessManager);
			that.$("#businessPhone").val(ScData.businessPhone)
			that.$("#businessEmail").val(ScData.businessEmail);
			that.$("#businessDesc").val(ScData.businessDesc);
			/*that.$selectStateCdBox.combobox('value', ScData.stateCd);
			that.$selectBRRelaBox.combobox('value', bornRuleData.zmgrClusterComponentBornrule.bornruleRela);
			that.$selectBRStatusBox.combobox('value',bornRuleData.zmgrClusterComponentBornrule.statusCd);*/
		},

		//新增商家信息
		addBusiness: function(){
            var that = this;
            if (that.isBusinessInfoEdit()) {
                return ;
            }
            that.editStatus = true;
            //that.initInput();
            that.$("#business_info_form").form('enable');
            that.$("#business_info_form").form('clear');
            that.$(".js-business-btns").show();
            /*that.$("#itemSales").attr('disabled', true);
            that.$("#itemRate").attr('disabled', true);*/
            //that.$("#stateCd").prev().find("input").attr("readonly", true);
            //that.$selectStateCdBox.combobox('value', '00A');
            that.saveType = 'add';
		},

		//编辑商家信息弹窗
		editBusiness: function(){
            var that = this;
            if (that.isBusinessInfoEdit()) {
                return false;
            };
            that.editStatus = true;
            that.$("#business_info_form").form('enable');
            that.$(".js-business-btns").show();
            /*that.$("#itemSales").attr('disabled', true);
            that.$("#itemRate").attr('disabled', true);*/
            //that.$("#stateCd").prev().find("input").attr("readonly", true);
            that.saveType = 'edit';
		},

		//保存商家信息
		saveBusiness: function(){
			var that = this;
            if(!$("business_info_form").isValid()){
                return false;
            }
            var params = that.$("#business_info_form").form('value');
            $.blockUI({
                message: '请稍后'
            });
            if(that.saveType == 'add'){
                businessAction.addBusiness(params, function(result){
                    $.unblockUI();
                    if(result && result.resultCode == '0'){
                        that.cancelBusiness();
                        that.editStatus = false;
                        that.getBusinessList_();
                        fish.success('新增成功！');
                    }else{
                        fish.error('新增失败: '+result.resultMsg);
                    }
                });
            }else if(that.saveType == 'edit'){
                params.businessId = $("#businessId").val();
                businessAction.updateBusiness(params, function(result){
                    $.unblockUI();
                    if(result && result.resultCode == '0'){
                        that.editStatus = false;
                        that.cancelBusiness();
                        that.getBusinessList_();
                        //that.$("#"+params.id).trigger('click');
                        fish.success('编辑成功！',function(){ });
                    }else{
                        fish.error('编辑失败: '+result.resultMsg);
                    }
                });
            }
		},

		//取消保存
		cancelBusiness: function(){
			var that = this;
			$(".js-business-btns").hide();
			$("#business_info_form").form('disable');
			$("#business_info_form").form('clear');
            $('#business_info_form').resetValid();
            that.editStatus = false;
			CommonUtil.removeValiStyle(that.$('#business_info_form'));
			//默认查看第一条信息详情
			$("#businessListGrid table tr.jqgrow:first").trigger('click');
		},

		deleteBusiness: function(){
			var that = this;
			var businessId = that.$("#businessListGrid").grid("getSelection").businessId;
	        fish.confirm('确定删除该商家所有信息？', function(){
                var params = {};
                params.businessId = businessId;
                businessAction.deleteBusiness(params, function(result){
                    if(result && result.resultCode == '0'){
                        that.getBusinessList_();
                        fish.success("商家信息删除成功！");
                    }else{
                        fish.error("删除失败："+result.resultMsg);
                    };
                });
	       	});
		},

		//名称查询
		searchBusinessByName: function(){
			var that = this;
			if(that.isBusinessInfoEdit()){
			    return false;
			}
			that.getBusinessList_();
		},
	});
	return businessView;

})
