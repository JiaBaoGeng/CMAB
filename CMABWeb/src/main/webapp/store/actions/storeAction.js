define(function(){

    var storeAction = {

        getStoreList : function(params, success){
            var result = fish.post("backend:StoreController/getStoreList", params, success);
            return result;
        },

        getStoreDetailById: function(params, success){
            var result = fish.post("backend:StoreController/getStoreDetailById", params, success);
            return result;
        },

        addStore : function(params, success){
            var result = fish.post("backend:StoreController/addStore", params, success);
            return result;
        },

        updateStore : function(params, success){
             var result = fish.post("backend:StoreController/updateStore", params, success);
             return result;
         },

        deleteStore: function(params, success){
             var result = fish.post("backend:StoreController/deleteStore", params, success);
             return result;
         },

    }

    return storeAction;

});
