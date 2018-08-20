package com.jonathan.framework.loader;

import java.lang.reflect.Method;
import java.util.Set;

import com.jonathan.framework.exception.MappingConflicException;
import com.jonathan.framework.adapt.iface.ParamsAdapt;
import com.jonathan.framework.annotation.PathBinding;
import com.jonathan.framework.config.Config;
import com.jonathan.framework.container.BeanContainer;
import com.jonathan.framework.container.MappingContainer;
import com.jonathan.framework.entity.MvcMapping;
import com.jonathan.framework.loader.base.Loader;
import com.jonathan.framework.util.PropertUtil;
import com.jonathan.framework.util.StringUtil;

/**
 * MVC加载器
 * 
 * @author Administrator
 *
 */
public class MvcLoader implements Loader {

	@Override
	public void doLoader(Set<Class<?>> clazzs) throws Exception {
		for (Class<?> clazz : clazzs) {
			Object bean = BeanContainer.getBean(clazz);
			if (StringUtil.isNullOrEmpty(bean)) {
				continue;
			}
			PathBinding classBindings = clazz.getAnnotation(PathBinding.class);
			if (StringUtil.isNullOrEmpty(classBindings)) {
				continue;
			}
			Method[] methods = clazz.getDeclaredMethods();
			com.jonathan.framework.annotation.ParamsAdapt clazzParamsAdapt = clazz.getAnnotation(com.jonathan.framework.annotation.ParamsAdapt.class);
			for (String clazzBinding : classBindings.value()) {
				for (Method method : methods) {
					PathBinding methodBinding = method.getAnnotation(PathBinding.class);
					if (StringUtil.isNullOrEmpty(methodBinding)) {
						continue;
					}
					for (String bindingPath : methodBinding.value()) {
						String path = StringUtil.formatPath(clazzBinding + "/" + bindingPath);
						if (MappingContainer.containsPath(path)) {
							throw new MappingConflicException(path);
						}
						Class<?> adaptClass = Config.DEFAULT_PARAM_ADAPT;
						com.jonathan.framework.annotation.ParamsAdapt methodParamsAdapt = method.getAnnotation(com.jonathan.framework.annotation.ParamsAdapt.class);
						if (methodParamsAdapt == null) {
							if (clazzParamsAdapt != null) {
								adaptClass = clazzParamsAdapt.value();
							}
						} else {
							adaptClass = methodParamsAdapt.value();
						}
						MvcMapping mapping = new MvcMapping();
						mapping.setBean(bean);
						mapping.setPath(path);
						mapping.setParamsAdapt(((ParamsAdapt) adaptClass.newInstance()));
						mapping.setMethod(method);
						mapping.setParamTypes(PropertUtil.getMethodParas(method));
						MappingContainer.writeMapping(mapping);
					}
				}
			}
		}
	}

}
