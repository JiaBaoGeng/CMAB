/**
 * 系统全局js
 * whm
 * 2016-3-1
 */
$(function () {
    initRadioSelect();
    initMultipleCheck('#multipleSelect');
    initMultipleCheck('#multipleSelectRows');
    initChecked();
    tabs(".nav-tabs", ".tabs-content");
    getTitleTab('.titleList');
    getConWrap();
    menu(".page-container", ".page-sidebar-menu", ".sub-menu");
});

//可变形页签组件
function tabs(t, e) {
    //切换页签
    var lis = $(t).find(">li");
    lis.click(function() {
        var li_index = $(this).parent().find(">li").index($(this)[0]);
        $(this).parent().find(">li.active").removeClass("active");
        $(this).addClass("active");
        var target = $(e).find(".tab-pane");
        $(e).find(".tab-pane.active").removeClass("active");
        target.eq(li_index).addClass("active");
    });
    //删除页签
    var close = $(t).find("li>i");
    close.click(function() {
        //判断删除的是否是当前页签
        if($(this).parent().hasClass(".active")){
            $(t).find("li.active").remove();
            $(e).find(".tab_pane.active").remove();
            $(t).find(">li").eq(0).addClass("active");
            $(e).find(">.tab_pane").eq(0).addClass("active");
        }
        $(this).parent().remove();
    });
}

//风险监控滑动
function slideMonitor(){

    $(".monitor-switch").find("a").click(function(){
        var pre = $(".monitor-switch a.active").index();
        $(".monitor-switch a").removeClass('active');
        $(this).addClass("active");
        $(".monitor-graph").find(".monitor-pane").css("zIndex",0).eq(pre).css("zIndex",1).css('left','100%').animate({left:'0'},500);
    })

}



function borderChange(){
    $(".adjust-border").find(">li").each(function(i) {
        $(this).click(function(){
            var ul=$(this).parents(".adjust-border");
            ul.find(">li").removeClass("border-left-color");
            ul.find("li:nth-child("+(i+2)+")").addClass("border-left-color");
            adaptWidth(true);
        });
    });
}


function btnSms(){
    $(".btn-oper").find("span").click(function(){
        $(this).toggleClass("active");
    })
}

//左侧菜单可变形组件
function menu(a,b,c){
    $(a).find(".switch").click(function() {
        $(a).toggleClass("page-sidebar-closed");
        $(b).find(">li.open").removeClass("open");
    });
    $(".page-sidebar-menu li").children('a').not(':has(i)').click(function() {
        if ($(a).hasClass("page-sidebar-closed")) {
            $(".page-sidebar-menu").find(">li.open").removeClass("open");
        }
    });

    $(c).parent().children('a').click(function(){
        if($(this).parent().hasClass('open')){
            $(this).parent().removeClass('open');
            $(this).next().children('li').removeClass('open');
        }else{
            $(this).parent().siblings().removeClass('open');
            $(this).parent().siblings().children().find('li').removeClass('open');
            $(this).parent().addClass('open');
        }
    });
}
/*调整iframe高度*/
function setIframeHeight(id) {
    var aditionHeight = 20;
    try {
        var iframe = typeof id=='string'?document.getElementById(id):id;
        if (iframe.attachEvent) {
            var resizeFun = function() {
                $('#tabFrame').contents().find('body').css('backgroundColor', 'transparent');
                var height = 400;
                if (iframe.contentDocument && height < iframe.contentDocument.body.scrollHeight + aditionHeight)
                    height = iframe.contentDocument.body.scrollHeight + aditionHeight;
                if (height < iframe.contentWindow.document.documentElement.scrollHeight + aditionHeight)
                    height = iframe.contentWindow.document.documentElement.scrollHeight + aditionHeight

                iframe.height = height; //iframe.contentWindow.document.documentElement.scrollHeight + 40;
            }
            iframe.attachEvent(
                "onload", resizeFun
            );
            resizeFun();
            return;
        } else {
            iframe.onload = function() {
                $('#tabFrame').contents().find('body').css('backgroundColor', 'transparent');
                iframe.height = iframe.contentDocument.body.scrollHeight + aditionHeight;
            };
            iframe.onload();
            return;
        }
    } catch (e) {
        throw new Error('setIframeHeight Error');
    }
}

function rowChange(){
    $(".index-tab-2").find("th").each(function(i) {
        $(this).click(function(){
            var tab=$(this).parents(".index-tab-2");
            tab.find("th").removeClass("th-active");
            $(this).addClass("th-active");
            var index=i%12+1;
            tab.find("td").removeClass("td-active");
            tab.find("td:nth-child("+index+")").addClass("td-active");
        });
    });

}

