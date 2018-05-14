define([ 'hbs!../templates/goodsTemplate.html',
         '../actions/goodsAction',
         '../../system/actions/SysUtilAction',
         '../../system/common/CommonUtil'

        ],
		function(temp, goodsAction, SysUtilAction, CommonUtil) {
	var goodsView = fish.View.extend({
		el : false,
		template : temp,
		pageSize : 5,
		pageIndex  : 1,
		editStatus : '',
		saveType : '',
		$goodsTagBox : '',
        $checkoutTagBox: '',
        itemImagesPath: '', //用来存储当前物品 的所有展示图片的路径
                            //二者共用一个进行图片显示的view吧
		events : {
            'click #searchGoodsByName': 'searchGoodsByName',
            'click #btn-add-goods' : 'addGoods',
            'click .editGoods' : 'editGoods',
            'click .deleteGoods': 'deleteGoods',
            'click #btn-goods-save': 'saveGoods',
            'click #btn-goods-cancel': 'cancelGoods',
            'click #showGoodsImg': 'showGoodsImg'
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
            that.getGoodsList_();
            $("#tabs-goods").tabs();
            $("#goods_info_form").form('disable');
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

        getGoodsList_: function(){
        	var that = this;
        	that.$('#goodsListPager').pagination('destroy');
        	that.getGoodsList(null);
        },

		//查询二手商品信息，根据商品名称
        getGoodsList: function(params){
            var that = this;
            $("#goodsListGrid").grid('destroy');
			if(params == null || params == '' || params == undefined){
				var params = {};
				params.pageIndex = that.pageIndex;
				params.pageSize = that.pageSize;
			}
			params.goodsName = $.trim($("#searchGoodsName").val());
			$.blockUI({
	            message: '请稍后'
	        });
            goodsAction.getGoodsList(params, function(result){
				$.unblockUI();
				if(result && result.resultCode == "0"){
					if(result.resultObject.goodsList.length > 0){
						that.$("#goodsListGrid").html("");
						that.initData(result);
						//默认查看第一条信息详情
						that.$("#goodsListGrid table tr.jqgrow:first").trigger('click');
					}else{
						that.$("#goodsListGrid").html("");
						var html = '<img src="marketing/css/base/images/none-1.png" style="height:100px;width:124px;text-align:center;margin:20px 0">';
	 					html += '<p style="width:100%;text-align:center;color:#d0d5e0;">抱歉！暂无数据</p>';
						that.$('#goodsListGrid').append(html);
						that.$('#goodsListPager').pagination('destroy');
					}

				}
			});
		},

        //二手商品信息表格渲染
        initData: function(result){
            var that = this;
            $("#goodsListGrid").grid('destroy');
            that.$goodsListGrid = $("#goodsListGrid").grid({
              data: result.resultObject.goodsList,
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
                  { name: 'goodsName', label:'名称',sortable: false},
                  { name: 'goodsPrice', label: '价格', sortable:false},
                  { name: 'onsaleTime', label: '上架时间', sortable: false},
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
                  { name: 'goodsLevel',label:'操作',sortable: false,
                      formatter: function (cellval, opts, rwdat, _act) {
                          var html = '<div class="btn-group">' +
                                  '<button type="button" class="btn btn-link js-edit editGoods">修改</button>' +
                                  '</div>' ;
                          html += '<div class="btn-group">' +
                                  '<button type="button" class="btn btn-link js-delete deleteGoods">删除</button>' +
                                  '</div>';
                          return html;
                      },
                  }
              ],
                onCellSelect: function (e, status) {//点击头部的checkbox时触发的事件
                    var selarrrow = $("#goodsListGrid").grid("getCheckRows");
                    //单选
                    if(selarrrow.length>=1){
                        var sel = [selarrrow[0].goodsId] ;
                        $("#goodsListGrid").grid("setCheckRows",sel,false);
                    }
                },
                beforeSelectRow: function (e, rowid) {  /*选中行之前事件*/
                    if (that.isGoodsInfoEdit()) {
                        return false;
                    }
                    return true;
                },
                onSelectRow: function (e, rowid, state, checked) {//选中行事件
                  that.showGoodsValues();  //显示商品详细信息
              },
          });
            that.initDataPagination(result.resultObject.pageInfo);
        },

        // 是否正在编辑中
        isGoodsInfoEdit: function () {
            if (this.editStatus) {
                fish.warn('商品信息编辑中，请先保存或者放弃再进行此操作');
                return true;
            }
            return false;
        },

		//二手商品信息分页
		initDataPagination: function(pageInfo){
			var that = this;
        	$('#goodsListPager').pagination({
        		 total:pageInfo.pageCount,
    	         records:pageInfo.total,
    	         displayNum:5,
    	         rowNum:pageInfo.pageSize,
    	         pgInput :true,
    	         pgRecText:true,
    	         pgTotal:true,
    	         rowtext:null,
    	         onPageClick: function (e, eventData) {
    	        	 if(that.isGoodsInfoEdit()) return false;
    	        	 $('#page-content').text(eventData.eventType+' '+eventData.rowNum+' '+eventData.page);
                     var params = {};
                     params.pageIndex = eventData.page;
                     params.pageSize = that.pageSize;
                     that.getGoodsList(params);
                 }
    	     });
		},

		//赋值
		showGoodsValues: function(){
            var that = this;
            var goodsId = that.$goodsListGrid.grid('getSelection').goodsId;
            var params = {
                goodsId : goodsId,
            };
            goodsAction.getGoodsDetail(params, function(result){
                if(result && result.resultCode == "0"){
                    var goodsDetail = result.resultObject;
                    that.$("#goodsId").val(goodsDetail.usedGoods.goodsId);
                    that.$("#goodsName").val(goodsDetail.usedGoods.goodsName);
                    that.$("#goodsPrice").val(goodsDetail.usedGoods.goodsPrice);
                    that.itemImagesPath = goodsDetail.usedGoods.goodsImg;
                    that.$("#goodsImg").val(goodsDetail.usedGoods.goodsImg);
                    that.$("#goodsNum").val(goodsDetail.usedGoods.goodsNum)
                    that.$("#userName").val(goodsDetail.userName);
                    that.$("#userId").val(goodsDetail.usedGoods.userId);
                    that.$("#buyerName").val(goodsDetail.buyerName);
                    that.$("#buyterId").val(goodsDetail.usedGoods.buyterId);
                    that.$("#onsaleTime").val(goodsDetail.usedGoods.onsaleTime);
                    that.$("#goodsDesc").val(goodsDetail.usedGoods.goodsDesc);
                    that.$checkoutTagBox.combobox('value', goodsDetail.usedGoods.checkoutTag);
                    /*that.$selectBRRelaBox.combobox('value', bornRuleData.zmgrClusterComponentBornrule.bornruleRela);
                    that.$selectBRStatusBox.combobox('value',bornRuleData.zmgrClusterComponentBornrule.statusCd);*/
                }
            });
		},

		//新增二手商品信息
		addGoods: function(){
            var that = this;
            if (that.isGoodsInfoEdit()) {
                return ;
            }
            that.editStatus = true;
            //that.initInput();
            that.$("#goods_info_form").form('enable');
            that.$("#goods_info_form").form('clear');
            that.itemImagesPath = '';
            that.$("#showGoodsImg").val("添加图片");
            that.$(".js-goods-btns").show();
            that.$("#userName").attr('disabled', true);
            that.$("#buyerName").attr('disabled', true);
            //that.$("#stateCd").prev().find("input").attr("readonly", true);
            //that.$selectStateCdBox.combobox('value', '00A');
            that.saveType = 'add';
		},

		//编辑二手商品信息弹窗
		editGoods: function(){
            var that = this;
            if (that.isGoodsInfoEdit()) {
                return false;
            };
            that.editStatus = true;
            that.$("#goods_info_form").form('enable');
            that.$(".js-goods-btns").show();
            that.$("#userName").attr('disabled', true);
            that.$("#buyerName").attr('disabled', true);
            //that.$("#stateCd").prev().find("input").attr("readonly", true);
            that.saveType = 'edit';
		},

		//保存二手商品信息
		saveGoods: function(){
			var that = this;
            if(!$("goods_info_form").isValid()){
                return false;
            }
            var params = that.$("#goods_info_form").form('value');
            //params.itemImg = that.itemImagesPath;
            $.blockUI({
                message: '请稍后'
            });
            if(that.saveType == 'add'){
                goodsAction.addGoods(params, function(result){
                    $.unblockUI();
                    if(result && result.resultCode == '0'){
                        // 重命名 图片 名称
                        var goodsIdTmp = result.resultObject;
                        $("input[type='file']").each(function(){
                            var fileNameTmp = $(this).attr('name');
                            fileNameTmp = fileNameTmp.replace('tmp',goodsIdTmp);
                            $(this).attr('name',fileNameTmp);
                        });
                        //上传图片
                        that.uploadImage();

                        that.cancelGoods();
                        that.editStatus = false;
                        that.getGoodsList_();
                        fish.success('新增成功！',function(){});
                    }else{
                        fish.error('新增失败: '+result.resultMsg);
                    }
                });
            }else if(that.saveType == 'edit'){
                goodsAction.updateGoods(params, function(result){
                    $.unblockUI();
                    if(result && result.resultCode == '0'){
                        that.uploadImage();

                        that.editStatus = false;
                        that.cancelGoods();
                        that.getGoodsList_();
                        //that.$("#"+params.id).trigger('click');
                        fish.success('编辑成功！',function(){ });
                    }else{
                        fish.error('编辑失败: '+result.resultMsg);
                    }
                });
            }
		},

		//取消保存
		cancelGoods: function(){
			var that = this;
			$(".js-goods-btns").hide();
			$("#goods_info_form").form('disable');
			$("#goods_info_form").form('clear');
            $('#goods_info_form').resetValid();
            that.editStatus = false;
			CommonUtil.removeValiStyle(that.$('#goods_info_form'));
			//默认查看第一条信息详情
			$("#goodsListGrid table tr.jqgrow:first").trigger('click');
		},

		deleteGoods: function(){
			var that = this;
			var goodsId = that.$("#goodsListGrid").grid("getSelection").goodsId;
	        fish.confirm('确定删除该商品所有信息？', function(){
                var params = {};
                params.goodsId = goodsId;
                goodsAction.deleteGoods(params, function(result){
                    if(result && result.resultCode == '0'){
                        that.getGoodsList_();
                        fish.success("商品信息删除成功！");
                    }else{
                        fish.error("删除失败："+result.resultMsg);
                    };
                });
	       	});
		},

		//名称查询
		searchGoodsByName: function(){
			var that = this;
			if(that.isGoodsInfoEdit()){
			    return false;
			}
			that.getGoodsList_();
		},

		//带着图片路径进行图片显示界面
        showGoodsImg: function(){
            var that = this;
            var param = {
                itemImagesPath:that.itemImagesPath,
                itemId: that.$("#goodsId").val(),
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
                        that.$("#goodsImg").val(view.itemImagesPath);

                        view.addAllImage();
                        popup.close();
                    });
                }
            });
		},

        //上传图片到服务器
        uploadImage: function() {
            $("#itemImageForm").submit();
        }
	});
	return goodsView;

})
