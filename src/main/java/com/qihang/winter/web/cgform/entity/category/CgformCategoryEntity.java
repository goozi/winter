package com.qihang.winter.web.cgform.entity.category;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.qihang.winter.core.common.entity.IdEntity;
import com.qihang.winter.web.system.pojo.base.TSCategoryEntity;
import com.qihang.winter.web.cgform.entity.config.CgFormHeadEntity;

/**
 * Online Coding 分类管理
 * 
 * @author Zerrion
 * @date 2014年10月14日 下午10:23:08
 */
@Entity
@Table(name = "cgform_category", schema = "")
public class CgformCategoryEntity extends IdEntity {
	/**
	 * 表单
	 */
	private CgFormHeadEntity form;
	/**
	 * 分类
	 */
	private TSCategoryEntity category;

	@ManyToOne(targetEntity = CgFormHeadEntity.class)
	@JoinColumn(name = "cgform_id", referencedColumnName = "id")
	public CgFormHeadEntity getForm() {
		return form;
	}

	public void setForm(CgFormHeadEntity form) {
		this.form = form;
	}

	@ManyToOne(targetEntity = TSCategoryEntity.class)
	@JoinColumn(name = "category_code", referencedColumnName = "code")
	public TSCategoryEntity getCategory() {
		return category;
	}

	public void setCategory(TSCategoryEntity category) {
		this.category = category;
	}

}
