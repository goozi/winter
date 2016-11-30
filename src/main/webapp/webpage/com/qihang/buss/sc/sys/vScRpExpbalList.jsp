<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('a[name^="addTScRpExpbalBtn"]').linkbutton({
    iconCls: 'icon-add',
    plain: true
	});  
	$('a[name^="delTScRpExpbalBtn"]').linkbutton({
    iconCls: 'icon-remove',
    plain: true
  });
  function clickAddTScRpExpbalBtn(rowIndex) {
    var tr = $("#add_tScRpExpbal_table_template tr").clone();
    $("#add_tScRpExpbal_table tr").eq(rowIndex).after(tr);
    resetTrNum('add_tScRpExpbal_table');
  }

  function clickDelTScRpExpbalBtn(rowIndex) {
    var len = $("#add_tScRpExpbal_table").find(">tr").length;
    $("#add_tScRpExpbal_table").find(">tr").eq(rowIndex).remove();
		if(rowIndex==0 && len == 1){//如果只有一行且删除这一行则删除后补一空行
      var tr = $("#add_tScRpExpbal_table_template tr").clone();
      $("#add_tScRpExpbal_table").append(tr);
    }
    resetTrNum('add_tScRpExpbal_table');
  }

    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tScRpExpbal_table").createhftable({
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
    });
</script>
<table border="0" cellpadding="2" cellspacing="1" id="tScRpExpbal_table" style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<td align="center" bgcolor="#476f9a" style="display: none">操作</td>
				  <td align="center" bgcolor="#476f9a">
						结算账户
				  </td>
				  <td align="center" bgcolor="#476f9a">
						部门
				  </td>
				  <td align="center" bgcolor="#476f9a">
						职员
				  </td>
				  <td align="center" bgcolor="#476f9a">
						期初金额
				  </td>
				  <td align="center" bgcolor="#476f9a">
						本期收入金额
				  </td>
				  <td align="center" bgcolor="#476f9a">
						本期发出金额
				  </td>
				  <td align="center" bgcolor="#476f9a">
						本年累计收入金额
				  </td>
				  <td align="center" bgcolor="#476f9a">
						本年累计发出金额
				  </td>
				  <td align="center" bgcolor="#476f9a">
						期末结存金额
				  </td>
	</tr>
	<tbody id="add_tScRpExpbal_table">	
	<c:if test="${fn:length(tScRpExpbalList)  <= 0 }">
			<tr>
				<td align="center" bgcolor="#F6FCFF"><div style="width: 25px;background-color: white;" name="xh">1</div></td>
				<td align="center" bgcolor="white" style="display: none">
          <div style="width: 80px;background-color: white;">
            <a name="addTScRpExpbalBtn[0]" id="addTScRpExpbalBtn[0]" href="#"
               onclick="clickAddTScRpExpbalBtn(0);"></a>
          </div>
				</td>
					<input name="tScRpExpbalList[0].id" type="hidden"/>
					<input name="tScRpExpbalList[0].createName" type="hidden"/>
					<input name="tScRpExpbalList[0].createBy" type="hidden"/>
					<input name="tScRpExpbalList[0].createDate" type="hidden"/>
					<input name="tScRpExpbalList[0].updateName" type="hidden"/>
					<input name="tScRpExpbalList[0].updateBy" type="hidden"/>
					<input name="tScRpExpbalList[0].updateDate" type="hidden"/>
					<input name="tScRpExpbalList[0].year" type="hidden"/>
					<input name="tScRpExpbalList[0].period" type="hidden"/>
					<input name="tScRpExpbalList[0].sonID" type="hidden"/>
					<input name="tScRpExpbalList[0].fid" type="hidden"/>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpExpbalList[0].name" maxlength="32"
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">结算账户</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpExpbalList[0].departname" maxlength="32"
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">部门</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpExpbalList[0].empname" maxlength="32"
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">职员</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpExpbalList[0].begBal" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">期初金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpExpbalList[0].debit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本期收入金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpExpbalList[0].credit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本期发出金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpExpbalList[0].ytdDebit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本年累计收入金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpExpbalList[0].ytdCredit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本年累计发出金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpExpbalList[0].endBal" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">期末结存金额</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tScRpExpbalList)  > 0 }">
		<c:forEach items="${tScRpExpbalList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="white"><div style="width: 30px;background-color: white;" name="xh">${stuts.index+1 }</div></td>
				<td align="center" bgcolor="white" style="display: none">
          <div style="width: 80px;background-color: white;">
            <a name="addTScRpExpbalBtn[${stuts.index}]" id="addTScRpExpbalBtn[${stuts.index}]" href="#"
               onclick="clickAddTScRpExpbalBtn(${stuts.index});"></a>

              <a name="delTScRpExpbalBtn[${stuts.index}]" id="delTScRpExpbalBtn[${stuts.index}]" href="#"
                 onclick="clickDelTScRpExpbalBtn(${stuts.index});"></a>
          </div>
        </td>
					<input name="tScRpExpbalList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tScRpExpbalList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tScRpExpbalList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tScRpExpbalList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tScRpExpbalList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tScRpExpbalList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tScRpExpbalList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tScRpExpbalList[${stuts.index }].year" type="hidden" value="${poVal.year }"/>
					<input name="tScRpExpbalList[${stuts.index }].period" type="hidden" value="${poVal.period }"/>
					<input name="tScRpExpbalList[${stuts.index }].sonID" type="hidden" value="${poVal.sonID }"/>
					<input name="tScRpExpbalList[${stuts.index }].fid" type="hidden" value="${poVal.fid }"/>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpExpbalList[${stuts.index }].name" maxlength="32"
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.name }">
					  <label class="Validform_label" style="display: none;">结算账户</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpExpbalList[${stuts.index }].departname" maxlength="32"
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.departname }">
					  <label class="Validform_label" style="display: none;">部门主键</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpExpbalList[${stuts.index }].empname" maxlength="32"
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.empname }">
					  <label class="Validform_label" style="display: none;">职员主键</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpExpbalList[${stuts.index }].begBal" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.begBal }">
					  <label class="Validform_label" style="display: none;">期初金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpExpbalList[${stuts.index }].debit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.debit }">
					  <label class="Validform_label" style="display: none;">本期收入金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpExpbalList[${stuts.index }].credit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.credit }">
					  <label class="Validform_label" style="display: none;">本期发出金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpExpbalList[${stuts.index }].ytdDebit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.ytdDebit }">
					  <label class="Validform_label" style="display: none;">本年累计收入金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpExpbalList[${stuts.index }].ytdCredit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.ytdCredit }">
					  <label class="Validform_label" style="display: none;">本年累计发出金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpExpbalList[${stuts.index }].endBal" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.endBal }">
					  <label class="Validform_label" style="display: none;">期末结存金额</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
