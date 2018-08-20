package com.jonathan.framework.loader.base;

import java.util.Set;

public interface Loader {

	/***
	 * 进行加载
	 * @param clazzs
	 * @throws Exception
	 */
	public void doLoader(Set<Class<?>> clazzs) throws Exception;
}
