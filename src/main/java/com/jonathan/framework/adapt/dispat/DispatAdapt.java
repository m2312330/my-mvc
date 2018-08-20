package com.jonathan.framework.adapt.dispat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jonathan.framework.adapt.iface.ParamsAdapt;


public class DispatAdapt {

	private static final Map<Class<?>, ParamsAdapt> ADAPT_MAP=new ConcurrentHashMap<Class<?>, ParamsAdapt>();
	
	
	
	public static ParamsAdapt getAdapt(Class<?> clazz) throws InstantiationException, IllegalAccessException{
		if(ADAPT_MAP.containsKey(clazz)){
			return ADAPT_MAP.get(clazz);
		}
		ParamsAdapt adapt=(ParamsAdapt) clazz.newInstance();
		ADAPT_MAP.put(clazz, adapt);
		return adapt;
	}
}
