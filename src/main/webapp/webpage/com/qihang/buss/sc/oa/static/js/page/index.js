define(function(require, exports, module) { //参数名字不能改
  var minicalendar = require("../plugin/minicalendar");
    $.ajaxSetup({
        cache:false
    })
  require("plugin/xgcalendar");
  require("dailog");
  exports.init =function() {
     var minical =new minicalendar({
        onchange:datechange
     });
     minical.init("#minical");    
     var op = {
        view: "week",
        theme:1,
        autoload:true, //
        showday: new Date(),
        EditCmdhandler:edit,
        //DeleteCmdhandler:dcal,
        ViewCmdhandler:view,    
        onWeekOrMonthToDay:wtd,
        onBeforeRequestData: cal_beforerequest,
        onAfterRequestData: cal_afterrequest,
        onRequestDataError: cal_onerror, 
        url: "tScScheduleController.do?datagrid" ,
        quickAddUrl: "tScScheduleController.do?doAdd" ,
        quickUpdateUrl: "tScScheduleController.do?doUpdate" ,
        quickDeleteUrl:  "tScScheduleController.do?doDel" //快速删除日程的
        /* timeFormat:" hh:mm t", //t表示上午下午标识,h 表示12小时制的小时，H表示24小时制的小时,m表示分钟
        tgtimeFormat:"ht" //同上 */ 
    };
    var _MH = document.documentElement.clientHeight-50;
    op.height = _MH;
    op.eventItems =[];
    var p = $("#xgcalendarp").bcalendar(op).BcalGetOp();
    if (p && p.datestrshow) {       
        $("#dateshow").text(p.datestrshow);
    }
    $("#addcalbtn").click(function(){
		alert("create");
		return;
        OpenModalDialog("calendar/add", { caption: "创建活动", width: 580, height: 460, onclose: function(){
            $("#xgcalendarp").BCalReload();
        }});
    });
    $("#daybtn").click(function() {      
       switchview.call(this,"day");       
    });
    $("#weekbtn").click(function() {      
       switchview.call(this,"week");       
    });
    $("#monthbtn").click(function() {       
       switchview.call(this,"month");       
    });
    $("#monthbtn").click(function() {       
        
    });
    $("#prevbtn").click(function(){
        var p = $("#xgcalendarp").BCalPrev().BcalGetOp();
        if (p && p.datestrshow) {
            $("#dateshow").text(p.datestrshow);
        }
    });
    $("#nextbtn").click(function(){
        var p = $("#xgcalendarp").BCalNext().BcalGetOp();
        if (p && p.datestrshow) {
            $("#dateshow").text(p.datestrshow);
        }
    });
    $("#todaybtn").click(function(e) {
        var p = $("#xgcalendarp").BCalGoToday().BcalGetOp();
        if (p && p.datestrshow) {
            $("#dateshow").text(p.datestrshow);
        }
    });
    function switchview (view) {
        $("#viewswithbtn button.current").each(function() {
            $(this).removeClass("current");
        })
        $(this).addClass("current");
        var p = $("#xgcalendarp").BCalSwtichview(view).BcalGetOp();
        if (p && p.datestrshow) {
            $("#dateshow").text(p.datestrshow);
        }
    }
    function datechange(r)
    {
        var p = $("#xgcalendarp").BCalGoToday(r).BcalGetOp();
        if (p && p.datestrshow) {
            $("#dateshow").text(p.datestrshow);
        }
    }
    function cal_beforerequest(type)
    {          
        var t=loadingmsg;
        switch(type)
        {
            case 1:
                t=loadingmsg;
                break;
            case 2:                      
            case 3:  
            case 4:    
                t=processdatamsg;                                   
                break;
        }
        $("#errorpannel").hide();
        $("#loadingpannel").html(t).show();        
    }
    function cal_afterrequest(type)
    {         
        switch(type)
        {
            case 1:
                $("#loadingpannel").hide();
                break;
            case 2:$("#loadingpannel").html(sucessmsg);
                window.setTimeout(function(){document.location.reload();},2000);break;
            case 3: $("#loadingpannel").html(sucessmsg);
                window.setTimeout(function(){$("#loadingpannel").hide();},2000);break;
            case 4:$("#loadingpannel").html(sucessmsg);
                window.setTimeout(function(){document.location.reload();},2000);break;
            case 5:$("#loadingpannel").html(sucessmsg);
                window.setTimeout(function(){document.location.reload();},2000);break;

        }              
    }
    function cal_onerror(type,data)
    {   
      $("#errorpannel").show(); 
    }
    function edit(data)
    { 
		//alert("edit");
		//return;
        if(data)
        {
            var url = StrFormat("tScScheduleController.do?goUpdate&id={0}&startTime={2}&endTime={3}&IsAllDayEvent={4}&CalendarTitle={1}",data);
            OpenModalDialog(url, { caption: "修改活动", width: 620, height: 320, onclose: function(){
                $("#xgcalendarp").BCalReload();
            }});
        }
    }
    function view(data)
    {              
    }    
    function dcal(data,callback)
    {            
    }
    function wtd(p)
    {
       if (p && p.datestrshow) {
            $("#txtdatetimeshow").text(p.datestrshow);
       }
       $("#viewswithbtn button.current").each(function() {
            $(this).removeClass("current");
        })
        $("#daybtn").addClass("current");             
    }
  }
});