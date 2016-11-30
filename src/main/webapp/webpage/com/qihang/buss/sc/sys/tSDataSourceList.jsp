<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
		}
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
<div style="width: auto;height: 300px;overflow-y:auto;overflow-x:auto;">
<table cellpadding="0" cellspacing="1" class="formtable" id="tSDataSource_table" >
	<tbody id="add_tSDataSource_table" >	
	<c:if test="${fn:length(tSDataSourceList)  <= 0 }">
			<tr>
					<input name="tSDataSourceList[0].id" type="hidden"  value="${poVal.id}"/>
			</tr>
				<tr>
				  <td align="right" width="15%">
					<label class="Validform_label">
										账套名称:
									</label>
					</td>
				  <td class="value">
					  	<input name="tSDataSourceList[0].dbKey" maxlength="50" 
					  		type="text" class="inputxt"  
					               datatype="*"
					               >
					  <label class="Validform_label" style="display: none;">账套名称</label>
					</td>
				  <td align="right" width="15%">
					<label class="Validform_label">
										数据源描述:
									</label>
					</td>
				  <td class="value">
					  	<input name="tSDataSourceList[0].description" maxlength="50" 
					  		type="text" class="inputxt"  
					               datatype="*"
					               >
					  <label class="Validform_label" style="display: none;">数据源描述</label>
					</td>
				</tr>
				<tr>
				  <td align="right">
					<label class="Validform_label">
										驱动类:
									</label>
					</td>
				  <td class="value">
					  	<input name="tSDataSourceList[0].driverClass" maxlength="50" 
					  		type="text" class="inputxt"  
					               datatype="*"
					               >
					  <label class="Validform_label" style="display: none;">驱动类</label>
					</td>
				  <td align="right">
					<label class="Validform_label">
										url:
									</label>
					</td>
				  <td class="value">
					  	<input name="tSDataSourceList[0].url" maxlength="100" 
					  		type="text" class="inputxt"  
					               datatype="*"
					               >
					  <label class="Validform_label" style="display: none;">url</label>
					</td>
				</tr>
				<tr>
				  <td align="right">
					<label class="Validform_label">
										数据库用户:
									</label>
					</td>
				  <td class="value">
					  	<input name="tSDataSourceList[0].dbUser" maxlength="50" 
					  		type="text" class="inputxt"  
					               datatype="*"
					               >
					  <label class="Validform_label" style="display: none;">数据库用户</label>
					</td>
				  <td align="right">
					<label class="Validform_label">
										数据库密码:
									</label>
					</td>
				  <td class="value">
					  	<input name="tSDataSourceList[0].dbPassword" maxlength="50" 
					  		type="text" class="inputxt"  
					               
					               >
					  <label class="Validform_label" style="display: none;">数据库密码</label>
					</td>
				</tr>
				<tr>
				  <td align="right">
					<label class="Validform_label">
										数据库类型:
									</label>
					</td>
				  <td class="value">
					  	<input name="tSDataSourceList[0].dbType" maxlength="50" 
					  		type="text" class="inputxt"  
					               
					               >
					  <label class="Validform_label" style="display: none;">数据库类型</label>
					</td>
				  <td align="right">
					<label class="Validform_label">
										账套Id:
									</label>
					</td>
				  <td class="value">
					  	<input name="tSDataSourceList[0].accountId" maxlength="36" 
					  		type="text" class="inputxt"  
					               
					               >
					  <label class="Validform_label" style="display: none;">账套Id</label>
					</td>
				</tr>
	</c:if>
	<c:if test="${fn:length(tSDataSourceList)  > 0 }">
		<c:forEach items="${tSDataSourceList}" var="poVal" varStatus="stuts" begin="0" end="0">
			<tr>
					<input name="tSDataSourceList[0].id" type="hidden" value="${poVal.id}"/>
			</tr>
			<tr>
				  <td align="right">
					<label class="Validform_label">
										账套名称:
									</label>
					</td>
				  <td class="value">
					  	<input name="tSDataSourceList[0].dbKey" maxlength="50" 
					  		type="text" class="inputxt"  
					               datatype="*"
					                value="${poVal.dbKey }">
					  <label class="Validform_label" style="display: none;">账套名称</label>
					</td>
				  <td align="right">
					<label class="Validform_label">
										数据源描述:
									</label>
					</td>
				  <td class="value">
					  	<input name="tSDataSourceList[0].description" maxlength="50" 
					  		type="text" class="inputxt"  
					               datatype="*"
					                value="${poVal.description }">
					  <label class="Validform_label" style="display: none;">数据源描述</label>
					</td>
				</tr>
			<tr>
				  <td align="right">
					<label class="Validform_label">
										驱动类:
									</label>
					</td>
				  <td class="value">
					  	<input name="tSDataSourceList[0].driverClass" maxlength="50" 
					  		type="text" class="inputxt"  
					               datatype="*"
					                value="${poVal.driverClass }">
					  <label class="Validform_label" style="display: none;">驱动类</label>
					</td>
				  <td align="right">
					<label class="Validform_label">
										url:
									</label>
					</td>
				  <td class="value">
					  	<input name="tSDataSourceList[0].url" maxlength="100" 
					  		type="text" class="inputxt"  
					               datatype="*"
					                value="${poVal.url }">
					  <label class="Validform_label" style="display: none;">url</label>
					</td>
				</tr>
			<tr>
				  <td align="right">
					<label class="Validform_label">
										数据库用户:
									</label>
					</td>
				  <td class="value">
					  	<input name="tSDataSourceList[0].dbUser" maxlength="50" 
					  		type="text" class="inputxt"  
					               datatype="*"
					                value="${poVal.dbUser }">
					  <label class="Validform_label" style="display: none;">数据库用户</label>
					</td>
				  <td align="right">
					<label class="Validform_label">
										数据库密码:
									</label>
					</td>
				  <td class="value">
					  	<input name="tSDataSourceList[0].dbPassword" maxlength="50" 
					  		type="text" class="inputxt"  
					               
					                value="${poVal.dbPassword }">
					  <label class="Validform_label" style="display: none;">数据库密码</label>
					</td>
				</tr>
			<tr>
				  <td align="right">
					<label class="Validform_label">
										数据库类型:
									</label>
					</td>
				  <td class="value">
					  	<input name="tSDataSourceList[0].dbType" maxlength="50" 
					  		type="text" class="inputxt"  
					               
					                value="${poVal.dbType }">
					  <label class="Validform_label" style="display: none;">数据库类型</label>
					</td>
				  <td align="right">
					<label class="Validform_label">
										账套Id:
									</label>
					</td>
				  <td class="value">
					  	<input name="tSDataSourceList[0].accountId" maxlength="36" 
					  		type="text" class="inputxt"  
					               
					                value="${poVal.accountId }">
					  <label class="Validform_label" style="display: none;">账套Id</label>
					</td>
				</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
</div>