package com.jonathan.framework.container;

import com.jonathan.framework.aspect.LoggerAspect;
import com.jonathan.framework.aspect.CacheAspect;
import com.jonathan.framework.aspect.TransactedAspect;
import com.jonathan.framework.wrapper.RequestWrapper;
import com.jonathan.framework.wrapper.ResponseWrapper;

public class BuiltContainer {

	
	public static final Class<?> [] INIT_BEAN={CacheAspect.class,TransactedAspect.class,LoggerAspect.class,RequestWrapper.class,ResponseWrapper.class};
}
