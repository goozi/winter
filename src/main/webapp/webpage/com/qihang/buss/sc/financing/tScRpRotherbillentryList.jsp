<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('a[name^="addTScRpRotherbillentryBtn"]').linkbutton({
    iconCls: 'icon-add',
    plain: true
	});  
	$('a[name^="delTScRpRotherbillentryBtn"]').linkbutton({
    iconCls: 'icon-remove',
    plain: true
  });
  function clickAddTScRpRotherbillentryBtn(rowIndex) {
    var tr = $("#add_tScRpRotherbillentry_table_template tr").clone();
    $("#add_tScRpRotherbillentry_table tr").eq(rowIndex).after(tr);
    resetTrNum('add_tScRpRotherbillentry_table');
  }

  function clickDelTScRpRotherbillentryBtn(rowIndex) {
    var len = $("#add_tScRpRotherbillentry_table").find("tr").length;
    $("#add_tScRpRotherbillentry_table").find("tr").eq(rowIndex).remove();
		if(rowIndex==0 && len == 1){//如果只有一行且删除这一行则删除后补一空行
      var tr = $("#add_tScRpRotherbillentry_table_template tr").clone();
      $("#add_tScRpRotherbillentry_table").append(tr);
    }
    resetTrNum('add_tScRpRotherbillentry_table');
	setAllAmount(null);
  }
	function keyDownInfo(index, evt) {
		var evt = (evt) ? evt : ((window.event) ? window.event : "");
		var code = evt.keyCode ? evt.keyCode : evt.which;
		if (code == 13) {
			selectFeeDialog(index);
		}
	}

    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tScRpRotherbillentry_table").createhftable({
	    	height: (h-150)+'px',
			  width:'auto',
			  fixFooter:true
			});
			//解决一对多页面中列表页输入框tip属性失效问题
			$('table').find("[tip]").each(function () {
					var defaultvalue = $(this).attr("tip");
					var altercss = $(this).attr("altercss");
					$(this).focus(function () {
							if ($(this).val() == defaultvalue) {
									$(this).val('');
									if (altercss) {
											$(this).removeClass(altercss);
									}
							}
					}).blur(function () {
							if ($.trim($(this).val()) === '') {
									$(this).val(defaultvalue);
									if (altercss) {
											$(this).addClass(altercss);
									}
							}
					});
			});
    });
</script>

<table border="0" cellpadding="2" cellspacing="1" id="tScRpRotherbillentry_table" totalRow="true" style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<td align="center" bgcolor="#476f9a">操作</td>
				  <td align="center" bgcolor="#476f9a">
						收支项目<span style="color: #fa0202">*</span>
				  </td>
				  <td align="center" bgcolor="#476f9a" total="true">
						金额<span style="color: #fa0202">*</span>
				  </td>
				  <td align="center" bgcolor="#476f9a">
						备注
				  </td>
	</tr>
	<tbody id="add_tScRpRotherbillentry_table">	
	<c:if test="${fn:length(tScRpRotherbillentryList)  <= 0 }">
			<tr>
				<td align="center" bgcolor="#F6FCFF"><div style="width: 25px;background-color: white;" name="xh">1</div></td>
				<td align="center" bgcolor="white">
          <div style="width: 80px;background-color: white;">
            <a name="addTScRpRotherbillentryBtn[0]" id="addTScRpRotherbillentryBtn[0]" href="#"
               onclick="clickAddTScRpRotherbillentryBtn(0);"></a>
			  <a name="delTScRpRotherbillentryBtn[0]" id="delTScRpRotherbillentryBtn[0]" href="#"  onclick="clickDelTScRpRotherbillentryBtn(0);"></a>
          </div>
				</td>
					<input name="tScRpRotherbillentryList[0].id" type="hidden"/>
					<input name="tScRpRotherbillentryList[0].createName" type="hidden"/>
					<input name="tScRpRotherbillentryList[0].createBy" type="hidden"/>
					<input name="tScRpRotherbillentryList[0].createDate" type="hidden"/>
					<input name="tScRpRotherbillentryList[0].updateName" type="hidden"/>
					<input name="tScRpRotherbillentryList[0].updateBy" type="hidden"/>
					<input name="tScRpRotherbillentryList[0].updateDate" type="hidden"/>
					<input name="tScRpRotherbillentryList[0].fid" type="hidden"/>
					<input name="tScRpRotherbillentryList[0].findex" type="hidden" value="1"/>
					<input name="tScRpRotherbillentryList[0].expId" type="hidden" datatype="*"/>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpRotherbillentryList[0].expName" maxlength="32"  datatype="*"
							   onkeypress="keyDownInfo(0,event)"   onchange="clearExp(this)"
							   onblur="orderListStockBlur('0','expId','expName');"
					  		type="text" class="inputxt popup-select"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">收支项目</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpRotherbillentryList[0].amount" maxlength="10"
							   onchange="setAllAmount(this)"
					  		type="text" class="inputxt"  style="width:120px;" datatype="float" errormsg="请填写正确的金额" >
					  <label class="Validform_label" style="display: none;">金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpRotherbillentryList[0].note" maxlength="255"
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">备注</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tScRpRotherbillentryList)  > 0 }">
		<c:forEach items="${tScRpRotherbillentryList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="white"><div style="width: 30px;background-color: white;" name="xh">${stuts.index+1 }</div></td>
				<td align="center" bgcolor="white">
          <div style="width: 80px;background-color: white;">
            <a name="addTScRpRotherbillentryBtn[${stuts.index}]" id="addTScRpRotherbillentryBtn[${stuts.index}]" href="#"
               onclick="clickAddTScRpRotherbillentryBtn(${stuts.index});"></a>

              <a name="delTScRpRotherbillentryBtn[${stuts.index}]" id="delTScRpRotherbillentryBtn[${stuts.index}]" href="#"
                 onclick="clickDelTScRpRotherbillentryBtn(${stuts.index});"></a>
          </div>
        </td>
					<input name="tScRpRotherbillentryList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tScRpRotherbillentryList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tScRpRotherbillentryList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tScRpRotherbillentryList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tScRpRotherbillentryList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tScRpRotherbillentryList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tScRpRotherbillentryList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
				    <input name="tScRpRotherbillentryList[${stuts.index }].fid" type="hidden" value="${poVal.fid}"/>
				    <input name="tScRpRotherbillentryList[${stuts.index }].findex" type="hidden" value="${poVal.findex}"/>
				    <input name="tScRpRotherbillentryList[${stuts.index }].expId" type="hidden" value="${poVal.expId}" datatype="*"/>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpRotherbillentryList[${stuts.index }].expName" maxlength="32" datatype="*"
					  		type="text" class="inputxt popup-select"  style="width:120px;"
							   onkeypress="keyDownInfo(${stuts.index },event)"  onchange="clearExp(this)"
							   onblur="orderListStockBlur('${stuts.index }','expId','expName');"
					  		 value="${poVal.expName }">
					  <label class="Validform_label" style="display: none;">收支项目</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpRotherbillentryList[${stuts.index }].amount" maxlength="10"
							   onchange="setAllAmount(this)"
							   datatype="float" errormsg="请填写正确的金额"
							   type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.amount }">
					  <label class="Validform_label" style="display: none;">金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpRotherbillentryList[${stuts.index }].note" maxlength="255" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.note }">
					  <label class="Validform_label" style="display: none;">备注</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
