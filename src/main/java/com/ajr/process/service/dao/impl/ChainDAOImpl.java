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
import com.ajr.process.service.exceptions.EntityNotFoundException;

@Repository("chainDAO")
public class ChainDAOImpl implements ChainDAO {

	@PersistenceContext
	private EntityManager em;

	private EntityManager getEntityManager() {
		return em;
	}

	@Override
	public void insertProject(String project, String chainProject,
			String description) {

		List<ChainProject> resultQuery = new ArrayList<ChainProject>();

		String selectQuery = "SELECT m FROM ChainProject m WHERE m.Project = :project and m.chainProject = :chainPrj";

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
			// Deve devolver um exce��o a indicar que j� existe projecto com nomeclatura do que est� a tentar criar!
		}

	}

	@Override
	public void insertProjectComponent(int id, String attribute,
			String description) {

		try {

			ChainProjComponent newPRJComp = new ChainProjComponent();

			ChainProject projActualizar = findChainProjectData(
					ChainProject.class, id);

			newPRJComp.setChainProject(projActualizar);
			newPRJComp.setAttribute(attribute.trim());
			newPRJComp.setDescription(description.trim());

			getEntityManager().persist(newPRJComp);

		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
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

	@SuppressWarnings("unchecked")
	@Override
	public List<ChainProject> retrieveProjects(String project) {

		List<ChainProject> resultQuery = new ArrayList<ChainProject>();

		Query q = getEntityManager().createQuery(
				"Select m from ChainProject m where project = :project");

		q.setParameter("project", project.trim());

		resultQuery = q.getResultList();

		return resultQuery;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ChainProject retrieveProject(int projectId) {

		ChainProject resultQuery = new ChainProject();

		Query q = getEntityManager().createQuery(
				"Select m from ChainProject m where m.id = :project ");
		q.setParameter("project", projectId);

		resultQuery = (ChainProject) q.getSingleResult();

		return resultQuery;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChainProjComponent> retrieveProjectComponents(int IdProject) {

		List<ChainProjComponent> resultQuery = new ArrayList<ChainProjComponent>();

		Query q = getEntityManager()
				.createQuery(
						"Select m from ChainProjComponent m where m.idProject = :project");
		q.setParameter("project", IdProject);

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