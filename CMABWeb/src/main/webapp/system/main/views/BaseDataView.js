define(['hbs!../templates/BaseDataView.html',
        '../actions/MainAction'],
    function(template,ActivityAction){
        var mktEvtListView = fish.View.extend({
            el:false,
            template:template,
            staffCode:'',
            events: {

            },
            initialize: function () {

            },

            render: function () {
                this.$el.html(this.template());
            },

            afterRender: function () {
                var that = this;
                // 初始化个人信息
                that.initBaseInfo();
            },

            // 初始化个人信息
            initBaseInfo: function(){
                var that = this;
                var loginInfo = portal.appGlobal.get('loginInfo');
                $.blockUI();
                ActivityAction.qryUserInfo(loginInfo.staffInfo,function(result){
                    $.unblockUI();
                    $(".userinfol").attr('disabled','disabled');
                    if(result.resultCode == '0'){
                        $('.userinfol').each(function(){
                            $(this).val(result.resultObject[$(this).attr('name')]);
                        });
                        that.staffCode = result.resultObject['staffCode'];
                    }
                    else{
                        fish.info(result.resultMsg);
                    }
                    if($('#sex').val()=='M'){
                        $('#sex').val('男');
                    }else{
                        $('#sex').val('女');
                    }
                    if($('#isManager').val()=='1'){
                        $('#isManager').val('是');
                    }else{
                        $('#isManager').val('否');
                    }
                });
            },

        });
        return mktEvtListView;
    })