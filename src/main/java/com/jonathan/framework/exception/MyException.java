package com.jonathan.framework.exception;

@SuppressWarnings("serial")
public class MyException extends Exception{

	public MyException(){
		super();
	}
	
	public MyException(String msg){
		super(msg);
	}
	
	public MyException(String msg, Exception e){
		super(msg, e);
	}
}
