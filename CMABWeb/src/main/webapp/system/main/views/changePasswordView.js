define(['hbs!../templates/changePassword.html',
        '../actions/changePasswordAction'],
    function(template,chwAction){

        var chwPasswordView = fish.View.extend({
            el:false,
            template:template,
            staffCode:'',
            events: {
                "click #confirmBtn": "onConfirm",
                "click #cancelBtn": "onCancel"
            },
            initialize: function () {

            },

            render: function () {
                this.$el.html(this.template());
            },

            afterRender: function () {

            },

            onConfirm : function () {
                var that = this;
                if(that.$('#updatePwdForm').isValid()){
                    var loginInfo = portal.appGlobal.get('loginInfo');
                    var params = {};
                    var oldPassword = $('#oldPassword').val();
                    var newPassword = $('#newPassword').val();
                    params.pwd=oldPassword;
                    params.newPwd = newPassword;
                    params.adminId = loginInfo.adminId;
                    chwAction.confirmPassword(params,function(result) {
                        var rstCode = result.resultCode;
                        if (rstCode == '-1') {
                            fish.error(result.resultMsg);
                        } else {
                            fish.info("操作成功");
                            that.popup.close();
                        }
                    });
                }else{
                    fish.error("[新密码]跟[确认新密码]不一致，请重新输入");
                }
            },
            onCancel : function () {
                this.popup.dismiss('close');
            }

        });
        return chwPasswordView;
    })