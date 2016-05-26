package com.ajr.process.service.dao;

import java.util.List;

import com.ajr.process.service.entity.MenuData;

public interface InitCharge {

	public void insertMenu(String project, int menuNum, int subMenu1, int subMenu2, String label);
	
	List<MenuData> findMenus(String project);
	
	List<MenuData> findSubMenus1(String project);
	
	List<MenuData> findSubMenus2(String project);
	
}
