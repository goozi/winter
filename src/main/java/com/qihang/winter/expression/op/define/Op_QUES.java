/**
 * 
 */
package com.qihang.winter.expression.op.define;

import com.qihang.winter.expression.IllegalExpressionException;
import com.qihang.winter.expression.datameta.BaseDataMeta;
import com.qihang.winter.expression.datameta.Constant;
import com.qihang.winter.expression.datameta.Reference;
import com.qihang.winter.expression.op.IOperatorExecution;
import com.qihang.winter.expression.op.Operator;

/**
 * @author Zerrion
 * @version 2.0 
 * 2016-06-12
 */
public class Op_QUES implements IOperatorExecution {
	
	public static final Operator THIS_OPERATOR = Operator.QUES;
	/* (non-Javadoc)
	 * @see org.wltea.expression.op.IOperatorExecution#execute(org.wltea.expression.datameta.Constant[])
	 */
	public Constant execute(Constant[] args) {
		throw new UnsupportedOperationException("操作符\"" + THIS_OPERATOR.getToken() + "不支持该方法");
	}

	/* (non-Javadoc)
	 * @see org.wltea.expression.op.IOperatorExecution#verify(int, org.wltea.expression.datameta.BaseDataMeta[])
	 */
	public Constant verify(int opPositin, BaseDataMeta[] args)
			throws IllegalExpressionException {
		throw new UnsupportedOperationException("操作符\"" + THIS_OPERATOR.getToken() + "不支持该方法");
	}

}
