define([ 'hbs!../templates/ordersTemplate.html',
         '../actions/ordersAction',
         '../../system/actions/SysUtilAction',
         '../../system/common/CommonUtil'

        ],
		function(temp, ordersAction, SysUtilAction, CommonUtil) {
	var ordersView = fish.View.extend({
		el : false,
		template : temp,
		pageSize : 5,
		pageIndex  : 1,
		editStatus : '',
		saveType : '',
		$orderTagBox : '',

		events : {
            'click #searchOrdersByName': 'searchOrdersByName',
            'click #btn-add-orders' : 'addOrders',
            'click .editOrders' : 'editOrders',
            'click .deleteOrders': 'deleteOrders',
            'click #btn-orders-save': 'saveOrders',
            'click #btn-orders-cancel': 'cancelOrders',
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
            that.getOrdersList_();
            $("#tabs-orders").tabs();
            $("#orders_info_form").form('disable');
		},

		 //初始化静态数据
        initInput : function(){
        	var that = this;
            var options = {
                placeholder: '请选择订单状态',
                dataTextField: 'displayValue',
                dataValueField: 'code',
                dataSource: [
                    {displayValue : '处理中', code : '0'},
                    {displayValue : '已发货', code : '1'},
                    {displayValue : '物流中', code : '2'},
                    {displayValue : '交易完成', code : '3'},
                    {displayValue : '客户拒收', code : '4'},
                    {displayValue : '返货物流中', code : '5'},
                    {displayValue : '已返货', code : '6'}
                ]
            };
            that.$orderTagBox = $('#orderTag').combobox(options);
        },

        getOrdersList_: function(){
        	var that = this;
        	that.$('#ordersListPager').pagination('destroy');
        	that.getOrdersList(null);
        },

		//查询商品信息，根据商品名称
        getOrdersList: function(params){
            $("#ordersListGrid").grid('destroy');
			var that = this;
			if(params == null || params == '' || params == undefined){
				var params = {};
				params.pageIndex = that.pageIndex;
				params.pageSize = that.pageSize;
			}
			params.userName = $.trim($("#searchOrdersName").val());
			$.blockUI({
	            message: '请稍后'
	        });
            ordersAction.getOrdersList(params, function(result){
				$.unblockUI();
				if(result && result.resultCode == "0"){
					if(result.resultObject.ordersList.length > 0){
						that.$("#ordersListGrid").html("");
						that.initData(result);
						//默认查看第一条信息详情
						that.$("#ordersListGrid table tr.jqgrow:first").trigger('click');
					}else{
						that.$("#ordersListGrid").html("");
						var html = '<img src="marketing/css/base/images/none-1.png" style="height:100px;width:124px;text-align:center;margin:20px 0">';
	 					html += '<p style="width:100%;text-align:center;color:#d0d5e0;">抱歉！暂无数据</p>';
						that.$('#ordersListGrid').append(html);
						that.$('#ordersListPager').pagination('destroy');
					}

				}
			});
		},

        //商品信息表格渲染
        initData: function(result){
            var that = this;
            $("#ordersListGrid").grid('destroy');
            that.$ordersListGrid = $("#ordersListGrid").grid({
              data: result.resultObject.ordersList,
              height: 200,
              rowNum : 5,
              rowList : [5,10,20],
              multiselect:true,
              colModel: [
                  { name: 'idKey', label: '订单id',hidden:true, key:true,
                              formatter: function(cellval, opts, rwdat, _act) {
                                    return  cellval;
                              }
                   },
                  { name: 'userId', label:'用户',sortable: false},
                  { name: 'itemId', label:'商品名称',sortable: false},
                  { name: 'itemNum', label: '商品数量', sortable: false},
                  { name: 'orderPrice', label: '订单价格', sortable:false},
                  { name: 'ordersLevel',label:'操作',sortable: false,
                      formatter: function (cellval, opts, rwdat, _act) {
                          var html = '<div class="btn-group">' +
                                  '<button type="button" class="btn btn-link js-edit editOrders">修改</button>' +
                                  '</div>' ;
                          html += '<div class="btn-group">' +
                                  '<button type="button" class="btn btn-link js-delete deleteOrders">删除</button>' +
                                  '</div>';
                          return html;
                      },
                  }
              ],
                onCellSelect: function (e, status) {//点击头部的checkbox时触发的事件
                    var selarrrow = $("#ordersListGrid").grid("getCheckRows");
                    //单选
                    if(selarrrow.length>=1){
                        var sel = [selarrrow[0].ordersId] ;
                        $("#ordersListGrid").grid("setCheckRows",sel,false);
                    }
                },
                beforeSelectRow: function (e, rowid) {  /*选中行之前事件*/
                    if (that.isOrdersInfoEdit()) {
                        return false;
                    }
                    return true;
                },
                onSelectRow: function (e, rowid, state, checked) {//选中行事件
                  that.showOrdersValues();  //显示商品详细信息
              },
          });
            that.initDataPagination(result.resultObject.pageInfo);
        },

        // 是否正在编辑中
        isOrdersInfoEdit: function () {
            if (this.editStatus) {
                fish.warn('订单信息编辑中，请先保存或者放弃再进行此操作');
                return true;
            }
            return false;
        },

		//商品信息分页
		initDataPagination: function(pageInfo){
			var that = this;
        	$('#ordersListPager').pagination({
        		 total:pageInfo.pageCount,
    	         records:pageInfo.total,
    	         displayNum:5,
    	         rowNum:pageInfo.pageSize,
    	         pgInput :true,
    	         pgRecText:true,
    	         pgTotal:true,
    	         rowtext:null,
    	         onPageClick: function (e, eventData) {
    	        	 if(that.isOrdersInfoEdit()) return false;
    	        	 $('#page-content').text(eventData.eventType+' '+eventData.rowNum+' '+eventData.page);
                     var params = {};
                     params.pageIndex = eventData.page;
                     params.pageSize = that.pageSize;
                     that.getOrdersList(params);
                 }
    	     });
		},

		//赋值
		showOrdersValues: function(){
            var that = this;
            var orderId = that.$ordersListGrid.grid('getSelection').orderId;
            var params = {
                orderId : orderId,
            };
            ordersAction.getOrdersDetail(params, function(result){
                if(result && result.resultCode == "0"){
                    var ordersDetail = result.resultObject;
                    that.$("#orderId").val(ordersDetail.orders.orderId);
                    that.$("#userId").val(ordersDetail.orders.userId);
                    that.$("#userName").val(ordersDetail.userName);
                    that.$("#itemBelong").val(ordersDetail.itemBelong);
                    that.$("#businessName").val(ordersDetail.businessName);
                    that.$("#itemId").val(ordersDetail.orders.itemId);
                    that.$("#itemName").val(ordersDetail.itemName);
                    that.$("#itemPrice").val(ordersDetail.orders.itemPrice);
                    that.$("#itemNum").val(ordersDetail.orders.itemNum);
                    that.$("#orderPrice").val(ordersDetail.orders.orderPrice)
                    that.$("#orderTime").val(ordersDetail.orders.orderTime);
                    that.$("#orderNote").val(ordersDetail.orders.orderNote);
                    that.$orderTagBox.combobox('value', ordersDetail.orders.orderTag);
                    /*that.$selectBRRelaBox.combobox('value', bornRuleData.zmgrClusterComponentBornrule.bornruleRela);
                    that.$selectBRStatusBox.combobox('value',bornRuleData.zmgrClusterComponentBornrule.statusCd);*/
                }
            });
		},

		//新增订单信息
		addOrders: function(){
            var that = this;
            if (that.isOrdersInfoEdit()) {
                return ;
            }
            that.editStatus = true;
            //that.initInput();
            that.$("#orders_info_form").form('enable');
            that.$("#orders_info_form").form('clear');
            //that.$("#ordersImg").val("添加图片");
            that.$(".js-orders-btns").show();
            //that.$("#itemSales").attr('disabled', true);
            //that.$("#itemRate").attr('disabled', true);
            //that.$("#stateCd").prev().find("input").attr("readonly", true);
            //that.$selectStateCdBox.combobox('value', '00A');
            that.saveType = 'add';
		},

		//编辑订单信息弹窗
		editOrders: function(){
            var that = this;
            if (that.isOrdersInfoEdit()) {
                return false;
            };
            that.editStatus = true;
            that.$("#orders_info_form").form('enable');
            that.$(".js-orders-btns").show();
            //that.$("#itemSales").attr('disabled', true);
            //that.$("#itemRate").attr('disabled', true);
            //that.$("#stateCd").prev().find("input").attr("readonly", true);
            that.saveType = 'edit';
		},

		//保存订单信息
		saveOrders: function(){
			var that = this;
            if(!$("orders_info_form").isValid()){
                return false;
            }
            var params = that.$("#orders_info_form").form('value');
            $.blockUI({
                message: '请稍后'
            });
            if(that.saveType == 'add'){
                ordersAction.addOrders(params, function(result){
                    $.unblockUI();
                    if(result && result.resultCode == '0'){
                        that.cancelOrders();
                        that.editStatus = false;
                        that.getOrdersList_();
                        fish.success('新增成功！',function(){});
                    }else{
                        fish.error('新增失败: '+result.resultMsg);
                    }
                });
            }else if(that.saveType == 'edit'){
                ordersAction.updateOrders(params, function(result){
                    $.unblockUI();
                    if(result && result.resultCode == '0'){
                        that.editStatus = false;
                        that.cancelOrders();
                        that.getOrdersList_();
                        //that.$("#"+params.id).trigger('click');
                        fish.success('编辑成功！',function(){ });
                    }else{
                        fish.error('编辑失败: '+result.resultMsg);
                    }
                });
            }
		},

		//取消保存
		cancelOrders: function(){
			var that = this;
			$(".js-orders-btns").hide();
			$("#orders_info_form").form('disable');
			$("#orders_info_form").form('clear');
            $('#orders_info_form').resetValid();
            that.editStatus = false;
			CommonUtil.removeValiStyle(that.$('#orders_info_form'));
			//默认查看第一条信息详情
			$("#ordersListGrid table tr.jqgrow:first").trigger('click');
		},

		deleteOrders: function(){
			var that = this;
			var ordersId = that.$("#ordersListGrid").grid("getSelection").ordersId;
	        fish.confirm('确定删除该订单所有信息？', function(){
                var params = {};
                params.ordersId = ordersId;
                ordersAction.deleteOrders(params, function(result){
                    if(result && result.resultCode == '0'){
                        that.getOrdersList_();
                        fish.success("订单信息删除成功！");
                    }else{
                        fish.error("删除失败："+result.resultMsg);
                    };
                });
	       	});
		},

		//名称查询
		searchOrdersByName: function(){
			var that = this;
			if(that.isOrdersInfoEdit()){
			    return false;
			}
			that.getOrdersList_();
		},
	});
	return ordersView;

})
