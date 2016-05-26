package com.ajr.process.service.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ajr.process.service.dao.MenuDataDAO;
import com.ajr.process.service.entity.MenuData;
import com.ajr.process.service.exceptions.EntityNotFoundException;

@Repository("menuDataDAO")
public class MenuDataDAOImpl implements MenuDataDAO {

	// private static MenuDataDAO instance;

	@PersistenceContext
	private EntityManager em;

	private EntityManager getEntityManager() {
		return em;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuData> retrieveMenuItems() {

		List<MenuData> resultQueryMenuItems = new ArrayList<MenuData>();

		Query q = getEntityManager()
				.createQuery(
						"Select m from MenuData m where m.submenu1num = 0 and m.submenu2num = 0");
		resultQueryMenuItems = q.getResultList();

		return resultQueryMenuItems;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MenuData> retrieveMenuItems(String project) {

		List<MenuData> resultQueryMenuItems = new ArrayList<MenuData>();

		Query q = getEntityManager()
				.createQuery(
						"Select m from MenuData m where m.project = :project and m.submenu1num = 0 and m.submenu2num = 0");
		q.setParameter("project", project);
		resultQueryMenuItems = q.getResultList();

		return resultQueryMenuItems;

	}

	@Override
	public MenuData retrieveMenuItem(String project, int menuid) {

		MenuData resultQueryMenuItems = new MenuData();

		Query q = getEntityManager()
				.createQuery(
						"Select m from MenuData m where where m.project = :project and m.menunum = :menuid and m.submenu1num = 0 and m.submenu2num = 0");
		q.setParameter("project", project);
		q.setParameter("menuid", menuid);

		resultQueryMenuItems = (MenuData) q.getSingleResult();

		return resultQueryMenuItems;

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MenuData> retrieveSubMenu1Items(String project) {

		List<MenuData> resultQueryMenuItems = new ArrayList<MenuData>();

		Query q = getEntityManager()
				.createQuery(
						"Select m from MenuData m where m.project = :project and m.submenu1num > 0 and m.submenu2num = 0");
		q.setParameter("project", project);

		resultQueryMenuItems = (List<MenuData>) q.getResultList();

		return resultQueryMenuItems;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MenuData> retrieveSubMenu1Items(String project, int menuid) {

		List<MenuData> resultQueryMenuItems = new ArrayList<MenuData>();

		Query q = getEntityManager()
				.createQuery(
						"Select m from MenuData m where m.project = :project and m.menunum = :menuid and m.submenu1num > 0 and m.submenu2num = 0");
		q.setParameter("project", project);
		q.setParameter("menuid", menuid);

		resultQueryMenuItems = (List<MenuData>) q.getResultList();

		return resultQueryMenuItems;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MenuData> retrieveSubMenu2Items(String project) {

		List<MenuData> resultQueryMenuItems = new ArrayList<MenuData>();

		Query q = getEntityManager()
				.createQuery(
						"Select m from MenuData m where m.project = :project and m.menunum > 0 and m.submenu1num > 0 and m.submenu2num > 0");
		q.setParameter("project", project);

		resultQueryMenuItems = (List<MenuData>) q.getResultList();

		return resultQueryMenuItems;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MenuData> retrieveSubMenu2Items(String project, int menuid,
			int submenu1id) {

		List<MenuData> resultQueryMenuItems = new ArrayList<MenuData>();

		Query q = getEntityManager()
				.createQuery(
						"Select m from MenuData m where m.project = :project and m.menunum = :menuid and m.submenu1num = :submenu1id and m.submenu2num > 0");
		q.setParameter("project", project);
		q.setParameter("menuid", menuid);
		q.setParameter("submenu1id", submenu1id);

		resultQueryMenuItems = (List<MenuData>) q.getResultList();

		return resultQueryMenuItems;
	}

	@Override
	public void insertMenu(String project, int menuNum, int subMenu1,
			int submenu2, String label) {

		MenuData newMD = new MenuData();

		newMD.setProject(project);
		newMD.setMenunum(menuNum);
		newMD.setSubmenu1num(subMenu1);
		newMD.setSubmenu2num(submenu2);
		newMD.setLabel(label);

		getEntityManager().persist(newMD);

	}

	@Override
	public void updateMenu(String project, int menuNum, String label) {

		try {

			Query q = getEntityManager()
					.createQuery(
							"SELECT m FROM MenuData m WHERE m.project = :project and m.menunum = :menuNumber and m.submenu1num = 0 and m.submenu2num = 0");
			q.setParameter("project", project);
			q.setParameter("menuNumber", menuNum);

			MenuData resulMenuData = (MenuData) q.getSingleResult();

			MenuData menuActualizar = findMenuData(MenuData.class,
					resulMenuData.getId());
			menuActualizar.setLabel(label);
			getEntityManager().merge(menuActualizar);
			// getEntityManager().flush();

		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateSubMenu1(String project, int menuNum, int subMenuNum,
			String label) {

		try {

			Query q = getEntityManager()
					.createQuery(
							"UPDATE MenuData m SET m.label=:label WHERE m.project = :project and m.menunum = :menuNumber and m.submenu1num = :subMenuNumber and m.submenu2num = 0");
			q.setParameter("label", label);
			q.setParameter("project", project);
			q.setParameter("menuNumber", menuNum);
			q.setParameter("subMenuNumber", subMenuNum);

			q.executeUpdate();

		} catch (Exception e) {
			System.out.print("Shit happens!!!");
		}

	}

	@Override
	public void updateSubMenu2(String project, int menuNum, int subMenu1Num,
			int subMenu2Num, String label) {

		try {

			Query q = getEntityManager()
					.createQuery(
							"UPDATE MenuData m SET m.label=:label WHERE m.project = :project and m.menunum = :menuNumber and m.submenu1num = :subMenu1Number and m.submenu2num = :subMenu2Number");
			q.setParameter("label", label);
			q.setParameter("project", project);
			q.setParameter("menuNumber", menuNum);
			q.setParameter("subMenu1Number", subMenu1Num);
			q.setParameter("subMenu2Number", subMenu2Num);

			q.executeUpdate();

		} catch (Exception e) {
			System.out.print("Shit happens!!!");
		}

	}

	@Override
	public void updateMenuItems(String project, String texto1, String texto2,
			String texto3, String texto4) {

		updateMenu(project, 1, texto1);
		updateMenu(project, 2, texto2);
		updateMenu(project, 3, texto3);
		updateMenu(project, 4, texto4);

	}

	@Override
	public void updateSubMenu1Items(String project, int menu, String texto1,
			String texto2, String texto3, String texto4, String texto5,
			String texto6) {

		updateSubMenu1(project, menu, 1, texto1);
		updateSubMenu1(project, menu, 2, texto2);
		updateSubMenu1(project, menu, 3, texto3);
		updateSubMenu1(project, menu, 4, texto4);
		updateSubMenu1(project, menu, 5, texto5);
		updateSubMenu1(project, menu, 6, texto6);

		// em.flush();

	}

	@Override
	public void updateSubMenu2Items(String project, int menu, int submenu,
			String texto1, String texto2, String texto3, String texto4,
			String texto5, String texto6) {

		updateSubMenu2(project, menu, submenu, 1, texto1);
		updateSubMenu2(project, menu, submenu, 2, texto2);
		updateSubMenu2(project, menu, submenu, 3, texto3);
		updateSubMenu2(project, menu, submenu, 4, texto4);
		updateSubMenu2(project, menu, submenu, 5, texto5);
		updateSubMenu2(project, menu, submenu, 6, texto6);

		// em.flush();

	}

	private <H extends MenuData> H findMenuData(Class<H> clazz, Integer id)
			throws EntityNotFoundException {
		H e = getEntityManager().find(clazz, id);
		if (e == null) {
			throw new EntityNotFoundException(clazz.getClass(), id);
		}
		return e;
	}

}
