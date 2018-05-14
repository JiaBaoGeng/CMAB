define(function(){

    var businessAction = {

        getBusinessList : function(params, success){
            var result = fish.post("backend:BusinessController/getBusinessList", params, success);
            return result;
        },

        addBusiness : function(params, success){
            var result = fish.post("backend:BusinessController/addBusiness", params, success);
            return result;
        },

        updateBusiness : function(params, success){
             var result = fish.post("backend:BusinessController/updateBusiness", params, success);
             return result;
         },

        deleteBusiness: function(params, success){
             var result = fish.post("backend:BusinessController/deleteBusiness", params, success);
             return result;
         },

    }

    return businessAction;

});
