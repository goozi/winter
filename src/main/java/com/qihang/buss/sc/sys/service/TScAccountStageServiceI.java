
package com.qihang.buss.sc.sys.service;
import com.qihang.buss.sc.sys.entity.TScAccountStageEntity;
import com.qihang.buss.sc.sys.entity.VScCheckstageEntity;
import com.qihang.buss.sc.sys.entity.VScCheckspeedbalEntity;
import com.qihang.buss.sc.sys.entity.TScIcBalEntity;
import com.qihang.buss.sc.sys.entity.TScRpContactbalEntity;
import com.qihang.buss.sc.sys.entity.TScRpExpbalEntity;

import java.util.List;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;

public interface TScAccountStageServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScAccountStageEntity tScAccountStage,
	        List<VScCheckstageEntity> vScCheckstageList,List<VScCheckspeedbalEntity> vScCheckspeedbalList,List<TScIcBalEntity> tScIcBalList,List<TScRpContactbalEntity> tScRpContactbalList,List<TScRpExpbalEntity> tScRpExpbalList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScAccountStageEntity tScAccountStage,
	        List<VScCheckstageEntity> vScCheckstageList,List<VScCheckspeedbalEntity> vScCheckspeedbalList,List<TScIcBalEntity> tScIcBalList,List<TScRpContactbalEntity> tScRpContactbalList,List<TScRpExpbalEntity> tScRpExpbalList);
	public void delMain (TScAccountStageEntity tScAccountStage);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScAccountStageEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScAccountStageEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScAccountStageEntity t);

	/**
	 * 按当前期别的计算月库存（主要筛选通过统计当月库存日结表，再累计上期库存结账表，筛选出负库存的数据）
	 * @param tScAccountStage
	 * @param isMinusQty 为true表示只显示负库存列表，为false显示全部库存列表
	 * @return
	 * @author hjh 2016-9-26
     */
	public List findVScCheckspeedbalList(TScAccountStageEntity tScAccountStage, boolean isMinusQty);

	/**
	 * 统计当期未审核的数量
	 * @param tScAccountStage
	 * @param isMinusQty 为true表示只显示负库存列表，为false显示全部库存列表
	 * @return
	 * @author hjh 2016-9-26
     */
	public long countStageCheck(TScAccountStageEntity tScAccountStage, boolean isMinusQty);

	/**
	 * 结账前当期检查结果，正确时返回信息为空字符串，否则则为错误信息
	 * @param tScAccountStage
	 * @param isMinusQty 为true表示只显示负库存列表，为false显示全部库存列表
	 * @return
	 * @author hjh 2016-9-26
	 */
	public String checkIsStage(TScAccountStageEntity tScAccountStage, boolean isMinusQty);

	/**
	 * 反结账前当期检查结果，正确时返回信息为空字符串，否则则为错误信息
	 * @param tScAccountStage
	 * @return
	 * @author hjh 2016-9-26
	 */
	public String checkIsUnStage(TScAccountStageEntity tScAccountStage);
}
