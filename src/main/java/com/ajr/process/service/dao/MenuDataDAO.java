package com.ajr.process.service.dao;

import java.util.List;

import com.ajr.process.service.entity.MenuData;

public interface MenuDataDAO {

	public List<MenuData> retrieveMenuItems();
	public List<MenuData> retrieveMenuItems(String project);
	public MenuData retrieveMenuItem(String project, int menuid);
	public List<MenuData> retrieveSubMenu1Items(String project);
	public List<MenuData> retrieveSubMenu1Items(String project, int menuid);
	public List<MenuData> retrieveSubMenu2Items(String project);
	public List<MenuData> retrieveSubMenu2Items(String project, int menuid, int submenu1id);
	public void insertMenu(String project, int menuNum, int subMenu1, int submenu2, String label);
	public void updateMenu(String project, int menuNum, String label);
	public void updateSubMenu1(String project, int menuNum, int subMenuNum,	String label);
	public void updateSubMenu2(String project, int menuNum, int subMenu1Num, int subMenu2Num, String label);
	public void updateMenuItems(String project, String texto1, String texto2, String texto3, String texto4);
	public void updateSubMenu1Items(String project, int menu, String texto1,
			String texto2, String texto3, String texto4, String texto5,	String texto6);
	public void updateSubMenu2Items(String project, int menu, int submenu,
			String texto1, String texto2, String texto3, String texto4,	String texto5, String texto6);
	
}
