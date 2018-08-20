package com.jonathan.web.comm.base;

import java.beans.PropertyVetoException;
import java.io.IOException;

import com.jonathan.framework.jdbc.JdbcHandle;
import com.jonathan.framework.annotation.InitBean;
import com.jonathan.framework.iface.InitFace;

@InitBean
public class JdbcTemplate extends JdbcHandle implements InitFace{


	@Override
	public void init() {
		try {
			initConfig("config/c3p0.properties");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

}
