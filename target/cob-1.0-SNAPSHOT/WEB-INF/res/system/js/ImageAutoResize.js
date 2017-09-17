function ImageAutoResize(findstr)
{
	var maxWidth = $(findstr).css("width").replace('px','');
	var maxHeight = $(findstr).css("height").replace('px','');
	$(findstr + ' img').each(function() {
		var ratio = 0;  // 缩放比例
		var width = $(this).width();    // 图片实际宽度
		var height = $(this).height();  // 图片实际高度
		// 检查图片是否超宽
		if(width > maxWidth){
			ratio = maxWidth / width;   // 计算缩放比例
			$(this).css("width", maxWidth + 'px'); // 设定实际显示宽度
			$(this).css("height", (height * ratio) + 'px');  // 设定等比例缩放后的高
		}
		var width = $(this).width();    // 图片实际宽度
		var height = $(this).height();  // 图片实际高度
		// 检查图片是否超高
		if(height > maxHeight){
			ratio = maxHeight / height; // 计算缩放比例
			$(this).css("height", maxHeight + 'px');   // 设定实际显示高度
			$(this).css("width", (width * ratio) + 'px');    // 设定等比例缩放后的高度
		}
		//调整位置
		var width = $(this).width();    // 图片实际宽度
		var height = $(this).height();  // 图片实际高度
		$(this).css("margin-left",(maxWidth - width) / 2 + 'px');
		$(this).css("margin-top",(maxHeight - height) / 2 + 'px');
	});
}