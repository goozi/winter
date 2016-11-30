
var userInfo;
//初始化下标
function resetTrNum(tableId) {
	$tbody = $("#"+tableId+"");
	$tbody.find('>tr').each(function(i){
		$(':input, select,button,a', this).each(function(){
			var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'), val = $this.val();
			if(name!=null){
				if (name.indexOf("#index#") >= 0){
					$this.attr("name",name.replace('#index#',i));
				}else{
					var s = name.indexOf("[");
					var e = name.indexOf("]");
					var new_name = name.substring(s+1,e);
					$this.attr("name",name.replace(new_name,i));
				}
				if(name.indexOf("leaveNum") >=0){
					$this.attr("value",i+1);
				}
			}
			if(id!=null){
				if (id.indexOf("#index#") >= 0){
					$this.attr("id",id.replace('#index#',i));
				}else{
					var s = id.indexOf("[");
					var e = id.indexOf("]");
					var new_id = id.substring(s+1,e);
					$this.attr("id",id.replace(new_id,i));
				}
			}
			if(onclick_str!=null){
				if (onclick_str.indexOf("#index#") >= 0){
					$this.attr("onclick",onclick_str.replace(/#index#/g,i));
				}else{
				}
			}
		});
		$(this).find('div[name=\'xh\']').html(i+1);
	});
}
//通用弹出式文件上传
function commonUpload(callback,inputId){
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
               iframe.uploadCallback(callback,inputId);
               return true;
       },
       cancelVal: '关闭',
       cancel: function(){
       } 
   });
}
//通用弹出式文件上传-回调
function commonUploadDefaultCallBack(url,name,inputId){
	$("#"+inputId+"_href").attr('href',url).html('下载');
	$("#"+inputId).val(url);
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

var oldInfo = $("#leaveNum").val() == "" ? 1 : $("#leaveNum").val();
function changeLine(){
	var v = $("select[name='leaveNum']").val();
	if(v > oldInfo) {
		var v1 = v - oldInfo;
		oldInfo = v;
		for (var i = 0; i < v1; i++) {
			var tr = $("#add_tSAuditLeave_table_template tr").clone();
			//console.log(tr);
			$("#add_tSAuditLeave_table").append(tr);
			resetTrNum('add_tSAuditLeave_table');
		}
		for(var k = 1; k < v ; k++){
			$("input[name='tSAuditLeaveList["+k+"].auditorId']").combotree({
				url:'tSAuditController.do?loadUserInfo',
				width:500,
				panelHeight:200,
				multiple:true
			})
			$("input[name='tSAuditLeaveList["+k+"].leaveNum']").css("background-color","white");
		}
	}else{
		var v1 = oldInfo - v;
		oldInfo = v;
		for(var j = 0 ; j < v1 ; j++) {
			var lasIndex = document.getElementById("add_tSAuditLeave_table").children.length-1;
			document.getElementById("add_tSAuditLeave_table").deleteRow(lasIndex);
		}
		resetTrNum('add_tSAuditLeave_table');
	}
}

function setValue(){
   var size = $("#rowSize").val();
   for(var i=0;i<size;i++){
	   var value = $("#auditorId"+i).val();
	   var ids = value.split(",");
	   $("#tree\\["+i+"\\]").combotree("setValues",ids);
   }
}

//保存前设置复选框的值；
function setCheckInfo(){
	var isAudit = document.getElementById("isAudit").checked;
	if(isAudit) {
		$("#isAudit").val(1);
	}else{
		$("#isAudit").val(0);
	}
	var isSort = document.getElementById("isSort").checked;
	if(isSort) {
		$("#isSort").val(1);
	}else{
		$("#isSort").val(0);
	}
	var isSendMessage = document.getElementById("isSendMessage").checked;
	if(isSendMessage) {
		$("#isSendMessage").val(1);
	}else{
		$("#isSendMessage").val(0);
	}
	var isEdit = document.getElementById("isEdit").checked;
	if(isEdit){
		$("#isEdit").val(1);
	}else{
		$("#isEdit").val(0);
	}
	var $tbody = $("#add_tSAuditLeave_table");
	var length = $tbody[0].rows.length;
	for(var i = 0 ; i < length ; i++){
		var v = $("#tree\\["+i+"\\]").combotree("getValues");
		if(v.length == 0){
			tip("请选择审核人");
			return false;
		}
	}
	//var isUnaudit = document.getElementById("isUnaudit").checked;
	//if(isUnaudit) {
	//	$("#isUnaudit").val(1);
	//}else{
	//	$("#isUnaudit").val(0);
	//}
	return true;
}

//初始化数据配置
$(function(){
	var isAudit = $("#isAudit").val();
	if(1 == isAudit){
		document.getElementById("isAudit").checked = true;
	}

	var isSort = $("#isSort").val();
	if(1 == isSort){
		document.getElementById("isSort").checked = true;
	}

	var isSendMessage = $("#isSendMessage").val();
	if(1 == isSendMessage){
		document.getElementById("isSendMessage").checked = true;
	}

	var isUnaudit = $("#isUnaudit").val();
	if(1 == isUnaudit){
		document.getElementById("isUnaudit").checked = true;
	}

	var isEdit = $("#isEdit").val();
	if(1 == isEdit){
		document.getElementById("isEdit").checked = true;
	}
	if(!isAudit || isAudit == "0"){
		$("#isSort").attr("disabled","disabled");
		$("#isEdit").attr("disabled","disabled");
		$("#isSendMessage").attr("disabled","disabled");
		$("select[name='leaveNum']").attr("disabled","disabled");
	}

	$("#isAudit").attr("onchange","isAuditChange()");

})

function readOnlyFun(){
	return false;
}

function isAuditChange(){
	var isAudit = document.getElementById("isAudit").checked;
	if(isAudit){
		$("#isSort").removeAttr("disabled");
		$("#isEdit").removeAttr("disabled");
		$("#isSendMessage").removeAttr("disabled");
		$("select[name='leaveNum']").removeAttr("disabled");
		var $tbody = $("#add_tSAuditLeave_table");
		var length = $tbody[0].rows.length;
		for(var i = 0 ; i < length ; i++){
			$("#tree\\["+i+"\\]").combotree({disabled:false});
			//$("#tree\\["+i+"\\]").attr("datatype","*");
		}
	} else {
		$("#isSort").attr("disabled","disabled");
		$("#isEdit").attr("disabled","disabled");
		$("#isSendMessage").attr("disabled","disabled");
		$("select[name='leaveNum']").attr("disabled","disabled");
		var $tbody = $("#add_tSAuditLeave_table");
		var length = $tbody[0].rows.length;
		for(var i = 0 ; i < length ; i++){
			$("#tree\\["+i+"\\]").combotree({disabled:true});
			//$("#tree\\["+i+"\\]").removeAttr("datatype");
		}
	}
}