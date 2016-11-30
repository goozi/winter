package com.qihang.buss.sc.inventory.page;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**   
 * @Title: Page
 * @Description: 盘点单
 * @author onlineGenerator
 * @date 2016-08-01 09:43:32
 * @version V1.0   
 *
 */

public class TScAutoChkPage implements java.io.Serializable {

	private String calType;//盘点分类 0：即时库存 1：截止日期
	private String date;//截止日期
	private String stockInfo;//仓库信息
	private String itemInfo;//商品信息
	private Integer maxNum;//最大记录数
	private Integer isZero;//盘点账面数量为零的数据
	private String empId;//经办人
	private String deptId;//部门

	public String getCalType() {
		return calType;
	}

	public void setCalType(String calType) {
		this.calType = calType;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStockInfo() {
		return stockInfo;
	}

	public void setStockInfo(String stockInfo) {
		this.stockInfo = stockInfo;
	}

	public String getItemInfo() {
		return itemInfo;
	}

	public void setItemInfo(String itemInfo) {
		this.itemInfo = itemInfo;
	}

	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public Integer getIsZero() {
		return isZero;
	}

	public void setIsZero(Integer isZero) {
		this.isZero = isZero;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
}
