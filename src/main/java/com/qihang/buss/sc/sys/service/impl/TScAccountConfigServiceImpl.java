package com.qihang.buss.sc.sys.service.impl;
import com.qihang.buss.sc.baseinfo.entity.TScIcitemEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemPriceEntity;
import com.qihang.buss.sc.init.controller.TScBegdataController;
import com.qihang.buss.sc.init.entity.TScBegdataEntity;
import com.qihang.buss.sc.init.entity.TScIcInitialEntity;
import com.qihang.buss.sc.init.entity.VScIcInitialEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryBatchnoEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryEntity;
import com.qihang.buss.sc.sys.entity.*;
import com.qihang.buss.sc.sys.service.TScAccountConfigServiceI;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.winter.core.annotation.Ehcache;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.*;
import com.qihang.winter.web.system.pojo.base.DynamicDataSourceEntity;
import com.qihang.winter.web.system.service.DynamicDataSourceServiceI;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.rhq.helpers.pluginAnnotations.agent.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.Serializable;

@Service("tScAccountConfigService")
@Transactional
public class TScAccountConfigServiceImpl extends CommonServiceImpl implements TScAccountConfigServiceI {


	@Autowired
	private DynamicDataSourceServiceI dynamicDataSourceService;

	@Autowired
	private SystemService systemService;
 	public <T> void delete(T entity) {
		//删除账套
 		super.delete(entity);
		//删除数据源
		String hql0 = "from DynamicDataSourceEntity where 1 = 1 AND accountId = ? ";
		String id0 = ((TScAccountConfigEntity) entity).getId();
		List<DynamicDataSourceEntity> dynamicDataSourceEntityList = systemService.findHql(hql0,id0);
		if (dynamicDataSourceEntityList.size()>0) {
			super.delete(dynamicDataSourceEntityList.get(0));
		}
		//todo:手动删除数据库和用户
 		//执行删除操作配置的sql增强
		this.doDelSql((TScAccountConfigEntity) entity);
 	}

	/**
	 * 创建新账套
	 * @param tScAccountConfig
	 * @param tSDataSourceList
	 * @author hjh 2016-9-9
     */
	public void addMain(TScAccountConfigEntity tScAccountConfig,
						List<DynamicDataSourceEntity> tSDataSourceList){
		DynamicDataSourceEntity tSDataSource = tSDataSourceList.get(0);
		//0.生成url
		tSDataSource.setDbKey(tScAccountConfig.getDbKey());
		if (tSDataSource.getDbName()==null || tSDataSource.getDbName().equals("")){
			tSDataSource.setDbName(tSDataSource.getDbKey());
		}
		String url =DBUtil.getDBUrl(tSDataSource.getDbType(), tSDataSource.getDbIp(), tSDataSource.getDbPort(), tSDataSource.getDbName());
		tSDataSource.setUrl(url);
		//1.数据库建库
		boolean isOk = DBUtil.createDatabaseAndInitialize(tScAccountConfig, tSDataSource);
		if (isOk) {
			//2.保存主信息
			tScAccountConfig.setOpenState(Globals.ACCOUNT_OPEN_NO);//未开启
			tScAccountConfig.setCloseState(Globals.ACCOUNT_CLOSE_NO);//未关闭
			tScAccountConfig.setStageStartDate(tScAccountConfig.getAccountStartDate());//默认第一个当期年月为账套启用年月
			this.save(tScAccountConfig);
			//3.保存新的数据源配置
			String databaseName = tSDataSource.getDbKey();
			tSDataSource.setDbUser(databaseName);
			tSDataSource.setDbPassword(databaseName);
			tSDataSource.setAccountId(tScAccountConfig.getId());
			tSDataSource.setDbKey(tScAccountConfig.getDbKey());
			commonDao.save(tSDataSource);
			//4.更新主表的记录数据源ID
			tScAccountConfig.setDbId(tSDataSource.getId());
			this.saveOrUpdate(tScAccountConfig);
			//5.更新新建账套的系统设置表的账套Id为当前账套ID
			DBUtil.updateConfigAccountId(tScAccountConfig, tSDataSource);
			//6.执行新增操作配置的sql增强
			this.doAddSql(tScAccountConfig);
		}else{
			throw new BusinessException("创建新库失败");
		}
	}

