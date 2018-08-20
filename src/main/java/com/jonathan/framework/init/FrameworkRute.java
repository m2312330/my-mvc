package com.jonathan.framework.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jonathan.framework.container.BuiltContainer;
import com.jonathan.framework.util.ClassUtil;
import com.jonathan.framework.util.StringUtil;
import com.jonathan.framework.loader.AspectLoader;
import com.jonathan.framework.loader.BeanLoader;
import com.jonathan.framework.loader.FieldLoader;
import com.jonathan.framework.loader.InitRunLoader;
import com.jonathan.framework.loader.MvcLoader;
import com.jonathan.framework.loader.TaskLoader;

public class FrameworkRute {

	public static void init(String... packets) throws Exception {
		List<String> packetArgs=new ArrayList<String>(Arrays.asList(packets));
		packetArgs.add("com.jonathan.framework");
		Set<Class<?>> clazzs = new HashSet<Class<?>>();
		for (String packet : packetArgs) {
			Set<Class<?>> clazzsTemp = ClassUtil.getClasses(packet);
			clazzs.addAll(clazzsTemp);
		}
		if (StringUtil.isNullOrEmpty(clazzs)) {
			return;
		}
		for (Class<?> clazz : BuiltContainer.INIT_BEAN) {
			clazzs.add(clazz);
		}
		new AspectLoader().doLoader(clazzs);
		new TaskLoader().doLoader(clazzs);
		new BeanLoader().doLoader(clazzs);
		new FieldLoader().doLoader(clazzs);
		new MvcLoader().doLoader(clazzs);
		new InitRunLoader().doLoader(clazzs);

	}

}
