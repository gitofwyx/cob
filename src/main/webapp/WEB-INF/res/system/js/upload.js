var multileuploader = new plupload.Uploader({
	runtimes: 'html5,flash,silverlight,html4',
	browse_button: 'browse', // you can pass an id...
	url: "/index.jsp",
	filters: {
		mime_types: [ //只允许上传图片文件
			{ title: "图片文件", extensions: "jpg,gif,png" }
		]
	},
	multipart_params: {

	},
	multi_selection: true, //不可以在文件浏览对话框中选择多个文件
	prevent_duplicates: true, //不允许选取重复文件
	init: {
		FilesAdded: function(uploader, files) {
			for(var i = 0, len = files.length; i < len; i++) {
				//构造html来更新UI
				var html = '<li class="fl adddtimgli col-lg-7 col-min-6 col-small-5"><div class="dyjc" id="file-' + files[i].id + '"></div><span class="curp ahover delimg">删除</span></li>';
				$(html).appendTo('#file-list');
				! function(i) {
					previewImage(files[i], function(imgsrc) {
						$('#file-' + files[i].id).append('<img src="' + imgsrc + '" />');
					})
				}(i);
			}
			var w = $(".adddtimgli").eq(0).width();
			var scale = 4 / 3;
			var h = w / scale;
			$(".adddtimgli>div").css("height", h);
			$(".delimg").click(function() {
				var index = $(this).parents(".adddtimgli").index();
				uploader.splice(index, 1);
				$(this).parents(".adddtimgli").remove();
			});
		},
		FilesRemoved: function(uploader, files) {
			alert("删除成功")
		},
		UploadComplete: function(uploader, files) {
			console.log(files[0].name + " " + files[1].name)
		},
		Error: function(up, err) {
		}
	}
});

function previewImage(file, callback) { //file为plupload事件监听函数参数中的file对象,callback为预览图片准备完成的回调函数
	if(!file || !/image\//.test(file.type)) return; //确保文件是图片
	if(file.type == 'image/gif') { //gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
		var fr = new mOxie.FileReader();
		fr.onload = function() {
			callback(fr.result);
			fr.destroy();
			fr = null;
		}
		fr.readAsDataURL(file.getSource());
	} else {
		var preloader = new mOxie.Image();
		preloader.onload = function() {
			preloader.downsize(); //先压缩一下要预览的图片
			var imgsrc = preloader.type == 'image/jpeg' ? preloader.getAsDataURL('image/jpeg', 80) : preloader.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
			callback && callback(imgsrc); //callback传入的参数为预览图片的url
			preloader.destroy();
			preloader = null;
		};
		preloader.load(file.getSource());
	}
}
multileuploader.init();

//单图上传
var singleuploader = new plupload.Uploader({
	runtimes: 'html5,flash,silverlight,html4',
	browse_button: 'singlegraph', // you can pass an id...
	url: "/index.jsp",
	filters: {
		mime_types: [ //只允许上传图片文件
			{ title: "图片文件", extensions: "jpg,gif,png" }
		]
	},
	multipart_params: {

	},

	multi_selection: false, //不可以在文件浏览对话框中选择多个文件
	prevent_duplicates: true, //不允许选取重复文件
	init: {
		FilesAdded: function(uploader, files) {
			for(var i = 0, len = files.length; i < len; i++) {
				! function(i) {
					previewImage(files[i], function(imgsrc) {
						$('#addimgdiv').html('<img src="' + imgsrc + '" />');
						if(files.length > 1) {
							$("#addimgdiv img").eq(0).remove();
							uploader.splice(0, 1);
						}
					})
				}(i);
			}
		},

		FileUploaded:function(uploader,file,responseObject) {

				if(!isNull(responseObject.response.error)){
					alert(responseObject.response.error);
				}
		},

		FilesRemoved: function(uploader, files) {
			alert(11)
		},
		UploadComplete: function(uploader, files) {
			console.log(files[0].name)
		},
		Error: function(up, err) {

		}
	}
});
singleuploader.init();

//最后给"开始上传"按钮注册事件


function isNull(data){  return (data == "" || data == undefined || data == null) ? true : false;  }

//判断value是否为空
function isEmpty(value) {
	return (Array.isArray(value) && value.length === 0) || (Object.prototype.isPrototypeOf(value) && Object.keys(value).length === 0);
}

