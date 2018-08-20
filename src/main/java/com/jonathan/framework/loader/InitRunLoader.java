package com.jonathan.framework.loader;

import java.lang.reflect.Method;
import java.util.Set;

import com.jonathan.framework.container.BeanContainer;
import com.jonathan.framework.exception.ErrorCronException;
import com.jonathan.framework.loader.base.Loader;
import com.jonathan.framework.task.TaskTrigger;
import com.jonathan.framework.util.StringUtil;
import org.apache.log4j.Logger;
import com.jonathan.framework.annotation.CronTask;
import com.jonathan.framework.annotation.InitBean;
import com.jonathan.framework.iface.InitFace;
import com.jonathan.framework.util.PrintException;

/**
 * 切面加载器
 * 
 * @author Administrator
 *
 */
public class InitRunLoader implements Loader {
	

	private static final Logger logger = Logger.getLogger(Loader.class);

	@Override
	public void doLoader(Set<Class<?>> clazzs) throws Exception {
		for (Class<?> clazz : clazzs) {
			InitBean initBean = clazz.getAnnotation(InitBean.class);
			if (initBean == null) {
				continue;
			}
			Object bean = BeanContainer.getBean(clazz);
			if (InitFace.class.isAssignableFrom(bean.getClass())) {
				// 初始化运行
				try {
					InitFace face = (InitFace) bean;
					if (StringUtil.isNullOrEmpty(face)) {
						continue;
					}
					face.init();
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
			// 执行定时任务
			Method[] methods = clazz.getDeclaredMethods();
			if (StringUtil.isNullOrEmpty(methods)) {
				continue;
			}
			for (Method method : methods) {
				CronTask task = method.getAnnotation(CronTask.class);
				if (task == null) {
					continue;
				}
				try {
					if (StringUtil.isNullOrEmpty(task.value())) {
						PrintException.printException(logger, new ErrorCronException(task.value(), method));
						continue;
					}
					TaskTrigger.nextRun(bean, method, task.value(), null);
				} catch (Exception e) {
					PrintException.printException(logger, new ErrorCronException(
							"CRON有误:" + bean.getClass() + ":" + method.getName() + ",Cron:" + task.value()));
					continue;
				}
			}
		}
	}

}