//单选下拉框
function initRadioSelect(){
    var id = '.zte-select-radio';
    if($(id).length>0){
        $(id +' .zte-select-result').children().click(function(){
            $(this).parents(' .zte-select-result').addClass('on');
            $(this).parents(id+'.selectCity').toggleClass('open');
            //选择省市未取值时
            if($(this).parents('.selectCity').find( 'input').val()=='选择省市'){
                $(this).parents('.selectCity').find( '.city').hide();
            }

            $(this).parents('.zte-select-radio').find('.zte-select-box').toggle();
            /*模拟nav*/
            $(this).parents('.zte-select-radio').find('.zte-container-fluid').toggle();

            if($(this).parents('.zte-select-radio').find('> .zte-select-box').is(':visible')){
                $(this).parent().find('.zte-btn-select').addClass('open');
                $(this).parent().find('.zte-inp-select').addClass('open');

                $(this).parents('.zte-select-radio').find('.borderBottom').show();
            }
            if($(this).parents('.zte-select-radio').find('> .zte-container-fluid').is(':visible')){
                $(this).parent().find('.zte-btn-select').addClass('open');
                $(this).parent().find('.zte-inp-select').addClass('open');

                $(this).parents('.zte-select-radio').find('.borderBottom').show();
            }
        });
        if($('.inpDefault').find( 'input').val()!=""){
            $('.inpDefault').find( '.zte-input-hint').show();
        }
        $('.inpDefault').find( '.zte-input-hint').show();
        $(id+ ' li:not(".area")').click(function(){
            var txt = $(this).text();
            $(this).siblings().removeClass('selected');
            $(this).addClass('selected');
            $(this).parents('.zte-select-radio').find( 'input').val(txt);
            if($(this).parents('.inpDefault').find( 'input').val()!=""){
                $(this).parents('.inpDefault').find( '.zte-input-hint').hide();
            }
            /* 选择省市 */
            if($(this).parents('.zte-select-radio').hasClass('selectCity')){
                $(this).parents('.zte-container-fluid').find( '.cur.area').text(txt);
                $(this).parents('.zte-container-fluid').find( '.city').show();
                var area = $(this).parents('.zte-container-fluid').find( '.area').text();
//              $(this).parents('.zte-select-radio').find( 'input').val(area);
            }
        });
        $(".zte-select-radio").hover(function(){},function(){
            $(this).children('.zte-select-box').hide();
            $(this).find('.zte-btn-select').removeClass('open');
            $(this).find('.zte-inp-select').removeClass('open');
            $(this).find('.zte-select-result').removeClass('on');

            $(this).children('.zte-container-fluid').hide();
        });

        $(window).click(function(e){
            e = window.e||e;
            var obj = $(e.srcElement|| e.target);
            if($(id+  ' .zte-select-result').find(obj).length==0){
                $(id+ ' .zte-select-box').hide();
                $(id+ ' .zte-select-result').removeClass('on');
            }

        });
    }
}
//多选下拉框
function initMultipleCheck(id) {
    //id = '#multipleSelect';
    if($(id).length>0){
        $(id+' .zte-select-result').children().click(function () {
            $(id+ ' .zte-select-result').addClass('on');
            $(id).find('.zte-select-box').toggle();
            if($(id).find('.zte-select-box').is(':visible')){
                $(id +' .zte-select-result').children('.zte-btn-select').addClass('open');
            }else{
                $(id +' .zte-select-result').children('.zte-btn-select').removeClass('open');
            }
        });
        $(".zte-select-multi").hover(function(){},function(){
            $(this).children('.zte-select-box').hide();
            $(id +' .zte-select-result').children('.zte-btn-select').removeClass('open');
            $(this).find('.zte-select-result').removeClass('on');
        });
        $(window).click(function(e){
            e = window.e||e;
            var obj = $(e.srcElement|| e.target);
            if($(id).find(obj).length==0){
                $(id +' .zte-select-box').hide();
                $(id+ ' .zte-select-result').removeClass('on');
            }
        });

        $(id).find('.zte-select-box .selectAll').change(function () {
            if ($(this).is(':checked')){
                $(id).find('.zte-select-box input').prop({checked:true});//.attr('checked', true);
                $(id).find('.zte-select-box li').addClass('selected');
            }
            else{
                $(id).find('.zte-select-box input').removeAttr('checked');
                $(id).find('.zte-select-box li').removeClass('selected');
            }
            $(id)[0].refresh();

        });

        $(id).find('.zte-select-box input:gt(0)').change(function () {
            $(this).parent('li').toggleClass('selected');
            var sel = $(id)[0].getSelected();
            if(sel.length >= $(this).parents('ul:eq(0)').children().length-1){
                $(id).find('.zte-select-box input:eq(0)').prop({checked:true});
                $(id).find('.zte-select-box li:eq(0)').addClass('selected');
            }else{
                $(id).find('.zte-select-box input:eq(0)').removeAttr('checked');
                $(id).find('.zte-select-box li:eq(0)').removeClass('selected');
            }
            $(id)[0].refresh();
        });

        $(id)[0].refresh = function () {
            var sel = $(id)[0].getSelected();
            var str = '';
            for (var i = 0; i < sel.length; i++) {
                str += (i > 0 ? ', ' : '') + sel[i].text;
            }
            $(this).find('.zte-select-result input:eq(0)').val(str);
        }
        $(id)[0].getSelected = function () {
            var arrSelected = [];
            $(this).find('.zte-select-box input:gt(0):checked').each(function () {
                arrSelected.push({
                    value: $(this).attr('value'),
                    text: $(this).next('label').text()
                });
            });
            return arrSelected;
        };

        if($(id).find('ul').hasClass('zte-ul-rows')){
            $(id).find('ul>li:last').prev().css('border-radius','0 0 0 4px')
        }
    }

}

