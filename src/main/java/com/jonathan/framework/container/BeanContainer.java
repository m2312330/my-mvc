package com.jonathan.framework.container;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.jonathan.framework.annotation.PathBinding;
import com.jonathan.framework.constant.FrameworkConstant;
import com.jonathan.framework.util.PropertUtil;
import com.jonathan.framework.util.StringUtil;

@SuppressWarnings({"unchecked","rawtypes"})
public class BeanContainer {
	
	
	private static Map<String, Object> beanMap=new ConcurrentHashMap<String, Object>();
	
	public static <T> T getBean(Class<?> cla){
		String beanName=getBeanName(cla);
		if(StringUtil.isNullOrEmpty(beanName)){
			return null;
		}
		return (T) beanMap.get(beanName);
	}
	
	public static <T> T getBean(String beanName){
		if(StringUtil.isNullOrEmpty(beanName)){
			return null;
		}
		return (T) beanMap.get(beanName);
	}
	public static void writeBean(String beanName,Object bean){
		beanMap.put(beanName, bean);
	}
	public static boolean containsBean(String beanName){
		return beanMap.containsKey(beanName);
	}
	public static Collection<?> getBeans(){
		return beanMap.values();
	}
	public static String getBeanName(Class<?> clazz){
		for (Class annotationClass : FrameworkConstant.BEAN_ANNOTATIONS) {
			Annotation initBean = clazz.getAnnotation(annotationClass);
			if (StringUtil.isNullOrEmpty(initBean)) {
				continue;
			}
			String beanName = clazz.getName();
			if(PathBinding.class.isAssignableFrom(initBean.annotationType())){
				return beanName;
			}
			beanName = (String) PropertUtil.getAnnotationValue(initBean, "value");
			if (!StringUtil.isNullOrEmpty(beanName)) {
				return beanName;
			}
			return clazz.getName();
		}
		return null;
	}
	
	public static List<String> getBeanNames(Class<?> clazz){
		Set<String> beanNames=new HashSet<String>();
		String beanName=getBeanName(clazz);
		if(StringUtil.isNullOrEmpty(beanName)){
			return null;
		}
		beanNames.add(beanName);
		Class<?>[] clazzs=clazz.getInterfaces();
		if(!StringUtil.isNullOrEmpty(clazzs)){
			for(Class<?> clazTemp:clazzs){
				beanName=getBeanName(clazTemp);
				if(StringUtil.isNullOrEmpty(beanName)){
					beanName=clazTemp.getName();
				}
				if(BeanContainer.containsBean(beanName)){
					continue;
				}
				beanNames.add(beanName);
			}
		}
		if(StringUtil.isNullOrEmpty(beanNames)){
			return null;
		}
		return new ArrayList<String>(beanNames);
	}
}
