define(function(){

    var usersAction = {

        getUsersList : function(params, success){
            var result = fish.post("backend:UsersController/getUsersList", params, success);
            return result;
        },

        getUsersDetail : function(params, success){
            var result = fish.post("backend:UsersController/getUsersDetailById", params, success);
            return result;
        },

        addUsers : function(params, success){
            var result = fish.post("backend:UsersController/addUsers", params, success);
            return result;
        },

        updateUsers : function(params, success){
             var result = fish.post("backend:UsersController/updateUsers", params, success);
             return result;
         },

        deleteUsers: function(params, success){
             var result = fish.post("backend:UsersController/deleteUsers", params, success);
             return result;
         },

    }

    return usersAction;

});
