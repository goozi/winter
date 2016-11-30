
$(function(){
	var basepath=$('#basepath').text();
    var comId = $('#comId').val();
    var sonId = $('#sonId').val();
	//最新公告
	$('#dg').datagrid({
		url:'tApBulletinController.do?getNoticeList&userId='+(comId == ""?sonId:comId),
		fit:true,
		onDblClickRow: function(rowIndex, rowData){

			var url = "tApBulletinController.do?goUpdate&load=detail&id="+rowData.id+"&show=true&userId="+(comId == ""?sonId:comId);
			createdetailwindow('查看公告', url, 700, 400);
			setTimeout(function(){
				$('#dg').datagrid('reload');
			},2000);

		},
		columns:[[
			{field:'id',title:'id',hidden:true},
			{field:'status',title:'status',formatter:function(v,r,i){return changeStyle(v,r,i);},hidden:true},
			{field:'title',title:'标题',height:10,width:500},
			{field:'issueDate',title:'日期',formatter:function(v,r,i){return formatterDate(v,r,i);},height:10,width:70},
		]]
	});


	//最新公告鼠标悬停字体变红
	//$('#noticea').hover(function(){
	//	$(this).addClass('s1');
	//},function(){
	//	$(this).removeClass('s1');
//});
	//----------------------------------------
	//预警提醒
	$('#trees').tree({
			url:basepath+'tApViewBuslicenceSupController.do?loadWarnTree&sonId='+sonId+'&comId='+comId,
			onClick:function(node){
                var url = "";
                var title = "";
				if(node.id==1){
                    title = "营业执照到期预警";
                    if(comId){
                        url = "/cgReportController.do?list&id=V_AP_BUSLICENCE_SUP"
                    }else {
                        url = "/tApViewBuslicenceSupController.do?tApViewBuslicenceSup";
                    }
				}
				if(node.id==2){
                    title = "流通许可证到期预警";
                    if(comId){
                        url = "/cgReportController.do?list&id=V_AP_CIRLICENCE_SUP"
                    }else {
                        url = "/tApViewCirlicenceSupController.do?tApViewCirlicenceSup";
                    }
				}
				if(node.id==3){
                    title = "机构代码证到期预警";
                    if(comId){
                        url = "/cgReportController.do?list&id=V_AP_CODELICENCE_SUP"
                    }else {
                        url = "/tApViewCodelicenceSupController.do?tApViewCodelicenceSup";
                    }
				}

                if(node.id==4){
                    title = "农药保质期预警";
                    if(comId){
                        url = "/cgReportController.do?list&id=V_AP_PESTICIDE"
                    }else {
                        url = "/tApViewIcitemDrugController.do?tApViewIcitemDrug";
                    }
                }
                if(node.id==5){
                    title = "化学肥料保质期预警";
                    if(comId){
                        url = "/cgReportController.do?list&id=V_AP_FERTILIZER_SUP"
                    }else {
                        url = "/tApViewIcitemFertilizerController.do?tApViewIcitemFertilizer";
                    }
                }
                if(node.id==6){
                    title = "饲料保质期预警";
                    if(comId){
                        url = "/cgReportController.do?list&id=V_AP_FEED_SUP"
                    }else {
                        url = "/tApViewIcitemFeedController.do?tApViewIcitemFeed";
                    }
                }
                if(node.id==7){
                    title = "农机用油保质期预警";
                    if(comId){
                        url = "/cgReportController.do?list&id=V_AP_OIL_SUP"
                    }else {
                        url = "/tApViewIcitemOilController.do?tApViewIcitemOil";
                    }
                }
                if(node.id==8){
                    title = "其他商品保质期预警";
                    if(comId){
                        url = "/cgReportController.do?list&id=V_AP_OTHER_SUP"
                    }else {
                        url = "/tApViewIcitemOtherController.do?tApViewIcitemOther";
                    }
                }
				parent.loadModule(title,url,'default');
			}
	//,
			//formatter:function(node){//对获取的每一个TREE 的TEXT进行转换变颜色
			//	var s = node.text;
			//	var splits=s.split(':');
			//	var splits1add2=splits[0]+'<span style=\'color:red\'>(' +splits[1]+ ')</span>';
			//	return splits1add2;
			//}
		});
	//预警提醒 鼠标悬停字体变红
	//$('#trees').hover(function(){
	//	$(this).addClass('s1');
	//},function(){
	//	$(this).removeClass('s1');
	//});
	//-------------------------------------------------
	$('#pol').datagrid({
		url:'tApPolicyController.do?getPolicyList',
		fit:true,
		onDblClickRow: function(rowIndex, rowData){
			var url = "tApPolicyController.do?goUpdate&load=detail&id="+rowData.id+"&show=true";
			createdetailwindow('政策法规查看', url, 700, 400);
			$('#dg').datagrid('reload');
		},
		columns:[[
			{field:'id',title:'id',hidden:true},
			{field:'title',title:'标题',height:10,width:305},
			{field:'issueDate',title:'日期',formatter:function(v,r,i){return formatterDate(v,r,i);},height:10,width:70},
		]]
	});

	$('#file').datagrid({
		url:'tApFileDownloadController.do?getFileList',
		fit:true,
		onDblClickRow: function(rowIndex, rowData){
			var url = "tApFileDownloadController.do?goUpdate&load=detail&id="+rowData.id+"&show=true";
			createdetailwindow('文件查看', url, 700, 400);
			$('#dg').datagrid('reload');
		},
		columns:[[
			{field:'id',title:'id',hidden:true},
			{field:'title',title:'标题',height:10,width:270},
			{field:'createDate',title:'日期',formatter:function(v,r,i){return formatterDate(v,r,i);},height:10,width:70},
			{field:'annexName',title:'操作',formatter:function(v,r,i){return fileDown(v,r,i);},height:10,width:50}
		]]
	});



	//政策法规
/*	$('#treeslaws').tree({
		url:basepath+'main/default.do?_q=getTreeslaws',
		onClick:function(node){
			var dialog = parent.sy.modalDialog({
				title : "政策法规",
				width : 800,
				height:550,
				url : basepath+'main/default.do?_q=tolaws&id='+node.id,
				toolbar:[{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						dialog.dialog('destroy');
					}
				}]
			});
		}
	});
	//政策法规鼠标悬停字体变红
	$('#treeslaws').hover(function(){
		$(this).addClass('s1');
	},function(){
		$(this).removeClass('s1');
	});*/
	//--------------------------------------------
	//文件下载
	/*$('#treesload').tree({
		url:basepath+'main/default.do?_q=getTreesload',
		onClick:function(node){
		}
	});
	//文件下载鼠标悬停字体变红
	$('#treesload').hover(function(){
		$(this).addClass('s1');
	},function(){
		$(this).removeClass('s1');
	});*/
});

