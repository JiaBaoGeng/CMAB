define([ 'hbs!../templates/storeTemplate.html',
         '../actions/storeAction',
         '../../system/actions/SysUtilAction',
         '../../system/common/CommonUtil'

        ],
		function(temp, storeAction, SysUtilAction, CommonUtil) {
	var storeView = fish.View.extend({
		el : false,
		template : temp,
		pageSize : 5,
		pageIndex  : 1,
		editStatus : '',
		saveType : '',
        $selectCheckoutTagBox:'',
        $checkoutTagBox: '',

        storeImagesPath: '', //用来存储当前物品 的所有展示图片的路径

		events : {
            'click #searchStoreByName': 'searchStoreByName',
            'click #btn-add-store' : 'addStore',
            'click .editStore' : 'editStore',
            'click .deleteStore': 'deleteStore',
            'click #btn-store-save': 'saveStore',
            'click #btn-store-cancel': 'cancelStore',
            'click #showStoreImg': 'showStoreImg'
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
            that.getStoreList_();
            $("#tabsStore").tabs();
            $("#store_info_form").form('disable');
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
            that.$checkoutTagBox = that.$('#checkoutTag').combobox(options);
        },

        getStoreList_: function(){
        	var that = this;
        	that.$('#storeListPager').pagination('destroy');
        	that.getStoreList(null);
        },

		//查询门店信息，根据门店名称
        getStoreList: function(params){
            $("#storeListGrid").grid('destroy');
			var that = this;
			if(params == null || params == '' || params == undefined){
				var params = {};
				params.pageIndex = that.pageIndex;
				params.pageSize = that.pageSize;
			}
			params.storeName = $.trim($("#searchStoreName").val());
			$.blockUI({
	            message: '请稍后'
	        });
            storeAction.getStoreList(params, function(result){
			    $.unblockUI();
				if(result && result.resultCode == "0"){
					if(result.resultObject.storeList.length > 0){
						that.$("#storeListGrid").html("");
						that.initData(result);
						//默认查看第一条信息详情
						that.$("#storeListGrid table tr.jqgrow:first").trigger('click');
					}else{
						that.$("#storeListGrid").html("");
						var html = '<img src="marketing/css/base/images/none-1.png" style="height:100px;width:124px;text-align:center;margin:20px 0">';
	 					html += '<p style="width:100%;text-align:center;color:#d0d5e0;">抱歉！暂无数据</p>';
						that.$('#storeListGrid').append(html);
						that.$('#storeListPager').pagination('destroy');
					}
				}

			});
		},

        //门店信息表格渲染
        initData: function(result){
            var that = this;
            $("#storeListGrid").grid('destroy');
            that.$storeListGrid = $("#storeListGrid").grid({
              data: result.resultObject.storeList,
              height: 200,
              rowNum : 5,
              rowList : [5,10,20],
              multiselect:true,
              colModel: [
                  { name: 'idKey', label: '门店id',hidden:true, key:true,
                              formatter: function(cellval, opts, rwdat, _act) {
                                    return  rwdat.storeId;
                              }
                   },
                  { name: 'storeName', label:'门店名称',sortable: false},
                  { name: 'storeManager', label: '负责人', sortable: false},
                  { name: 'storePhone', label: '联系方式', sortable: false},
                  { name: 'businessHours', label: '营业时间', sortable:false},
                  { name: 'checkoutTag', label:'审核状态',sortable: false,
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
                  { name: 'storeLevel',label:'操作',sortable: false,
                      formatter: function (cellval, opts, rwdat, _act) {
                          var html = '<div class="btn-group">' +
                                  '<button type="button" class="btn btn-link js-edit editStore">修改</button>' +
                                  '</div>' ;
                          html += '<div class="btn-group">' +
                                  '<button type="button" class="btn btn-link js-delete deleteStore">删除</button>' +
                                  '</div>';
                          return html;
                      },
                  }
              ],
                onCellSelect: function (e, status) {//点击头部的checkbox时触发的事件
                    var selarrrow = $("#storeListGrid").grid("getCheckRows");
                    //单选
                    if(selarrrow.length>=1){
                        var sel = [selarrrow[0].storeId] ;
                        $("#storeListGrid").grid("setCheckRows",sel,false);
                    }
                },
                beforeSelectRow: function (e, rowid) {  /*选中行之前事件*/
                    if (that.isStoreInfoEdit()) {
                        return false;
                    }
                    return true;
                },
                onSelectRow: function (e, rowid, state, checked) {//选中行事件
                    that.showStoreInfo();
              },
          });
            that.initDataPagination(result.resultObject.pageInfo);
        },

        // 是否正在编辑中
        isStoreInfoEdit: function () {
            if (this.editStatus) {
                fish.warn('门店信息编辑中，请先保存或者放弃再进行此操作');
                return true;
            }
            return false;
        },

		//门店信息分页
		initDataPagination: function(pageInfo){
			var that = this;
        	$('#storeListPager').pagination({
        		 total:pageInfo.pageCount,
    	         records:pageInfo.total,
    	         displayNum:5,
    	         rowNum:pageInfo.pageSize,
    	         pgInput :true,
    	         pgRecText:true,
    	         pgTotal:true,
    	         rowtext:null,
    	         onPageClick: function (e, eventData) {
    	        	 if(that.isStoreInfoEdit()) return false;
    	        	 $('#page-content').text(eventData.eventType+' '+eventData.rowNum+' '+eventData.page);
                     var params = {};
                     params.pageIndex = eventData.page;
                     params.pageSize = that.pageSize;
                     that.getStoreList(params);
                 }
    	     });
		},

		//赋值
		showStoreInfo: function(){
		    var that = this;
            //当前选中的门店
            var storeId =   that.$storeListGrid.grid('getSelection').storeId;
            var params = {
                storeId: storeId,
            };
            $.blockUI({
                message: '请稍后'
            });
            storeAction.getStoreDetailById(params, function(result) {
                $.unblockUI();
                if (result && result.resultCode == "0") {
                    var storeDetail = result.resultObject;
                    that.$("#storeId").val(storeDetail.store.storeId);
                    that.$("#storeName").val(storeDetail.store.storeName);
                    that.$("#belongName").val(storeDetail.belongName);
                    that.storeImagesPath = storeDetail.store.storeImg;
                    that.$("#addr").val(storeDetail.store.addr);
                    that.$("#storeManager").val(storeDetail.store.storeManager)
                    that.$("#storePhone").val(storeDetail.store.storePhone);
                    that.$("#businessHours").val(storeDetail.store.businessHours);
                    that.$("#serviceType").val(storeDetail.store.serviceType)
                    that.$("#checkoutTag").val(storeDetail.store.checkoutTag);
                    that.$("#storeDesc").val(storeDetail.store.storeDesc);
                    that.$checkoutTagBox.combobox('value', storeDetail.store.checkoutTag);
                    /*that.$selectBRRelaBox.combobox('value', bornRuleData.zmgrClusterComponentBornrule.bornruleRela);
                    that.$selectBRStatusBox.combobox('value',bornRuleData.zmgrClusterComponentBornrule.statusCd);*/
                }else{
                    fish.warn(result.resultMsg);
                }
            });
		},

		//新增门店信息
		addStore: function(){
            var that = this;
            if (that.isStoreInfoEdit()) {
                return ;
            }
            that.editStatus = true;
            //that.initInput();
            that.$("#store_info_form").form('enable');
            that.$("#store_info_form").form('clear');
            that.storeImagesPath = '';
            that.$("#showStoreImg").val("添加图片");
            that.$(".js-store-btns").show();
            that.storeImagesPath = '';
            /*that.$("#StoreSales").attr('disabled', true);
            that.$("#stateCd").prev().find("input").attr("readonly", true);
            that.$selectStateCdBox.combobox('value', '00A');*/
            that.saveType = 'add';
		},

		//编辑门店信息弹窗
		editStore: function(){
            var that = this;
            if (that.isStoreInfoEdit()) {
                return false;
            };
            that.editStatus = true;
            that.$("#store_info_form").form('enable');
            that.$(".js-store-btns").show();
            /*that.$("#itemSales").attr('disabled', true);
            that.$("#itemRate").attr('disabled', true);
            that.$("#stateCd").prev().find("input").attr("readonly", true);*/
            that.saveType = 'edit';
		},

		//保存门店信息
		saveStore: function(){
			var that = this;
            if(!$("store_info_form").isValid()){
                return false;
            }
            var params = that.$("#store_info_form").form('value');
            $.blockUI({
                message: '请稍后'
            });
            if(that.saveType == 'add'){
                storeAction.addStore(params, function(result){
                    $.unblockUI();
                    if(result && result.resultCode == '0'){
                        // 重命名 图片 名称
                        var storeIdTmp = result.resultObject;
                        $("input[type='file']").each(function(){
                            var fileNameTmp = $(this).attr('name');
                            fileNameTmp = fileNameTmp.replace('tmp',storeIdTmp);
                            $(this).attr('name',fileNameTmp);
                        });
                        //上传图片
                        that.uploadImage();

                        that.cancelStore();
                        that.editStatus = false;
                        that.getStoreList_();
                        fish.success('新增成功！');
                    }else{
                        fish.error('新增失败: '+result.resultMsg);
                    }
                });
            }else if(that.saveType == 'edit'){
                params.storeId = $("#storeId").val();
                storeAction.updateStore(params, function(result){
                    $.unblockUI();
                    if(result && result.resultCode == '0'){
                        //上传图片
                        that.uploadImage();

                        that.editStatus = false;
                        that.cancelStore();
                        that.getStoreList_();
                        //that.$("#"+params.id).trigger('click');
                        fish.success('编辑成功！');
                    }else{
                        fish.error('编辑失败: '+result.resultMsg);
                    }
                });
            }
		},

		//取消保存
		cancelStore: function(){
			var that = this;
			$(".js-store-btns").hide();
			$("#store_info_form").form('disable');
			$("#store_info_form").form('clear');
            $('#store_info_form').resetValid();
            that.editStatus = false;
			CommonUtil.removeValiStyle(that.$('#store_info_form'));
			//默认查看第一条信息详情
			$("#storeListGrid table tr.jqgrow:first").trigger('click');
		},

		deleteStore: function(){
			var that = this;
			var storeId = that.$("#storeListGrid").grid("getSelection").storeId;
	        fish.confirm('确定删除该商家所有信息？', function(){
                var params = {};
                params.storeId = storeId;
                storeAction.deleteStore(params, function(result){
                    if(result && result.resultCode == '0'){
                        that.getStoreList_();
                        fish.success("商家信息删除成功！");
                    }else{
                        fish.error("删除失败："+result.resultMsg);
                    };
                });
	       	});
		},

		//名称查询
		searchStoreByName: function(){
			var that = this;
			if(that.isStoreInfoEdit()){
			    return false;
			}
			that.getStoreList_();
		},

        //带着图片路径进行图片显示界面
        showStoreImg: function(){
            var that = this;
            var param = {
                storeImagesPath:that.storeImagesPath,
                storeId: that.$("#storeId").val(),
            };
            fish.popupView({
                url:'store/views/storeImageView',
                height:500,
                width: '80%',
                viewOption:param,
                close:function(){

                },
                callback:function(popup, view){
                    view.$("#save-storeImage-button").on('click', function(){
                        that.storeImagesPath = view.storeImagesPath;
                        that.$("#storeImg").val(view.storeImagesPath);
                        popup.close();
                    });
                }
            });
        },

        //上传图片到服务器
        uploadImage: function() {
            var that = this;
            $("#storeImageForm").submit();
        }
	});
	return storeView;

})
