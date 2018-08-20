package com.jonathan.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jonathan.framework.annotation.OutBean;
import com.jonathan.framework.util.StringUtil;
import com.jonathan.web.comm.entity.MsgEntity;
import com.jonathan.web.domain.DomainTest;
import com.jonathan.web.service.Service;
import com.jonathan.framework.annotation.JsonSerialize;
import com.jonathan.framework.annotation.PathBinding;

@PathBinding("/mvc")
public class Controller {

	
	@OutBean
	Service service;
	
	@PathBinding("load.do")
	@JsonSerialize
	public Object load(){
		List<DomainTest> domainTests= service.gets();
		return domainTests;
	}
	/**
	 * 删除数据
	 * @param request
	 * @return
	 */
	@PathBinding("del.do")
	@JsonSerialize
	public Object del(HttpServletRequest request){
		Integer id= StringUtil.toInteger(request.getParameter("id"));
		Long code= service.del(id);
		if(code>0){
			return new MsgEntity(0,"操作成功");
		}
		return new MsgEntity(-1,"系统出错");
	}
}
