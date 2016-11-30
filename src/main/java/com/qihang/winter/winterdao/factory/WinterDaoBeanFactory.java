package com.qihang.winter.winterdao.factory;

import java.lang.reflect.Proxy;

import com.qihang.winter.winterdao.aop.WinterDaoHandler;
import org.springframework.beans.factory.FactoryBean;

/**
 * 持久层的的工厂
 * 
 * @author Zerrion
 * @date 2014年12月7日 下午4:18:47
 * @param <T>
 */
public class WinterDaoBeanFactory<T> implements FactoryBean<T> {

	private Class<T> daoInterface;

	private WinterDaoHandler proxy;

	@Override
	public T getObject() throws Exception {
		return newInstance();
	}

	@Override
	public Class<?> getObjectType() {
		return daoInterface;
	}

	public WinterDaoHandler getProxy() {
		return proxy;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@SuppressWarnings("unchecked")
	private T newInstance() {
		return (T) Proxy.newProxyInstance(daoInterface.getClassLoader(), new Class[] { daoInterface }, proxy);
	}

	public void setProxy(WinterDaoHandler proxy) {
		this.proxy = proxy;
	}

	public void setDaoInterface(Class<T> daoInterface) {
		this.daoInterface = daoInterface;
	}

}
