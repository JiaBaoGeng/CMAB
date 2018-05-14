define(function () {

    var MainAction =  {


        //加载菜单
        getCommonMenus: function (params, success) {
            var result = fish.post('backend:ModuleController/getModulesByAdmin', params, success);
            return result;
        },

        //添加常用菜单
        addCommonMenus: function(params, success) {
            var result = fish.post('backend:ZmgrFuncMenuController/addCommonMenus', params, success);
            return result;
        },
        //查询post对应的URL
        qrySystemPost:function(params,success){
            var result = fish.post('backend:SystemPostController/qrySystemPost',params,success);
            return result;
        },

    };

    return MainAction;
});