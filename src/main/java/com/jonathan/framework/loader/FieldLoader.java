package com.jonathan.framework.loader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.jonathan.framework.annotation.OutBean;
import com.jonathan.framework.container.BeanContainer;
import com.jonathan.framework.exception.BeanNotFoundException;
import com.jonathan.framework.loader.base.Loader;
import com.jonathan.framework.util.StringUtil;

/**
 * 字段加载器
 * @author Administrator
 *
 */
public class FieldLoader implements Loader {

	@Override
	public void doLoader(Set<Class<?>> clazzs) throws Exception {
		for (Object bean : BeanContainer.getBeans()) {
			List<Field> fields = loadFields(bean.getClass());
			if (StringUtil.isNullOrEmpty(fields)) {
				continue;
			}
			for (Field field : fields) {
				OutBean writeBean = field.getAnnotation(OutBean.class);
				if (StringUtil.isNullOrEmpty(writeBean)) {
					continue;
				}
				String beanName = writeBean.beanName();
				if (StringUtil.isNullOrEmpty(beanName)) {
					beanName = field.getType().getName();
				}
				if (!BeanContainer.containsBean(beanName)) {
					throw new BeanNotFoundException(beanName, bean.getClass());
				}
				Object writeValue = null;
				field.setAccessible(true);
				writeValue = BeanContainer.getBean(beanName);
				field.set(bean, writeValue);
			}
		}
	}
	
	private static List<Field> loadFields(Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>();
		Field[] fieldArgs = clazz.getDeclaredFields();
		for (Field f : fieldArgs) {
			fields.add(f);
		}
		Class<?> superClass = clazz.getSuperclass();
		if (superClass == null) {
			return fields;
		}
		List<Field> childFields = loadFields(superClass);
		if (StringUtil.isNullOrEmpty(childFields)) {
			return fields;
		}
		fields.addAll(childFields);
		return fields;
	}

}
