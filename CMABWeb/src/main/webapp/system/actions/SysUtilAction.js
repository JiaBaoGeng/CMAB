define(function () {

    var SysUtilAction =  {

    	//定义portal.appGlobal的key值
    	keys:{
    		menuView:"menuView",
    		coorsystem_privilege:"coorsystem_privilege",
    		coorsystem_orgId:"coorsystem_orgId",
    	    coorsystem_orgName:"coorsystem_orgName",
    	    coorsystem_regionId:"coorsystem_regionId",
    	    coorsystem_regionName:"coorsystem_regionName",
    	    coorsystem_openMenuId:"coorsystem_openMenuId"
    	},
		// 获取静态数据【工具类】同步加载
		qryDcSqlStaticValSync: function(params, success){
			var result = fish.postSync('manager:DcSystemConfigListController/getDcSystemConfigListByType', params, success);
			return result;
		},
		// 获取静态数据【工具类】异步加载
		qryDcSqlStaticVal: function(params, success){
			var result = fish.post('cmabbackend::DcSystemConfigListController/getDcSystemConfigListByType', params, success);
			return result;
		},
		// 功能组件权限控制
		qryCompByLoginInfoSync : function(params,that, success) {
			var funcComps = {};
			var loginInfo = portal.appGlobal.get('loginInfo');
			params.postId = loginInfo.userInfo.postId;
			var result = fish.postSync('manager:PrivilegeController/qryCompByLoginInfo',params,success);
			if(result && result.resultCode==0){
				if(result.resultObject && result.resultObject){
					funcComps = result.resultObject;
					for(var i in funcComps){
						that.$("."+funcComps[i].urlAddr).each(function(){
							if(funcComps[i].isRelPriv == 'Y'){
								$(this).show();
							}else if(funcComps[i].isRelPriv == 'N'){
								$(this).hide();
							}
						});
					}
				}
			}
			return result;
		},
		////////////////////////////用到的方法









        // 批量获取静态数据【工具类】同步加载
        qryDcSqlStaticValSyncForBatch: function(params, success){
            var result = fish.postSync('coorsystem/DcSqlController/qryDcSqlStaticValForBatch', params, success);
            return result;
        },
        
        // 根据code和value获取name,异步加载
        qryDcValName: function(params, success){
        	var result = fish.post('coorsystem/DcSqlController/qryDcValName', params, success);
            return result;
        },

	    // 翻译所有状态静态数据
    	transStatusCd: function(statusCd){
    		var params = {};
    		params.statusCdClass = "";
    		params.statusCdName = "";
    		if(statusCd == "1000"){
    			params.statusCdClass = "status-1";
    			params.statusCdName = "有效";
    		}else if(statusCd == "1100"){
    			params.statusCdClass = "status-2";
    			params.statusCdName = "无效";
    		}else{
    			params.statusCdClass = "status-6";
    			params.statusCdName = "未生效";
    		}
    		return params;
    	},

        // 获取指定表名和列名的下一个序列号【工具类】同步加载
        getSequenceNumberSync: function(params, success){
            var result = fish.postSync('coorsystem/DcSqlController/getSequenceNumber', params, success);
            return result;
        },

        // 获取指定表名和列名的下一个序列号【工具类】异步加载
        getSequenceNumber: function(params, success){
            var result = fish.post('coorsystem/DcSqlController/getSequenceNumber', params, success);
            return result;
        },
        


    };

    return SysUtilAction;
});