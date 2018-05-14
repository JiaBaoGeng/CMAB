define([ 'hbs!../templates/storeImage.html',
         '../../system/actions/SysUtilAction',
         '../../system/common/CommonUtil'

        ],
		function(temp, SysUtilAction, CommonUtil) {
	var storeImageView = fish.View.extend({
		el : false,
		template : temp,
        $imageContainer: '',
		storeImagesPath: '',
        storeId: '',
        storeImgOrder: 1,
        selectedImagePath: '',

		events : {
            'click #findStoreImage':'findStoreImage',
		},

		initialize : function(param) {
            this.storeImagesPath = param.storeImagesPath;
            this.storeId = param.storeId;
		},

		render: function () {
            this.$el.html(this.template());
        },

		afterRender : function() {
			var that = this;
			that.$imageContainer = $("#image-container");
            if(that.storeId == null || that.storeId == ''){
                that.storeId = 'tmp';
            }
            that.showImages();
            that.appendImageFile();
		},

        // 处理路径数组，显示图画
        showImages:function(){
            var that = this;
            if(that.storeImagesPath != '' && that.storeImagesPath != null){
                var images = that.storeImagesPath.split(";");
                that.storeImgOrder = images.length + 1;
                $.each(images, function(index, val){
                    var html = '<div class="item-image-container">'
                            + '<image class="item-image" src="'+ '/images/'+ val + '"></image>'
                            + '</div>';
                   $("#addImageCon").before(html);
                });
            }

        },

        //上传图片文件
        findStoreImage: function(){
            var that= this;
            var $lastInput = $("#storeImageForm").children().last();
            $lastInput.click();
            $lastInput.change(function(){
              var file = this.files[0];
              //console.log(file);
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
            var spliter =  that.storeImagesPath == '' ? "" : ";";
            that.storeImagesPath = that.storeImagesPath + spliter + that.selectedImagePath;
        },

        //为form 表单文件 插入新的input file 元素
        appendImageFile: function(){
            var that = this;
            that.selectedImagePath = 'store/'+ that.storeId+ '_' +that.storeImgOrder;
            that.storeImgOrder = that.storeImgOrder + 1;
            var html = '<input type="file" name="'+ that.selectedImagePath
                + '" accept=".png,.gif,.jp2,.jpe,.jpeg,.jpg,.svf"/>';
            $("#storeImageForm").append(html);
        }
	});
	return storeImageView;

})
