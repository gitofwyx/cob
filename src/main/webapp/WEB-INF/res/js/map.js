$(window).ready(function(){

});

var myGeo = new BMap.Geocoder();
var map = new BMap.Map("map");

//地图展示
function showMap() {
    map.centerAndZoom(new BMap.Point("110.228144", "19.952145"), 11);//以哪个点位中心,
    map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
    map.enableScrollWheelZoom(true);//鼠标缩放
}

function add_overlay(point){
    marker=null;
    if(!isNull(point)){
        marker = new BMap.Marker(point);
    }else{
        marker = new BMap.Marker(new BMap.Point( $("input[name='coordinate_x']").val(),$("input[name='coordinate_y']").val()));  // 创建标注
    }
    map.addOverlay(marker);               // 将标注添加到地图中
    //marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
}



//展示
function show_point(point,label) {
    var style={                                   //给label设置样式，任意的CSS都是可以的
        color:"red",                   //颜色
        fontSize:"14px",               //字号
        border:"30px",                    //边
        height:"32px",                //高度
        width:"35px",                 //宽
        textAlign:"center",            //文字水平居中显示
        lineHeight:"70px",            //行高，文字垂直居中显示
        background:"url(img/dq_2.gif)",    //背景图片
        cursor:"pointer"
    };
    var opts = {
        width : 50,     // 信息窗口宽度
        height: 25,     // 信息窗口高度
        //title : "海底捞王府井店" , // 信息窗口标题
        //enableMessage:true,//设置允许信息窗发送短息
        //message:"亲耐滴，晚上一起吃个饭吧？戳下面的链接看下地址喔~"
    }

    if(isNull(point)){
        point = new BMap.Point($("input[name='coordinate_x']").val(), $("input[name='coordinate_y']").val());
    }
    if(isNull(label)){
        label='';

    }

    //map.centerAndZoom(point, 16);
    var myLabel = new BMap.Label("",     //为lable填写内容
        {offset:new BMap.Size(-15,-20),                  //label的偏移量，为了让label的中心显示在点上
            position:point});                                //label的位置
    myLabel.setTitle("坐标:"+ $("input[name='coordinate_x']").val()+" "+$("input[name='coordinate_y']").val());  //为label添加鼠标提示
    myLabel.setStyle(style);
    map.addOverlay(myLabel);                             //把label添加到地图上
    var infoWindow = new BMap.InfoWindow(label, opts);  // 创建信息窗口对象
    myLabel.addEventListener("click", function(){
        map.openInfoWindow(infoWindow,point); //开启信息窗口
    });
    myLabel.addEventListener("mouseover", function(){
        map.openInfoWindow(infoWindow,point); //开启信息窗口
    });
    myLabel.addEventListener("mouseout", function(){

        map.hideInfoWindow();
    });
}

//显示信息窗口，显示标注点的信息。
function showInfo(thisMaker){
    var sContent ="123";
    var infoWindow = new BMap.InfoWindow(sContent);  // 创建信息窗口对象
    thisMaker.openInfoWindow(infoWindow);   //图片加载完毕重绘infowindow
}


$(function () {
    showMap();

    //地图添加标注
    $("#createLabel").bind("click", function () {
        add_overlay(null);
    });
});
