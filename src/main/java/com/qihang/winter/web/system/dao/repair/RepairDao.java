package com.qihang.winter.web.system.dao.repair;

import org.springframework.stereotype.Repository;

/**
 * 工程修复
 * @author Zerrion
 * @date 2013-11-11
 * @version 1.0
 */
@Repository("repairDao")
public interface RepairDao {
	
	public void batchRepairTerritory();

}
