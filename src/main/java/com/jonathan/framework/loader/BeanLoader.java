package com.jonathan.framework.loader;

import java.util.List;
import java.util.Set;

import com.jonathan.framework.container.BeanContainer;
import com.jonathan.framework.loader.base.Loader;
import com.jonathan.framework.util.StringUtil;
import com.jonathan.framework.exception.BeanConflictException;
import com.jonathan.framework.proxy.CglibProxy;

/**
 * Bean加载器
 * @author Administrator
 *
 */
public class BeanLoader implements Loader {
	

	static CglibProxy proxy = new CglibProxy();

	@Override
	public void doLoader(Set<Class<?>> clazzs) throws Exception {
		if (StringUtil.isNullOrEmpty(clazzs)) {
			return;
		}
		for (Class<?> cla : clazzs) {
			List<String> beanNames = BeanContainer.getBeanNames(cla);
			if (StringUtil.isNullOrEmpty(beanNames)) {
				continue;
			}
			Object bean = proxy.getProxy(cla);
			for (String beanName : beanNames) {
				if (StringUtil.isNullOrEmpty(beanName)) {
					continue;
				}
				if (BeanContainer.containsBean(beanName)) {
					throw new BeanConflictException(beanName);
				}
				BeanContainer.writeBean(beanName, bean);
			}
		}
	}

}
