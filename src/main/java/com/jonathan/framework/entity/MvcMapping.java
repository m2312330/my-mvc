package com.jonathan.framework.entity;

import com.jonathan.framework.adapt.iface.ParamsAdapt;

import java.lang.reflect.Method;
import java.util.List;

@SuppressWarnings("serial")
public class MvcMapping extends BaseModel {

	private String path;

	private Method method;

	private Object bean;

	private List<BeanEntity> paramTypes;

	private ParamsAdapt ParamsAdapt;

	public ParamsAdapt getParamsAdapt() {
		return ParamsAdapt;
	}

	public void setParamsAdapt(ParamsAdapt ParamsAdapt) {
		this.ParamsAdapt = ParamsAdapt;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public List<BeanEntity> getParamTypes() {
		return paramTypes;
	}

	public void setParamTypes(List<BeanEntity> paramTypes) {
		this.paramTypes = paramTypes;
	}

}