	public void updateMain(TScAccountConfigEntity tScAccountConfig,
						   List<DynamicDataSourceEntity> tSDataSourceList) {
		//保存主表信息
		this.saveOrUpdate(tScAccountConfig);
		//===================================================================================
		//获取参数
		Object id0 = tScAccountConfig.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-数据源
		String hql0 = "from TSDataSourceEntity where 1 = 1 AND aCCOUNTID = ? ";
		List<DynamicDataSourceEntity> tSDataSourceOldList = this.findHql(hql0, id0);
		//2.筛选更新明细数据-数据源
		for(DynamicDataSourceEntity oldE:tSDataSourceOldList){
			boolean isUpdate = false;
			for(DynamicDataSourceEntity sendE:tSDataSourceList){
				//需要更新的明细数据-数据源
				if(oldE.getId().equals(sendE.getId())){
					try {
						MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
						this.saveOrUpdate(oldE);
					} catch (Exception e) {
						e.printStackTrace();
						throw new BusinessException(e.getMessage());
					}
					isUpdate= true;
					break;
				}
			}
			if(!isUpdate){
				//如果数据库存在的明细，前台没有传递过来则是删除-数据源
				super.delete(oldE);
			}

		}
		//3.持久化新增的数据-数据源
		for(DynamicDataSourceEntity tSDataSource:tSDataSourceList){
			if(oConvertUtils.isEmpty(tSDataSource.getId())){
				//外键设置
				tSDataSource.setAccountId(tScAccountConfig.getId());
				this.save(tSDataSource);
			}
		}
		//执行更新操作配置的sql增强
		this.doUpdateSql(tScAccountConfig);
	}

