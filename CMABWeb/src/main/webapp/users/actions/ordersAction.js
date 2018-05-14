define(function(){

    var ordersAction = {

        getOrdersList : function(params, success){
            var result = fish.post("backend:OrdersController/getOrdersList", params, success);
            return result;
        },

        getOrdersDetail: function(params, success){
            var result = fish.post("backend:OrdersController/getOrdersDetailById", params, success);
            return result;
        },

        addOrders : function(params, success){
            var result = fish.post("backend:OrdersController/addOrders", params, success);
            return result;
        },

        updateOrders : function(params, success){
             var result = fish.post("backend:OrdersController/updateOrders", params, success);
             return result;
         },

        deleteOrders: function(params, success){
             var result = fish.post("backend:OrdersController/deleteOrders", params, success);
             return result;
         },

    }

    return ordersAction;

});
