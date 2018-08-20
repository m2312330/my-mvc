package com.jonathan.web.service.impl;

import java.util.List;

import com.jonathan.framework.annotation.OutBean;
import com.jonathan.framework.annotation.Transacted;
import com.jonathan.web.domain.DomainTest;
import com.jonathan.web.service.Service;
import com.jonathan.framework.annotation.InitBean;
import com.jonathan.web.dao.Dao;

@InitBean
public class ServiceImpl implements Service {

	@OutBean
    Dao dao;
	@Override
	public DomainTest get(Integer id){
		return dao.get(id);
	}
	
	@Override
	public List<DomainTest> gets(){
		return dao.gets()
				;
	}
	@Override
	@Transacted
	public Long del(Integer id){
		Long code= dao.del(id);
		Integer i=50/0;
		System.out.println(i);
		return code;
	}
	
}
