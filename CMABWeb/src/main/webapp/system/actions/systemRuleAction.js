define(function(){

    var systemRuleAction = {

        getSystemRule : function(params, success){
            var result = fish.post("backend:SystemRuleController/getSystemRuleByType", params, success);
            return result;
        },

        addSystemRule : function(params, success){
            var result = fish.post("backend:SystemRuleController/addSystemRule", params, success);
            return result;
        },

        updateSystemRule : function(params, success){
             var result = fish.post("backend:SystemRuleController/updateSystemRule", params, success);
             return result;
         },

        deleteSystemRule: function(params, success){
             var result = fish.post("backend:SystemRuleController/deleteSystemRule", params, success);
             return result;
         },

    }

    return systemRuleAction;

});
