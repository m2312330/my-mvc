package com.jonathan.framework.aspect;

import java.lang.reflect.Method;

import com.jonathan.framework.annotation.Around;
import com.jonathan.framework.annotation.InitBean;
import com.jonathan.framework.annotation.LogHead;
import com.jonathan.framework.point.AspectPoint;
import com.jonathan.framework.util.AspectUtil;
import com.jonathan.framework.util.PropertUtil;
import com.jonathan.framework.util.StringUtil;

@InitBean
public class LoggerAspect {

	
	/**
	 * 日志标头设置
	 * @param wrapper
	 * @return
	 * @throws Throwable
	 */
	@Around(annotationClass=LogHead.class)
	public Object logMonitor(AspectPoint wrapper) throws Throwable{
		try {
			// AOP获取方法执行信息
			Method method = wrapper.getMethod();
			Class<?> clazz = PropertUtil.getClass(method);
			String module = AspectUtil.getCurrLog();
			if (!StringUtil.isNullOrEmpty(module)) {
				module += "_";
			}
			String classLog = AspectUtil.getClassLog(clazz);
			if (!StringUtil.isNullOrEmpty(classLog)) {
				module += classLog;
			}
			if (!StringUtil.isNullOrEmpty(module)) {
				module += ".";
			}
			String methodLog = AspectUtil.getMethodLog(method);
			if (!StringUtil.isNullOrEmpty(methodLog)) {
				module += methodLog;
			} else {
				module += method.getName();
			}
			AspectUtil.writeLog(module);
			return wrapper.invoke();
		} finally {
			AspectUtil.minusLog();
		}
	}
}
