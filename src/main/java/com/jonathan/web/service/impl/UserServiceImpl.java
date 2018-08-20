package com.jonathan.web.service.impl;

import java.util.List;

import com.jonathan.framework.annotation.CacheWipe;
import com.jonathan.framework.annotation.CacheWrite;
import com.jonathan.framework.annotation.LogHead;
import com.jonathan.framework.annotation.OutBean;
import com.jonathan.web.comm.constant.CacheFinal;
import com.jonathan.web.dao.UserDao;
import com.jonathan.web.domain.UserInfo;
import com.jonathan.web.service.UserService;
import com.jonathan.framework.annotation.InitBean;

@InitBean
public class UserServiceImpl implements UserService {

	@OutBean
    UserDao userDao;
	
	
	/**
	 * 保存或更新用户信息
	 * @param user
	 */
	@Override
	@CacheWipe(key= CacheFinal.USER_INFO,fields="user.userId")
	@CacheWipe(key=CacheFinal.USER_LIST)
	public void saveOrUpdateUser(UserInfo user){
		userDao.saveOrUpdateUser(user);
	}
	
	/**
	 * 查询用户列表
	 */
	@Override
	@CacheWrite(key=CacheFinal.USER_LIST,time=3600)
	@LogHead("获取用户信息")
	public List<UserInfo> getUsers(){
		return userDao.getUsers();
	}
	
	/**
	 * 删除用户
	 * @param userId
	 */
	@Override
	@CacheWipe(key=CacheFinal.USER_INFO,fields="user.userId")
	@CacheWipe(key=CacheFinal.USER_LIST)
	public void deleteUser(String userId){
		userDao.deleteUser(userId);
	}
	
	/**
	 * 查询用户信息
	 */
	@Override
	@CacheWrite(key=CacheFinal.USER_INFO,fields="userId")
	public UserInfo getUserInfo(String userId){
		return userDao.getUserInfo(userId);
	}
}
