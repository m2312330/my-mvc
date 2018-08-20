package com.jonathan.framework.exception;

@SuppressWarnings("serial")
public class MappingConflicException extends MyException {


	public MappingConflicException(String path) {
		super("Mapping地址已存在:" + path);
	}


	public MappingConflicException(String path, Exception e) {
		super("Mapping地址已存在:" + path, e);
	}
}
