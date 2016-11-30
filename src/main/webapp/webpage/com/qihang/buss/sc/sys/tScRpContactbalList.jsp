<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('a[name^="addTScRpContactbalBtn"]').linkbutton({
    iconCls: 'icon-add',
    plain: true
	});  
	$('a[name^="delTScRpContactbalBtn"]').linkbutton({
    iconCls: 'icon-remove',
    plain: true
  });
  function clickAddTScRpContactbalBtn(rowIndex) {
    var tr = $("#add_tScRpContactbal_table_template tr").clone();
    $("#add_tScRpContactbal_table tr").eq(rowIndex).after(tr);
    resetTrNum('add_tScRpContactbal_table');
  }

  function clickDelTScRpContactbalBtn(rowIndex) {
    var len = $("#add_tScRpContactbal_table").find(">tr").length;
    $("#add_tScRpContactbal_table").find(">tr").eq(rowIndex).remove();
		if(rowIndex==0 && len == 1){//如果只有一行且删除这一行则删除后补一空行
      var tr = $("#add_tScRpContactbal_table_template tr").clone();
      $("#add_tScRpContactbal_table").append(tr);
    }
    resetTrNum('add_tScRpContactbal_table');
  }

    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tScRpContactbal_table").createhftable({
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
<table border="0" cellpadding="2" cellspacing="1" id="tScRpContactbal_table" style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<td align="center" bgcolor="#476f9a">操作</td>
				  <td align="center" bgcolor="#476f9a">
						业务类型(0：应付；1：应收)
				  </td>
				  <td align="center" bgcolor="#476f9a">
						核算项目(客户、供应商主键)
				  </td>
				  <td align="center" bgcolor="#476f9a">
						部门主键
				  </td>
				  <td align="center" bgcolor="#476f9a">
						职员主键
				  </td>
				  <td align="center" bgcolor="#476f9a">
						期初金额=上一期期末
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
						期末结存金额=期初金额+本期收入-本期发出
				  </td>
	</tr>
	<tbody id="add_tScRpContactbal_table">	
	<c:if test="${fn:length(tScRpContactbalList)  <= 0 }">
			<tr>
				<td align="center" bgcolor="#F6FCFF"><div style="width: 25px;background-color: white;" name="xh">1</div></td>
				<td align="center" bgcolor="white">
          <div style="width: 80px;background-color: white;">
            <a name="addTScRpContactbalBtn[0]" id="addTScRpContactbalBtn[0]" href="#"
               onclick="clickAddTScRpContactbalBtn(0);"></a>
          </div>
				</td>
					<input name="tScRpContactbalList[0].id" type="hidden"/>
					<input name="tScRpContactbalList[0].createName" type="hidden"/>
					<input name="tScRpContactbalList[0].createBy" type="hidden"/>
					<input name="tScRpContactbalList[0].createDate" type="hidden"/>
					<input name="tScRpContactbalList[0].updateName" type="hidden"/>
					<input name="tScRpContactbalList[0].updateBy" type="hidden"/>
					<input name="tScRpContactbalList[0].updateDate" type="hidden"/>
					<input name="tScRpContactbalList[0].year" type="hidden"/>
					<input name="tScRpContactbalList[0].period" type="hidden"/>
					<input name="tScRpContactbalList[0].sonID" type="hidden"/>
					<input name="tScRpContactbalList[0].fid" type="hidden"/>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[0].rp" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">业务类型(0：应付；1：应收)</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[0].itemID" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">核算项目(客户、供应商主键)</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[0].deptID" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">部门主键</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[0].empID" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">职员主键</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[0].begBal" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">期初金额=上一期期末</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[0].debit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本期收入金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[0].credit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本期发出金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[0].ytdDebit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本年累计收入金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[0].ytdCredit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本年累计发出金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[0].endBal" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">期末结存金额=期初金额+本期收入-本期发出</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tScRpContactbalList)  > 0 }">
		<c:forEach items="${tScRpContactbalList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="white"><div style="width: 30px;background-color: white;" name="xh">${stuts.index+1 }</div></td>
				<td align="center" bgcolor="white">
          <div style="width: 80px;background-color: white;">
            <a name="addTScRpContactbalBtn[${stuts.index}]" id="addTScRpContactbalBtn[${stuts.index}]" href="#"
               onclick="clickAddTScRpContactbalBtn(${stuts.index});"></a>

              <a name="delTScRpContactbalBtn[${stuts.index}]" id="delTScRpContactbalBtn[${stuts.index}]" href="#"
                 onclick="clickDelTScRpContactbalBtn(${stuts.index});"></a>
          </div>
        </td>
					<input name="tScRpContactbalList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tScRpContactbalList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tScRpContactbalList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tScRpContactbalList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tScRpContactbalList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tScRpContactbalList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tScRpContactbalList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tScRpContactbalList[${stuts.index }].year" type="hidden" value="${poVal.year }"/>
					<input name="tScRpContactbalList[${stuts.index }].period" type="hidden" value="${poVal.period }"/>
					<input name="tScRpContactbalList[${stuts.index }].sonID" type="hidden" value="${poVal.sonID }"/>
					<input name="tScRpContactbalList[${stuts.index }].fid" type="hidden" value="${poVal.fid }"/>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[${stuts.index }].rp" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.rp }">
					  <label class="Validform_label" style="display: none;">业务类型(0：应付；1：应收)</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[${stuts.index }].itemID" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.itemID }">
					  <label class="Validform_label" style="display: none;">核算项目(客户、供应商主键)</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[${stuts.index }].deptID" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.deptID }">
					  <label class="Validform_label" style="display: none;">部门主键</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[${stuts.index }].empID" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.empID }">
					  <label class="Validform_label" style="display: none;">职员主键</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[${stuts.index }].begBal" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.begBal }">
					  <label class="Validform_label" style="display: none;">期初金额=上一期期末</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[${stuts.index }].debit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.debit }">
					  <label class="Validform_label" style="display: none;">本期收入金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[${stuts.index }].credit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.credit }">
					  <label class="Validform_label" style="display: none;">本期发出金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[${stuts.index }].ytdDebit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.ytdDebit }">
					  <label class="Validform_label" style="display: none;">本年累计收入金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[${stuts.index }].ytdCredit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.ytdCredit }">
					  <label class="Validform_label" style="display: none;">本年累计发出金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScRpContactbalList[${stuts.index }].endBal" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.endBal }">
					  <label class="Validform_label" style="display: none;">期末结存金额=期初金额+本期收入-本期发出</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
