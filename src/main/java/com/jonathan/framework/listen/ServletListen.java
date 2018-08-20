package com.jonathan.framework.listen;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import com.jonathan.framework.init.FrameworkRute;
import com.jonathan.framework.util.StringUtil;

public class ServletListen implements ServletContextListener {

	Logger logger = Logger.getLogger(ServletListen.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("运行contextDestroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			String packet = event.getServletContext().getInitParameter("scanPacket");
			if (StringUtil.isNullOrEmpty(packet)) {
				logger.error("启动参数:scanPacket为空");
				return;
			}
			String[] packets = packet.split(",");
			FrameworkRute.init(packets);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
