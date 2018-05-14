define(function(){

    var goodsAction = {

        getGoodsList : function(params, success){
            var result = fish.post("backend:UsedGoodsController/getGoodsList", params, success);
            return result;
        },

        getGoodsDetail: function(params, success){
            var result = fish.post("backend:UsedGoodsController/getUsedGoodsDetailById", params, success);
            return result;
        },

        addGoods : function(params, success){
            var result = fish.post("backend:UsedGoodsController/addUsedGoods", params, success);
            return result;
        },

        updateGoods : function(params, success){
             var result = fish.post("backend:UsedGoodsController/updateUsedGoods", params, success);
             return result;
         },

        deleteGoods: function(params, success){
             var result = fish.post("backend:UsedGoodsController/deleteUsedGoods", params, success);
             return result;
         },

    }

    return goodsAction;

});
