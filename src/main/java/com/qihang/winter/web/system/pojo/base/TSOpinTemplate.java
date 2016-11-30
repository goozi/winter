package com.qihang.winter.web.system.pojo.base;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.qihang.winter.core.common.entity.IdEntity;

/**
 * TType entity.
 *  @author  Zerrion
 */
@Entity
@Table(name = "t_s_opintemplate")
public class TSOpinTemplate extends IdEntity implements java.io.Serializable {
	private String descript;
	@Column(name = "descript", length = 100)
	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}
	
}