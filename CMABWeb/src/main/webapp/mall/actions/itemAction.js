define(function(){

    var itemAction = {

        getItemList : function(params, success){
            var result = fish.post("backend:ItemController/getItemList", params, success);
            return result;
        },
        getItemDetail: function(params, success){
            var result = fish.post("backend:ItemController/getItemDetailByItemId", params, success);
            return result;
        },

        addItem : function(params, success){
            var result = fish.post("backend:ItemController/addItem", params, success);
            return result;
        },

        updateItem : function(params, success){
             var result = fish.post("backend:ItemController/updateItem", params, success);
             return result;
         },

        deleteItem: function(params, success){
             var result = fish.post("backend:ItemController/deleteItem", params, success);
             return result;
         },

    }

    return itemAction;

});
