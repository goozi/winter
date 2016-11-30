<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('a[name^="addVScCheckspeedbalBtn"]').linkbutton({
    iconCls: 'icon-add',
    plain: true
	});  
	$('a[name^="delVScCheckspeedbalBtn"]').linkbutton({
    iconCls: 'icon-remove',
    plain: true
  });
  function clickAddVScCheckspeedbalBtn(rowIndex) {
    var tr = $("#add_vScCheckspeedbal_table_template tr").clone();
    $("#add_vScCheckspeedbal_table tr").eq(rowIndex).after(tr);
    resetTrNum('add_vScCheckspeedbal_table');
  }

  function clickDelVScCheckspeedbalBtn(rowIndex) {
    var len = $("#add_vScCheckspeedbal_table").find(">tr").length;
    $("#add_vScCheckspeedbal_table").find(">tr").eq(rowIndex).remove();
		if(rowIndex==0 && len == 1){//如果只有一行且删除这一行则删除后补一空行
      var tr = $("#add_vScCheckspeedbal_table_template tr").clone();
      $("#add_vScCheckspeedbal_table").append(tr);
    }
    resetTrNum('add_vScCheckspeedbal_table');
  }

    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#vScCheckspeedbal_table").createhftable({
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
		<%--var datalength = ${fn:length(vScCheckspeedbalList)};--%>
		<%--$("#vScCheckspeedbalListSize").val(datalength);--%>
		<%--var vScCheckstageListSize = $("#vScCheckstageListSize").val();--%>
		<%--if($("#isAccountOpen").val()=="true") {//允许负库存出库--%>
			<%--if (parseInt(datalength) == 0) {//如果未审核单据数量为0，则允许结账--%>
				<%--$("#openType1").removeAttr("disabled");--%>
				<%--if ($("#isAccountOpen").val() == "true") {//判断已开账才允许反结账--%>
					<%--$("#openType2").removeAttr("disabled");--%>
				<%--}--%>
			<%--}--%>
		<%--}else{//不允许负库存结账--%>
			<%--if (parseInt(datalength) == 0 && vScCheckstageListSize != "" && parseInt(vScCheckstageListSize) == 0) {//如果未审核单据数量为0，且负库存情况也为0，则允许结账--%>
				<%--$("#openType1").removeAttr("disabled");--%>
				<%--if ($("#isAccountOpen").val() == "true") {//判断已开账才允许反结账--%>
					<%--$("#openType2").removeAttr("disabled");--%>
				<%--}--%>
			<%--}--%>
		<%--}--%>
    });
