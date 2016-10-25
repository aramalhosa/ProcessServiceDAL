package com.ajr.process.service.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajr.process.service.dao.InitCharge;
import com.ajr.process.service.dao.MenuDataDAO;
import com.ajr.process.service.entity.MenuData;

@Service
public class InitChargeImpl implements InitCharge {
	
	  @Autowired
	  private MenuDataDAO mdDAO;

	  @Transactional
	  public void insertMenu(String project, int menuNum, int subMenu1, int subMenu2, String label) {
	    mdDAO.insertMenu(project, menuNum,subMenu1, subMenu2, label);
	  }

	  public List<MenuData> findMenus(String project) {
	    return mdDAO.retrieveMenuItems(project);
	  }
	  
	  public List<MenuData> findSubMenus1(String project) {
	    return mdDAO.retrieveSubMenu1Items(project);
	  }
	  
	  public List<MenuData> findSubMenus2(String project) {
	    return mdDAO.retrieveSubMenu2Items(project);
	  }

}
