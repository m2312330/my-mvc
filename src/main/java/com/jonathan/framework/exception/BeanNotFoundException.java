package com.jonathan.framework.exception;

@SuppressWarnings("serial")
public class BeanNotFoundException extends MyException {

	
	public BeanNotFoundException(String bean){
		super("未找到Bean:"+bean);
	}
	
	public BeanNotFoundException(String bean,Class<?> clazz){
		super("未找到Bean:"+bean+" by "+clazz.getName());
	}
	
	public BeanNotFoundException(String bean,Exception e){
		super("未找到Bean:"+bean, e);
	}
}
