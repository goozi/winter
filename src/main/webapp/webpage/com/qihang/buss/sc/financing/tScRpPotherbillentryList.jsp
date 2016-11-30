<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('a[name^="addTScRpPotherbillentryBtn"]').linkbutton({
    iconCls: 'icon-add',
    plain: true
	});  
	$('a[name^="delTScRpPotherbillentryBtn"]').linkbutton({
    iconCls: 'icon-remove',
    plain: true
  });
  function clickAddTScRpPotherbillentryBtn(rowIndex) {
	  var tr = $("#add_tScRpPotherbillentry_table_template tr").clone();
	  $("#add_tScRpPotherbillentry_table tr").eq(rowIndex).after(tr);
	  resetTrNum('add_tScRpPotherbillentry_table');
  }

  function clickDelTScRpPotherbillentryBtn(rowIndex,obj) {
    var len = $("#add_tScRpPotherbillentry_table").find(">tr").length;
    $("#add_tScRpPotherbillentry_table").find(">tr").eq(rowIndex).remove();
	if(rowIndex==0 && len == 1){//如果只有一行且删除这一行则删除后补一空行
      		var tr = $("#add_tScRpPotherbillentry_table_template tr").clone();
      $("#add_tScRpPotherbillentry_table").append(tr);
		$("#itemName").removeAttr("disabled");
    }
	  //$(obj).parent().parent().parent("tr").remove();
      resetTrNum('add_tScRpPotherbillentry_table');
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
	    $("#tScRpPotherbillentry_table").createhftable({
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
<table border="0" cellpadding="2" cellspacing="1" id="tScRpPotherbillentry_table" totalRow="true" style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a"  style="width:25px;">序号</td>
		<td align="center" bgcolor="#476f9a" style="width: 80px;">操作</td>
				  <td align="center" bgcolor="#476f9a" style="width: 124px;">
						收支项目
				  </td>
				  <td align="center" bgcolor="#476f9a" style="width: 124px;">
						源单编号
				  </td>
				  <td align="center" bgcolor="#476f9a" style="width: 124px;" total="true">
						源单已报销金额
				  </td>
				  <td align="center" bgcolor="#476f9a" style="width: 124px;" total="true">
						源单未报销金额
				  </td>
				  <td align="center" bgcolor="#476f9a" style="width: 124px;" total="true">
						本次支出
				  </td>
				  <td align="center" bgcolor="#476f9a" style="width: 124px;">
						备注
				  </td>
	</tr>
	<tbody id="add_tScRpPotherbillentry_table">
	<c:if test="${fn:length(tScRpPotherbillentryList)  <= 0 }">
			<tr>
				<td align="center" bgcolor="#F6FCFF"><div style="width: 25px;background-color: white;" name="xh">1</div></td>
				<td align="center" bgcolor="white">
          <div style="width: 80px;background-color: white;">

            <a name="addTScRpPotherbillentryBtn[0]" id="addTScRpPotherbillentryBtn[0]" href="#"
               onclick="clickAddTScRpPotherbillentryBtn(0);"></a>

			  <a name="delTScRpPotherbillentryBtn[0]" id="delTScRpPotherbillentryBtn[0]" href="#"
				 onclick="clickDelTScRpPotherbillentryBtn(0,this);"></a>
          </div>
				</td>
					<input name="tScRpPotherbillentryList[0].id" type="hidden"/>
					<input name="tScRpPotherbillentryList[0].createName" type="hidden"/>
					<input name="tScRpPotherbillentryList[0].createBy" type="hidden"/>
					<input name="tScRpPotherbillentryList[0].createDate" type="hidden"/>
					<input name="tScRpPotherbillentryList[0].updateName" type="hidden"/>
					<input name="tScRpPotherbillentryList[0].updateBy" type="hidden"/>
					<input name="tScRpPotherbillentryList[0].updateDate" type="hidden"/>
					<input name="tScRpPotherbillentryList[0].fid" type="hidden"/>
					<input name="tScRpPotherbillentryList[0].findex" type="hidden" value="1"/>
					<input name="tScRpPotherbillentryList[0].expId" type="hidden" datatype="*"/>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpPotherbillentryList[0].expName" maxlength="32"
							   datatype="*"
							   onkeypress="keyDownInfo(0,event)"
							   onblur="orderListStockBlur('0','expId','expName');"
					  		type="text" class="inputxt popup-select"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">收支项目</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpPotherbillentryList[0].billNoSrc" maxlength="32"  readonly="readonly"
					  		type="text" class="inputxt"  style="width:120px;background-color: rgb(226, 226, 226);">
					  <label class="Validform_label" style="display: none;">源单编号</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpPotherbillentryList[0].haveAmountSrc" maxlength="32" readonly="readonly" value="0"
					  		type="text" class="inputxt"  style="width:120px;background-color: rgb(226, 226, 226);" datatype="float">
					  <label class="Validform_label" style="display: none;">源单已报销金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpPotherbillentryList[0].notAmountSrc" maxlength="32" readonly="readonly" value="0"
					  		type="text" class="inputxt"  style="width:120px;background-color: rgb(226, 226, 226);" datatype="float" >
					  <label class="Validform_label" style="display: none;">源单未报销金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpPotherbillentryList[0].amount" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" datatype="d" onchange="setAllAmount(this);">
					  <label class="Validform_label" style="display: none;">本次支出</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpPotherbillentryList[0].note" maxlength="255" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">备注</label>
					</td>
				<input name="tScRpPotherbillentryList[0].classIdSrc"  type="hidden"/>
				<input name="tScRpPotherbillentryList[0].interIdSrc" type="hidden"/>
				<input name="tScRpPotherbillentryList[0].fidSrc"type="hidden" />

			</tr>
	</c:if>
	<c:if test="${fn:length(tScRpPotherbillentryList)  > 0 }">
		<c:choose>

			<c:when test="${param.load == 'fcopy'}">fdghd${param.load}
				<c:forEach items="${tScRpPotherbillentryList}" var="poVal" varStatus="stuts">
					<tr>
						<td align="center" bgcolor="white"><div style="width: 30px;background-color: white;" name="xh">${stuts.index+1 }</div></td>
						<td align="center" bgcolor="white">
							<div style="width: 80px;background-color: white;">

								<a name="addTScRpPotherbillentryBtn[${stuts.index}]" id="addTScRpPotherbillentryBtn[${stuts.index}]" href="#"
								   onclick="clickAddTScRpPotherbillentryBtn(${stuts.index});"></a>

								<a name="delTScRpPotherbillentryBtn[${stuts.index}]" id="delTScRpPotherbillentryBtn[${stuts.index}]" href="#"
								   onclick="clickDelTScRpPotherbillentryBtn(${stuts.index},this);"></a>
							</div>
						</td>
						<input name="tScRpPotherbillentryList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].fid" type="hidden" value="${poVal.fid}"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].findex" type="hidden" value="${poVal.findex}"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].expId" type="hidden" datatype="*" value="${poVal.expId}"/>
						<td align="left" bgcolor="white">
							<input name="tScRpPotherbillentryList[${stuts.index }].expName" maxlength="32"
								   type="text" class="inputxt popup-select"  style="width:120px;"
								   datatype="*"
								   onkeypress="keyDownInfo(${stuts.index },event)"
								   onblur="orderListStockBlur('${stuts.index }','expId','expName');"
								   value="${poVal.expName }">
							<label class="Validform_label" style="display: none;">收支项目</label>
						</td>
						<td align="left" bgcolor="white">
							<input name="tScRpPotherbillentryList[${stuts.index }].billNoSrc" maxlength="32"
								   type="text" class="inputxt"  style="width:120px;background-color: rgb(226, 226, 226);" readonly="readonly"
								   value="">
							<label class="Validform_label" style="display: none;">源单编号</label>
						</td>
						<td align="left" bgcolor="white">
							<input name="tScRpPotherbillentryList[${stuts.index }].haveAmountSrc" maxlength="32"
								   type="text" class="inputxt"  style="width:120px;background-color: rgb(226, 226, 226);" readonly="readonly"
								   value="">
							<label class="Validform_label" style="display: none;">源单已报销金额</label>
						</td>
						<td align="left" bgcolor="white">
							<input name="tScRpPotherbillentryList[${stuts.index }].notAmountSrc" maxlength="32"
								   type="text" class="inputxt"  style="width:120px;background-color: rgb(226, 226, 226);" readonly="readonly"
								   value="${poVal.amount }">
							<label class="Validform_label" style="display: none;">源单未报销金额</label>
						</td>
						<td align="left" bgcolor="white">
							<input name="tScRpPotherbillentryList[${stuts.index }].amount" maxlength="32"
								   type="text" class="inputxt"  style="width:120px;" onchange="setAllAmount(this);"
								   value="${poVal.amount }">
							<label class="Validform_label" style="display: none;">本次支出</label>
						</td>
						<td align="left" bgcolor="white">
							<input name="tScRpPotherbillentryList[${stuts.index }].note" maxlength="255"
								   type="text" class="inputxt"  style="width:120px;"
								   value="${poVal.note }">
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
						<input name="tScRpPotherbillentryList[${stuts.index }].classIdSrc"  type="hidden" value="" />
						<input name="tScRpPotherbillentryList[${stuts.index }].interIdSrc" type="hidden" value=""/>
						<input name="tScRpPotherbillentryList[${stuts.index }].fidSrc" type="hidden"  value=""/>

					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>adg
				<c:forEach items="${tScRpPotherbillentryList}" var="poVal" varStatus="stuts">
					<tr>
						<td align="center" bgcolor="white"><div style="width: 30px;background-color: white;" name="xh">${stuts.index+1 }</div></td>
						<td align="center" bgcolor="white">
							<div style="width: 80px;background-color: white;">

								<a name="addTScRpPotherbillentryBtn[${stuts.index}]" id="addTScRpPotherbillentryBtn[${stuts.index}]" href="#"
								   onclick="clickAddTScRpPotherbillentryBtn(${stuts.index});"></a>

								<a name="delTScRpPotherbillentryBtn[${stuts.index}]" id="delTScRpPotherbillentryBtn[${stuts.index}]" href="#"
								   onclick="clickDelTScRpPotherbillentryBtn(${stuts.index},this);"></a>
							</div>
						</td>
						<input name="tScRpPotherbillentryList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].fid" type="hidden" value="${poVal.fid}"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].findex" type="hidden" value="${poVal.findex}"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].expId" type="hidden" datatype="*" value="${poVal.expId}"/>
						<td align="left" bgcolor="white">
							<input name="tScRpPotherbillentryList[${stuts.index }].expName" maxlength="32"
								   type="text" class="inputxt popup-select"  style="width:120px; <c:if test="${not empty poVal.interIdSrc }">background-color: rgb(226, 226, 226);</c:if>"
								   <c:if test="${not empty poVal.interIdSrc }">disabled="disabled"</c:if>
								   datatype="*"
								   onkeypress="keyDownInfo(${stuts.index },event)"
								   onblur="orderListStockBlur('${stuts.index }','expId','expName');"
								   value="${poVal.expName }">
							<label class="Validform_label" style="display: none;">收支项目</label>
						</td>
						<td align="left" bgcolor="white">
							<input name="tScRpPotherbillentryList[${stuts.index }].billNoSrc" maxlength="32"
								   type="text" class="inputxt"  style="width:120px;background-color: rgb(226, 226, 226);" readonly="readonly"
								   value="${poVal.billNoSrc }">
							<label class="Validform_label" style="display: none;">源单编号</label>
						</td>
						<td align="left" bgcolor="white">
							<input name="tScRpPotherbillentryList[${stuts.index }].haveAmountSrc" maxlength="32"
								   type="text" class="inputxt"  style="width:120px;background-color: rgb(226, 226, 226);" readonly="readonly"
								   value="${poVal.haveAmountSrc }">
							<label class="Validform_label" style="display: none;">源单已报销金额</label>
						</td>
						<td align="left" bgcolor="white">
							<input name="tScRpPotherbillentryList[${stuts.index }].notAmountSrc" maxlength="32"
								   type="text" class="inputxt"  style="width:120px;background-color: rgb(226, 226, 226);" readonly="readonly"
								   value="${poVal.notAmountSrc }">
							<label class="Validform_label" style="display: none;">源单未报销金额</label>
						</td>
						<td align="left" bgcolor="white">
							<input name="tScRpPotherbillentryList[${stuts.index }].amount" maxlength="32"
								   type="text" class="inputxt"  style="width:120px;" onchange="setAllAmount(this);"
								   value="${poVal.amount }">
							<label class="Validform_label" style="display: none;">本次支出</label>
						</td>
						<td align="left" bgcolor="white">
							<input name="tScRpPotherbillentryList[${stuts.index }].note" maxlength="255"
								   type="text" class="inputxt"  style="width:120px;"
								   value="${poVal.note }">
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
						<input name="tScRpPotherbillentryList[${stuts.index }].classIdSrc" maxlength="32" type="hidden" class="inputxt" style="width:120px;" value="${poVal.classIdSrc}" readonly="readonly"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].interIdSrc" maxlength="32" type="hidden" class="inputxt" style="width:120px;" value="${poVal.interIdSrc}" readonly="readonly"/>
						<input name="tScRpPotherbillentryList[${stuts.index }].fidSrc" maxlength="32" type="hidden" class="inputxt" style="width:120px;" value="${poVal.fidSrc}" readonly="readonly"/>

					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</c:if>	
	</tbody>
</table>
