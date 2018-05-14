define(function(){
	var CommonUtil = {
		//去除校验样式
		removeValiStyle:function(dom){
			$dom = $(dom);
			$dom.find('.has-error').each(function(e){
				$(this).removeAttr('title').removeClass('has-error');
				$(this).find('div').unbind();
			});
		}
	};
	return CommonUtil;
});