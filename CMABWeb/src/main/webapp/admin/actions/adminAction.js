define(function(){

    var adminAction = {

        getAdminList : function(params, success){
            var result = fish.post("backend:AdminController/getItemList", params, success);
            return result;
        },

        addAdmin : function(params, success){
            var result = fish.post("backend:AdminController/addItem", params, success);
            return result;
        },

        updateAdmin : function(params, success){
             var result = fish.post("cmabbackend:AdminController/updateItem", params, success);
             return result;
         },

        deleteAdmin: function(params, success){
             var result = fish.post("cmabbackend:AdminController/deleteItem", params, success);
             return result;
         },

    }

    return adminAction;

});
