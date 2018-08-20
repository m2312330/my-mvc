package com.jonathan.framework.exception;

@SuppressWarnings("serial")
public class BeanConflictException extends MyException {


	public BeanConflictException(String bean){
		super("Bean已经存在:"+bean);
	}
	
	public BeanConflictException(String bean,Exception e){
		super("Bean已经存在:"+bean, e);
	}
}
