package com.jonathan.framework.loader;

import java.lang.reflect.Method;
import java.util.Set;

import com.jonathan.framework.aspect.entity.AspectEntity;
import com.jonathan.framework.constant.FrameworkConstant;
import com.jonathan.framework.loader.base.Loader;
import com.jonathan.framework.task.TaskTrigger;
import com.jonathan.framework.util.StringUtil;
import com.jonathan.framework.annotation.CronTask;
import com.jonathan.framework.annotation.InitBean;
import com.jonathan.framework.util.AspectUtil;

/**
 * 定时任务加载器
 * @author Administrator
 *
 */
public class TaskLoader implements Loader {

	@Override
	public void doLoader(Set<Class<?>> clazzs) throws Exception {
		if (StringUtil.isNullOrEmpty(clazzs)) {
			return;
		}
		for (Class<?> cla : clazzs) {
			InitBean initBean = cla.getAnnotation(InitBean.class);
			if (StringUtil.isNullOrEmpty(initBean)) {
				continue;
			}
			Method[] methods = cla.getDeclaredMethods();
			if (StringUtil.isNullOrEmpty(methods)) {
				continue;
			}
			for (Method method : methods) {
				CronTask cronTask = method.getAnnotation(CronTask.class);
				if (StringUtil.isNullOrEmpty(cronTask) || StringUtil.isNullOrEmpty(cronTask.value())) {
					continue;
				}
				AspectEntity aspectEntity = new AspectEntity();
				// 装载切面控制方法
				aspectEntity.setAnnotationClass(new Class<?>[] { CronTask.class });
				aspectEntity.setAspectInvokeMethod(TaskTrigger.getTriggerMethod());
				FrameworkConstant.writeToAspectMap(AspectUtil.getBeanKey(TaskTrigger.getTriggerMethod()), aspectEntity);
			}
		}
	}

}
