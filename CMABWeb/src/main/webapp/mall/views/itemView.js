define([ 'hbs!../templates/itemTemplate.html',
         '../actions/itemAction',
         '../../system/actions/SysUtilAction',
         '../../system/actions/systemRuleAction',
         '../../system/common/CommonUtil'

        ],
		function(temp, itemAction, SysUtilAction, systemRuleAction, CommonUtil) {
	var itemView = fish.View.extend({
		el : false,
		template : temp,
		pageSize : 5,
		pageIndex  : 1,
		editStatus : '',
		saveType : '',
		$selectStateCdBox : '',
        $checkoutTagBox: '',

        itemImagesPath: '', //用来存储当前物品 的所有展示图片的路径

		events : {
            'click #searchItemByName': 'searchItemByName',
            'click #btn-add-item' : 'addItem',
            'click .editItem' : 'editItem',
            'click .delItem': 'delItem',
            'click #btn-item-save': 'saveItem',
            'click #btn-item-cancel': 'cancelItem',
            'click #showItemImg': 'getItemImg'
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
            that.getItemList_();
            $("#tabs").tabs();
            $("#item_info_form").form('disable');
		},

		 //初始化静态数据
        initInput : function(){
        	var that = this;
            var options = {
                placeholder: '请选择审核状态',
                dataTextField: 'displayValue',
                dataValueField: 'code',
                dataSource: [
                    {displayValue : '待审核', code : '0'},
                    {displayValue : '审核通过', code : '1'},
                    {displayValue : '审核未通过', code : '2'}
                ]
            };
            that.$checkoutTagBox = $('#checkoutTag').combobox(options);
        },

        getItemList_: function(){
        	var that = this;
        	that.$('#itemListPager').pagination('destroy');
        	that.getItemList(null);
        },

		//查询商品信息，根据商品名称
        getItemList: function(params){
            $("#itemListGrid").grid('destroy');
			var that = this;
			if(params == null || params == '' || params == undefined){
				var params = {};
				params.pageIndex = that.pageIndex;
				params.pageSize = that.pageSize;
			}
			params.itemName = $.trim($("#searchItemName").val());
			$.blockUI({
	            message: '请稍后'
	        });
			itemAction.getItemList(params, function(result){
				$.unblockUI();
				if(result && result.resultCode == "0"){
					if(result.resultObject.itemList.length > 0){
						that.$("#itemListGrid").html("");
						that.initData(result);
						//默认查看第一条信息详情
						that.$("#itemListGrid table tr.jqgrow:first").trigger('click');
					}else{
						that.$("#itemListGrid").html("");
						var html = '<img src="marketing/css/base/images/none-1.png" style="height:100px;width:124px;text-align:center;margin:20px 0">';
	 					html += '<p style="width:100%;text-align:center;color:#d0d5e0;">抱歉！暂无数据</p>';
						that.$('#itemListGrid').append(html);
						that.$('#itemListPager').pagination('destroy');
					}

				}
			});
		},

        //商品信息表格渲染
        initData: function(result){
            var that = this;
            $("#itemListGrid").grid('destroy');
            that.$itemListGrid = $("#itemListGrid").grid({
              data: result.resultObject.itemList,
              height: 200,
              rowNum : 5,
              rowList : [5,10,20],
              multiselect:true,
              colModel: [
                  { name: 'idKey', label: '商品id',hidden:true, key:true,
                              formatter: function(cellval, opts, rwdat, _act) {
                                    return  cellval;
                              }
                   },
                  { name: 'itemName', label:'名称',sortable: false},
                  { name: 'itemPrice', label: '价格', sortable:false},
                   //{ name: 'itemImg', label: '图画', sortable: false, hidden:true},
                  { name: 'itemSales', label: '销量', sortable: false},
                  { name: 'itemInventory', label: '库存量', sortable: false},
                  //{ name: 'itemDesc', label: '描述', sortable: false, hidden:true},
                  //{ name: 'itemRate', label: '评分', sortable: false, hidden:true},
                  //{ name: 'itemCredit', label: '提供积分', sortable: false, hidden:true},
                  { name: 'checkoutTag', label: '审核状态', sortable: false,
                      formatter: function(cellval, opts, rwdat, _act) {
                          if(cellval == '0'){
                              return '待审核';
                          }else if(cellval == '1'){
                              return '通过审核';
                          }else if(cellval == '2'){
                              return '未通过审核'
                          }else{
                              return '状态未知';
                          }
                      }
                  },
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
                    var selarrrow = $("#itemListGrid").grid("getCheckRows");
                    //单选
                    if(selarrrow.length>=1){
                        var sel = [selarrrow[0].itemId] ;
                        $("#itemListGrid").grid("setCheckRows",sel,false);
                    }
                },
                beforeSelectRow: function (e, rowid) {  /*选中行之前事件*/
                    if (that.isItemInfoEdit()) {
                        return false;
                    }
                    return true;
                },
                onSelectRow: function (e, rowid, state, checked) {//选中行事件
                  //当前选中的商品
                  //var data = that.$itemListGrid.grid('getSelection');
                    that.showItemValues();  //显示商品详细信息

              },
          });
            that.initDataPagination(result.resultObject.pageInfo);
        },

        // 是否正在编辑中
        isItemInfoEdit: function () {
            if (this.editStatus) {
                fish.warn('商品信息编辑中，请先保存或者放弃再进行此操作');
                return true;
            }
            return false;
        },

		//商品信息分页
		initDataPagination: function(pageInfo){
			var that = this;
        	$('#itemListPager').pagination({
        		 total:pageInfo.pageCount,
    	         records:pageInfo.total,
    	         displayNum:5,
    	         rowNum:pageInfo.pageSize,
    	         pgInput :true,
    	         pgRecText:true,
    	         pgTotal:true,
    	         rowtext:null,
    	         onPageClick: function (e, eventData) {
    	        	 if(that.isItemInfoEdit()) return false;
    	        	 $('#page-content').text(eventData.eventType+' '+eventData.rowNum+' '+eventData.page);
                     var params = {};
                     params.pageIndex = eventData.page;
                     params.pageSize = that.pageSize;
                     that.getItemList(params);
                 }
    	     });
		},

		//赋值
		showItemValues: function(){
            var that = this;
            var itemId = that.$itemListGrid.grid('getSelection').itemId;
            var params = {
              itemId : itemId,
            };
            itemAction.getItemDetail(params, function(result){
                if(result && result.resultCode == "0"){
                    var itemDetail = result.resultObject;
                    that.$("#itemId").val(itemDetail.item.itemId);
                    that.$("#itemName").val(itemDetail.item.itemName);
                    that.$("#itemPrice").val(itemDetail.item.itemPrice);
                    that.itemImagesPath = itemDetail.item.itemImg;
                    that.$("#itemImg").val(itemDetail.item.itemImg);
                    that.$("#itemSales").val(itemDetail.item.itemSales)
                    that.$("#itemInventory").val(itemDetail.item.itemInventory);
                    that.$("#itemRate").val(itemDetail.item.itemRate);
                    that.$("#itemCredit").val(itemDetail.item.itemCredit);
                    that.$("#itemDesc").val(itemDetail.item.itemDesc);
                    that.$("#itemBelong").val(itemDetail.item.itemBelong);
                    that.$("#itemBelongName").val(itemDetail.itemBelongName);
                    that.$checkoutTagBox.combobox('value', itemDetail.item.checkoutTag);
                    /*that.$selectBRRelaBox.combobox('value', bornRuleData.zmgrClusterComponentBornrule.bornruleRela);
                    that.$selectBRStatusBox.combobox('value',bornRuleData.zmgrClusterComponentBornrule.statusCd);*/
                }
            });
		},

		//新增商品信息
		addItem: function(){
            var that = this;
            if (that.isItemInfoEdit()) {
                return ;
            }
            that.editStatus = true;
            //that.initInput();
            that.$("#item_info_form").form('enable');
            that.$("#item_info_form").form('clear');
            that.itemImagesPath = '';
            that.$("#showItemImg").val("添加图片");
            that.$(".js-item-btns").show();
            that.$("#itemSales").attr('disabled', true);
            that.$("#itemRate").attr('disabled', true);
            //that.$("#stateCd").prev().find("input").attr("readonly", true);
            //that.$selectStateCdBox.combobox('value', '00A');
            that.saveType = 'add';
		},

		//编辑商品信息弹窗
		editItem: function(){
            var that = this;
            if (that.isItemInfoEdit()) {
                return false;
            };
            that.editStatus = true;
            that.$("#item_info_form").form('enable');
            that.$(".js-item-btns").show();
            that.$("#itemSales").attr('disabled', true);
            that.$("#itemRate").attr('disabled', true);
            //that.$("#stateCd").prev().find("input").attr("readonly", true);
            that.saveType = 'edit';
		},

		//保存商城物品信息
		saveItem: function(){
			var that = this;
            if(!$("item_info_form").isValid()){
                return false;
            }
            var params = that.$("#item_info_form").form('value');
            $.blockUI({
                message: '请稍后'
            });
            if(that.saveType == 'add'){
                itemAction.addItem(params, function(result){
                    $.unblockUI();
                    if(result && result.resultCode == '0'){
                        // 重命名 图片 名称
                        var itemIdTmp = result.resultObject;
                        $("input[type='file']").each(function(){
                           var fileNameTmp = $(this).attr('name');
                            fileNameTmp = fileNameTmp.replace('tmp',itemIdTmp);
                           $(this).attr('name',fileNameTmp);
                        });
                        //上传图片
                        that.uploadImage();

                        that.cancelItem();
                        that.editStatus = false;
                        that.getItemList_();
                        fish.success('新增成功！');
                    }else{
                        fish.error('新增失败: '+result.resultMsg);
                    }
                });
            }else if(that.saveType == 'edit'){
                params.id = $("#id").val();
                itemAction.updateItem(params, function(result){
                    $.unblockUI();
                    if(result && result.resultCode == '0'){
                        //上传图片
                        that.uploadImage();

                        that.editStatus = false;
                        that.cancelItem();
                        that.getItemList_();
                        //that.$("#"+params.id).trigger('click');
                        fish.success('编辑成功！');
                    }else{
                        fish.error('编辑失败: '+result.resultMsg);
                    }
                });
            }
		},

		//取消保存
		cancelItem: function(){
			var that = this;
			$(".js-item-btns").hide();
			$("#item_info_form").form('disable');
			$("#item_info_form").form('clear');
            $('#item_info_form').resetValid();
            that.editStatus = false;
			CommonUtil.removeValiStyle(that.$('#item_info_form'));
			//默认查看第一条信息详情
			$("#itemListGrid table tr.jqgrow:first").trigger('click');
		},

		delItem: function(){
			var that = this;
			var itemId = that.$("#itemListGrid").grid("getSelection").itemId;
	        fish.confirm('确定删除该商品所有信息？', function(){
                var params = {};
                params.itemId = itemId;
                itemAction.deleteItem(params, function(result){
                    if(result && result.resultCode == '0'){
                        that.getItemList_();
                        fish.success("商品信息删除成功！");
                    }else{
                        fish.error("删除失败："+result.resultMsg);
                    };
                });
	       	});
		},

		//名称查询
		searchItemByName: function(){
			var that = this;
			if(that.isItemInfoEdit()){
			    return false;
			}
			that.getItemList_();
		},

		//带着图片路径进行图片显示界面
		getItemImg: function(){
            var that = this;
            var param = {
                itemImagesPath:that.itemImagesPath,
                itemId: that.$("#itemId").val(),
            };
            fish.popupView({
                url:'mall/views/itemImageView',
                height:500,
                width: '80%',
                viewOption:param,
                close:function(){

                },
                callback:function(popup, view){
                    view.$("#save-itemImage-button").on('click', function(){
                        that.itemImagesPath = view.itemImagesPath;
                        that.$("#itemImg").val(view.itemImagesPath);
                        view.addAllImage();
                        popup.close();
                    });
                }
            });
		},

        //上传图片到服务器
        uploadImage: function() {
		    var that = this;
            /*$('#itemImageForm').submit(function (event){
                event.preventDefault();
                var form = $(this);
                //上传文件
                var formData = new FormData(form);
                $.ajax({
                    type: form.attr('method'),
                    url: form.attr('action'),
                    //url: 'ItemController/addItem',
                    data: JSON.stringify(formData), //formData,
                    mimeType: "multipart/form-data",
                    contentType: false,
                    cache: false,
                    processData: false
                }).success(function () {
                    console.log("success");
                    //成功提交
                }).fail(function (jqXHR, textStatus, errorThrown) {
                    console.log("fail");
                    //错误信息
                });
            });*/
            $("#itemImageForm").submit();
        }

	});
	return itemView;

})
