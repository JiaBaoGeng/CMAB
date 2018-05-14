define(function () {

    var chw =  {
        confirmPassword: function (params, success) {
           var result = fish.post('AdminController/changePassword', params, success);
           return result;
        }
    };

    return chw;
});