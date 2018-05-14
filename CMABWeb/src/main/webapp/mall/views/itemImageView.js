define([ 'hbs!../templates/itemImage.html',
         '../actions/itemImageAction',
         '../../system/actions/SysUtilAction',
         '../../system/common/CommonUtil'

        ],
		function(temp, itemImageAction, SysUtilAction, CommonUtil) {
	var itemImageView = fish.View.extend({
		el : false,
		template : temp,
        $imageContainer: '',
		itemImagesPath: '',
        itemId: '',
        itemImgOrder: 1,
        selectedImagePath: '',

		events : {
            'click #findImage':'findImage',
		},

		initialize : function(param) {
            this.itemImagesPath = param.itemImagesPath;
            this.itemId = param.itemId;
		},

		render: function () {
            this.$el.html(this.template());
        },

		afterRender : function() {
			var that = this;
			that.$imageContainer = $("#image-container");
            if(that.itemId == null || that.itemId == ''){
                that.itemId = 'tmp';
            }
            that.showImages();
            that.appendImageFile();

		},

        // 处理路径数组，显示图画
        showImages:function(){
            var that = this;
            if(that.itemImagesPath != ''){
                var images = that.itemImagesPath.split(";");
                that.itemImgOrder = images.length + 1;
                $.each(images, function(index, val){
                    var html = '<div class="item-image-container">'
                            + '<image class="item-image" src="'+ '/images/'+ val + '"></image>'
                            + '</div>';
                   $("#addImageCon").before(html);
                });
            }

        },

        //上传图片文件
        findImage: function(){
            var that= this;
            var $lastInput = $("#itemImageForm").children().last();
            $lastInput.click();
            $lastInput.change(function(){
              var file = this.files[0];
              console.log(file);
              //判断是否是图片类型
              var fileType = file.type;
              var pat=/^image/i;
              if(fileType.match(pat)){
                if (window.FileReader) {
                   var reader = new FileReader();
                   reader.readAsDataURL(file);
                   //监听文件读取结束后事件
                   reader.onloadend = function (e) {
                        //需保留 原图片文件的后缀
                        var fileNameArr = file.name.split(".");
                        var arrLength = fileNameArr.length;
                        var fileSuffix = fileNameArr[arrLength - 1];
                        that.selectedImagePath = that.selectedImagePath + "." + fileSuffix;

                        that.insertSelectedImage(e.target.result);//e.target.result就是最后的路径地址
                        //新加入一个 input file 之前， 将
                        $lastInput.attr("name",that.selectedImagePath);
                        that.appendImageFile();
                   };
                }
              }else{
                fish.warn("您选择的文件不是图片类型！请重新选择");
                $lastInput.val("");
                return false;
              }

            });
        },

        //将新选择的图像显示出来
        insertSelectedImage: function(src){
            var that = this;
            var html = '<div class="item-image-container">'
                     + '<image class="item-image" src="'+ src + '"></image>'
                     + '</div>';
            $("#addImageCon").before(html);
            var spliter =  that.itemImagesPath == '' ? "" : ";";
            that.itemImagesPath = that.itemImagesPath + spliter + that.selectedImagePath;
        },

        //为form 表单文件 插入新的input file 元素
        appendImageFile: function(){
            var that = this;
            that.selectedImagePath = 'item/'+ that.itemId+ '_' +that.itemImgOrder;
            that.itemImgOrder = that.itemImgOrder + 1;
            var html = '<input type="file" name="'+ that.selectedImagePath
                + '" accept=".png,.gif,.jp2,.jpe,.jpeg,.jpg,.svf"/>';
            $("#itemImageForm").append(html);
        },

        addAllImage:function(){
            var that = this;
            /*$('form').submit(function (event){
              event.preventDefault();
              var form = $(this);

              if (!form.hasClass('form-upload')) {
                //普通表单
                $.ajax({
                  type: form.attr('method'),
                  url: form.attr('action'),
                  data: form.serialize()
                }).success(function () {
                  //成功提交
                }).fail(function (jqXHR, textStatus, errorThrown) {
                  //错误信息
                });
              } else {
                // mulitipart form,如文件上传类
                var formData = new FormData(form);
                var fileTmp = $("input[type='file']").first();
                formData.append(fileTmp.attr("name"), fileTmp.attr("value"));
                $.ajax({
                  type: form.attr('method'),
                  url: form.attr('action'),
                  data: JSON.stringify(formData), //formData,
                  mimeType: "multipart/form-data",
                  contentType: false,
                  cache: false,
                  processData: false
                }).success(function () {
                  //成功提交
                }).fail(function (jqXHR, textStatus, errorThrown) {
                  //错误信息
                });
              };
            });
            $("#itemImageForm").submit();*/
        }

	});
	return itemImageView;

})
