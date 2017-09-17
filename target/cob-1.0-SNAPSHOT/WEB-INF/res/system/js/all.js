$(document).ready(function() {
	//头部样式变化
	$(".courtbcdoc-bottom>li").click(function() {
		$(this).addClass("active");
		$(this).siblings().removeClass("active");
	});
	$(".courtbcdoc-bottom>li").mousemove(function() {
		$(this).find("div").removeClass("dyn").addClass("dyb");
	});
	$(".courtwzq-drop").mousemove(function() {
		$(this).removeClass("dyn").addClass("dyb");
	});
	$(".courtbcdoc-bottom>li").mouseout(function() {
		$(this).find("div").removeClass("dyb").addClass("dyn");
	});
	//判断点击删除之后是否有选中项
	$(".deltable").click(function() {
		delshtk("是否确认删除？", "请选择删除项");
	});
});
//查看
function dflooktkshow(str2, id) {
	var checklength = $('.unitcheck:checked').length;
	if(checklength == 0) {
		promptin(str2, 2000);
	} else if(checklength > 1) {
		promptin('只能选择一项', 2000);
	} else {
		console.log($('.unitcheck:checked').parents("tr").index());
		linkbt(id)
	}
};
//删除
function delete_click(model){
	var indexarr = new Array();
	$(".obj_table_class").each(function() {
		if($(this).find(".unitcheck").attr("checked")) {
			indexarr.push($(this).attr("id"));
		}
	});
	tkhide("#adddoc_tstk");
	if(Array.prototype.isPrototypeOf(indexarr) && indexarr.length === 0){
		return;
	}
	postAjax("../"+model+"/deleteList"+model,{listStr:indexarr},(function(data){
		if(data!=null){
			promptin(data,"2000");
			for(x in indexarr){
				$("#"+indexarr[x]).remove();
			}
		}
	}),"json");
}

//删除弹框
function delshtk(str1, str2) {
	var checklength = $('.unitcheck:checked').length;
	var indexarr = new Array();
	$(".obj_table_class").each(function() {
		if($(this).find(".unitcheck").attr("checked")) {
			indexarr.push($(this).attr("id"));
		}
	});
	if(checklength == 0) {
		promptin(str2, 3000);
	} else {
		deltk(str1);
		$(".tstkdetermine").click(function() {
			tkhide("#adddoc_tstk");
			/*
			for(x in indexarr){
				var currId = "#"+$(".unitcheck").eq(indexarr[x]-1).attr("id");
				//alert(currId);
				trhide(currId);
			}*/
		});
	}
};
//删除框
function deltk(str) {
	$("#adddoc_tstk>.cnt").html(str);
	$("#adddoc_tstk").removeClass("dyn").addClass("dyb");
}
//提示
function promptin(str, time) {
	$(".prompt").html(str)
	$(".prompt").fadeIn(1000);
	setTimeout(promptout, time)
}

function promptout() {
	$(".prompt").fadeOut(1000);
}
//显示
function tkshowf(id) {
	$(id).removeClass("dyn").addClass("dyb");
}

//隐藏
function tkhide(id) {
	$(id).removeClass("dyb").addClass("dyn");
}

function trhide(id){
	$(id).parents("tr").remove();
}

//点击按钮跳转
function linkbt(id) {
	window.location.assign(id)
}
//使图片按等比例显示
function imgwh(id,idimg) {
	var liwidth = $(id).eq(0).width();
	var liheight = $(id).eq(0).height();
	var realWidth = $(idimg).find("img").width();
	var realHeight = $(idimg).find("img").height();
	if(realWidth > liwidth) {
		$(idimg).find("img").css("width", "100%").css("height", "auto");
		if($(idimg).find("img").height() > liheight) {
			$(idimg).find("img").css("width", "auto").css("height", "100%");
		}
	} else if(realHeight > liheight) {
		$(idimg).find("img").css("height", "100%").css("width", "auto");
		if($(idimg).find("img").width() > liwidth) {
			$(idimg).find("img").css("width", "100%").css("height", "auto");
		}
	} else {
		$(idimg).find("img").css("width", "auto").css("height", "auto");
	}
}