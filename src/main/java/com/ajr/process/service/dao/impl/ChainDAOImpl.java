package com.ajr.process.service.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ajr.process.service.dao.ChainDAO;
import com.ajr.process.service.entity.ChainProjComponent;
import com.ajr.process.service.entity.ChainProject;
import com.ajr.process.service.entity.ComponentRelation;
import com.ajr.process.service.exceptions.EntityNotFoundException;

@Repository("chainDAO")
public class ChainDAOImpl implements ChainDAO {

	@PersistenceContext
	private EntityManager em;

	private EntityManager getEntityManager() {
		return em;
	}

	@SuppressWarnings("unchecked")
	public void insertProject(String project, String chainProject,
			String description) {

		List<ChainProject> resultQuery = new ArrayList<ChainProject>();

		String selectQuery = "SELECT m FROM ChainProject m WHERE m.project = :project and m.chainProject = :chainPrj";

		Query q = getEntityManager().createQuery(selectQuery);

		q.setParameter("project", project);
		q.setParameter("chainPrj", chainProject);

		resultQuery = q.getResultList();

		if (resultQuery.isEmpty()) {

			ChainProject newPRJ = new ChainProject();

			newPRJ.setProject(project.trim());
			newPRJ.setChainProject(chainProject.trim());
			newPRJ.setDescription(description.trim());

			getEntityManager().persist(newPRJ);

		} else {

			// FIXME
			// Deve devolver um exceção a indicar que já existe projecto com
			// nomeclatura do que está a tentar criar!
		}

	}

