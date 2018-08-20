package com.jonathan.web.service;

import java.util.List;

import com.jonathan.web.domain.DomainTest;

public interface Service {

	
	public DomainTest get(Integer id);
	
	
	public List<DomainTest> gets();

	public Long del(Integer id);
	
}
