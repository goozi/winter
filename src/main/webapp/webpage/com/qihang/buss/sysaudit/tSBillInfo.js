

//通用弹出式文件上传
function commonUpload(callback){
    $.dialog({
           content: "url:systemController.do?commonUpload",
           lock : true,
           title:"文件上传",
           zIndex:2100,
           width:700,
           height: 200,
           parent:windowapi,
           cache:false,
       ok: function(){
               var iframe = this.iframe.contentWindow;
               iframe.uploadCallback(callback);
                   return true;
       },
       cancelVal: '关闭',
       cancel: function(){
       } 
   });
}
function browseImages(inputId, Img) {// 图片管理器，可多个上传共用
		var finder = new CKFinder();
		finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
			$("#" + Img).attr("src", fileUrl);
			$("#" + inputId).attr("value", fileUrl);
		};
		finder.resourceType = 'Images';// 指定ckfinder只为图片进行管理
		finder.selectActionData = inputId; //接收地址的input ID
		finder.removePlugins = 'help';// 移除帮助(只有英文)
		finder.defaultLanguage = 'zh-cn';
		finder.popup();
	}
function browseFiles(inputId, file) {// 文件管理器，可多个上传共用
	var finder = new CKFinder();
	finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
		$("#" + file).attr("href", fileUrl);
		$("#" + inputId).attr("value", fileUrl);
		decode(fileUrl, file);
	};
	finder.resourceType = 'Files';// 指定ckfinder只为文件进行管理
	finder.selectActionData = inputId; //接收地址的input ID
	finder.removePlugins = 'help';// 移除帮助(只有英文)
	finder.defaultLanguage = 'zh-cn';
	finder.popup();
}
function decode(value, id) {//value传入值,id接受值
	var last = value.lastIndexOf("/");
	var filename = value.substring(last + 1, value.length);
	$("#" + id).text(decodeURIComponent(filename));
}

//复选框切换换值
function onChange(id){
	var checked = document.getElementById(id).checked;
	if(checked){
		$("#"+id).val(1);
		document.getElementById(id).checked = true;
	}else{
		$("#"+id).val(0);
		document.getElementById(id).checked = false;
	}
}

$(function(){
	var backZero = $("#backZero").val();
	var radio = document.getElementsByName("backZero");
	for(var i=0 ; i < radio.length ; i++){
		if(radio[i].value == backZero){
			radio[i].checked=true;
			break;
		}
	}
	var isEdit = $("#isEdit").val();
	var isOffOn = $("#isOffOn").val();
	if(isEdit == 1) {
		document.getElementById("isEdit").checked = true;
	}
	if(isOffOn == 1) {
		document.getElementById("isOffOn").checked = true;
	}
})

//保存前设置checkbox值
function setCheckInfo(){
	var isEdit = document.getElementById("isEdit").checked;
	if(!isEdit){
		$("input[name='isEdit']").val(0);
	}else{
		$("input[name='isEdit']").val(1);
	}
	var isOffOn = document.getElementById("isOffOn").checked;
	if(!isOffOn){
		$("input[name='isOffOn']").val(0);
	}else{
		$("input[name='isOffOn']").val(1);
	}
	return true;
}