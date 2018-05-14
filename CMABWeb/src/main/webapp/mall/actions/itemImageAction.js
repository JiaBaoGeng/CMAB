define(function(){

    var itemImageAction = {

        getItemList : function(params, success){
            var result = fish.post("ItemController/getItemList", params, success);
            return result;
        },

        addItem : function(params, success){
            var result = fish.post("ItemController/addItem", params, success);
            return result;
        },

        updateItem : function(params, success){
             var result = fish.post("ItemController/updateItem", params, success);
             return result;
         },

        deleteItem: function(params, success){
             var result = fish.post("ItemController/deleteItem", params, success);
             return result;
         },

    }

    return itemImageAction;

});