//关闭按钮
function close(){
    $('.closed').click(function(){
        $(this).parents('.zte-pop-message').hide();
        $(this).parents('.zte-pop-box').hide();
    });
}

//导航链接子级菜单开合
function openSub(){
    $('.zte-nav-link li').click(function(){
        $(this).siblings().removeClass('open');
        $(this).siblings('li').find('a').removeClass('cur');
        $(this).addClass('open');
        $(this).children('a').addClass('cur');
    });
}

/* 表格全选 */
function initChecked(){
    $('.zte-table tr input[type="checkbox"]:gt(0)').click(function(){
        var total = $(this).parents('.zte-table').find('tr input[type="checkbox"]:gt(0)').length;
        var len =$(this).parents('.zte-table').find('tr input[type="checkbox"]:gt(0):checked').length;
        if(total==len){
            $(this).parents('.zte-table').find('tr input[type="checkbox"]:eq(0)')[0].checked='checked';
        }else{
            $(this).parents('.zte-table').find('tr input[type="checkbox"]:eq(0)')[0].checkedd='';
        }
    });
    $('.zte-table tr input[type="checkbox"]:eq(0)').click(function(){
        if($(this).is(':checked')){
            $(this).parents('.zte-table').find('tr input[type="checkbox"]:gt(0)').each(function(){
                this.checked='checked';
            });
        }else{
            $(this).parents('.zte-table').find('tr input[type="checkbox"]:gt(0)').attr('checked',false);
        }
    });
}

//查看环节中申请单详情
function getFormInfo(){
    $('.zte-progress-bar li').hover(function(){
        $('#auditInfo').appendTo('body').show()
            .css('left',$(this).offset().left-$(this).outerWidth()/2+5)
            .css('top',$(this).offset().top-$('#auditInfo').outerHeight()+30);
    });
    $(window).click(function(e){
        e = window.e||e;
        var obj = $(e.srcElement|| e.target);
        if($('.zte-progress-bar li').find(obj).length==0){
            $('#auditInfo').hide();
        }
    });
    //点击提交按钮后，隐藏弹出框
    $(".zte-audit-info button").click(function(){
        $(this).parents('.zte-audit-info').hide();
    });
}
//左侧菜单可变形组件
function menu(a,b,c){

    $(a).find(".switch").click(function() {
        $(a).toggleClass("page-sidebar-closed");
        $(b).find(">li.open").removeClass("open");
        if ($(a).hasClass("page-sidebar-closed")) {
            $(a).find('.page-sidebar').removeAttr('id');
            /*$(".page-sidebar-menu li").children('a.active').parents('li').find('>a').addClass('active');*/
        }else{
            $(a).find('.page-sidebar').addAttr('id','sidebarScroll');
        }
    });
    $(".page-sidebar-menu li").children('a').not(':has(i)').click(function() {
        $(".page-sidebar-menu li").children('a').removeClass("active");
        $(this).addClass("active");
        $(this).parents('.page-sidebar-menu').find('>li.open>a').addClass('active');
        if ($(a).hasClass("page-sidebar-closed")) {
            $(".page-sidebar-menu").find(">li.open").removeClass("open");
        }
    });

    $(c).parent().children('a').click(function(){
        if($(this).parent().hasClass('open')){
            $(this).parent().removeClass('open');
            $(this).next().children('li').removeClass('open');
        }else{
            $(this).parent().siblings().removeClass('open');
            $(this).parent().siblings().children().find('li').removeClass('open');
            $(this).parent().addClass('open');
        }
    });
}

