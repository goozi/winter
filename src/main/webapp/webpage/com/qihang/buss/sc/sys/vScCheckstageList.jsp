<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('a[name^="addVScCheckstageBtn"]').linkbutton({
    iconCls: 'icon-add',
    plain: true
	});  
	$('a[name^="delVScCheckstageBtn"]').linkbutton({
    iconCls: 'icon-remove',
    plain: true
  });
  function clickAddVScCheckstageBtn(rowIndex) {
    var tr = $("#add_vScCheckstage_table_template tr").clone();
    $("#add_vScCheckstage_table tr").eq(rowIndex).after(tr);
    resetTrNum('add_vScCheckstage_table');
  }

  function clickDelVScCheckstageBtn(rowIndex) {
    var len = $("#add_vScCheckstage_table").find(">tr").length;
    $("#add_vScCheckstage_table").find(">tr").eq(rowIndex).remove();
		if(rowIndex==0 && len == 1){//如果只有一行且删除这一行则删除后补一空行
      var tr = $("#add_vScCheckstage_table_template tr").clone();
      $("#add_vScCheckstage_table").append(tr);
    }
    resetTrNum('add_vScCheckstage_table');
  }

    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#vScCheckstage_table").createhftable({
	    	height: (h-50)+'px',
			  width:'auto',
			  fixFooter:false
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
		$("input").attr("readonly","readonly");//置所有输入框为只读
		$("input").removeAttr("onclick");//移除日期控件的点击事件
		<%--//获得当前数据的行数--%>
		<%--debugger;--%>
		<%--var datalength = ${fn:length(vScCheckstageList)};--%>
		<%--$("#vScCheckstageListSize").val(datalength);--%>
		<%--var vScCheckspeedbalListSize = $("#vScCheckspeedbalListSize").val();--%>
		<%--if($("#isAccountOpen").val()=="true") {//允许负库存出库--%>
			<%--if (parseInt(datalength) == 0) {//如果未审核单据数量为0，则允许结账--%>
				<%--$("#openType1").removeAttr("disabled");--%>
				<%--if ($("#isAccountOpen").val() == "true") {//判断已开账才允许反结账--%>
					<%--$("#openType2").removeAttr("disabled");--%>
				<%--}--%>
			<%--}--%>
		<%--}else{//不允许负库存结账--%>
			<%--if (parseInt(datalength) == 0 && vScCheckspeedbalListSize != "" && parseInt(vScCheckspeedbalListSize) == 0) {//如果未审核单据数量为0，且负库存情况也为0，则允许结账--%>
				<%--$("#openType1",).removeAttr("disabled");--%>
				<%--if ($("#isAccountOpen").val() == "true") {//判断已开账才允许反结账--%>
					<%--$("#openType2").removeAttr("disabled");--%>
				<%--}--%>
			<%--}--%>
		<%--}--%>
    });
</script>
<table border="0" cellpadding="2" cellspacing="1" id="vScCheckstage_table" style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<td align="center" bgcolor="#476f9a" style="display: none">操作</td>
				  <td align="center" bgcolor="#476f9a">
						单据类型
				  </td>
				  <td align="center" bgcolor="#476f9a">
						单据编号
				  </td>
				  <td align="center" bgcolor="#476f9a">
						单据日期
				  </td>
				  <td align="center" bgcolor="#476f9a">
						分支机构
				  </td>
	</tr>
	<tbody id="add_vScCheckstage_table">	
	<c:if test="${fn:length(vScCheckstageList)  <= 0 }">
			<tr>
				<td align="center" bgcolor="#F6FCFF"><div style="width: 25px;background-color: white;" name="xh">1</div></td>
				<td align="center" bgcolor="white" style="display: none">
          <div style="width: 80px;background-color: white;">
            <a name="addVScCheckstageBtn[0]" id="addVScCheckstageBtn[0]" href="#"
               onclick="clickAddVScCheckstageBtn(0);"></a>
          </div>
				</td>
					<input name="vScCheckstageList[0].checkstate" type="hidden"/>
					<input name="vScCheckstageList[0].trantype" type="hidden"/>
					<input name="vScCheckstageList[0].sonid" type="hidden"/>
					<input name="vScCheckstageList[0].id" type="hidden"/>
				  <td align="left" bgcolor="white">
					  	<input name="vScCheckstageList[0].billName" maxlength="100" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">单据类型</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="vScCheckstageList[0].billno" maxlength="50" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">单据编号</label>
					</td>
				  <td align="left" bgcolor="white">
							<input name="vScCheckstageList[0].date" maxlength="32" 
					  		type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"
					  		>
					  <label class="Validform_label" style="display: none;">单据日期</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="vScCheckstageList[0].departmentname" maxlength="80" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">分支机构</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(vScCheckstageList)  > 0 }">
		<c:forEach items="${vScCheckstageList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="white"><div style="width: 30px;background-color: white;" name="xh">${stuts.index+1 }</div></td>
				<td align="center" bgcolor="white" style="display: none">
          <div style="width: 80px;background-color: white;">
            <a name="addVScCheckstageBtn[${stuts.index}]" id="addVScCheckstageBtn[${stuts.index}]" href="#"
               onclick="clickAddVScCheckstageBtn(${stuts.index});"></a>

              <a name="delVScCheckstageBtn[${stuts.index}]" id="delVScCheckstageBtn[${stuts.index}]" href="#"
                 onclick="clickDelVScCheckstageBtn(${stuts.index});"></a>
          </div>
        </td>
					<input name="vScCheckstageList[${stuts.index }].checkstate" type="hidden" value="${poVal.checkstate }"/>
					<input name="vScCheckstageList[${stuts.index }].trantype" type="hidden" value="${poVal.trantype }"/>
					<input name="vScCheckstageList[${stuts.index }].sonid" type="hidden" value="${poVal.sonid }"/>
					<input name="vScCheckstageList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
				   <td align="left" bgcolor="white">
					  	<input name="vScCheckstageList[${stuts.index }].billName" maxlength="100" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.billName }">
					  <label class="Validform_label" style="display: none;">单据类型</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="vScCheckstageList[${stuts.index }].billno" maxlength="50" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.billno }">
					  <label class="Validform_label" style="display: none;">单据编号</label>
				   </td>
				   <td align="left" bgcolor="white">
							<input name="vScCheckstageList[${stuts.index }].date" maxlength="32" 
					  		type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"
					                
					                value="<fmt:formatDate value='${poVal.date}' type="date" pattern="yyyy-MM-dd"/>">  
					  <label class="Validform_label" style="display: none;">单据日期</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="vScCheckstageList[${stuts.index }].departmentname" maxlength="80" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.departmentname }">
					  <label class="Validform_label" style="display: none;">分支机构</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
