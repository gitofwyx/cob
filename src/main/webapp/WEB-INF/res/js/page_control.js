var model=null;
var pageNow=null;
var class_tag='.';
//按钮控制
// page:当前页码；this_class：页码对应的class;
function btcontrol(page,this_class){
    class_tag='.'+this_class;
    //alert($(class_tag).attr('class'));
    if(this_class.indexOf('default')>=0){
        return;
    }
    if(!isNull(page)&&page>=3){
        $(class_tag).removeClass("default");
        $(class_tag).eq(2).addClass("default");
        $("#page_1").text(parseInt(page)-2);
        $("#page_2").text(parseInt(page)-1);
        $("#page_3").text(parseInt(page));
        $("#page_4").text(parseInt(page)+1);
        $("#page_5").text(parseInt(page)+2);

    }else if(!isNull(page)&&page==2){
        $(class_tag).removeClass("default");
        $(class_tag).eq(1).addClass("default");
        page='index';
    }else if(!isNull(page)&&page==1){
        $(class_tag).removeClass("default");
        $(class_tag).eq(0).addClass("default");
        page='index';
    }
    if(!isNull(page)&&page=='index'){
        $("#page_1").text(1);
        $("#page_2").text(2);
        $("#page_3").text(3);
        $("#page_4").text(4);
        $("#page_5").text(5);
    }
}
//单页点击调用函数  page_click:获取点击的页码
var listahover=function (page_click,count) {
    if(page_click<1){
        return;
    }
    var data={
        model:model,
        page:page_click+'/'+count,
    }
    postAjax("/pageObject",data,function(data){
        if(data!=null){
            $('#obj_table').html(data);
            pageNow=page_click;
            //$('.bt').bind('click',page_bt);
            //$('.listahover').bind('click',listahover);
            //btcontrol($(data).find('#pageNow').val());//********获取不到可能是freemarker 通过if语句判断没有加载该class或id的页面标签**********
            //$('#page_size').val()
        }
    });
};
//翻页控制
var page_bt=function(this_id,count){
    var page=null;
    if(isNull(pageNow)){
        page=1;
    }else{
        page=parseInt(pageNow);
    }
    if(this_id=='last_page'){
        page=page-1;
    }
    else if(this_id=='next_page'){
        page=page+1;
    }
    if(page<1){
        return;
    }
    var pageNum={
        model:model,
        page:page+'/'+count,
    }
    postAjax("/pageObject",pageNum,function(data){
        if(data!=null){
            $('#obj_table').html(data);
            pageNow=$('#pageNow').val();//通过返回的数据获取当前页码实时更新
            //$('.bt').bind('click',page_bt);
            //$('.listahover').bind('click',listahover);
        }
    });
};

function listRequest(id,count){
    model=id;
    var data={
        model:model,
        page:'1/'+count,
    }
    if(!isNull(model)){
        postAjax("/pageObject",data,function(data){
            $('#obj_table').html(data);
        });
    }
}