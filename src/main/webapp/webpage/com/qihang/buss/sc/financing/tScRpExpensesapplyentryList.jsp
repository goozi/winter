<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('a[name^="addTScRpExpensesapplyentryBtn"]').linkbutton({
    iconCls: 'icon-add',
    plain: true
	});  
	$('a[name^="delTScRpExpensesapplyentryBtn"]').linkbutton({
    iconCls: 'icon-remove',
    plain: true
  });
  function clickAddTScRpExpensesapplyentryBtn(rowIndex) {
    var tr = $("#add_tScRpExpensesapplyentry_table_template tr").clone();
    $("#add_tScRpExpensesapplyentry_table tr").eq(rowIndex).after(tr);
    resetTrNum('add_tScRpExpensesapplyentry_table');
  }

  function clickDelTScRpExpensesapplyentryBtn(rowIndex) {
    var len = $("#add_tScRpExpensesapplyentry_table").find(">tr").length;
    $("#add_tScRpExpensesapplyentry_table").find(">tr").eq(rowIndex).remove();
		if(rowIndex==0 && len == 1){//如果只有一行且删除这一行则删除后补一空行
      var tr = $("#add_tScRpExpensesapplyentry_table_template tr").clone();
      $("#add_tScRpExpensesapplyentry_table").append(tr);
    }
	  setAllAmount(null);
    resetTrNum('add_tScRpExpensesapplyentry_table');
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
	    $("#tScRpExpensesapplyentry_table").createhftable({
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
<table border="0" cellpadding="2" cellspacing="1" id="tScRpExpensesapplyentry_table" totalRow="true" style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<td align="center" bgcolor="#476f9a">操作</td>
				  <td align="center" bgcolor="#476f9a">
						收支项目
				  </td>
				  <td align="center" bgcolor="#476f9a" total="true">
						金额
				  </td>
				  <td align="center" bgcolor="#476f9a" total="true">
						已报销金额
				  </td>
				  <td align="center" bgcolor="#476f9a" total="true">
						未报销金额
				  </td>
				  <td align="center" bgcolor="#476f9a">
						备注
				  </td>
	</tr>
	<tbody id="add_tScRpExpensesapplyentry_table">	
	<c:if test="${fn:length(tScRpExpensesapplyentryList)  <= 0 }">
			<tr>
				<td align="center" bgcolor="#F6FCFF"><div style="width: 25px;background-color: white;" name="xh">1</div></td>
				<td align="center" bgcolor="white">
          <div style="width: 80px;background-color: white;">
            <a name="addTScRpExpensesapplyentryBtn[0]" id="addTScRpExpensesapplyentryBtn[0]" href="#"
               onclick="clickAddTScRpExpensesapplyentryBtn(0);"></a>
			  <a name="delTScRpExpensesapplyentryBtn[0]" id="delTScRpExpensesapplyentryBtn[0]" href="#"
				 onclick="clickDelTScRpExpensesapplyentryBtn(0);"></a>

		  </div>
				</td>
					<input name="tScRpExpensesapplyentryList[0].id" type="hidden"/>
					<input name="tScRpExpensesapplyentryList[0].createName" type="hidden"/>
					<input name="tScRpExpensesapplyentryList[0].createBy" type="hidden"/>
					<input name="tScRpExpensesapplyentryList[0].createDate" type="hidden"/>
					<input name="tScRpExpensesapplyentryList[0].updateName" type="hidden"/>
					<input name="tScRpExpensesapplyentryList[0].updateBy" type="hidden"/>
					<input name="tScRpExpensesapplyentryList[0].updateDate" type="hidden"/>
					<input name="tScRpExpensesapplyentryList[0].fid" type="hidden"/>
					<input name="tScRpExpensesapplyentryList[0].findex" type="hidden" value="1"/>
					<input name="tScRpExpensesapplyentryList[0].expId" type="hidden" datatype="*"/>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpExpensesapplyentryList[0].expName" maxlength="32"
							 datatype="*"
							   onkeypress="keyDownInfo(0,event)"
							   onblur="orderListStockBlur('0','expId','expName');"
					  		type="text" class="inputxt popup-select"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">收支项目</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpExpensesapplyentryList[0].amount" maxlength="32"
							   onchange="setAllAmount(this)"
							   datatype="d"
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpExpensesapplyentryList[0].haveAmount" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" value="0" readonly="readonly" >
					  <label class="Validform_label" style="display: none;">已报销金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpExpensesapplyentryList[0].notAmount" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" value="0" readonly="readonly">
					  <label class="Validform_label" style="display: none;">未报销金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpExpensesapplyentryList[0].note" maxlength="255"

					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">备注</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tScRpExpensesapplyentryList)  > 0 }">
		<c:forEach items="${tScRpExpensesapplyentryList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="white"><div style="width: 30px;background-color: white;" name="xh">${stuts.index+1 }</div></td>
				<td align="center" bgcolor="white">
          <div style="width: 80px;background-color: white;">
            <a name="addTScRpExpensesapplyentryBtn[${stuts.index}]" id="addTScRpExpensesapplyentryBtn[${stuts.index}]" href="#"
               onclick="clickAddTScRpExpensesapplyentryBtn(${stuts.index});"></a>

              <a name="delTScRpExpensesapplyentryBtn[${stuts.index}]" id="delTScRpExpensesapplyentryBtn[${stuts.index}]" href="#"
                 onclick="clickDelTScRpExpensesapplyentryBtn(${stuts.index});"></a>
          </div>
        </td>
					<input name="tScRpExpensesapplyentryList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tScRpExpensesapplyentryList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tScRpExpensesapplyentryList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tScRpExpensesapplyentryList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tScRpExpensesapplyentryList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tScRpExpensesapplyentryList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tScRpExpensesapplyentryList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tScRpExpensesapplyentryList[${stuts.index }].fid" type="hidden" value="${poVal.fid}"/>
					<input name="tScRpExpensesapplyentryList[${stuts.index }].findex" type="hidden" value="${poVal.findex}"/>
					<input name="tScRpExpensesapplyentryList[${stuts.index }].expId" type="hidden" datatype="*" value="${poVal.expId}"/>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpExpensesapplyentryList[${stuts.index }].expName" maxlength="32"
							   onkeypress="keyDownInfo(${stuts.index },event)"
							   onblur="orderListStockBlur('${stuts.index }','expId','expName');"
							   datatype="*"
					  		type="text" class="inputxt popup-select"  style="width:120px;"
					  		 value="${poVal.expName }">
					  <label class="Validform_label" style="display: none;">收支项目</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpExpensesapplyentryList[${stuts.index }].amount" maxlength="32"
							   onchange="setAllAmount(this)"
							   datatype="d" errormsg="请填写正确的金额"
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.amount }">
					  <label class="Validform_label" style="display: none;">金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpExpensesapplyentryList[${stuts.index }].haveAmount" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"  readonly="readonly"
					  		 value="${poVal.haveAmount }">
					  <label class="Validform_label" style="display: none;">已报销金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpExpensesapplyentryList[${stuts.index }].notAmount" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"  readonly="readonly"
					  		 value="${poVal.notAmount }">
					  <label class="Validform_label" style="display: none;">未报销金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpExpensesapplyentryList[${stuts.index }].note" maxlength="255" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.note }">
					  <label class="Validform_label" style="display: none;">备注</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
