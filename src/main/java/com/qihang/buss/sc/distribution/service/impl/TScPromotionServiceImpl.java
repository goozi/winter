
package com.qihang.buss.sc.distribution.service.impl;

import com.qihang.buss.sc.distribution.entity.TScPromotionEntity;
import com.qihang.buss.sc.distribution.entity.TScPromotionViewEntity;
import com.qihang.buss.sc.distribution.entity.TScPromotiongiftsentryEntity;
import com.qihang.buss.sc.distribution.entity.TScPromotiongoodsentryEntity;
import com.qihang.buss.sc.distribution.service.TScPromotionServiceI;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.oConvertUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service("tScPromotionService")
@Transactional
public class TScPromotionServiceImpl extends CommonServiceImpl implements TScPromotionServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScPromotionEntity) entity);
 	}
	
	public void addMain(TScPromotionEntity tScPromotion,
	        List<TScPromotiongiftsentryEntity> tScPromotiongiftsentryList,List<TScPromotiongoodsentryEntity> tScPromotiongoodsentryList){
			//保存主信息
			this.save(tScPromotion);
		
			/**保存-赠品信息*/
			for(TScPromotiongiftsentryEntity tScPromotiongiftsentry:tScPromotiongiftsentryList){
				//外键设置
				tScPromotiongiftsentry.setInterID(tScPromotion.getId());
				this.save(tScPromotiongiftsentry);
			}
			/**保存-商品信息*/
			for(TScPromotiongoodsentryEntity tScPromotiongoodsentry:tScPromotiongoodsentryList){
				//外键设置
				tScPromotiongoodsentry.setInterID(tScPromotion.getId());
				this.save(tScPromotiongoodsentry);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScPromotion);
	}

	
	public void updateMain(TScPromotionEntity tScPromotion,
	        List<TScPromotiongiftsentryEntity> tScPromotiongiftsentryList,List<TScPromotiongoodsentryEntity> tScPromotiongoodsentryList) {
		//保存主表信息
		this.saveOrUpdate(tScPromotion);
		//===================================================================================
		//获取参数
		Object id0 = tScPromotion.getId();
		Object id1 = tScPromotion.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-赠品信息
	    String hql0 = "from TScPromotiongiftsentryEntity where 1 = 1 AND iNTERID = ? ";
	    List<TScPromotiongiftsentryEntity> tScPromotiongiftsentryOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-赠品信息
		for(TScPromotiongiftsentryEntity oldE:tScPromotiongiftsentryOldList){
			boolean isUpdate = false;
				for(TScPromotiongiftsentryEntity sendE:tScPromotiongiftsentryList){
					//需要更新的明细数据-赠品信息
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-赠品信息
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-赠品信息
			for(TScPromotiongiftsentryEntity tScPromotiongiftsentry:tScPromotiongiftsentryList){
				if(oConvertUtils.isEmpty(tScPromotiongiftsentry.getId())){
					//外键设置
					tScPromotiongiftsentry.setInterID(tScPromotion.getId());
					this.save(tScPromotiongiftsentry);
				}
			}
		//===================================================================================
		//1.查询出数据库的明细数据-商品信息
	    String hql1 = "from TScPromotiongoodsentryEntity where 1 = 1 AND iNTERID = ? ";
	    List<TScPromotiongoodsentryEntity> tScPromotiongoodsentryOldList = this.findHql(hql1,id1);
		//2.筛选更新明细数据-商品信息
		for(TScPromotiongoodsentryEntity oldE:tScPromotiongoodsentryOldList){
			boolean isUpdate = false;
				for(TScPromotiongoodsentryEntity sendE:tScPromotiongoodsentryList){
					//需要更新的明细数据-商品信息
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-商品信息
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-商品信息
			for(TScPromotiongoodsentryEntity tScPromotiongoodsentry:tScPromotiongoodsentryList){
				if(oConvertUtils.isEmpty(tScPromotiongoodsentry.getId())){
					//外键设置
					tScPromotiongoodsentry.setInterID(tScPromotion.getId());
					this.save(tScPromotiongoodsentry);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScPromotion);
	}

	
	public void delMain(TScPromotionEntity tScPromotion) {
		//删除主表信息
		this.delete(tScPromotion);
		//===================================================================================
		//获取参数
		Object id0 = tScPromotion.getId();
		Object id1 = tScPromotion.getId();
		//===================================================================================
		//删除-赠品信息
	    String hql0 = "from TScPromotiongiftsentryEntity where 1 = 1 AND iNTERID = ? ";
	    List<TScPromotiongiftsentryEntity> tScPromotiongiftsentryOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(tScPromotiongiftsentryOldList);
		//===================================================================================
		//删除-商品信息
	    String hql1 = "from TScPromotiongoodsentryEntity where 1 = 1 AND iNTERID = ? ";
	    List<TScPromotiongoodsentryEntity> tScPromotiongoodsentryOldList = this.findHql(hql1,id1);
		this.deleteAllEntitie(tScPromotiongoodsentryOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScPromotionEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScPromotionEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScPromotionEntity t){
	 	return true;
 	}

	@Override
	public List<TScPromotionViewEntity> getGiftsGoodsInfo(String goodsId, double goodsQty) {
		//1.根据商品ID查询已审核的赠品记录
		String getGiftsHql = "from TScPromotionViewEntity where 1 = 1 and goodsItemId = ?  and checkstate = 2 order by date desc ";
		List<TScPromotionViewEntity> giftsList = this.findHql(getGiftsHql, new Object[]{goodsId});
		//如果赠品记录不为空
		if(!giftsList.isEmpty()){
			long currentDate = new Date().getTime();//获得当前时间毫秒数
			int size = giftsList.size()==1?1:giftsList.size()-1;
			outer:
			for (int i = 0;i<size;i++){
				//获得当前记录的起始日期的时间毫秒数
				long bDate = giftsList.get(i).getBegDate().getTime();
				long eDate = giftsList.get(i).getEndDate().getTime();
				//获得当前记录的商品数量
				double oGoodsQty = giftsList.get(i).getGoodsQty().doubleValue();
				//如果当前日期的时间毫秒数在有效期内并且数量也在有效范围
				if((currentDate >= bDate && currentDate <= eDate) && (goodsQty >= oGoodsQty)){
					for (int j = giftsList.size()-1;j>i;j--){
						//获得当前记录的起始日期的时间毫秒数
						long begDate = giftsList.get(j).getBegDate().getTime();
						long endDate = giftsList.get(j).getEndDate().getTime();

						//获得当前记录的商品数量
						double oldGoodsQty = giftsList.get(j).getGoodsQty().doubleValue();

						//如果当前日期的时间毫秒数在有效期内并且数量也在有效范围
						if((currentDate >= begDate && currentDate <= endDate) && (goodsQty >= oldGoodsQty)){
							//如果是相同赠品
							if(giftsList.get(i).getGiftItemId().equals(giftsList.get(j).getGiftItemId())){
								//根据单据日期比较
								if(giftsList.get(i).getDate().getTime() >= giftsList.get(j).getDate().getTime()){
									if(giftsList.size() > 1){//如果集合记录大于1才允许移除
										giftsList.remove(j);//移除掉单据日期最旧的记录
									}
									if(giftsList.size() == 1){ //如果集合只有一条记录直接 退出循环
										break outer;
									}
								}
							}
						}else{
							giftsList.remove(j);//移除不符合条件的记录
							if(giftsList.size() == 1){ //如果集合只有一条记录直接 退出循环
								break outer;
							}
						}
					}
				}else{
					giftsList.remove(i); //移除不符合条件的记录
					if(giftsList.size()==0){ //如果集合为空 直接退出循环
						break;
					}
					--i;//重置 循环变量 重新开始遍历
				}
			}
			if(giftsList.size()>0){
				for(TScPromotionViewEntity gift: giftsList){
					System.out.println("====================================");
					System.out.println(gift.getDate());
					System.out.println(gift.getGiftNumber());
					System.out.println(gift.getGiftName());
					System.out.println(gift.getGiftModel());
					System.out.println(gift.getGiftBarCode());
					System.out.println(gift.getGiftUnitName());
					System.out.println(gift.getGiftQty());
					System.out.println(gift.getGiftNote());
					System.out.println("====================================");
				}
			}else{
				System.out.println("emptyList=========================");
			}
			return giftsList;
		}
		return Collections.emptyList();
	}

	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScPromotionEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{trantype}",String.valueOf(t.getTranType()));
 		sql  = sql.replace("#{date}",String.valueOf(t.getDate()));
 		sql  = sql.replace("#{billno}",String.valueOf(t.getBillNo()));
 		sql  = sql.replace("#{empid}",String.valueOf(t.getEmpID()));
 		sql  = sql.replace("#{deptid}",String.valueOf(t.getDeptID()));
 		sql  = sql.replace("#{begdate}",String.valueOf(t.getBegDate()));
 		sql  = sql.replace("#{enddate}",String.valueOf(t.getEndDate()));
 		sql  = sql.replace("#{checkerid}",String.valueOf(t.getCheckerID()));
 		sql  = sql.replace("#{billerid}",String.valueOf(t.getBillerID()));
 		sql  = sql.replace("#{checkdate}",String.valueOf(t.getCheckDate()));
 		sql  = sql.replace("#{checkstate}",String.valueOf(t.getCheckState()));
 		sql  = sql.replace("#{cancellation}",String.valueOf(t.getCancellation()));
 		sql  = sql.replace("#{explanation}",String.valueOf(t.getExplanation()));
 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonID()));
 		sql  = sql.replace("#{version}",String.valueOf(t.getVersion()));
 		sql  = sql.replace("#{disabled}",String.valueOf(t.getDisabled()));
 		sql  = sql.replace("#{deleted}",String.valueOf(t.getDeleted()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}