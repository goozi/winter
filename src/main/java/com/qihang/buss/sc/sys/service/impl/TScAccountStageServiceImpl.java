
package com.qihang.buss.sc.sys.service.impl;
import com.qihang.buss.sc.baseinfo.entity.TScIcitemEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemPriceEntity;
import com.qihang.buss.sc.baseinfo.entity.TScMeasureunitEntity;
import com.qihang.buss.sc.baseinfo.entity.TScStockEntity;
import com.qihang.buss.sc.sys.service.TScAccountStageServiceI;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.buss.sc.util.StageUtil;
import com.qihang.winter.core.common.dao.impl.CommonDao;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.sys.entity.TScAccountStageEntity;
import com.qihang.buss.sc.sys.entity.VScCheckstageEntity;
import com.qihang.buss.sc.sys.entity.VScCheckspeedbalEntity;
import com.qihang.buss.sc.sys.entity.TScIcBalEntity;
import com.qihang.buss.sc.sys.entity.TScRpContactbalEntity;
import com.qihang.buss.sc.sys.entity.TScRpExpbalEntity;

import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.core.util.DateUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.core.util.oConvertUtils;

import java.io.Serializable;


@Service("tScAccountStageService")
@Transactional
public class TScAccountStageServiceImpl extends CommonServiceImpl implements TScAccountStageServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScAccountStageEntity)entity);
 	}
	
	public void addMain(TScAccountStageEntity tScAccountStage,
	    List<VScCheckstageEntity> vScCheckstageList,List<VScCheckspeedbalEntity> vScCheckspeedbalList,List<TScIcBalEntity> tScIcBalList,List<TScRpContactbalEntity> tScRpContactbalList,List<TScRpExpbalEntity> tScRpExpbalList){
		//保存主信息
//		this.save(tScAccountStage);
		
		/**保存-未审核单据*/
//			for(VScCheckstageEntity vScCheckstage:vScCheckstageList){
//				//外键设置
//				vScCheckstage.setDate(tScAccountStage.getDate());
//				this.save(vScCheckstage);
//			}
			/**保存-负库存情况*/
//			for(VScCheckspeedbalEntity vScCheckspeedbal:vScCheckspeedbalList){
//				//外键设置
//				vScCheckspeedbal.setDate(tScAccountStage.getDate());
//				this.save(vScCheckspeedbal);
//			}
//		/**保存-存货结账*/
//		for(TScIcBalEntity tScIcBal:tScIcBalList){
//			//外键设置
//			tScIcBal.setFid(tScAccountStage.getId());
//			this.save(tScIcBal);
//		}
//		/**保存-应收应付结账*/
//		for(TScRpContactbalEntity tScRpContactbal:tScRpContactbalList){
//			//外键设置
//			tScRpContactbal.setFid(tScAccountStage.getId());
//			this.save(tScRpContactbal);
//		}
//		/**保存-收支结账*/
//		for(TScRpExpbalEntity tScRpExpbal:tScRpExpbalList){
//			//外键设置
//			tScRpExpbal.setFid(tScAccountStage.getId());
//			this.save(tScRpExpbal);
//		}
		//取当前用户登录的sonId分支机构ID
		String sonId = ResourceUtil.getSessionUserName().getSonCompanyId();
		String createname = ResourceUtil.getSessionUserName().getRealName();
		String createby = ResourceUtil.getSessionUserName().getId();
	    //调用 结账功能后操作主方法
		StageUtil.doStageMain(tScAccountStage.getDate(), createname, createby);
		//执行新增操作配置的sql增强
		this.doAddSql(tScAccountStage);
	}
	
	public void updateMain(TScAccountStageEntity tScAccountStage,
	        List<VScCheckstageEntity> vScCheckstageList,List<VScCheckspeedbalEntity> vScCheckspeedbalList,List<TScIcBalEntity> tScIcBalList,List<TScRpContactbalEntity> tScRpContactbalList,List<TScRpExpbalEntity> tScRpExpbalList) {
		//保存主表信息
		this.saveOrUpdate(tScAccountStage);
		//===================================================================================
		//获取参数
		Object dATE0 = tScAccountStage.getDate();
		Object dATE1 = tScAccountStage.getDate();
		Object id2 = tScAccountStage.getId();
		Object id3 = tScAccountStage.getId();
		Object id4 = tScAccountStage.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-未审核单据
//	    String hql0 = "from VScCheckstageEntity where 1 = 1 AND dATE = ? ";
//	    List<VScCheckstageEntity> vScCheckstageOldList = this.findHql(hql0,dATE0);
//		//2.筛选更新明细数据-未审核单据
//		for(VScCheckstageEntity oldE:vScCheckstageOldList){
//			boolean isUpdate = false;
//				for(VScCheckstageEntity sendE:vScCheckstageList){
//					//需要更新的明细数据-未审核单据
//					if(oldE.getId().equals(sendE.getId())){
//		    			try {
//							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
//							this.saveOrUpdate(oldE);
//						} catch (Exception e) {
//							e.printStackTrace();
//							throw new BusinessException(e.getMessage());
//						}
//						isUpdate= true;
//		    			break;
//		    		}
//		    	}
//	    		if(!isUpdate){
//		    		//如果数据库存在的明细，前台没有传递过来则是删除-未审核单据
//		    		super.delete(oldE);
//	    		}
//
//			}
//			//3.持久化新增的数据-未审核单据
//			for(VScCheckstageEntity vScCheckstage:vScCheckstageList){
//				if(oConvertUtils.isEmpty(vScCheckstage.getId())){
//					//外键设置
//					vScCheckstage.setDate(tScAccountStage.getDate());
//					this.save(vScCheckstage);
//				}
//			}
		//===================================================================================
		//1.查询出数据库的明细数据-负库存情况
//	    String hql1 = "from VScCheckspeedbalEntity where 1 = 1 AND dATE = ? ";
//	    List<VScCheckspeedbalEntity> vScCheckspeedbalOldList = this.findHql(hql1,dATE1);
//		//2.筛选更新明细数据-负库存情况
//		for(VScCheckspeedbalEntity oldE:vScCheckspeedbalOldList){
//			boolean isUpdate = false;
//				for(VScCheckspeedbalEntity sendE:vScCheckspeedbalList){
//					//需要更新的明细数据-负库存情况
//					if(oldE.getId().equals(sendE.getId())){
//		    			try {
//							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
//							this.saveOrUpdate(oldE);
//						} catch (Exception e) {
//							e.printStackTrace();
//							throw new BusinessException(e.getMessage());
//						}
//						isUpdate= true;
//		    			break;
//		    		}
//		    	}
//	    		if(!isUpdate){
//		    		//如果数据库存在的明细，前台没有传递过来则是删除-负库存情况
//		    		super.delete(oldE);
//	    		}
//
//			}
//			//3.持久化新增的数据-负库存情况
//			for(VScCheckspeedbalEntity vScCheckspeedbal:vScCheckspeedbalList){
//				if(oConvertUtils.isEmpty(vScCheckspeedbal.getId())){
//					//外键设置
//					vScCheckspeedbal.setDate(tScAccountStage.getDate());
//					this.save(vScCheckspeedbal);
//				}
//			}
		//===================================================================================
		//1.查询出数据库的明细数据-存货结账
	    String hql2 = "from TScIcBalEntity where 1 = 1 AND fID = ? ";
	    List<TScIcBalEntity> tScIcBalOldList = this.findHql(hql2,id2);
		//2.筛选更新明细数据-存货结账
		for(TScIcBalEntity oldE:tScIcBalOldList){
			boolean isUpdate = false;
				for(TScIcBalEntity sendE:tScIcBalList){
					//需要更新的明细数据-存货结账
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-存货结账
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-存货结账
			for(TScIcBalEntity tScIcBal:tScIcBalList){
				if(oConvertUtils.isEmpty(tScIcBal.getId())){
					//外键设置
					tScIcBal.setFid(tScAccountStage.getId());
					this.save(tScIcBal);
				}
			}
		//===================================================================================
		//1.查询出数据库的明细数据-应收应付结账
	    String hql3 = "from TScRpContactbalEntity where 1 = 1 AND fID = ? ";
	    List<TScRpContactbalEntity> tScRpContactbalOldList = this.findHql(hql3,id3);
		//2.筛选更新明细数据-应收应付结账
		for(TScRpContactbalEntity oldE:tScRpContactbalOldList){
			boolean isUpdate = false;
				for(TScRpContactbalEntity sendE:tScRpContactbalList){
					//需要更新的明细数据-应收应付结账
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-应收应付结账
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-应收应付结账
			for(TScRpContactbalEntity tScRpContactbal:tScRpContactbalList){
				if(oConvertUtils.isEmpty(tScRpContactbal.getId())){
					//外键设置
					tScRpContactbal.setFid(tScAccountStage.getId());
					this.save(tScRpContactbal);
				}
			}
		//===================================================================================
		//1.查询出数据库的明细数据-收支结账
	    String hql4 = "from TScRpExpbalEntity where 1 = 1 AND fID = ? ";
	    List<TScRpExpbalEntity> tScRpExpbalOldList = this.findHql(hql4,id4);
		//2.筛选更新明细数据-收支结账
		for(TScRpExpbalEntity oldE:tScRpExpbalOldList){
			boolean isUpdate = false;
				for(TScRpExpbalEntity sendE:tScRpExpbalList){
					//需要更新的明细数据-收支结账
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-收支结账
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-收支结账
			for(TScRpExpbalEntity tScRpExpbal:tScRpExpbalList){
				if(oConvertUtils.isEmpty(tScRpExpbal.getId())){
					//外键设置
					tScRpExpbal.setFid(tScAccountStage.getId());
					this.save(tScRpExpbal);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScAccountStage);
	}

	
	public void delMain(TScAccountStageEntity tScAccountStage) {
		//删除主表信息
		this.delete(tScAccountStage);
		//===================================================================================
		//获取参数
		Object dATE0 = tScAccountStage.getDate();
		Object dATE1 = tScAccountStage.getDate();
		Object id2 = tScAccountStage.getId();
		Object id3 = tScAccountStage.getId();
		Object id4 = tScAccountStage.getId();
		//===================================================================================
		//删除-未审核单据
	    String hql0 = "from VScCheckstageEntity where 1 = 1 AND dATE = ? ";
	    List<VScCheckstageEntity> vScCheckstageOldList = this.findHql(hql0,dATE0);
		this.deleteAllEntitie(vScCheckstageOldList);
		//===================================================================================
		//删除-负库存情况
	    String hql1 = "from VScCheckspeedbalEntity where 1 = 1 AND dATE = ? ";
	    List<VScCheckspeedbalEntity> vScCheckspeedbalOldList = this.findHql(hql1,dATE1);
		this.deleteAllEntitie(vScCheckspeedbalOldList);
		//===================================================================================
		//删除-存货结账
	    String hql2 = "from TScIcBalEntity where 1 = 1 AND fID = ? ";
	    List<TScIcBalEntity> tScIcBalOldList = this.findHql(hql2,id2);
		this.deleteAllEntitie(tScIcBalOldList);
		//===================================================================================
		//删除-应收应付结账
	    String hql3 = "from TScRpContactbalEntity where 1 = 1 AND fID = ? ";
	    List<TScRpContactbalEntity> tScRpContactbalOldList = this.findHql(hql3,id3);
		this.deleteAllEntitie(tScRpContactbalOldList);
		//===================================================================================
		//删除-收支结账
	    String hql4 = "from TScRpExpbalEntity where 1 = 1 AND fID = ? ";
	    List<TScRpExpbalEntity> tScRpExpbalOldList = this.findHql(hql4,id4);
		this.deleteAllEntitie(tScRpExpbalOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScAccountStageEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScAccountStageEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScAccountStageEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScAccountStageEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{date}",String.valueOf(t.getDate()));
 		sql  = sql.replace("#{accountid}",String.valueOf(t.getAccountId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	/**
	 * 按当前期别的计算月库存（主要筛选通过统计当月库存日结表，再累计上期库存结账表，筛选出负库存的数据）
	 * @param tScAccountStage
	 * @param isMinusQty 为true表示只显示负库存列表，为false显示全部库存列表
	 * @return
	 * @author hjh 2016-9-26
	 */
	public List findVScCheckspeedbalList(TScAccountStageEntity tScAccountStage, boolean isMinusQty){
		//===================================================================================
		//获取参数
		Date dATE0 = tScAccountStage.getDate();
		List<VScCheckspeedbalEntity> vScCheckspeedbalEntityList = null;
		//===================================================================================
		//查询-负库存情况
		String hql1 = "from VScCheckspeedbalEntity where 1 = 1 AND dATE >= ? AND dATE< ? order by dATE,stockId,sonName";//日期条件为当月第一天到当月最后一天,即大于等于当月第一天，小于下个月第一天
		try{
			String strDate = DateUtils.date2Str(dATE0, DateUtils.date_sdf);
			strDate = DateUtils.formatAddMonth(strDate, "yyyy-MM-dd",1);//当月1日先加一个月
			//strDate = DateUtils.formatAddDate(strDate,  "yyyy-MM-dd",-1);//再减一天，即当月的最后一天
			Date dATE1 = DateUtils.str2Date(strDate, DateUtils.date_sdf);
			//strDate = DateUtils.formatDate(dATE0, "yyyyMM");
			vScCheckspeedbalEntityList = commonDao.findHql(hql1, dATE0, dATE1);
			//todo:要再从上期结存来累加当月每个商品库存，是否有负库存，要把v_sc_checkspeedbal视图里的库存数小于0的条件去掉。
			//找到上期的期未结余
			String prestrMonth = DateUtils.formatAddMonth(strDate, "yyyy-MM-dd",-2);//当期上个月(前面已经加了1个月)
			Date predateMonth = DateUtils.str2Date(prestrMonth, DateUtils.date_sdf);
			String preStageYear = DateUtils.formatDate(predateMonth, "yyyy");
			String preStagePeriod = DateUtils.formatDate(predateMonth, "MM");
			Date accountStartDate = AccountUtil.getAccountStartDate();
			Date accountInitStartDate = AccountUtil.getAccountInitStartDate();
			boolean isFirstStage = false;//是否第一期
			if (DateUtils.formatDate(accountStartDate,"yyyyMM").equals(DateUtils.formatDate(accountInitStartDate,"yyyyMM"))){
				isFirstStage = true;
			}
			//todo:如果当期是该账套的第一期，则通过本期的期初数量来计算；否则 通过上期的期未数量来计算
			String preStageSql = "from TScIcBalEntity where 1=1 and year=? and period=?" ;
			List<TScIcBalEntity> tScIcBalEntityList = commonDao.findHql(preStageSql, preStageYear, preStagePeriod);
			//进行上期的期未，在本期存货日结中未体现的，追加到本期结余
			for (TScIcBalEntity tScIcBalEntity : tScIcBalEntityList){
				boolean isExstsItemId = false;
				for(VScCheckspeedbalEntity vScCheckspeedbalEntity : vScCheckspeedbalEntityList) {
					if (tScIcBalEntity.getItemID().equals(vScCheckspeedbalEntity.getItemid()) && tScIcBalEntity.getStockID().equals(vScCheckspeedbalEntity.getStockid())
							&& ((tScIcBalEntity.getBatchNo()==null && vScCheckspeedbalEntity.getBatchno()==null) || (tScIcBalEntity.getBatchNo().equals(vScCheckspeedbalEntity.getBatchno())))){
						isExstsItemId = true;
						break;
					}
				}
				if (isExstsItemId==false){//上期结存不在本期存货日结表中体现的，追加到本期结余
					VScCheckspeedbalEntity vScCheckspeedbalEntity = new VScCheckspeedbalEntity();
					vScCheckspeedbalEntity.setDate(tScAccountStage.getDate());
					vScCheckspeedbalEntity.setStockid(tScIcBalEntity.getStockID());
					vScCheckspeedbalEntity.setItemid(tScIcBalEntity.getItemID());
					vScCheckspeedbalEntity.setBatchno(tScIcBalEntity.getBatchNo());
					if (isFirstStage){//首期,本期期初
						vScCheckspeedbalEntity.setQty(tScIcBalEntity.getBegQty().doubleValue());
					}else {//非首期，上期期未
						vScCheckspeedbalEntity.setQty(tScIcBalEntity.getEndQty().doubleValue());
					}
					//vScCheckspeedbalEntity.setSonname();//根据sonid来计算
					//vScCheckspeedbalEntity.setItemnumber();//根据商品id来计算
					//vScCheckspeedbalEntity.setItemname();
					//vScCheckspeedbalEntity.setUnitname();//找商品的默认单位的单位名称
					//vScCheckspeedbalEntity.setBigqty();//根据endQty来计算
					//vScCheckspeedbalEntity.setSmallqty();
					//vScCheckspeedbalEntity.setSecqty();
					vScCheckspeedbalEntity.setDepartmentname(vScCheckspeedbalEntity.getSonname());
					//计算仓库名称
					TScStockEntity tScStockEntity = commonDao.getEntity(TScStockEntity.class, tScIcBalEntity.getStockID());
					if (tScStockEntity!=null){
						vScCheckspeedbalEntity.setSonname(tScStockEntity.getName());
					}
					//计算商品信息
					TScIcitemEntity tScIcitemEntity = commonDao.getEntity(TScIcitemEntity.class, tScIcBalEntity.getItemID());
					if (tScIcitemEntity!=null){
						vScCheckspeedbalEntity.setItemnumber(tScIcitemEntity.getNumber());
						vScCheckspeedbalEntity.setItemname(tScIcitemEntity.getName());
					}
					//计算商品默认单位ID
					String itempriceHql = "from TScItemPriceEntity where itemID = ? and a.unitType=?";
					List<TScItemPriceEntity> tScItemPriceEntityList = commonDao.findHql(itempriceHql, tScIcBalEntity.getItemID(), Globals.UNIT_TYPE_BASIC);
					if (tScItemPriceEntityList.size()>0){
						TScItemPriceEntity tScItemPriceEntity = tScItemPriceEntityList.get(0);
						//计算商品默认单位ID对应的名称
						TScMeasureunitEntity tScMeasureunitEntity = commonDao.getEntity(TScMeasureunitEntity.class, tScItemPriceEntity.getUnitID());
						if (tScMeasureunitEntity!=null){
							vScCheckspeedbalEntity.setUnitname(tScMeasureunitEntity.getName());
						}
						//重新按照基本单位的换算率计算箱数、散数
						BigDecimal smallQty = new BigDecimal(vScCheckspeedbalEntity.getQty() % tScItemPriceEntity.getCoefficient().doubleValue());
						BigDecimal bigQty = new BigDecimal(vScCheckspeedbalEntity.getQty() / tScItemPriceEntity.getCoefficient().doubleValue());
						vScCheckspeedbalEntity.setBigqty(bigQty.intValue());
						vScCheckspeedbalEntity.setSmallqty(smallQty.doubleValue());
					}
					//计算商品辅助单位，有辅助单位的话，除以辅助单位换算率，没有的话就按0
					List<TScItemPriceEntity> sectScItemPriceEntityList = commonDao.findHql(itempriceHql, tScIcBalEntity.getItemID(), Globals.UNIT_TYPE_SEC);
					if (tScItemPriceEntityList.size()>0){
						TScItemPriceEntity tScItemPriceEntity = sectScItemPriceEntityList.get(0);
						//重新按照辅助单位的辅助换算率计算辅助数量
						BigDecimal secQty = new BigDecimal(vScCheckspeedbalEntity.getQty() % tScItemPriceEntity.getCoefficient().doubleValue());
						vScCheckspeedbalEntity.setSecqty(secQty.doubleValue());
					}else{
						vScCheckspeedbalEntity.setSecqty(new Double(0));
					}
					vScCheckspeedbalEntityList.add(vScCheckspeedbalEntity);
				}
			}
			//上期期未 累加到 本期库存
			for(VScCheckspeedbalEntity vScCheckspeedbalEntity : vScCheckspeedbalEntityList) {
				for (TScIcBalEntity tScIcBalEntity : tScIcBalEntityList){
					if (tScIcBalEntity.getItemID().equals(vScCheckspeedbalEntity.getItemid()) && tScIcBalEntity.getStockID().equals(vScCheckspeedbalEntity.getStockid())
							&& ((tScIcBalEntity.getBatchNo()==null && vScCheckspeedbalEntity.getBatchno()==null) || (tScIcBalEntity.getBatchNo().equals(vScCheckspeedbalEntity.getBatchno())))){
						vScCheckspeedbalEntity.setQty(vScCheckspeedbalEntity.getQty().doubleValue() + tScIcBalEntity.getEndQty().doubleValue());
						//计算商品默认单位ID
						String itempriceHql = "from TScItemPriceEntity where itemID = ? and a.unitType=?";
						List<TScItemPriceEntity> tScItemPriceEntityList = commonDao.findHql(itempriceHql, tScIcBalEntity.getItemID(), Globals.UNIT_TYPE_BASIC);
						if (tScItemPriceEntityList.size()>0) {
							TScItemPriceEntity tScItemPriceEntity = tScItemPriceEntityList.get(0);
							//重新搂照换算率计算箱数、散数
							BigDecimal qty = new BigDecimal(0);
							if (isFirstStage){//首期,本期期初
								qty = tScIcBalEntity.getBegQty();
							}else {//非首期，上期期未
								qty = tScIcBalEntity.getEndQty();
							}
							qty = new BigDecimal(vScCheckspeedbalEntity.getQty().doubleValue() + qty.doubleValue());
							vScCheckspeedbalEntity.setQty(qty.doubleValue());
							BigDecimal smallQty = new BigDecimal(vScCheckspeedbalEntity.getQty() % tScItemPriceEntity.getCoefficient().doubleValue());
							BigDecimal bigQty = new BigDecimal(vScCheckspeedbalEntity.getQty() / tScItemPriceEntity.getCoefficient().doubleValue());
							vScCheckspeedbalEntity.setBigqty(bigQty.intValue());
							vScCheckspeedbalEntity.setSmallqty(smallQty.doubleValue());
						}
						//计算商品辅助单位，有辅助单位的话，除以辅助单位换算率，没有的话就按0
						List<TScItemPriceEntity> sectScItemPriceEntityList = commonDao.findHql(itempriceHql, tScIcBalEntity.getItemID(), Globals.UNIT_TYPE_SEC);
						if (tScItemPriceEntityList.size()>0){
							TScItemPriceEntity tScItemPriceEntity = sectScItemPriceEntityList.get(0);
							//重新按照辅助单位的辅助换算率计算辅助数量
							BigDecimal secqty = new BigDecimal(0);
							if (isFirstStage){//首期,本期期初
								secqty = tScIcBalEntity.getSecBegQty();
							}else {//非首期，上期期未
								secqty = tScIcBalEntity.getSecEndQty();
							}
							BigDecimal secQty = new BigDecimal(secqty.doubleValue() % tScItemPriceEntity.getCoefficient().doubleValue());
							vScCheckspeedbalEntity.setSecqty(secQty.doubleValue());
						}else{
							vScCheckspeedbalEntity.setSecqty(new Double(0));
						}
					}
				}
			}

			if (isMinusQty){//要筛选掉库存大于等于0的商品
				for(Iterator<VScCheckspeedbalEntity> it = vScCheckspeedbalEntityList.iterator();it.hasNext();) {
					VScCheckspeedbalEntity vScCheckspeedbalEntity = it.next();
					if (vScCheckspeedbalEntity.getQty()>=0){
						//vScCheckspeedbalEntityList.remove(vScCheckspeedbalEntity);
						it.remove();
					}
				}
			}
		}catch(Exception e){
			throw new BusinessException("计算当前期别负库存商品方法出错" + e.getMessage());
		}
		return vScCheckspeedbalEntityList;
	}

	/**
	 * 统计当期未审核的数量
	 * @param tScAccountStage
	 * @param isMinusQty 为true表示只显示负库存列表，为false显示全部库存列表
	 * @return
	 * @author hjh 2016-9-26
	 */
	public long countStageCheck(TScAccountStageEntity tScAccountStage, boolean isMinusQty){
		//统计未审核单据数量
		String strDate = DateUtils.date2Str(tScAccountStage.getDate(), DateUtils.date_sdf);
		try {
			strDate = DateUtils.formatAddMonth(strDate, "yyyy-MM-dd",1);//当月1日先加一个月
		}catch (Exception e){

		}
		Date dATE1 = DateUtils.str2Date(strDate, DateUtils.date_sdf);
		String checksql = "select count(*) from v_sc_checkstage where date>=? and date<?";
		long vScCheckstageNum = commonDao.getCountForJdbcParam(checksql, new Object[]{tScAccountStage.getDate(), dATE1});
		return vScCheckstageNum;
	}

	/**
	 * 结账前当期检查结果，正确时返回信息为空字符串，否则则为错误信息
	 * @param tScAccountStage
	 * @param isMinusQty 为true表示只显示负库存列表，为false显示全部库存列表
	 * @return
	 * @author hjh 2016-9-26
	 */
	public String checkIsStage(TScAccountStageEntity tScAccountStage, boolean isMinusQty){
		String msg = "";
		//获取当前账套是否允许负库存结账
		boolean isMinusInventoryAccount = AccountUtil.isMinusInventoryAccount();
		//统计未审核单据数量
		long vScCheckstageNum = this.countStageCheck(tScAccountStage, isMinusQty);
		long vScCheckspeedbalNum = 0;
		if (vScCheckstageNum==0) {
			//如果不允许负库存结账，则统计负库存数量
			if (!isMinusInventoryAccount) {
				//要再从上期结存来累加当月每个商品库存，是否有负库存，要把v_sc_checkspeedbal视图里的库存数小于0的条件去掉。
				List<VScCheckspeedbalEntity> scCheckspeedbalEntityList = this.findVScCheckspeedbalList(tScAccountStage, true);
				vScCheckspeedbalNum = scCheckspeedbalEntityList.size();
				if (vScCheckspeedbalNum==0){//如果不允许负库存结账，则如果无负库存商品才允许结账

				}else{
					msg += "当期存在负库存商品记录" + vScCheckspeedbalNum + "条，请检查。";
				}
			}else{//如果允许负库存结账时，如果无未审核单据则允许结账
			}
		}else{
			msg += "当期存在未审核单据记录" + vScCheckstageNum + "条，请检查。";
		}
		boolean isAccountOpen = AccountUtil.isAccountOpen();
		if (isAccountOpen==false){
			msg += "系统未开账，请检查。";
		}
		return msg;
	}

	/**
	 * 反结账前当期检查结果，正确时返回信息为空字符串，否则则为错误信息
	 * @param tScAccountStage
	 * @return
	 * @author hjh 2016-9-26
	 */
	public String checkIsUnStage(TScAccountStageEntity tScAccountStage){
		String msg = "";
		//检查账套是否开账
		boolean isAccountOpen = AccountUtil.isAccountOpen();
		if (isAccountOpen==false){
			msg += "系统未开账，请检查。";
		}else{
			Date accountStartDate = AccountUtil.getAccountStartDate();
			Date accountInitStartDate = AccountUtil.getAccountInitStartDate();
			if (DateUtils.formatDate(accountStartDate,"yyyyMM").equals(DateUtils.formatDate(accountInitStartDate,"yyyyMM"))){//开账当期不允许反结账，即第一期不允许反结账
				msg += "当前期间为首期，不允许再对上一期进行反结账，请检查。";
			}
		}
		return msg;
	}
}