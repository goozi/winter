
//经办人
$("#empName").keypress(function(e){
    if (e.keyCode == 13) {
        selectEmpDialog();
    }
})
//选择职员
function selectEmpDialog(){
    var empName = $("#empName").val();
    var url = "tScEmpController.do?goselectdeptIdNameDialog&name="+empName;
    var width = 800;
    var height = 500;
    var title = "经办人";
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
                $("#empName").val(item.name);
                $("#empId").val(item.id);
                $("#deptId").val(item.deptID);
                $("#deptName").val(item.deptIdName);
                setValOldIdAnOldName($("#empName"), item.id, item.name);
            },
            focus: true
        }, {
            name: '取消',
            callback: function () {
            }
        }]
    }).zindex();
};

//部门
$("#deptName").keypress(function(e){
    if (e.keyCode == 13) {
        selectDeptDialog();
    }
})
//选择部门
function selectDeptDialog(){
    var deptName = $("#deptName").val();
    var url = 'tScDepartmentController.do?goSelectDialog&name=' + deptName;
    var width = 800;
    var height = 500;
    var title = "部门";
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
                $("#deptName").val(item.name);
                $("#deptId").val(item.id);
                setValOldIdAnOldName($("#deptName"), item.id, item.name);
            },
            focus: true
        }, {
            name: '取消',
            callback: function () {
            }
        }]
    }).zindex();
};
/**
 * 存放旧值
 * 弹出框回传设置值
 */
function setValOldIdAnOldName(inputid, oldId, oldName) {
    inputid.attr("oldid", oldId);
    inputid.attr("oldname", oldName);
}
//初始化下标
function resetTrNum(tableId) {
    $tbody = $("#"+tableId+"");
    $tbody.find('>tr').each(function(i){
        $(':input, select,button,a', this).each(function(){
            var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'),str_press = $this.attr('onkeypress'), val = $this.val();
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
                //if(name.indexOf("amount") > -1){
                //    $this.attr("onchange","setAllAmount(this)");
                //}
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
                    var s = onclick_str.indexOf("(");
                    var e = onclick_str.indexOf(")");
                    var new_onclick_str = onclick_str.substring(s+1,e);
                    $this.attr("onclick",onclick_str.replace(new_onclick_str,i));
                }
            }
            if(str_press != null){
                if (str_press.indexOf("#index#") >= 0) {
                    $this.attr("onkeypress", str_press.replace(/#index#/g, i));
                } else {
                    var s = str_press.indexOf("(");
                    var e = str_press.indexOf(",");
                    var new_key= str_press.substring(s + 1, e);
                    $this.attr("onkeypress", str_press.replace(new_key, i));
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
        $("input[name='tScRpAdjustbillentryList["+i+"].findex']").val(i+1);
    });
}

//表格校验事件
function orderListStockBlur(rowIndex,id,name){
    checkInput( $('input[name="tScRpAdjustbillentryList['+rowIndex+'].'+id+'"]'),$('input[name="tScRpAdjustbillentryList['+rowIndex+'].'+name+'"]'));
}
function setAllAmount(obj){
    var CFG_MONEY = $("#CFG_MONEY").val();
    if(obj != null && obj != "undefined"){
        if($.trim($(obj).val()) == "" || $.trim($(obj).val()) == "undefined"){
            var thisAmount = 0;
            $(obj).val(thisAmount.toFixed(CFG_MONEY));
        }else{
            $(obj).val( parseFloat($(obj).val()).toFixed(CFG_MONEY));
        }
    }
    var amount =  $("#tScRpAdjustbillentry_table").find("input[name$='.amount']");
    var allAmount = 0;
    amount.each(function(){
        if($.trim($(this).val()) != "" && $.trim($(this).val()) != "undefined"){
            allAmount = allAmount + parseFloat($(this).val());
        }
    });
    allAmount = allAmount.toFixed(CFG_MONEY);
    $("#allAmount").val(allAmount);
}
$(function(){
    var CFG_MONEY = $("#CFG_MONEY").val();
    var allAmount = parseFloat($("#allAmount").val());
    allAmount = allAmount.toFixed(CFG_MONEY);
    $("#allAmount").val(allAmount);
});
//选择收支项目
function selectFeeDialog(index){
    var expName = $("input[name='tScRpAdjustbillentryList["+index+"].expName']").val();
    var url = 'tScFeeController.do?goSelectEmpDialog&name=' + expName;
    var width = 800;
    var height = 500;
    var title = "收支项目";
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
                var expList = iframe.getMoreSelectionData();
                //for(var i=0 ; i<itemList.length ; i++) {
                var itemLength = expList.length;
               // var nextTrLength = $('input[name="tScRpAdjustbillentryList[' + index + '].findex"]').parent().nextAll().length;//判断后面的行数
               // var newLength = itemLength - nextTrLength - 1
                for (var j = 0; j < itemLength-1; j++) {
                    clickAddTScRpAdjustbillentryBtn(index);
                }
                $.each(expList, function (i, item) {
                    var rowindex = parseInt(index) + i;
                    var expId = item.id;
                    var expName = item.name;
                    $("input[name='tScRpAdjustbillentryList["+rowindex+"].expId']").val(expId);
                    $("input[name='tScRpAdjustbillentryList["+rowindex+"].expName']").val(expName);
                    setValOldIdAnOldName($("input[name='tScRpAdjustbillentryList["+rowindex+"].expName']"),expId,expName);
                });
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
 * 提交之前检查一下金额
 * @returns {boolean}
 */
function checkPartten(){
    var amount =  $("#tScRpAdjustbillentry_table").find("input[name$='.amount']");
    var allAmount = 0;
    var num = 0;
    amount.each(function(){
       if($.trim($(this).val()) == 0 || $.trim($(this).val()) == "" || $.trim($(this).val()) == "undefined"){
           num = num + 1;
       }
    });
    if(num > 0){
        tip("资金调账明细金额项不能为0或空");
        return false;
    }
    return true;
}