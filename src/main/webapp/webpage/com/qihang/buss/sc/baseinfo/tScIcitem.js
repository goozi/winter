

/**
 * 价格位数控制
 * @param obj
 */
function setAllAmount(obj){
    var CFG_MONEY = $("#CFG_MONEY").val();
    if($(obj) != "" && $(obj) != "undefined"){
        $(obj).val( parseFloat($(obj).val()).toFixed(CFG_MONEY));
    }
}
$(function(){
    $.ajax({
        cache: false
    });
    //按保质期管理
    $('select[name="iskfPeriod"]').on('change',function(){
        var iSKFPeriodVal = $('select[name="iskfPeriod"]').val();
        if(iSKFPeriodVal != "" && iSKFPeriodVal == 'Y'){
            //保质期
            $("#kfPeriod").removeAttr("readonly");
            $("#kfPeriod").css("background-color","#ffffff");
            $("#kfPeriod").attr('datatype','n');
            $("#kfPeriod").next().text("*");
            //保质期类型
            $('select[name="kfDateType"]').removeAttr("disabled");
            $('select[name="kfDateType"]').removeAttr("style");
            $('select[name="kfDateType"]').attr("datatype","*");
            $('select[name="kfDateType"]').next().text("*");
            $('select[name="batchManager"]').val('Y');

        }else{
            //保质期
            $("#kfPeriod").removeAttr('datatype');
            $("#kfPeriod").next().text("");
            $("#kfPeriod").css("background-color","rgb(226, 226, 226)");
            $("#kfPeriod").val("")
            $("#kfPeriod").attr("readonly","true");
            //保质期类型
            $('select[name="kfDateType"]').val("");
            $('select[name="kfDateType"]').removeAttr("datatype");
            $('select[name="kfDateType"]').next().text("");
            $('select[name="kfDateType"]').css("background-color","rgb(226, 226, 226)");
            $('select[name="kfDateType"]').attr("disabled","true");
        }
        //$("input[name='tScItemPriceList[" + index + "].lsRetailPrice']").val(0.0.toFixed(CFG_PRICE));
        //$("input[name='tScItemPriceList[" + index + "].lsMemberPrice']").val(0.0.toFixed(CFG_PRICE));
        //$("input[name='tScItemPriceList[" + index + "].cgPrice']").val(0.0.toFixed(CFG_PRICE));
        //$("input[name='tScItemPriceList[" + index + "].cgPrice1']").val(0.0.toFixed(CFG_PRICE));
        //$("input[name='tScItemPriceList[" + index + "].cgPrice2']").val(0.0.toFixed(CFG_PRICE));
        //$("input[name='tScItemPriceList[" + index + "].cgPrice3']").val(0.0.toFixed(CFG_PRICE));
        //$("input[name='tScItemPriceList[" + index + "].cgLimitPrice']").val(0.0.toFixed(CFG_PRICE));
        //$("input[name='tScItemPriceList[" + index + "].cgLatestPrice']").val(0.0.toFixed(CFG_PRICE));
        //$("input[name='tScItemPriceList[" + index + "].xsPrice']").val(0.0.toFixed(CFG_PRICE));
        //$("input[name='tScItemPriceList[" + index + "].xsPrice1']").val(0.0.toFixed(CFG_PRICE));
        //$("input[name='tScItemPriceList[" + index + "].xsPrice2']").val(0.0.toFixed(CFG_PRICE));
        //$("input[name='tScItemPriceList[" + index + "].xsPrice3']").val(0.0.toFixed(CFG_PRICE));
        //$("input[name='tScItemPriceList[" + index + "].xsLimitPrice']").val(0.0.toFixed(CFG_PRICE));
        //$("input[name='tScItemPriceList[" + index + "].xsLatestPrice']").val(0.0.toFixed(CFG_PRICE));
    });
    //名称
    $("#name").blur(function () {
       var name = $("#name").val();
        if(name!=null&&name!="") {
            $.ajax({
                type: 'POST',
                url: 'tScIcitemController.do?getShortName',
                async: false,
                data: {'name':name},
                dataType: 'json',
                success:function(data){
                 if(data.success == true){
                    var pingyinname = data.obj;
                     $("#shortName").val(pingyinname);
                 }
                }
            });
        }else{
            $("#shortName").val("");
        }
    });

    //主供货商
    $("#supplierName").keypress(function (e) {
        if (e.keyCode == 13) {
            selectSupplierNameDialog();
        }
    });
    $("#supplierName").blur(function () {
        checkInput($("#supplierID"), $("#supplierName"));
    });
    //仓库
    $("#stockName").keypress(function (e) {
        if (e.keyCode == 13) {
            selectStockNameDialog();
        }
    });
    $("#stockName").blur(function () {
        checkInput($("#stockID"), $("#stockName"));
    });

    //若被引用则控制存货类型、计价方法、按保质期管理、保质期类型、启用批次管理
    var count = $('#count').val();
    var load = $('#load').val();//区分编辑和复制
    if(count > 0 && load == 'edit'){
        $('select[name="itemType"]').attr("disabled",true);
        $('select[name="track"]').attr("disabled", true);
        $('select[name="iskfPeriod"]').attr("disabled",true);
        $('select[name="kfDateType"]').attr("disabled", true);
        $('select[name="batchManager"]').attr("disabled",true);

        $('select[name="itemType"]').css("background","#ddd");
        $('select[name="track"]').css("background", "#ddd");
        $('select[name="iskfPeriod"]').css("background","#ddd");
        $('select[name="kfDateType"]').css("background", "#ddd");
        $('select[name="batchManager"]').css("background","#ddd");
    }
});

