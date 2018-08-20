package com.jonathan.web.task;

import com.jonathan.framework.util.DateUtils;
import com.jonathan.framework.annotation.CronTask;
import com.jonathan.framework.annotation.InitBean;

@InitBean
public class TestTask	 {

	@CronTask("0/5 * * * * ? ")
	public void test() {
		System.out.println("定时任务执行中:" + DateUtils.getDateTimeString());
	}

}