	public void insertProjectComponent(int id, String attribute,
			String description) {

		try {

			ChainProjComponent newPRJComp = new ChainProjComponent();

			ChainProject projActualizar = findChainProjectData(
					ChainProject.class, id);

			newPRJComp.setChainProject(projActualizar);
			newPRJComp.setAttribute(attribute.trim());
			newPRJComp.setDescription(description.trim());
			newPRJComp.setSelected('0');

			getEntityManager().persist(newPRJComp);

		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public void removeProjectComponents(int idProject) {

		List<ChainProjComponent> componentsToRemove = new ArrayList<ChainProjComponent>();

		String selectQuery = "SELECT m FROM ChainProjComponent m WHERE m.idProject = :project";

		Query q = getEntityManager().createQuery(selectQuery);
		q.setParameter("project", idProject);

		componentsToRemove = q.getResultList();

		for (ChainProjComponent m : componentsToRemove) {
			em.remove(m);
		}
	}

	public void updateSelecedProjectComponent(int idProj, int idChainProj,
			int idComponent) {

		Query q = getEntityManager()
				.createQuery(
						"SELECT c FROM ChainProjects c WHERE c.IdProject = :idProj and c.Selected='1'");
		q.setParameter("idProj", idProj);

		ChainProject returnChainProj = (ChainProject) q.getSingleResult();

		if (!returnChainProj.equals(null)) {
			returnChainProj.setSelected('0');
			getEntityManager().merge(returnChainProj);
		}

		Query q1 = getEntityManager().createQuery(
				"SELECT c FROM ChainProjects c WHERE c.Id = :idProj");
		q1.setParameter("idProj", idProj);

		returnChainProj = (ChainProject) q.getSingleResult();

		if (!returnChainProj.equals(null)) {
			returnChainProj.setSelected('1');
			getEntityManager().merge(returnChainProj);
		}

		Query q2 = getEntityManager()
				.createQuery(
						"SELECT c FROM ChainProjComponent c WHERE c.Id_Project = :idChainProj and c.Selected='1'");
		q2.setParameter("idChainProj", idChainProj);

		ChainProjComponent returnComponent = (ChainProjComponent) q
				.getSingleResult();

		if (!returnComponent.equals(null)) {
			returnComponent.setSelected('0');
			getEntityManager().merge(returnComponent);
		}

		Query q3 = getEntityManager().createQuery(
				"SELECT c FROM ChainProjComponent c WHERE c.Id = :idComp");
		q3.setParameter("idComp", idComponent);

		returnComponent = (ChainProjComponent) q.getSingleResult();

		if (!returnComponent.equals(null)) {
			returnComponent.setSelected('1');
			getEntityManager().merge(returnComponent);
		}

	}

	@SuppressWarnings("unchecked")
	public List<ChainProject> retrieveProjects(String project) {

		List<ChainProject> resultQuery = new ArrayList<ChainProject>();

		Query q = getEntityManager().createQuery(
				"Select m from ChainProject m where project = :project");

		q.setParameter("project", project.trim());

		resultQuery = q.getResultList();

		return resultQuery;
	}

	@SuppressWarnings("unchecked")
	public List<ChainProjComponent> retrieveChainProjectComponents(int chainProj) {

		List<ChainProjComponent> resultQuery = new ArrayList<ChainProjComponent>();

		Query q = getEntityManager()
				.createQuery(
						"Select c from ChainProject p join p.chainProjComponents c where p.id=:idProj");

		q.setParameter("idProj", chainProj);

		resultQuery = q.getResultList();

		return resultQuery;
	}

	@SuppressWarnings("unchecked")
	public List<ChainProjComponent> retrieveSelectedChainProjectComponents(
			String project) {

		List<ChainProjComponent> resultQuery = new ArrayList<ChainProjComponent>();

		Query q = getEntityManager()
				.createQuery(
						"Select c from ChainProject p join p.chainProjComponents c where p.project= :project and p.selected = '1'");

		q.setParameter("project", project);

		resultQuery = q.getResultList();

		return resultQuery;
	}

	public ChainProject retrieveProject(int projectId) {

		ChainProject resultQuery = new ChainProject();

		Query q = getEntityManager().createQuery(
				"Select m from ChainProject m where m.id = :project ");
		q.setParameter("project", projectId);

		resultQuery = (ChainProject) q.getSingleResult();

		return resultQuery;
	}

	public ChainProject retrieveProjectSelected(String project) {

		ChainProject resultQuery = new ChainProject();

		Query q = getEntityManager()
				.createQuery(
						"Select c from ChainProject c where c.project= :project and c.selected = '1'");
		q.setParameter("project", project);

		resultQuery = (ChainProject) q.getSingleResult();

		return resultQuery;
	}

	public ChainProjComponent retrieveProjectComponent(int projectId,
			int componentId) {

		ChainProjComponent resultQuery = new ChainProjComponent();

		Query q = getEntityManager()
				.createQuery(
						"Select c from ChainProjComponent c where c.id = :idComp and c.ChainProject.id = :idProj");
		q.setParameter("idProj", projectId);
		q.setParameter("idComp", componentId);

		resultQuery = (ChainProjComponent) q.getSingleResult();

		return resultQuery;

	}

	public ChainProjComponent retrieveSelectedComponentFromSelectedProject(
			String project) {

		ChainProjComponent resultQuery = new ChainProjComponent();

		String selectQuery = "Select c from ChainProject p join p.chainProjComponents c where p.project= :project and p.selected = '1' and c.selected = '1'";

		Query q = getEntityManager().createQuery(selectQuery);
		q.setParameter("project", project);

		resultQuery = (ChainProjComponent) q.getSingleResult();

		return resultQuery;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> retrieveComponentRelations(int idComp) {

		Query q = getEntityManager()
				.createQuery(
						"Select c.chainProjectComponent2.id, c.chainProjectComponent2.description, c.chainProjectComponent2.attribute from ChainProjComponent p join p.componentRelations c where p.id = :componentId");

		q.setParameter("componentId", idComp);

		List<Object[]> resultQuery = q.getResultList();

//		for (Object[] l : resultQuery) {
//			
//			System.out.println("Component -> Project: "
//					+ Integer.toString((Integer) l[0]) + " Component1 - "
//					+ (String) l[1] + " Component2 - " + (String) l[2]);
//		}

		return resultQuery;

	}

	@SuppressWarnings("unchecked")
	public List<ComponentRelation> retrieveRelations() {

		List<ComponentRelation> resultQuery = new ArrayList<ComponentRelation>();

		Query q = getEntityManager().createQuery(
				"Select c from ComponentRelation c where c.projectId = 1");

		resultQuery = q.getResultList();

		return resultQuery;

	}

	private <H extends ChainProject> H findChainProjectData(Class<H> clazz,
			Integer id) throws EntityNotFoundException {
		H e = getEntityManager().find(clazz, id);
		if (e == null) {
			throw new EntityNotFoundException(clazz.getClass(), id);
		}
		return e;
	}

}
