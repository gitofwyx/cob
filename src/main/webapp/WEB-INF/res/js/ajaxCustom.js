function postAjax(url,data,success,dataType){
	$.ajax({
		url:url,
		type:'post',
		cache:false,
		async:false,
		data:data,
		dataType:dataType,
		success:success,
	});
}

function isNull(data){  return (data == "" || data == undefined || data == null) ? true : false;  }

//判断value是否为空
function isEmpty(value) {
	return (Array.isArray(value) && value.length === 0) || (Object.prototype.isPrototypeOf(value) && Object.keys(value).length === 0);
}

