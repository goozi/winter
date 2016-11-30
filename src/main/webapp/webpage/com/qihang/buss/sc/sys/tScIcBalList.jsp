<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('a[name^="addTScIcBalBtn"]').linkbutton({
    iconCls: 'icon-add',
    plain: true
	});  
	$('a[name^="delTScIcBalBtn"]').linkbutton({
    iconCls: 'icon-remove',
    plain: true
  });
  function clickAddTScIcBalBtn(rowIndex) {
    var tr = $("#add_tScIcBal_table_template tr").clone();
    $("#add_tScIcBal_table tr").eq(rowIndex).after(tr);
    resetTrNum('add_tScIcBal_table');
  }

  function clickDelTScIcBalBtn(rowIndex) {
    var len = $("#add_tScIcBal_table").find(">tr").length;
    $("#add_tScIcBal_table").find(">tr").eq(rowIndex).remove();
		if(rowIndex==0 && len == 1){//如果只有一行且删除这一行则删除后补一空行
      var tr = $("#add_tScIcBal_table_template tr").clone();
      $("#add_tScIcBal_table").append(tr);
    }
    resetTrNum('add_tScIcBal_table');
  }

    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tScIcBal_table").createhftable({
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
<table border="0" cellpadding="2" cellspacing="1" id="tScIcBal_table" style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<td align="center" bgcolor="#476f9a" style="display: none">操作</td>
				  <td align="center" bgcolor="#476f9a">
						商品主键
				  </td>
				  <td align="center" bgcolor="#476f9a">
						仓库主键
				  </td>
				  <td align="center" bgcolor="#476f9a">
						批号
				  </td>
				  <td align="center" bgcolor="#476f9a">
						期初数量(上一期期末)
				  </td>
				  <td align="center" bgcolor="#476f9a">
						期初辅助数量
				  </td>
				  <td align="center" bgcolor="#476f9a">
						本期收入数量
				  </td>
				  <td align="center" bgcolor="#476f9a">
						本期发出数量
				  </td>
				  <td align="center" bgcolor="#476f9a">
						本年累计收入数量
				  </td>
				  <td align="center" bgcolor="#476f9a">
						本年累计发出数量
				  </td>
				  <td align="center" bgcolor="#476f9a">
						本年累计收入辅助数量
				  </td>
				  <td align="center" bgcolor="#476f9a">
						本年累计发出辅助数量
				  </td>
				  <td align="center" bgcolor="#476f9a">
						本期结存数量=期初金额+本期收入-本期发出
				  </td>
				  <td align="center" bgcolor="#476f9a">
						本期结存辅助数量
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
						期末结存金额=期初金额+本期收入-本期发出
				  </td>
	</tr>
	<tbody id="add_tScIcBal_table">	
	<c:if test="${fn:length(tScIcBalList)  <= 0 }">
			<tr>
				<td align="center" bgcolor="#F6FCFF"><div style="width: 25px;background-color: white;" name="xh">1</div></td>
				<td align="center" bgcolor="white" style="display: none">
          <div style="width: 80px;background-color: white;">
            <a name="addTScIcBalBtn[0]" id="addTScIcBalBtn[0]" href="#"
               onclick="clickAddTScIcBalBtn(0);"></a>
          </div>
				</td>
					<input name="tScIcBalList[0].id" type="hidden"/>
					<input name="tScIcBalList[0].createName" type="hidden"/>
					<input name="tScIcBalList[0].createBy" type="hidden"/>
					<input name="tScIcBalList[0].createDate" type="hidden"/>
					<input name="tScIcBalList[0].updateName" type="hidden"/>
					<input name="tScIcBalList[0].updateBy" type="hidden"/>
					<input name="tScIcBalList[0].updateDate" type="hidden"/>
					<input name="tScIcBalList[0].year" type="hidden"/>
					<input name="tScIcBalList[0].period" type="hidden"/>
					<input name="tScIcBalList[0].sonID" type="hidden"/>
					<input name="tScIcBalList[0].fid" type="hidden"/>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].itemID" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">商品主键</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].stockID" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">仓库主键</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].batchNo" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">批号</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].begQty" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">期初数量(上一期期末)</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].secBegQty" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">期初辅助数量</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].receive" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本期收入数量</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].send" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本期发出数量</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].ytdReceive" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本年累计收入数量</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].ytdSend" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本年累计发出数量</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].secYtdReceive" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本年累计收入辅助数量</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].secYtdSend" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本年累计发出辅助数量</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].endQty" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本期结存数量=期初金额+本期收入-本期发出</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].secEndQty" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本期结存辅助数量</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].begBal" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">期初金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].debit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本期收入金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].credit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本期发出金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].ytdDebit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本年累计收入金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].ytdCredit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">本年累计发出金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[0].endBal" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">期末结存金额=期初金额+本期收入-本期发出</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tScIcBalList)  > 0 }">
		<c:forEach items="${tScIcBalList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="white"><div style="width: 30px;background-color: white;" name="xh">${stuts.index+1 }</div></td>
				<td align="center" bgcolor="white" style="display: none">
          <div style="width: 80px;background-color: white;">
            <a name="addTScIcBalBtn[${stuts.index}]" id="addTScIcBalBtn[${stuts.index}]" href="#"
               onclick="clickAddTScIcBalBtn(${stuts.index});"></a>

              <a name="delTScIcBalBtn[${stuts.index}]" id="delTScIcBalBtn[${stuts.index}]" href="#"
                 onclick="clickDelTScIcBalBtn(${stuts.index});"></a>
          </div>
        </td>
					<input name="tScIcBalList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tScIcBalList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tScIcBalList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tScIcBalList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tScIcBalList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tScIcBalList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tScIcBalList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tScIcBalList[${stuts.index }].year" type="hidden" value="${poVal.year }"/>
					<input name="tScIcBalList[${stuts.index }].period" type="hidden" value="${poVal.period }"/>
					<input name="tScIcBalList[${stuts.index }].sonID" type="hidden" value="${poVal.sonID }"/>
					<input name="tScIcBalList[${stuts.index }].fid" type="hidden" value="${poVal.fid }"/>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].itemID" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.itemID }">
					  <label class="Validform_label" style="display: none;">商品主键</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].stockID" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.stockID }">
					  <label class="Validform_label" style="display: none;">仓库主键</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].batchNo" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.batchNo }">
					  <label class="Validform_label" style="display: none;">批号</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].begQty" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.begQty }">
					  <label class="Validform_label" style="display: none;">期初数量(上一期期末)</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].secBegQty" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.secBegQty }">
					  <label class="Validform_label" style="display: none;">期初辅助数量</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].receive" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.receive }">
					  <label class="Validform_label" style="display: none;">本期收入数量</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].send" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.send }">
					  <label class="Validform_label" style="display: none;">本期发出数量</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].ytdReceive" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.ytdReceive }">
					  <label class="Validform_label" style="display: none;">本年累计收入数量</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].ytdSend" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.ytdSend }">
					  <label class="Validform_label" style="display: none;">本年累计发出数量</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].secYtdReceive" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.secYtdReceive }">
					  <label class="Validform_label" style="display: none;">本年累计收入辅助数量</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].secYtdSend" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.secYtdSend }">
					  <label class="Validform_label" style="display: none;">本年累计发出辅助数量</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].endQty" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.endQty }">
					  <label class="Validform_label" style="display: none;">本期结存数量=期初金额+本期收入-本期发出</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].secEndQty" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.secEndQty }">
					  <label class="Validform_label" style="display: none;">本期结存辅助数量</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].begBal" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.begBal }">
					  <label class="Validform_label" style="display: none;">期初金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].debit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.debit }">
					  <label class="Validform_label" style="display: none;">本期收入金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].credit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.credit }">
					  <label class="Validform_label" style="display: none;">本期发出金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].ytdDebit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.ytdDebit }">
					  <label class="Validform_label" style="display: none;">本年累计收入金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].ytdCredit" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.ytdCredit }">
					  <label class="Validform_label" style="display: none;">本年累计发出金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScIcBalList[${stuts.index }].endBal" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		 value="${poVal.endBal }">
					  <label class="Validform_label" style="display: none;">期末结存金额=期初金额+本期收入-本期发出</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
