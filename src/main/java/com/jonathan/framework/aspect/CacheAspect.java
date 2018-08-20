package com.jonathan.framework.aspect;

import java.lang.reflect.Method;

import com.jonathan.framework.annotation.CacheWipe;
import com.jonathan.framework.annotation.CacheWrite;
import com.jonathan.framework.util.StringUtil;
import org.apache.log4j.Logger;
import com.jonathan.framework.annotation.Around;
import com.jonathan.framework.annotation.CacheWipes;
import com.jonathan.framework.annotation.InitBean;
import com.jonathan.framework.cache.LocalCache;
import com.jonathan.framework.point.AspectPoint;
import com.jonathan.framework.util.AspectUtil;

@InitBean
public class CacheAspect {

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 写缓存操作
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around(annotationClass=CacheWrite.class)
	public Object cCacheWrite(AspectPoint aspect) throws Throwable {
		Class<?> clazz = aspect.getClazz();
		Method method = aspect.getMethod();
		if (method == null) {
			return aspect.invoke();
		}
		// 获取注解
		CacheWrite handle = method.getAnnotation(CacheWrite.class);
		if (handle == null) {
			return aspect.invoke();
		}
		// 封装缓存KEY
		Object[] paras = aspect.getParams();
		String key = handle.key();
		try {
			if (StringUtil.isNullOrEmpty(key)) {
				key = AspectUtil.getMethodKey(clazz, method);
			}
			if (StringUtil.isNullOrEmpty(handle.fields())) {
				String paraKey = AspectUtil.getBeanKey(paras);
				if (!StringUtil.isNullOrEmpty(paraKey)) {
					key += ":";
					key += paraKey;
				}
			}
			if (!StringUtil.isNullOrEmpty(handle.fields())) {
				key = AspectUtil.getFieldKey(clazz, method, paras, key, handle.fields());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer cacheTimer = ((handle.time() == 0) ? 24 * 3600 : handle.time());
		// 获取缓存
		try {
			Object result = LocalCache.getCache(key);
			logger.debug("获取缓存:" + key + ",结果:" + result);
			if (!StringUtil.isNullOrEmpty(result)) {
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object result = aspect.invoke();
		if (result != null) {
			try {
				LocalCache.setCache(key, result, cacheTimer);
				logger.debug("设置缓存:" + key + ",结果:" + result + ",缓存时间:" + cacheTimer);
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * 缓存清理
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around(annotationClass=CacheWipes.class)
	@Around(annotationClass=CacheWipe.class)
	public Object zCacheWipe(AspectPoint aspect) throws Throwable {
		Class<?> clazz = aspect.getClazz();
		Method method = aspect.getMethod();
		if (method == null) {
			return aspect.invoke();
		}
		Object[] paras = aspect.getParams();
		Object result = aspect.invoke();
		CacheWipe[] handles = method.getAnnotationsByType(CacheWipe.class);
		if (StringUtil.isNullOrEmpty(handles)) {
			return result;
		}
		for (CacheWipe handle : handles) {
			try {
				String key = handle.key();
				if (StringUtil.isNullOrEmpty(handle.key())) {
					key = (AspectUtil.getMethodKey(clazz, method));
				}
				if (!StringUtil.isNullOrEmpty(handle.fields())) {
					key = AspectUtil.getFieldKey(clazz, method, paras, key, handle.fields());
				}
				logger.debug("删除缓存:" + key);
				LocalCache.delCache(key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
