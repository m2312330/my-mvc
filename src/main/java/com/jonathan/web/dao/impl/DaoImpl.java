package com.jonathan.web.dao.impl;

import java.util.List;

import com.jonathan.framework.annotation.OutBean;
import com.jonathan.web.domain.DomainTest;
import com.jonathan.framework.annotation.InitBean;
import com.jonathan.web.comm.base.JdbcTemplate;
import com.jonathan.web.dao.Dao;

@InitBean
public class DaoImpl implements Dao {

	@OutBean
	JdbcTemplate jdbcTemplate;
	
	@Override
	public DomainTest get(Integer id){
		return jdbcTemplate.findBeanFirst(DomainTest.class,"id",id);
	}
	
	@Override
	public List<DomainTest> gets(){
		return jdbcTemplate.findBean(DomainTest.class);
	}
	@Override
	public Long del(Integer id){
		
		String sql="delete from test where id=? limit 1";
		return jdbcTemplate.doUpdate(sql,id);
	}
}