function changeStyle(v,r,i){
	if(v==0){
		r.title = "<font color='red'>"+ r.title+"</font>"
	}
}
function formatterDate(v,r,i){
	var date = new Date(v);
	var newDate = date.getFullYear()+"-"+((date.getMonth()+1) < 10?"0"+(date.getMonth()+1):(date.getMonth()+1))+"-"+(date.getDate() < 10?"0"+date.getDate():date.getDate());
	return newDate;
}

function fileDown(v,r,i){
		v = "<a href='commonController.do?viewFile&fileid="+v+"'>下载</a>";
	return v;
}

function addTabHome(title,url){
   parent.addTab(title,url);
}

/*
//去最新公告TAB页
function tonotice(){
	addTab("公告查看", "main/default.do?_q=tonoticetab");
}
//清空
function noticeclare(){
	$('#ftitle').val('');
	$('#fissuedate').datebox('setValue','');
	$('#isorno').combobox('clear');
	$('#fisread').combobox('setValue',0);
	noticesearch();
}

//查询
function noticesearch(){
	var ftitle=$('#ftitle').val();
	var fissuedate=$('#fissuedate').datebox('getValue');
	var fisread=$('#fisread').combobox('getValue');
	$('#datag').datagrid('load',{
		ftitle:ftitle,
		fissuedate:fissuedate,
		fisread:fisread
	});
}
//时间格式转换
function showdate(v,r,i){
	  var timeArr = v.split("T");
	  var d = timeArr[0].split("-");
	  var t = timeArr[1].split(":");
    var unixTimestamp=new Date(d[0],(d[1]-1),d[2],t[0],t[1],t[2]);
    if(unixTimestamp.toLocaleString()=='Invalid Date')
	     return ''; 
	  var year = unixTimestamp.getFullYear();
	  var month = unixTimestamp.getMonth()+1;
	  if(month < 10){
             month = '0'+month;
		  }
	  var day = unixTimestamp.getDate();
	  if(day < 10){
		  day = '0'+day;
	  }
	  return year+'-'+month+'-'+day; 
  }
//对GRID里面公告类型的转换
function showcom(data){
	if(data==0){
		return '全部人员';
	}
	if(data==1){
		return '质检人员';
	}
	if(data==2){
		return '企业人员';
	}
}




//导出
function exportExcel(page){
	var option = $('#datag').datagrid('options');
	var ftitle=$('#ftitle').val();
	var fissuedate=$('#fissuedate').datebox('getValue');
	var fisread=$('#fisread').combobox('getValue');
	ftitle=encodeURI(encodeURI(ftitle));//更具URL后带参数的中文要先转换格式
	//FItemName=encodeURI(encodeURI(FItemName));
		if(page==1){//当前页
			var rows=option.pageSize;
			var page=option.pageNumber;
			window.location.href="main/default.do?_q=excelload&rows="+rows+"&page="+page+"&ftitle="+ftitle+"&fissuedate="+fissuedate+"&fisread="+fisread;
		}else{//所有页
			window.location.href="main/default.do?_q=excelload&ftitle="+ftitle+"&fissuedate="+fissuedate+"&fisread="+fisread;
		}
}

//是否已读转换
function showread(data){
	if(data==0){
		return '否';
	}
	if(data==1){
		return '是';
	}
}

//查看
function noticelook(){
	var rows=$('#datag').datagrid('getSelections');
	if(rows.length==0||rows.length>1){
		$.messager.show({
			title:'提示消息',
			msg:'请选择一行数据进行查看',
			timeout:3000,
			showType:'slide'
			});
		return;
	}
	var dialog = parent.sy.modalDialog({
		title : '最新公告查看内容',
		width : 800,
		height:450,
		url : 'main/default.do?_q=tolookdialog&zid='+rows[0].zid+'&id='+rows[0].id,
		toolbar:[{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				dialog.dialog('destroy');
			
			}
		}]
	});
}

//双击GRID查看
function rowsclick(rowIndex, rowData){
	var dialog = parent.sy.modalDialog({
		title : '最新公告查看内容',
		width : 800,
		height:450,
		url : 'main/default.do?_q=tolookdialog&zid='+rowData.zid+'&id='+rowData.id,
		toolbar:[{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				dialog.dialog('destroy');
			
			}
		}]
	});
}
*/