</script>
<table border="0" cellpadding="2" cellspacing="1" id="vScCheckspeedbal_table" style="background-color: #cbccd2" totalRow="false">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<td align="center" bgcolor="#476f9a" style="display: none">操作</td>
				  <td align="center" bgcolor="#476f9a">
						仓库
				  </td>
				  <td align="center" bgcolor="#476f9a">
						商品编号
				  </td>
				  <td align="center" bgcolor="#476f9a">
						商品名称
				  </td>
				  <td align="center" bgcolor="#476f9a">
						批号
				  </td>
				  <td align="center" bgcolor="#476f9a">
						单位
				  </td>
				  <td align="center" bgcolor="#476f9a" total="true">
						箱数
				  </td>
				  <td align="center" bgcolor="#476f9a" total="true">
						散数
				  </td>
				  <td align="center" bgcolor="#476f9a" total="true">
						基本数量
				  </td>
				  <td align="center" bgcolor="#476f9a" total="true">
						辅助数量
				  </td>
				  <td align="center" bgcolor="#476f9a">
						分支机构
				  </td>
	</tr>
	<tbody id="add_vScCheckspeedbal_table">	
	<c:if test="${fn:length(vScCheckspeedbalList)  <= 0 }">
			<tr>
				<td align="center" bgcolor="#F6FCFF"><div style="width: 25px;background-color: white;" name="xh">1</div></td>
				<td align="center" bgcolor="white" style="display: none">
          <div style="width: 80px;background-color: white;">
            <a name="addVScCheckspeedbalBtn[0]" id="addVScCheckspeedbalBtn[0]" href="#"
               onclick="clickAddVScCheckspeedbalBtn(0);"></a>
          </div>
				</td>
					<input name="vScCheckspeedbalList[0].date" type="hidden"/>
					<input name="vScCheckspeedbalList[0].stockid" type="hidden"/>
					<input name="vScCheckspeedbalList[0].itemid" type="hidden"/>
					<input name="vScCheckspeedbalList[0].id" type="hidden"/>
					<input name="vScCheckspeedbalList[0].coefficient" type="hidden"/>
				  <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[0].sonname" maxlength="80" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">仓库</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[0].itemnumber" maxlength="80" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">商品编号</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[0].itemname" maxlength="100" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">商品名称</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[0].batchno" maxlength="100" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">批号</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[0].unitname" maxlength="80" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">单位</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[0].bigqty" maxlength="19" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">箱数</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[0].smallqty" maxlength="23" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">散数</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[0].qty" maxlength="23" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">基本数量</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[0].secqty" maxlength="23" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">辅助数量</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[0].departmentname" maxlength="100" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">分支机构</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(vScCheckspeedbalList)  > 0 }">
		<c:forEach items="${vScCheckspeedbalList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="white"><div style="width: 30px;background-color: white;" name="xh">${stuts.index+1 }</div></td>
				<td align="center" bgcolor="white" style="display: none">
          <div style="width: 80px;background-color: white;">
            <a name="addVScCheckspeedbalBtn[${stuts.index}]" id="addVScCheckspeedbalBtn[${stuts.index}]" href="#"
               onclick="clickAddVScCheckspeedbalBtn(${stuts.index});"></a>

              <a name="delVScCheckspeedbalBtn[${stuts.index}]" id="delVScCheckspeedbalBtn[${stuts.index}]" href="#"
                 onclick="clickDelVScCheckspeedbalBtn(${stuts.index});"></a>
          </div>
        </td>
					<input name="vScCheckspeedbalList[${stuts.index }].date" type="hidden" value="${poVal.date }"/>
					<input name="vScCheckspeedbalList[${stuts.index }].stockid" type="hidden" value="${poVal.stockid }"/>
					<input name="vScCheckspeedbalList[${stuts.index }].itemid" type="hidden" value="${poVal.itemid }"/>
					<input name="vScCheckspeedbalList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="vScCheckspeedbalList[${stuts.index }].coefficient" type="hidden" value="${poVal.coefficient }"/>
				   <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[${stuts.index }].sonname" maxlength="80" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.sonname }">
					  <label class="Validform_label" style="display: none;">仓库</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[${stuts.index }].itemnumber" maxlength="80" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.itemnumber }">
					  <label class="Validform_label" style="display: none;">商品编号</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[${stuts.index }].itemname" maxlength="100" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.itemname }">
					  <label class="Validform_label" style="display: none;">商品名称</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[${stuts.index }].batchno" maxlength="100" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.batchno }">
					  <label class="Validform_label" style="display: none;">批号</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[${stuts.index }].unitname" maxlength="80" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.unitname }">
					  <label class="Validform_label" style="display: none;">单位</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[${stuts.index }].bigqty" maxlength="19" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.bigqty }">
					  <label class="Validform_label" style="display: none;">箱数</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[${stuts.index }].smallqty" maxlength="23" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.smallqty }">
					  <label class="Validform_label" style="display: none;">散数</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[${stuts.index }].qty" maxlength="23" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.qty }">
					  <label class="Validform_label" style="display: none;">基本数量</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[${stuts.index }].secqty" maxlength="23" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.secqty }">
					  <label class="Validform_label" style="display: none;">辅助数量</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="vScCheckspeedbalList[${stuts.index }].departmentname" maxlength="100" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.departmentname }">
					  <label class="Validform_label" style="display: none;">分支机构</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
