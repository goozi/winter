package com.qihang.buss.sc.sys.service.impl;
import com.qihang.buss.sc.sys.entity.TSConfigEntity;
import com.qihang.buss.sc.sys.page.TSConfigEntityPage;
import com.qihang.buss.sc.sys.service.TSConfigServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.constant.Globals;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Service("tSConfigService")
@Transactional
public class TSConfigServiceImpl extends CommonServiceImpl implements TSConfigServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TSConfigEntity)entity);
 	}


 	public <T> Serializable save(T entity) {
		TSConfigEntityPage pageVo = (TSConfigEntityPage)entity;
		String sql="select a.id,a.code,a.content,a.name,a.note,a.userid from t_s_config a";
		List<TSConfigEntity> itemList = this.findListbySql(sql, TSConfigEntity.class);
		if(itemList.size() > 0){
			for(TSConfigEntity temp :itemList ){
				//分页记录
				if(temp.getCode().equals(Globals.PAGE_NUMBER_CODE)){

					temp.setContent(pageVo.getPageRecord());
					super.saveOrUpdate(temp);
				//行高
				} else if(temp.getCode().equals(Globals.ROW_HEIGHT_CODE)){

					temp.setContent(pageVo.getRowHeight());
					super.saveOrUpdate(temp);
				}
				//默认税率
				else if(temp.getCode().equals(Globals.DEFAULT_RATE_CODE)){

					temp.setContent(pageVo.getDefaultRate());
					super.saveOrUpdate(temp);
				}
				//数量
				else if(temp.getCode().equals(Globals.NUMBER_CODE)){

					temp.setContent(pageVo.getNumber());
					super.saveOrUpdate(temp);
				}
				//单价
				else if(temp.getCode().equals(Globals.UNITP_RICE_CODE)){

					temp.setContent(pageVo.getUnitPrice());
					super.saveOrUpdate(temp);
				}
				//金额
				else if(temp.getCode().equals(Globals.MONEY_CODE)){

					temp.setContent(pageVo.getMoney());
					super.saveOrUpdate(temp);
				}
				//税率
				else if(temp.getCode().equals(Globals.RATES_CODE)){

					temp.setContent(pageVo.getRates());
					super.saveOrUpdate(temp);
				}
				//折扣率
				else if(temp.getCode().equals(Globals.DISCOUNT_RATE_CODE)){

					temp.setContent(pageVo.getDiscountRate());
					super.saveOrUpdate(temp);
				}
				//其他
				else if(temp.getCode().equals(Globals.OTHER_CODE)){

					temp.setContent(pageVo.getOther());
					super.saveOrUpdate(temp);
				}
				//控制方式
				else if(temp.getCode().equals(Globals.CONTROL_METHOD_CODE)){

					temp.setContent(pageVo.getControlMethod());
					super.saveOrUpdate(temp);
				}
				//控制时点
				else if(temp.getCode().equals(Globals.CONTROL_TIME_POINT_CODE)){

					temp.setContent(pageVo.getControlTimePoint());
					super.saveOrUpdate(temp);
				}
				//应收预警天数
				else if(temp.getCode().equals(Globals.RECEARWAR_DAYS_CODE)){

					temp.setContent(pageVo.getRecearwarDays());
					super.saveOrUpdate(temp);
				}
				//采购订单预警天数
				else if(temp.getCode().equals(Globals.PRUORDEARWAR_DAYS_CODE)){

					temp.setContent(pageVo.getPruordearwarDays());
					super.saveOrUpdate(temp);
				}
				//应付预警天数
				else if(temp.getCode().equals(Globals.PAYEARWAR_DAYS_CODE)){

					temp.setContent(pageVo.getPayearwarDays());
					super.saveOrUpdate(temp);
				}
				//销售订单预警天数
				else if(temp.getCode().equals(Globals.SALORDEARWAR_DAYS_CODE)){

					temp.setContent(pageVo.getSalordearwarDays());
					super.saveOrUpdate(temp);
				}
				//保质期预警天数
				else if(temp.getCode().equals(Globals.SHELFLIFEEARWAR_DAYS_CODE)){

					temp.setContent(pageVo.getShelflifeearwarDays());
					super.saveOrUpdate(temp);
				}
				//系统日志保留天数
				else if(temp.getCode().equals(Globals.SYSLOGHOLD_DAYS_CODE)){

					temp.setContent(pageVo.getSyslogholdDays());
					super.saveOrUpdate(temp);
				}
			}

		}

// 		Serializable t = super.save(config);
 		//执行新增操作配置的sql增强
 		//this.doAddSql((TSConfigEntity)entity);
 		return null;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
		TSConfigEntity  pageVo = (TSConfigEntity)entity;
		//查出所有的数据
		String sql="select a.id,a.code,a.content,a.name,a.note,a.userid from t_s_config a";
		//放到一个集合里 进行遍历  进行保存
		List<TSConfigEntity> itemList = this.findListbySql(sql, TSConfigEntity.class);
		if(itemList.size() > 0){
			for(TSConfigEntity temp :itemList ){
				//分页记录
				if(temp.getCode().equals(Globals.PAGE_NUMBER_CODE)){
					temp.setContent(pageVo.getPageRecord());
					//行高
				} else if(temp.getCode().equals(Globals.ROW_HEIGHT_CODE)){
					temp.setContent(pageVo.getRowHeight());
				}
				//默认税率
				else if(temp.getCode().equals(Globals.DEFAULT_RATE_CODE)){
					temp.setContent(pageVo.getDefaultRate());
				}
				//数量
				else if(temp.getCode().equals(Globals.NUMBER_CODE)){
					temp.setContent(pageVo.getNumber());
				}
				//单价
				else if(temp.getCode().equals(Globals.UNITP_RICE_CODE)){
					temp.setContent(pageVo.getUnitPrice());
				}
				//金额
				else if(temp.getCode().equals(Globals.MONEY_CODE)){
					temp.setContent(pageVo.getMoney());
				}
				//税率
				else if(temp.getCode().equals(Globals.RATES_CODE)){
					temp.setContent(pageVo.getRates());
				}
				//折扣率
				else if(temp.getCode().equals(Globals.DISCOUNT_RATE_CODE)){
					temp.setContent(pageVo.getDiscountRate());
				}
				//其他
				else if(temp.getCode().equals(Globals.OTHER_CODE)){
					temp.setContent(pageVo.getOther());
				}
				//控制方式
				else if(temp.getCode().equals(Globals.CONTROL_METHOD_CODE)){
					temp.setContent(pageVo.getControlMethod());
				}
				//控制时点
				else if(temp.getCode().equals(Globals.CONTROL_TIME_POINT_CODE)){
					temp.setContent(pageVo.getControlTimePoint());
				}
				//应收预警天数
				else if(temp.getCode().equals(Globals.RECEARWAR_DAYS_CODE)){
					temp.setContent(pageVo.getRecearwarDays());
				}
				//采购订单预警天数
				else if(temp.getCode().equals(Globals.PRUORDEARWAR_DAYS_CODE)){
					temp.setContent(pageVo.getPruordearwarDays());
				}
				//应付预警天数
				else if(temp.getCode().equals(Globals.PAYEARWAR_DAYS_CODE)){
					temp.setContent(pageVo.getPayearwarDays());
				}
				//销售订单预警天数
				else if(temp.getCode().equals(Globals.SALORDEARWAR_DAYS_CODE)){
					temp.setContent(pageVo.getSalordearwarDays());
				}
				//保质期预警天数
				else if(temp.getCode().equals(Globals.SHELFLIFEEARWAR_DAYS_CODE)){
					temp.setContent(pageVo.getShelflifeearwarDays());
				}
				//系统日志保留天数
				else if(temp.getCode().equals(Globals.SYSLOGHOLD_DAYS_CODE)){
					temp.setContent(pageVo.getSyslogholdDays());
				}
				super.saveOrUpdate(temp);
 			}

		}

// 		super.saveOrUpdate(entity);
// 		//执行更新操作配置的sql增强
// 		this.doUpdateSql((TSConfigEntity)entity);
 	}

	public <T> void saveOrUpdateSupply(T entity) {
		TSConfigEntity pageVo = (TSConfigEntity)entity;
		//查出所有的数据
		String sql="select a.id,a.code,a.content,a.name,a.note,a.userid from t_s_config a";
		//放到一个集合里 进行遍历  进行保存
		List<TSConfigEntity> itemList = this.findListbySql(sql, TSConfigEntity.class);
		if(itemList.size() > 0){
			for(TSConfigEntity temp :itemList ){
				//允许负库存结账
				if(temp.getCode().equals(Globals.MINUSINVENTORYACCOUNT_CODE)){
					temp.setContent(pageVo.getMinusInventoryAccount());
					//允许负库存出库
				} else if(temp.getCode().equals(Globals.MINUSINVENTORYSL_CODE)){
					temp.setContent(pageVo.getMinusInventorySl());
				}
				//允许盘点有未审核存货单据的数据
				else if(temp.getCode().equals(Globals.STOCKTAKINGNOTAUDITEDSTOCKBILL_CODE)){
					temp.setContent(pageVo.getStocktakingNotAuditedStockBill());
				}
				//单据保存时系统自动审核
				else if(temp.getCode().equals(Globals.BILLSAVESYSTEMWITHEXAMINE_CODE)){
					temp.setContent(pageVo.getBillSaveSystemWithExamine());
				}
				//单据审核时系统自带后续业务单据
				else if(temp.getCode().equals(Globals.BILLEXAMINESYSTEMWITHFOLLOW_CODE)){
					temp.setContent(pageVo.getBillExamineSystemWithFollow());
				}
				//不允许手工开入库单
				else if(temp.getCode().equals(Globals.CANNOTMANUALOPENINREPERTORY_CODE)){
					temp.setContent(pageVo.getCannotManualOpenInRepertory());
				}
				//不允许入库单数量大于采购订单数量
				else if(temp.getCode().equals(Globals.CANNOTINREPERTORYNYTPURCHASEN_CODE)){
					temp.setContent(pageVo.getCannotInRepertoryngtPurchasen());
				}
				//采购模块启用价格调用顺序
				else if(temp.getCode().equals(Globals.PURCHASESTARTPRICECALLORDER_CODE)){
					temp.setContent(pageVo.getPurchaseStartPriceCallOrder());
				}
				//采购设置下拉框一
				else if(temp.getCode().equals(Globals.PURCHASESELECTONE_CODE)){
					temp.setContent(pageVo.getPurchaseselectOne());
				}
				//采购设置下拉框二
				else if(temp.getCode().equals(Globals.PURCHASESELECTTWO_CODE)){
					temp.setContent(pageVo.getPurchaseselectTwo());
				}
				//不允许手工开出库单
				else if(temp.getCode().equals(Globals.CANNOTMANUALOPENOUTREPERTORY_CODE)){
					temp.setContent(pageVo.getCannotManualOpenOutRepertory());
				}
				//不允许出库单数量大于销售订单数量
				else if(temp.getCode().equals(Globals.CANNOTOUTREPERTORYNGTSALE_CODE)){
					temp.setContent(pageVo.getCannotOutRepertoryngtSale());
				}
				//销售模块启用价格调用顺序
				else if(temp.getCode().equals(Globals.SALESTARTPRICECALLORDER_CODE)){
					temp.setContent(pageVo.getSaleStartPriceCallOrder());
				}
				//销售设置下拉框一
				else if(temp.getCode().equals(Globals.SALESELECTONE_CODE)){
					temp.setContent(pageVo.getSaleSelectOne());
				}
				//销售设置下拉框二
				else if(temp.getCode().equals(Globals.SALESELECTTWO_CODE)){
					temp.setContent(pageVo.getSaleSelectTwo());
				}
				//销售设置下拉框三
				else if(temp.getCode().equals(Globals.SALESELECTTHREE_CODE)){
					temp.setContent(pageVo.getSaleSelectThree());
				}
				super.saveOrUpdate(temp);

			}

		}

// 		super.saveOrUpdate(entity);
// 		//执行更新操作配置的sql增强
// 		this.doUpdateSql((TSConfigEntity)entity);
	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TSConfigEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TSConfigEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TSConfigEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TSConfigEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{code}",String.valueOf(t.getCode()));
 		sql  = sql.replace("#{content}",String.valueOf(t.getContent()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{note}",String.valueOf(t.getNote()));
 		sql  = sql.replace("#{userid}",String.valueOf(t.getUserid()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}