	public void delMain(TScAccountConfigEntity tScAccountConfig) {
		//删除主表信息
		this.delete(tScAccountConfig);
		//===================================================================================
		//获取参数
		Object id0 = tScAccountConfig.getId();
		//===================================================================================
		//删除-数据源
		String hql0 = "from TSDataSourceEntity where 1 = 1 AND aCCOUNTID = ? ";
		List<DynamicDataSourceEntity> tSDataSourceOldList = this.findHql(hql0, id0);
		this.deleteAllEntitie(tSDataSourceOldList);
	}
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TScAccountConfigEntity) entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {

 		super.saveOrUpdate(entity);
// 		//执行更新操作配置的sql增强
// 		this.doUpdateSql((TScAccountConfigEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScAccountConfigEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScAccountConfigEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScAccountConfigEntity t){
	 	return true;
 	}

	/**
	 * 开账操作
	 * @return
	 */
	@Override
	public AjaxJson openAccount(String accountId,Date startDate,String sonId,String userId,String userName) {
		AjaxJson j = new AjaxJson();
		//TScAccountConfigEntity accountConfigEntity = systemService.getCurrentAccountConfigByAccountid(id);
		//if(null != accountConfigEntity) {
			TScAccountStageEntity accountStageEntity = new TScAccountStageEntity();
			accountStageEntity.setAccountId(accountId);
			accountStageEntity.setDate(startDate);
			accountStageEntity.setState(0);
		    super.save(accountStageEntity);
			Date openDate = startDate;//开账日期
			Calendar c = Calendar.getInstance();
			c.setTime(openDate);
			String checkSql = "select * from v_sc_checkAccount";
			List<Map<String, Object>> checkResult = this.findForJdbc(checkSql);
			if (null != checkResult && checkResult.size() > 0) {
				Set<String> tranTypeInfo = new HashSet<String>();
				for (Map<String, Object> info : checkResult) {
					String tranType = (String) info.get("tranType");
					if ("1020".equals(tranType)) {
						tranTypeInfo.add("存货初始化");
					} else if ("1030".equals(tranType)) {
						tranTypeInfo.add("应收初始化");
					} else if ("1031".equals(tranType)) {
						tranTypeInfo.add("应付初始化");
					} else if ("1032".equals(tranType)) {
						tranTypeInfo.add("收支初始化");
					}
				}
				String errInfo = "";
				for (String tranTypeName : tranTypeInfo) {
					errInfo += tranTypeName + "、";
				}
				errInfo = errInfo.substring(0, errInfo.length() - 1);
				j.setSuccess(false);
				j.setMsg("不能开账，尚有未审核的" + errInfo);
				return j;
			}
			//存货初始化数据
			List<VScIcInitialEntity> initialInfo = this.findHql("from VScIcInitialEntity");
			//存货初始化根据商品、仓库、批号汇总数据
			List<Map<String, Object>> initialGroupInfo = this.findForJdbc("SELECT " +
					" entryItemId as itemId, " +
					" entryStockId as stockId, " +
					" batchNo, " +
					" sum(basicQty) AS basicQty, " +
					" sum(secQty) AS secQty, " +
					" sum(costAmount) AS costAmount, " +
					" kfDate, " +
					" kfDateType, " +
					" kfPeriod, " +
					" periodDate " +
					" FROM " +
					" v_sc_ic_initial " +
					" GROUP BY " +
					" entryItemId, " +
					" entryStockId, " +
					" batchNo, " +
					" kfDate, " +
					" kfDateType, " +
					" kfPeriod, " +
					" periodDate");
			//存货初始化根据商品、仓库汇总数据
			List<Map<String, Object>> initialGroupInfo2 = this.findForJdbc("SELECT " +
							" entryItemId as itemId, " +
							" entryStockId as stockId, " +
							" sum(basicQty) AS basicQty, " +
							" sum(secQty) AS secQty, " +
							" sum(costAmount) AS costAmount " +
							" FROM " +
							" v_sc_ic_initial " +
							" GROUP BY " +
							" entryItemId, " +
							" entryStockId"
			);
			//应收初始化
			List<Map<String,Object>> begdataEntityList1 = this.findForJdbc("select itemID,empID,deptID,tranType,sum(ifnull(allamount,0)) as allamount" +
					" from T_SC_BEGDATA where tranType='" + Globals.SC_IC_BEGDATA_TRANTYPE + "' group by itemID,empID,deptID,tranType");
			//应付初始化
			List<Map<String,Object>> begdataEntityList2 = this.findForJdbc("select itemID,empID,deptID,tranType,sum(ifnull(allamount,0)) as allamount" +
					" from T_SC_BEGDATA where tranType='" + Globals.SC_IC_BEGDATAPAYABLE_TRANTYPE + "' group by itemID,empID,deptID,tranType");
			//收支初始化
			List<Map<String,Object>> begdataEntityList3 = this.findForJdbc("select itemID,empID,deptID,tranType,sum(ifnull(allamount,0)) as allamount" +
					" from T_SC_BEGDATA where tranType='" + Globals.SC_IC_BEGDATAINCOMEPAY_TRANTYPE + "' group by itemID,empID,deptID,tranType");

			//插入存货日结表
			for (VScIcInitialEntity init : initialInfo) {
				//存货日结记录
				TScIcSpeedbalEntity speedbalEntity = new TScIcSpeedbalEntity();
				speedbalEntity.setTranType(init.getTrantype());//单据类型
				speedbalEntity.setDate(init.getDate());//单据日期
				speedbalEntity.setBillId(init.getId());//单据主键
				speedbalEntity.setBillEntryId(init.getEntryid());//单据分录主键
				speedbalEntity.setBillCreateTime(init.getCreateDate());//单据创建日期
				speedbalEntity.setItemId(init.getEntryitemid());//商品id
				speedbalEntity.setStockId(init.getEntrystockid());//仓库id
				speedbalEntity.setBatchNo(init.getBatchno());//批号
				speedbalEntity.setQty(init.getBasicqty().doubleValue());//数量
				speedbalEntity.setSecQty(init.getSecqty().doubleValue());//辅助数量
				//BigDecimal price = init.getCostprice().divide(BigDecimal.valueOf(entry.getCoefficient()),2,BigDecimal.ROUND_HALF_EVEN);
				if(null != init.getCostprice()) {
					speedbalEntity.setPrice(init.getCostprice().doubleValue());//成本单价
				}else{
					speedbalEntity.setPrice(0.0);
				}
				if(null != init.getCostamount()) {
					speedbalEntity.setAmount(init.getCostamount().doubleValue());//成本金额
				}else{
					speedbalEntity.setAmount(0.0);
				}
				speedbalEntity.setEPrice(0.0);//结存单价
				speedbalEntity.setEAmount(0.0);//结存金额
				//BigDecimal diffQty = BigDecimal.valueOf(eAmount).subtract(BigDecimal.valueOf(entry.getCostAmount()));
				speedbalEntity.setDiffAmount(0.0);//差异金额
				speedbalEntity.setBlidSrc("");//源单类型
				speedbalEntity.setFlag(0);//存货初始化
				speedbalEntity.setStatus(1);//计算状态
				speedbalEntity.setNegFlag(0);//负结余处理状态
				super.save(speedbalEntity);
			}
			//更新存货结账表和即时库存批号表
			for (Map<String, Object> init : initialGroupInfo) {
				TScIcBalEntity bal = new TScIcBalEntity();
				String batchNo = "";
				if (null != init.get("batchNo")) {
					batchNo = (String) init.get("batchNo");
				}
				String itemId = (String) init.get("itemId");
				String stockId = (String) init.get("stockId");
				BigDecimal basicQty = (BigDecimal) init.get("basicQty");
				BigDecimal secQty = (BigDecimal) init.get("secQty");
				BigDecimal amount = (BigDecimal) init.get("costAmount");
				Date kfDate = (Date) init.get("kfDate");
				Integer kfPeriod = (Integer) init.get("kfPeriod");
				String kfDateType = (String) init.get("kfDateType");
				Date periodDate = (Date) init.get("periodDate");

				TScBalEntity example = new TScBalEntity();
				example.setItemID(itemId);
				example.setStockID(stockId);
				if (StringUtils.isNotEmpty(batchNo)) {
					example.setBatchNo(batchNo);
				}
				bal.setYear(c.get(Calendar.YEAR)+"");//年份
				bal.setPeriod((c.get(Calendar.MONTH) + 1) + "");//月份
				bal.setBatchNo(batchNo);
				bal.setItemID(itemId);
				bal.setStockID(stockId);
				bal.setBegQty(basicQty);
				bal.setSecBegQty(secQty);
				bal.setBegBal(amount);
				bal.setReceive(BigDecimal.ZERO);
				bal.setSend(BigDecimal.ZERO);
				bal.setYtdReceive(basicQty);
				bal.setYtdSend(BigDecimal.ZERO);
				bal.setSecYtdReceive(secQty);
				bal.setSecYtdSend(BigDecimal.ZERO);
				bal.setEndQty(BigDecimal.ZERO);
				bal.setSecEndQty(BigDecimal.ZERO);
				bal.setDebit(BigDecimal.ZERO);
				bal.setCredit(BigDecimal.ZERO);
				bal.setYtdDebit(amount);
				bal.setYtdCredit(BigDecimal.ZERO);
				bal.setEndBal(BigDecimal.ZERO);
				bal.setSonID(sonId);
				bal.setSecSend(BigDecimal.ZERO);
				bal.setSecReceive(secQty);
				bal.setFid(accountStageEntity.getId());
				super.save(bal);
				//即时库存批次表
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				//获取商品单位数据
				List<TScItemPriceEntity> unitList = this.findHql("from TScItemPriceEntity where priceToIcItem.id = ?", new Object[]{itemId});
				BigDecimal coefficient = BigDecimal.ONE;
				for (TScItemPriceEntity unit : unitList) {
					if (null != unit.getDefaultCK() && 1 == unit.getDefaultCK()) {
						coefficient = unit.getCoefficient();
					}
				}
				BigDecimal qty = basicQty;
				qty = qty.divide(coefficient, 2, BigDecimal.ROUND_HALF_EVEN);
				Double xQty = Math.floor(qty.doubleValue());//箱数
				Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(coefficient).doubleValue();//散数
				BigDecimal avgPrice = amount.divide(basicQty, 2, BigDecimal.ROUND_HALF_EVEN);//单价
				//即时库存批号表
				TScIcInventoryBatchnoEntity batchnoEntity = new TScIcInventoryBatchnoEntity();
				batchnoEntity.setItemId(itemId);
				batchnoEntity.setStockId(stockId);
				batchnoEntity.setBasicQty(basicQty.doubleValue());
				batchnoEntity.setQty(xQty);
				batchnoEntity.setSmallQty(smallQty);
				batchnoEntity.setSecQty(secQty.doubleValue());
				batchnoEntity.setCostAmount(amount.doubleValue());
				batchnoEntity.setCostPrice(avgPrice.doubleValue());
				batchnoEntity.setCount(1);
				batchnoEntity.setIsCheck(0);
				batchnoEntity.setBatchNo(batchNo);
				if (null != kfDate) {
					batchnoEntity.setKfDate(kfDate);
				}
				if (null != periodDate) {
					batchnoEntity.setPeriodDate(periodDate);
				}
				if (StringUtils.isNotEmpty(kfDateType)) {
					batchnoEntity.setKfDateType(kfDateType);
				}
				if (null != kfPeriod) {
					batchnoEntity.setKfPeriod(kfPeriod);
				}
				super.save(batchnoEntity);
			}
			//即时库存基本表
			for (Map<String, Object> init : initialGroupInfo2) {

				String itemId = (String) init.get("itemId");
				String stockId = (String) init.get("stockId");
				BigDecimal basicQty = (BigDecimal) init.get("basicQty");
				BigDecimal secQty = (BigDecimal) init.get("secQty");
				BigDecimal amount = (BigDecimal) init.get("costAmount");
				//获取商品单位数据
				List<TScItemPriceEntity> unitList = this.findHql("from TScItemPriceEntity where priceToIcItem.id = ?", new Object[]{itemId});
				BigDecimal coefficient = BigDecimal.ONE;
				for (TScItemPriceEntity unit : unitList) {
					if (null != unit.getDefaultCK() && 1 == unit.getDefaultCK()) {
						coefficient = unit.getCoefficient();
					}
				}

				TScIcInventoryEntity inventoryEntity = new TScIcInventoryEntity();
				inventoryEntity.setItemId(itemId);
				inventoryEntity.setStockId(stockId);
				BigDecimal qty = basicQty;
				inventoryEntity.setBasicQty(qty.doubleValue());
				qty = qty.divide(coefficient, 2, BigDecimal.ROUND_HALF_EVEN);
				Double xQty = Math.floor(qty.doubleValue());
				Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(coefficient).doubleValue();
				inventoryEntity.setQty(xQty);
				inventoryEntity.setSmallQty(smallQty);
				inventoryEntity.setSecQty(secQty.doubleValue());
				inventoryEntity.setCostAmount(amount.doubleValue());
				BigDecimal avgPrice = amount.divide(basicQty, 2, BigDecimal.ROUND_HALF_EVEN);
				inventoryEntity.setCostPrice(avgPrice.doubleValue());
				inventoryEntity.setCount(1);
				super.save(inventoryEntity);
			}
			//应收结算
			for (Map<String,Object> begdataEntity : begdataEntityList1) {
				TScRpContactbalEntity contactbalEntity = new TScRpContactbalEntity();
				contactbalEntity.setYear(c.get(Calendar.YEAR)+"");//年份
				contactbalEntity.setPeriod((c.get(Calendar.MONTH) + 1) + "");//月份
				contactbalEntity.setRp(1);//应收
				contactbalEntity.setItemID((String) begdataEntity.get("itemID"));
				contactbalEntity.setDeptID((String) begdataEntity.get("deptID"));
				contactbalEntity.setEmpID((String) begdataEntity.get("empID"));
				contactbalEntity.setBegBal((BigDecimal) begdataEntity.get("allamount"));
				contactbalEntity.setDebit(BigDecimal.ZERO);
				contactbalEntity.setCredit(BigDecimal.ZERO);
				contactbalEntity.setYtdCredit(BigDecimal.ZERO);
				contactbalEntity.setYtdDebit((BigDecimal) begdataEntity.get("allamount"));
				contactbalEntity.setEndBal(BigDecimal.ZERO);
				contactbalEntity.setSonID(sonId);
				contactbalEntity.setFid(accountStageEntity.getId());
				super.save(contactbalEntity);
			}
			//应付结账
			for (Map<String,Object> begdataEntity : begdataEntityList2) {
				TScRpContactbalEntity contactbalEntity = new TScRpContactbalEntity();
				contactbalEntity.setYear(c.get(Calendar.YEAR)+"");//年份
				contactbalEntity.setPeriod((c.get(Calendar.MONTH) + 1) + "");//月份
				contactbalEntity.setRp(0);//应付
				contactbalEntity.setItemID((String) begdataEntity.get("itemID"));
				contactbalEntity.setDeptID((String) begdataEntity.get("deptID"));
				contactbalEntity.setEmpID((String) begdataEntity.get("empID"));
				contactbalEntity.setBegBal((BigDecimal) begdataEntity.get("allamount"));
				contactbalEntity.setDebit(BigDecimal.ZERO);
				contactbalEntity.setCredit(BigDecimal.ZERO);
				contactbalEntity.setYtdCredit(BigDecimal.ZERO);
				contactbalEntity.setYtdDebit((BigDecimal) begdataEntity.get("allamount"));
				contactbalEntity.setEndBal(BigDecimal.ZERO);
				contactbalEntity.setSonID(sonId);
				contactbalEntity.setFid(accountStageEntity.getId());
				super.save(contactbalEntity);
			}
			//收支汇总表
			for (Map<String,Object> begdataEntity : begdataEntityList3) {
				TScRpExpbalEntity expbalEntity = new TScRpExpbalEntity();
				//TScContactbalEntity contactbalEntity = new TScContactbalEntity();
				expbalEntity.setYear(c.get(Calendar.YEAR)+"");//年份
				expbalEntity.setPeriod((c.get(Calendar.MONTH) + 1) + "");//月份
				expbalEntity.setAccountID((String) begdataEntity.get("itemID"));
				expbalEntity.setDeptID((String) begdataEntity.get("deptID"));
				expbalEntity.setEmpID((String) begdataEntity.get("empID"));
				expbalEntity.setBegBal((BigDecimal) begdataEntity.get("allamount"));
				expbalEntity.setDebit(BigDecimal.ZERO);
				expbalEntity.setCredit(BigDecimal.ZERO);
				expbalEntity.setYtdCredit(BigDecimal.ZERO);
				expbalEntity.setYtdDebit((BigDecimal) begdataEntity.get("allamount"));
				expbalEntity.setEndBal(BigDecimal.ZERO);
				expbalEntity.setSonID(sonId);
				expbalEntity.setFid(accountStageEntity.getId());
				super.save(expbalEntity);
			}
			//String updateSql = "update T_SC_ACCOUNT_CONFIG set openState = 1,openBy='"+userId+"',openName='"+userName+"' where id = '"+id+"'";

//			accountConfigEntity.setOpenState(1);
//			accountConfigEntity.setOpenBy(userId);
//			accountConfigEntity.setOpenName(userName);
//			accountConfigEntity.setOpenDate(accountConfigEntity.getAccountStartDate());
//			this.saveOrUpdate(accountConfigEntity);
//			AccountUtil.clearAccountConfigCache(accountConfigEntity);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("没有开账信息");
//		}
		return j;
	}

	/**
	 * 反开账操作
	 * @param id
	 * @return
	 */
	@Override
	public AjaxJson unOpenAccount(String id) {
		AjaxJson j = new AjaxJson();
		String checkSql = "select * from v_sc_checkUnAccount";
		List<Map<String, Object>> checkResult = this.findForJdbc(checkSql);
		if (null != checkResult && checkResult.size() > 0) {
			Set<String> tranTypeInfo = new HashSet<String>();
			for (Map<String, Object> info : checkResult) {
				String tranType = (String) info.get("tranType");
				if ("52".equals(tranType)) {
					tranTypeInfo.add("采购入库单");
				} else if ("53".equals(tranType)) {
					tranTypeInfo.add("采购退货单");
				} else if ("61".equals(tranType)) {
					tranTypeInfo.add("采购换货单");
				} else if ("103".equals(tranType)) {
					tranTypeInfo.add("销售出库单");
				} else if ("104".equals(tranType)) {
					tranTypeInfo.add("销售退货单");
				} else if ("110".equals(tranType)) {
					tranTypeInfo.add("销售换货单");
				} else if ("54".equals(tranType)) {
					tranTypeInfo.add("收款单");
				} else if ("105".equals(tranType)) {
					tranTypeInfo.add("付款单");
				}
			}
			String errInfo = "";
			for (String tranTypeName : tranTypeInfo) {
				errInfo += tranTypeName + "、";
			}
			errInfo = errInfo.substring(0, errInfo.length() - 1);
			j.setSuccess(false);
			j.setMsg("不能反开账，存在"+errInfo+"已审核单据");
			return j;
		}
		String delSql = "delete from t_sc_ic_speedbal";
		super.executeSql(delSql);
		delSql = "delete from T_SC_IC_Bal";
		super.executeSql(delSql);
		delSql = "delete from t_sc_ic_inventory";
		super.executeSql(delSql);
		delSql = "delete from t_sc_ic_inventory_batchno";
		super.executeSql(delSql);
		delSql = "delete from t_sc_RP_ContactBal";
		super.executeSql(delSql);
		delSql = "delete from t_sc_RP_ExpBal";
		super.executeSql(delSql);
		delSql = "delete from t_sc_account_stage where accountId = '"+id+"'";
		super.executeSql(delSql);
		return j;
	}

	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TScAccountConfigEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{companyname}",String.valueOf(t.getCompanyName()));
 		sql  = sql.replace("#{taxnumber}",String.valueOf(t.getTaxNumber()));
 		sql  = sql.replace("#{bankaccount}",String.valueOf(t.getBankAccount()));
 		sql  = sql.replace("#{companyaddress}",String.valueOf(t.getCompanyAddress()));
 		sql  = sql.replace("#{phone}",String.valueOf(t.getPhone()));
 		sql  = sql.replace("#{fax}",String.valueOf(t.getFax()));
 		sql  = sql.replace("#{email}",String.valueOf(t.getEmail()));
 		sql  = sql.replace("#{companyurl}",String.valueOf(t.getCompanyUrl()));
 		sql  = sql.replace("#{registrationnumber}",String.valueOf(t.getRegistrationNumber()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	/**
	 * 按账套dbKey查询账套配置信息，从数据查询后会缓存到EhCache的accountConfigCache中，即下次查询都从缓存中取（不访问数据库），除非清除手动清除（即账套开启、关闭状态、系统设置时要手动清除缓存）
	 * @param dbKey
	 * @return
	 * @author hjh 2016-09-07
	 */
	//@Cacheable(value ="accountConfigCache", key = "'findByDbKey'+#dbKey")
	//@Ehcache(cacheName ="accountConfigCache", addOrdel=true, eternal=true)
	public TScAccountConfigEntity findByDbKey(String dbKey){
		return findByDbKeyAddOrdel(dbKey,true);
	}

	@Ehcache(cacheName ="accountConfigCache", eternal=true)
	public TScAccountConfigEntity findByDbKeyAddOrdel(String dbKey, @Param("addOrdel") boolean addOrdel){
		//方案一
//		HttpServletRequest request = ContextHolderUtils.getRequest();
//		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
//		String hql = "from TScAccountConfigEntity where dbKey=?";
//		List<TScAccountConfigEntity> list = this.findByProperty(TScAccountConfigEntity.class,"dbKey",dbKey);//findHql(hql, dbKey);
//		TScAccountConfigEntity tScAccountConfigEntity = null;
//		if (list.size()>0){
//			tScAccountConfigEntity = list.get(0);
//		}else{
//			tScAccountConfigEntity = new TScAccountConfigEntity();
//		}
//		//查询完主库后，再要回到原数据源
//		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, request);
		//方案二
		SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
		TScAccountConfigEntity tScAccountConfigEntity = systemService.getCurrentAccountConfigByDbkey(dbKey);
		return tScAccountConfigEntity;
	}

	/**
	 * 按账套dbKey清除EhCache的accountConfigCache中值，即下次查询时要访问数据库
	 *   即账套开启、关闭状态、系统设置时要手动调用此方法来清除缓存
	 * @param dbKey
	 * @author hjh 2016-09-07
	 */
	//@CacheEvict(value = "accountConfigCache", key = "'findByDbKey'+#dbKey" )
	//@Ehcache(cacheName ="accountConfigCache", addOrdel=false)
	public void clearAccountConfigCacheByDbKey(String dbKey){
		findByDbKeyAddOrdel(dbKey,false);
	}

	/**
	 * 按账套Id查询账套配置信息，从数据查询后会缓存到EhCache的accountConfigCache中，即下次查询都从缓存中取（不访问数据库），除非清除手动清除（即账套开启、关闭状态、系统设置时要手动清除缓存）
	 * @param accountId
	 * @return
	 * @author hjh 2016-09-07
	 */
	//@Cacheable(value ="accountConfigCache", key = "'findByAccountId'+#accountId")
	//@Ehcache(cacheName ="accountConfigCache", addOrdel=true, eternal=true)
	public TScAccountConfigEntity findByAccountId(String accountId){
		return findByAccountIdAddOrdel(accountId,true);
	}

	@Ehcache(cacheName ="accountConfigCache", eternal=true)
	public TScAccountConfigEntity findByAccountIdAddOrdel(String accountId, @Param("addOrdel") boolean addOrdel){
		//方案一
//		HttpServletRequest request = ContextHolderUtils.getRequest();
//		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
//		String hql = "from TScAccountConfigEntity where id=?";
//		String sql = "select * from T_sc_account_config where id='" + accountId +"'";
//		List<TScAccountConfigEntity> list = this.findListbySql(sql, TScAccountConfigEntity.class);//this.findByProperty(TScAccountConfigEntity.class,"id",accountId);//this.findHql(hql, accountId);
//		TScAccountConfigEntity tScAccountConfigEntity = null;
//		if (list.size()>0){
//			tScAccountConfigEntity = list.get(0);
//		}else{
//			tScAccountConfigEntity = new TScAccountConfigEntity();
//		}
//		//查询完主库后，再要回到原数据源
//		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, request);
		//方案二
		//测试ehcache里的数据情况,测试后要清除
//		Cache cache = CacheManager.getInstance().getCache("accountConfigCache");
//		List<String> keys = cache.getKeys();
//		for (String key: keys){
//			System.out.println(key + ":" + cache.get(key));
//		}
		SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
		TScAccountConfigEntity tScAccountConfigEntity = systemService.getCurrentAccountConfigByAccountid(accountId);
		return tScAccountConfigEntity;
	}

	/**
	 * 按账套Id清除EhCache的accountConfigCache中值，即下次查询时要访问数据库
	 *   即账套开启、关闭状态、系统设置时要手动调用此方法来清除缓存
	 * @param accountId
	 * @author hjh 2016-09-07
	 */
	//@CacheEvict(value = "accountConfigCache", key = "'findByAccountId'+#accountId" )
	//@Ehcache(cacheName ="accountConfigCache", addOrdel=false)
	public void clearAccountConfigCacheByAccountId(String accountId){
		findByAccountIdAddOrdel(accountId,false);
	}
}