/**
 *
 * checkId 文本框里面的id
 * checkNameId 另外用名称代替的id
 *
 */
function checkInput(checkId, checkNameId) {
    if (checkNameId.val() != undefined && checkNameId.val() != "") {
        if (checkNameId.attr("oldid") != undefined && checkNameId.attr("oldid") != "") {
            checkNameId.val(checkNameId.attr("oldname"));
            checkId.val(checkNameId.attr("oldid"));
        } else {
            checkNameId.val("");
            checkId.val("");
        }
    } else {
        checkNameId.attr("oldid", "");
        checkNameId.attr("oldname", "");
        checkId.val("");
    }
}
function selectStockNameDialog(){
    var stockName = $("#stockName").val();
    var url = encodeURI('tBiStockController.do?goSelectDialog&name=' + stockName);
    var width = 550;
    var height = 400;
    var title = "仓库";
    $.dialog({
        content: 'url:' + url,
        title: title,
        lock: true,
        zIndex: 600,
        height: height,
        cache: false,
        width: width,
        opacity: 0.3,
        button: [{
            name: '确定',
            callback: function () {
                iframe = this.iframe.contentWindow;
                var item = iframe.getSelectionData();
                $("input[name='stockName']").val(item.name);
                $("input[name='stockID']").val(item.id);
                setValOldIdAnOldName($("#stockName"), item.id, item.name);
            },
            focus: true
        }, {
            name: '取消',
            callback: function () {
            }
        }]
    }).zindex();
}
function selectSupplierNameDialog(){
    var supplierName = $("#supplierName").val();
    var url = encodeURI('tScSupplierController.do?goSelectDialog&name=' + supplierName);
    var width = 1000;
    var height = 500;
    var title = "主供应商";
    $.dialog({
        content: 'url:' + url,
        title: title,
        lock: true,
        zIndex: 600,
        height: height,
        cache: false,
        width: width,
        opacity: 0.3,
        button: [{
            name: '确定',
            callback: function () {
                iframe = this.iframe.contentWindow;
                var item = iframe.getSelectionData();
                $("input[name='supplierName']").val(item.name);
                $("input[name='supplierID']").val(item.id);
                setValOldIdAnOldName($("#supplierName"), item.id, item.name);
            },
            focus: true
        }, {
            name: '取消',
            callback: function () {
            }
        }]
    }).zindex();
}

