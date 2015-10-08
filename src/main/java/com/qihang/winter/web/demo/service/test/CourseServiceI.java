package com.qihang.winter.web.demo.service.test;

import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.web.demo.entity.test.CourseEntity;

public interface CourseServiceI extends CommonService {

	/**
	 * 保存课程
	 *@author Zerrion
	 *@date   2013-11-10
	 *@param  course
	 */
	void saveCourse(CourseEntity course);
	/**
	 * 更新课程
	 *@author Zerrion
	 *@date   2013-11-10
	 *@param  course
	 */
	void updateCourse(CourseEntity course);

}