//列表题目分类选中
function getTitleTab(id){
    $(id+ ' li').not('li.line').click(function(){
        $(this).parent().find('li.cur').removeClass('cur');
        $(this).addClass('cur');

    });
}

//按钮获取下拉内容
function getConWrap(){
    $('.searchSenior').click(function() {
        var t = $(this).parents('.zte-search-condition').children('.conWrap');
        t.toggle();
        if (t.is(':visible')) {
            $(this).children('i').addClass('open');
        } else {
            $(this).children('i').removeClass('open');
        }
        $(this).parents('.zte-search-condition').toggleClass('open');
    });
    $('.search-checkbox>li').not(':first-child').click(function(){
        $(this).parent().find('span.cur').removeClass('cur');
        $(this).children('span').addClass('cur');
    });
}

//区域选择下拉框
function showRegion(region_id){
    $('.selectRegion a.cur').removeClass('cur');
    $(this).addClass('cur');
}

function showRegionCity(region_id){
    $('.selectRegionCity a.cur').removeClass('cur');
    $(this).addClass('cur');
}

//省市选择
function initSelectArea() {
    var id = '.zte-select-area';
    if ($(id).length > 0) {
        $(id + ' .zte-select-result').children().click(function () {
            $(this).parents(id).toggleClass('open');
            //选择省市未取值时
            if ($(this).parents('.selectCity').find('input').val() == "" || $(this).parents('.selectCity').find('input').val() == '选择省市') {
                $(this).parents('.selectCity').find('.zte-area-title:gt(0)').addClass('zte-area-title-hide').removeClass('cur');
                $(this).parents('.selectCity').find('.zte-area-title').eq(0).addClass('cur');
                $(this).parents('.selectCity').find('.zte-area-title').text('请选择');
                $(this).parents('.selectCity').find('.zte-tab-pane').removeClass('cur').eq(0).addClass('cur');
                $(this).parents('.selectCity').find('.zte-tab-pane li').removeClass('selected');
            }

            if ($(this).parents('.inpDefault').find('input').val() == "") {
                $(this).parents('.inpDefault').find('.zte-input-hint').show();
            }
            $(id + ' li:not(".zte-area-title")').click(function () {
                var txt = $(this).text();
                $(this).siblings().removeClass('selected');
                $(this).addClass('selected');
                /* 选择省市 */
                if ($(this).parents('.zte-select-area').hasClass('selectCity')) {
                    $(this).parents('.zte-container-fluid').find('.cur.zte-area-title').text(txt);
                    var title_cur = $(this).parents('.zte-container-fluid').find('.zte-area-title.cur');
                    if (title_cur.next().val() != null && title_cur.next() != " ") {
                        title_cur.removeClass('cur');
                        title_cur.next().removeClass('zte-area-title-hide').addClass('cur');
                        $(this).parents('.zte-tab-pane').removeClass('cur').next().addClass('cur');
                    } else {
                        var area = $(this).parents('.zte-container-fluid').find('.zte-area-title').text();
                        $(this).parents('.zte-select-area').find('input').val(area);
                        if ($(this).parents('.inpDefault').find('input').val() != "") {
                            $(this).parents('.inpDefault').find('.zte-input-hint').hide();
                        }
                        $(this).parents(id).removeClass('open');
                    }
                }

                /*  选择区县  */
                if ($(this).parents('.zte-select-area').hasClass('selectCounty')) {
                    if($(this).parents('.zte-city-select-box').next().val() == null){
                        var selected = $(this).parents('.zte-container-fluid').find('li>a.cur').text();
                        $(this).parents('.zte-select-area').find('input').val(selected);
                        if ($(this).parents('.inpDefault').find('input').val() != "") {
                            $(this).parents('.inpDefault').find('.zte-input-hint').hide();
                        }
                        $(this).parents(id).removeClass('open');
                    }
                }
            });
            $(".zte-select-area").hover(function () {
            }, function () {
                $(this).children('.zte-select-box').hide();
                $(this).find('.zte-btn-select').removeClass('open');
                $(this).find('.zte-inp-select').removeClass('open');
                //$(this).find('.zte-select-result').removeClass('on');

                $(this).children('.zte-container-fluid').hide();
            });

            $(window).click(function (e) {
                e = window.e || e;
                var obj = $(e.srcElement || e.target);
                if ($(id).find(obj).length == 0) {
                    $(id).removeClass('open');
                    $(id + ' .zte-select-result').removeClass('on');
                }

            });
        });
    }
}


