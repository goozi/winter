//销售 判断以及下拉框互斥
var strchecked = new Array();
$('.saleSelect').change(function (e) {
    if($(this).prev().find('option:selected').attr('value')==''){
        tip('请先选择上一级');
        $(this).val('');
        return false;
    }
    if($(this).find('option:selected').attr('value')=='' && $(this).next().find('option:selected').attr('value')!=undefined && $(this).next().find('option:selected').attr('value')!=''){
        tip('下一级有值，当前级必选');
        $(this).val(strchecked[$(this).index()]);
        return false;
    }

    $('.saleSelect').each(function () {
        strchecked[$(this).index()] = $(this).val();
    });
    $(this).siblings().find('option').each(function (i, obj) {
        if (($(this).attr('value') == strchecked[0] || $(this).attr('value') == strchecked[1] || $(this).attr('value') == strchecked[2]) && ($(this).index() != 0 && $(this).attr('value') != $(this).parents('select').val())) {
            $(this).hide();
        } else {
            $(this).show();
        }
    });
});
//采购 判断以及下拉框互斥
$('.purchaseselect').change(function (e) {
    if($(this).prev().find('option:selected').attr('value')==''){
        tip('请先选择上一级');
        $(this).val('');
        return false;
    }
    if($(this).find('option:selected').attr('value')=='' && $(this).next().find('option:selected').attr('value')!=undefined && $(this).next().find('option:selected').attr('value')!=''){
        tip('下一级有值，当前级必选');
        $(this).val(strchecked[$(this).index()]);
        return false;
    }

    $('.purchaseselect').each(function () {
        strchecked[$(this).index()] = $(this).val();
    });
    $(this).siblings().find('option').each(function (i, obj) {
        if (($(this).attr('value') == strchecked[0] || $(this).attr('value') == strchecked[1] ) && ($(this).index() != 0 && $(this).attr('value') != $(this).parents('select').val())) {
            $(this).hide();
        } else {
            $(this).show();
        }
    });
});

//通用弹出式文件上传
function commonUpload(callback) {
    $.dialog({
        content: "url:systemController.do?commonUpload",
        lock: true,
        title: "文件上传",
        zIndex: 2100,
        width: 700,
        height: 200,
        parent: windowapi,
        cache: false,
        ok: function () {
            var iframe = this.iframe.contentWindow;
            iframe.uploadCallback(callback);
            return true;
        },
        cancelVal: '关闭',
        cancel: function () {
        }
    });
}
function browseImages(inputId, Img) {// 图片管理器，可多个上传共用
    var finder = new CKFinder();
    finder.selectActionFunction = function (fileUrl, data) {//设置文件被选中时的函数
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
    finder.selectActionFunction = function (fileUrl, data) {//设置文件被选中时的函数
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

/**
 * 采购模块启用价格调用顺序
 */
//function changeInfo(){
    //左边下拉框值
    //var oneValue =  $("select[name='purchaseselectOne']").val();
    //var options=$("select[name='purchaseselectTwo']")[0].options;
    //for(var i=0;i<options.length;i++){
    //    var option = options[i];
    //    var v = option.value;
    //    if(v == oneValue){
    //        $("select[name='purchaseselectTwo']")[0].options.remove(i);
    //        //$("select[name='purchaseselectTwo']").append("<option value='value'>asd</option>");
    //    }
    //}


//}

$(function (){
    //点击复选框启用采购调用顺序
    purchaseStartPriceCallOrderChange();
    //点击复选框启用销售调用顺序
    saleStartPriceCallOrderChange()
});
/**
 * 点击复选框启用采购调用顺序
 */
function purchaseStartPriceCallOrderChange(){

    var a=$('input[name$="purchaseStartPriceCallOrder"]').eq(0).attr("checked");
    if(a!="checked"){
        $("select[name='purchaseselectOne']").attr("disabled","disabled")
        $("select[name='purchaseselectTwo']").attr("disabled","disabled")
    }else{
        $("select[name='purchaseselectOne']").removeAttr("disabled","");
        $("select[name='purchaseselectTwo']").removeAttr("disabled","");
    }
}



/**
 * 点击复选框启用销售调用顺序
 */
function saleStartPriceCallOrderChange(){

    var a=$('input[name$="saleStartPriceCallOrder"]').eq(0).attr("checked");
    if(a!="checked"){
        $("select[name='saleSelectOne']").attr("disabled","disabled")
        $("select[name='saleSelectTwo']").attr("disabled","disabled")
        $("select[name='saleSelectThree']").attr("disabled","disabled")
    }else{
        $("select[name='saleSelectOne']").removeAttr("disabled","");
        $("select[name='saleSelectTwo']").removeAttr("disabled","");
        $("select[name='saleSelectThree']").removeAttr("disabled","");

    }
}








