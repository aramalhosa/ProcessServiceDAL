package com.ajr.process.service.dao;

import java.util.List;
import java.util.ListIterator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ajr.process.service.entity.MenuData;

public class InvokeInitCharge {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"beans.xml");

		InitCharge obj = (InitCharge) context.getBean("initChargeImpl");

//		for (int k = 1; k < 7; k++) {
//			for (int i = 1; i < 7; i++) {
//				for (int j = 0; j < 7; j++) {
//					obj.insertMenu("XPTO", k, i, j, "Teste");
//				}
//			}
//		}

		List<MenuData> data = obj.findSubMenus2("XPTO");

		ListIterator<MenuData> li = data.listIterator();

		while (li.hasNext()) {

			MenuData mData = li.next();

			System.out.println("Menu->" + mData.getMenunum() + " SubMenu1->"
					+ mData.getSubmenu1num() + " SubMenu2->"
							+ mData.getSubmenu2num() + " Label->" + mData.getLabel());
		}

	}

}
