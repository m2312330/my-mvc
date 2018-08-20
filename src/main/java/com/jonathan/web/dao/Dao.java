package com.jonathan.web.dao;

import java.util.List;

import com.jonathan.web.domain.DomainTest;

public interface Dao {

	
	public DomainTest get(Integer id);
	
	
	public List<DomainTest> gets();
	
	public Long del(Integer id);
}