/**
 * 存放旧值
 * 弹出框回传设置值
 */
function setValOldIdAnOldName(inputid, oldId, oldName) {
    inputid.attr("oldid", oldId);
    inputid.attr("oldname", oldName);
}
/**在提交之前检查信息*/

function checkMsg(){
    var name = $("#name").val();
    if(name!=null && name!="") {
        $.ajax({
            type: 'POST',
            url: 'tScIcitemController.do?getShortName',
            async: false,
            data: {'name':name},
            dataType: 'json',
            success:function(data){
                if(data.success == true){
                    var pingyinname = data.obj;
                    $("#shortName").val(pingyinname);
                }
            }
        });
    }else{
        $("#shortName").val("");
    }
    /**** 检查主供应商*/
    checkInput($("#supplierID"), $("#supplierName"));
    /*** 检查仓库*/
    checkInput($("#stockID"), $("#stockName"));
    /***** 对默认采购单位，默认销售单位，默认仓存单位进行判断*/
    var addtempDefaultCK = 0;
    $("#add_tScItemPrice_table > tr").each(function(i,data){
        var val = $(data).find('input[name$="defaultCK"]').eq(0).attr("checked");
        if(val=="checked"){
            addtempDefaultCK = addtempDefaultCK +1;
        }
    });
    if(addtempDefaultCK == 0){
        tip("请选择默认仓存单位");
        return false;
    }
    var addtempDefaultXS = 0;
    $("#add_tScItemPrice_table > tr").each(function(i,data){
        var val = $(data).find('input[name$="defaultXS"]').eq(0).attr("checked");
        if(val=="checked"){
            addtempDefaultXS = addtempDefaultXS +1;
        }
    });
    if(addtempDefaultXS == 0){
        tip("请选择默认销售单位");
        return false;
    }
    var addtempDefaultCG = 0;
    $("#add_tScItemPrice_table > tr").each(function(i,data){
        var val = $(data).find('input[name$="defaultCG"]').eq(0).attr("checked");
        if(val=="checked"){
            addtempDefaultCG = addtempDefaultCG +1;
        }
    });
    if(addtempDefaultCG == 0){
        tip("请选择默认采购单位");
        return false;
    }
    /***
     * 对单位类型的判断
     * 基本单位每个商品有且只能有一个基本单位
     * 常用单位每个商品可以有N个常用位，也可以一个也没有
     * 辅助单位每个商品最多只能一个辅助单位，也可以没有
     */
      var basicUnitTypeCount =  0;//基本单位
      var assistUnitTypeCount = 0;//辅助单位
      var isassist = false;
      $("#add_tScItemPrice_table > tr:gt(0)").each(function(i,data){
            var val = $(data).find('select[name$="unitType"]').eq(0).val();
            if(val != "" && val == "0001"){
                basicUnitTypeCount += 1;
            }
            if(val != "" && val == "0003"){
                var defaultCGcheckVal = $(this).find('input[name$="defaultCG"]').eq(0).attr('checked');
                var defaultXScheckVal = $(this).find('input[name$="defaultXS"]').eq(0).attr('checked');
                var defaultCKcheckVal = $(this).find('input[name$="defaultCK"]').eq(0).attr('checked');
                if(defaultCGcheckVal != "" && defaultCGcheckVal == "checked"){
                    tip("辅助单位的单位类型不能做为默认采购单位");
                    $(this).find('input[name$="defaultCG"]').removeAttr('checked');
                    isassist = true ;
                    return false;
                }else if(defaultXScheckVal !="" && defaultXScheckVal =="checked"){
                    tip("辅助单位的单位类型不能做为默认销售单位");
                    $(this).find('input[name$="defaultXS"]').removeAttr('checked');
                    isassist = true ;
                    return false;
                }else if(defaultCKcheckVal !="" && defaultCKcheckVal =="checked"){
                    tip("辅助单位的单位类型不能做为默认仓存单位");
                    $(this).find('input[name$="defaultCK"]').removeAttr('checked');
                    isassist = true ;
                    return false;
                }
                assistUnitTypeCount+=1;
            }
       });
        if(isassist == true){
            return false;
        }
       if(basicUnitTypeCount >= 1){
           tip("一个商品只能有一个基本单位,请重新输入！");
           return false;
       }
       if(assistUnitTypeCount>1){
            tip("一个商品只能有一个辅助单位,请重新输入！");
           return false;

       }
    var danUnidBool = true;
    $("#add_tScItemPrice_table").find('select[name$=".unitID"]').each(function(){
        var danUnid = $(this).val();
        var num = 0;
        $("#add_tScItemPrice_table").find('select[name$=".unitID"]').each(function(){
            if($(this).val() == danUnid){
                num = num + 1;
            }
        });
        if(num > 1){
            tip("单位不能选一样的");
            danUnidBool = false;
        }
    });
   if(!danUnidBool)
        return danUnidBool;
    return checkNameHave();
}
var isDoCheck = false;
var isDoSub = false;
function checkNameHave(){
    var id = $("#id").val();
    var name = $("#name").val();
    if(!isDoCheck) {
        url = 'tScIcitemController.do?checkName&name=' + name+'&id='+id;
        $.ajax({
            url: url,
            async: false,
            dataType: 'json',
            type: 'POST',
            cache: false,
            success: function (data) {
                if (data.success) {
                    parent.$.dialog.confirm(name + "已存在，是否继续保存？", function () {
                            isDoCheck = true;
                            isDoSub = true;
                            $("#formobj").submit();
                        }
                        , function () {
                        }
                    )
                } else {
                    isDoCheck = true;
                    isDoSub = true;
                }
            }
        });
    }
    return isDoSub;
}
//初始化下标
function resetTrNum(tableId) {
	$tbody = $("#"+tableId+"");
	$tbody.find('>tr').each(function(i){
		$(':input, select,button,a', this).each(function(){
			var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'), val = $this.val();
      var blur_str=$this.attr('onblur');
			if(name!=null){
				if (name.indexOf("#index#") >= 0){
					$this.attr("name",name.replace('#index#',i));
				}else{
					var s = name.indexOf("[");
					var e = name.indexOf("]");
					var new_name = name.substring(s+1,e);
					$this.attr("name",name.replace(new_name,i));
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
            if (onclick_str != null) {
                if (onclick_str.indexOf("#index#") >= 0) {
                    $this.attr("onclick", onclick_str.replace(/#index#/g, i));
                }else if(onclick_str.indexOf("commonUpload") >=0){

                } else {
                    var s = onclick_str.indexOf("(");
                    var e = onclick_str.indexOf(")");
                    var new_onclick = onclick_str.substring(s + 1, e);
                    $this.attr("onclick", onclick_str.replace(new_onclick, i));

                }
            }
        if (blur_str != null) {
            if (blur_str.indexOf("#index#") >= 0) {
                $this.attr("onblur", blur_str.replace(/#index#/g, i));
            } else {
                var s = blur_str.indexOf("(");
                var e = blur_str.indexOf(",");
                var new_blur = blur_str.substring(s + 1, e);
                $this.attr("onblur", blur_str.replace(new_blur, i));
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
function commonUploadDefaultCallBack(url,key,name,extend,inputId){
	$("#"+inputId+"_href").attr('href',url).html('下载');
	$("#"+inputId).val(url);
    $("#"+inputId).parent().parent().find('input[name$="fileName"]').eq(0).val(name);

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


//精度控制
var CFG_PRICE = $("#CFG_UNITP_RICE").val();//单价精度
function checkPrecision(index,model){
    //零售价
    if(model == 'lsRetailPrice') {
        var lsRetailPrice = $("input[name='tScItemPriceList["+index+"].lsRetailPrice']").val();
        if (lsRetailPrice == "") {
            $("input[name='tScItemPriceList[" + index + "].lsRetailPrice']").val(0.0.toFixed(CFG_PRICE));
        } else {
            lsRetailPrice = parseFloat(lsRetailPrice);
            $("input[name='tScItemPriceList[" + index + "].lsRetailPrice']").val(lsRetailPrice.toFixed(CFG_PRICE));
        }
    }
    //会员价
    if(model == 'lsMemberPrice') {
        var lsMemberPrice = $("input[name='tScItemPriceList["+index+"].lsMemberPrice']").val();
        if (lsMemberPrice == "") {
            $("input[name='tScItemPriceList[" + index + "].lsMemberPrice']").val(0.0.toFixed(CFG_PRICE));
        } else {
            lsMemberPrice = parseFloat(lsMemberPrice);
            $("input[name='tScItemPriceList[" + index + "].lsMemberPrice']").val(lsMemberPrice.toFixed(CFG_PRICE));
        }
    }
    //采购单价
    if(model == 'cgPrice') {
        var cgPrice = $("input[name='tScItemPriceList["+index+"].cgPrice']").val();
        if (cgPrice == "") {
            $("input[name='tScItemPriceList[" + index + "].cgPrice']").val(0.0.toFixed(CFG_PRICE));
        } else {
            cgPrice = parseFloat(cgPrice);
            $("input[name='tScItemPriceList[" + index + "].cgPrice']").val(cgPrice.toFixed(CFG_PRICE));
        }
    }
    //一级采购价
    if(model == 'cgPrice1') {
        var cgPrice1 = $("input[name='tScItemPriceList["+index+"].cgPrice1']").val();
        if (cgPrice1 == "") {
            $("input[name='tScItemPriceList[" + index + "].cgPrice1']").val(0.0.toFixed(CFG_PRICE));
        } else {
            cgPrice1 = parseFloat(cgPrice1);
            $("input[name='tScItemPriceList[" + index + "].cgPrice1']").val(cgPrice1.toFixed(CFG_PRICE));
        }
    }
    //二级采购价
    if(model == 'cgPrice2') {
        var cgPrice2 = $("input[name='tScItemPriceList["+index+"].cgPrice2']").val();
        if (cgPrice2 == "") {
            $("input[name='tScItemPriceList[" + index + "].cgPrice2']").val(0.0.toFixed(CFG_PRICE));
        } else {
            cgPrice2 = parseFloat(cgPrice2);
            $("input[name='tScItemPriceList[" + index + "].cgPrice2']").val(cgPrice2.toFixed(CFG_PRICE));
        }
    }
    //三级采购价
    if(model == 'cgPrice3') {
        var cgPrice3 = $("input[name='tScItemPriceList["+index+"].cgPrice3']").val();
        if (cgPrice3 == "") {
            $("input[name='tScItemPriceList[" + index + "].cgPrice3']").val(0.0.toFixed(CFG_PRICE));
        } else {
            cgPrice3 = parseFloat(cgPrice3);
            $("input[name='tScItemPriceList[" + index + "].cgPrice3']").val(cgPrice3.toFixed(CFG_PRICE));
        }
    }
    //采购限价
    if(model == 'cgLimitPrice') {
        var cgLimitPrice = $("input[name='tScItemPriceList["+index+"].cgLimitPrice']").val();
        if (cgLimitPrice == "") {
            $("input[name='tScItemPriceList[" + index + "].cgLimitPrice']").val(0.0.toFixed(CFG_PRICE));
        } else {
            cgLimitPrice = parseFloat(cgLimitPrice);
            $("input[name='tScItemPriceList[" + index + "].cgLimitPrice']").val(cgLimitPrice.toFixed(CFG_PRICE));
        }
    }
    //最近采购价
    if(model == 'cgLatestPrice') {
        var cgLatestPrice = $("input[name='tScItemPriceList["+index+"].cgLatestPrice']").val();
        if (cgLatestPrice == "") {
            $("input[name='tScItemPriceList[" + index + "].cgLatestPrice']").val(0.0.toFixed(CFG_PRICE));
        } else {
            cgLatestPrice = parseFloat(cgLatestPrice);
            $("input[name='tScItemPriceList[" + index + "].cgLatestPrice']").val(cgLatestPrice.toFixed(CFG_PRICE));
        }
    }
    //销售单价
    if(model == 'xsPrice') {
        var xsPrice = $("input[name='tScItemPriceList["+index+"].xsPrice']").val();
        if (xsPrice == "") {
            $("input[name='tScItemPriceList[" + index + "].xsPrice']").val(0.0.toFixed(CFG_PRICE));
        } else {
            xsPrice = parseFloat(xsPrice);
            $("input[name='tScItemPriceList[" + index + "].xsPrice']").val(xsPrice.toFixed(CFG_PRICE));
        }
    }
    //一级销售价
    if(model == 'xsPrice1') {
        var xsPrice1 = $("input[name='tScItemPriceList["+index+"].xsPrice1']").val();
        if (xsPrice1 == "") {
            $("input[name='tScItemPriceList[" + index + "].xsPrice1']").val(0.0.toFixed(CFG_PRICE));
        } else {
            xsPrice1 = parseFloat(xsPrice1);
            $("input[name='tScItemPriceList[" + index + "].xsPrice1']").val(xsPrice1.toFixed(CFG_PRICE));
        }
    }
    //二级销售价
    if(model == 'xsPrice2') {
        var xsPrice2 = $("input[name='tScItemPriceList["+index+"].xsPrice2']").val();
        if (xsPrice2 == "") {
            $("input[name='tScItemPriceList[" + index + "].xsPrice2']").val(0.0.toFixed(CFG_PRICE));
        } else {
            xsPrice2 = parseFloat(xsPrice2);
            $("input[name='tScItemPriceList[" + index + "].xsPrice2']").val(xsPrice2.toFixed(CFG_PRICE));
        }
    }
    //三级销售价
    if(model == 'xsPrice3') {
        var xsPrice3 = $("input[name='tScItemPriceList["+index+"].xsPrice3']").val();
        if (xsPrice3 == "") {
            $("input[name='tScItemPriceList[" + index + "].xsPrice3']").val(0.0.toFixed(CFG_PRICE));
        } else {
            xsPrice3 = parseFloat(xsPrice3);
            $("input[name='tScItemPriceList[" + index + "].xsPrice3']").val(xsPrice3.toFixed(CFG_PRICE));
        }
    }
    //销售限价
    if(model == 'xsLimitPrice') {
        var xsLimitPrice = $("input[name='tScItemPriceList["+index+"].xsLimitPrice']").val();
        if (xsLimitPrice == "") {
            $("input[name='tScItemPriceList[" + index + "].xsLimitPrice']").val(0.0.toFixed(CFG_PRICE));
        } else {
            xsLimitPrice = parseFloat(xsLimitPrice);
            $("input[name='tScItemPriceList[" + index + "].xsLimitPrice']").val(xsLimitPrice.toFixed(CFG_PRICE));
        }
    }
    //最近销售价
    if(model == 'xsLatestPrice') {
        var xsLatestPrice = $("input[name='tScItemPriceList["+index+"].xsLatestPrice']").val();
        if (xsLatestPrice == "") {
            $("input[name='tScItemPriceList[" + index + "].xsLatestPrice']").val(0.0.toFixed(CFG_PRICE));
        } else {
            xsLatestPrice = parseFloat(xsLatestPrice);
            $("input[name='tScItemPriceList[" + index + "].xsLatestPrice']").val(xsLatestPrice.toFixed(CFG_PRICE));
        }
    }

}

