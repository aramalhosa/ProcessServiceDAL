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
import com.ajr.process.service.exceptions.AttributeAlreadyExistsException;
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

		String selectQuery = "Select m from ChainProject m where m.project = :project and m.chainProject = :chainPrj";

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

	@SuppressWarnings("unchecked")
	public void insertProjectComponent(String project, String attribute,
			String description) throws AttributeAlreadyExistsException {

		try {

			List<ChainProjComponent> resultQuery = new ArrayList<ChainProjComponent>();
			List<ChainProject> resultQuery1 = new ArrayList<ChainProject>();

			ChainProjComponent newPRJComp = new ChainProjComponent();

			String selectQuery = "Select c from ChainProjComponent c join c.chainProject p where c.attribute = :attr and p.project = :projName";

			Query q = getEntityManager().createQuery(selectQuery);

			q.setParameter("projName", project);
			q.setParameter("attr", attribute);

			resultQuery = q.getResultList();

			if (resultQuery.isEmpty()) {

				selectQuery = "Select c from ChainProject c where c.project = :projName";

				q = getEntityManager().createQuery(selectQuery);

				q.setParameter("projName", project);

				resultQuery1 = q.getResultList();

				ChainProject returnChainProj1 = resultQuery1.get(0);

				ChainProject projActualizar = findChainProjectData(
						ChainProject.class, returnChainProj1.getId());

				newPRJComp.setChainProject(projActualizar);
				newPRJComp.setAttribute(attribute.trim());
				newPRJComp.setDescription(description.trim());
				newPRJComp.setSelected('0');

				getEntityManager().persist(newPRJComp);

			} else {

				throw new AttributeAlreadyExistsException(project, attribute);
			}

		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public void insertComponentsRelation(int compId1, int compId2) {

		List<ChainProjComponent> resultQuery1 = new ArrayList<ChainProjComponent>();
		List<ChainProjComponent> resultQuery2 = new ArrayList<ChainProjComponent>();
		List<ComponentRelation> resultQuery3 = new ArrayList<ComponentRelation>();

		String selectQuery = "Select c from ChainProjComponent c where c.id = :compId";

		Query q = getEntityManager().createQuery(selectQuery);

		q.setParameter("compId", compId1);

		resultQuery1 = q.getResultList();

		if (!resultQuery1.isEmpty()) {

			String selectQuery1 = "Select c from ChainProjComponent c where c.id = :compId";

			Query q1 = getEntityManager().createQuery(selectQuery1);

			q1.setParameter("compId", compId2);

			resultQuery2 = q1.getResultList();

			if (!resultQuery2.isEmpty()) {

				String selectQuery2 = "Select r from ComponentRelation r join r.chainProjectComponent c join chainProjectComponent2 c2 where c.id = :compId and c2.id = :compId2";

				Query q2 = getEntityManager().createQuery(selectQuery2);

				q2.setParameter("compId", compId1);
				q2.setParameter("compId2", compId2);

				resultQuery3 = q2.getResultList();

				if (resultQuery3.isEmpty()) {

					ComponentRelation newCompRel = new ComponentRelation();

					newCompRel.setChainProjectComponent(resultQuery1.get(0));
					newCompRel.setChainProjectComponent2(resultQuery2.get(0));
					newCompRel.setProjectId(resultQuery1.get(0).getId());

					getEntityManager().persist(newCompRel);

					ComponentRelation newCompRel2 = new ComponentRelation();

					newCompRel2.setChainProjectComponent(resultQuery2.get(0));
					newCompRel2.setChainProjectComponent2(resultQuery1.get(0));
					newCompRel2.setProjectId(resultQuery1.get(0).getId());

					getEntityManager().persist(newCompRel2);

				}

			}

		}

	}

	@SuppressWarnings("unchecked")
	public void removeProjectComponents(int idProject) {

		List<ChainProjComponent> componentsToRemove = new ArrayList<ChainProjComponent>();

		String selectQuery = "Select m from ChainProjComponent m where m.id = :project";

		Query q = getEntityManager().createQuery(selectQuery);
		q.setParameter("project", idProject);

		componentsToRemove = q.getResultList();

		for (ChainProjComponent m : componentsToRemove) {
			getEntityManager().remove(m);
		}
	}

	@SuppressWarnings("unchecked")
	public void updateSelecedProjectComponent(String project, int idChainProj,
			int idComponent) {

		Query q = getEntityManager()
				.createQuery(
						"Select c from ChainProject c where c.project = :proj and c.selected='1'");
		q.setParameter("proj", project);

		List<ChainProject> returnChainProj = q.getResultList();

		if (!returnChainProj.equals(null)) {
			ChainProject returnChainProj1 = returnChainProj.get(0);
			returnChainProj1.setSelected('0');
			getEntityManager().merge(returnChainProj1);
		}

		Query q1 = getEntityManager().createQuery(
				"Select c from ChainProject c where c.id = :idProj");
		q1.setParameter("idProj", idChainProj);

		returnChainProj = q.getResultList();

		if (!returnChainProj.equals(null)) {
			ChainProject returnChainProj1 = returnChainProj.get(0);
			returnChainProj1.setSelected('1');
			getEntityManager().merge(returnChainProj1);
		}

		Query q2 = getEntityManager()
				.createQuery(
						"Select c from ChainProjComponent c where c.id = :idChainProj and c.selected='1'");
		q2.setParameter("idChainProj", idChainProj);

		List<ChainProjComponent> returnComponent = q2.getResultList();

		if (!returnComponent.equals(null)) {
			ChainProjComponent returnComponent1 = returnComponent.get(0);
			returnComponent1.setSelected('0');
			getEntityManager().merge(returnComponent1);
		}

		Query q3 = getEntityManager().createQuery(
				"Select c from ChainProjComponent c where c.id = :idComp");
		q3.setParameter("idComp", idComponent);

		returnComponent = q3.getResultList();

		if (!returnComponent.equals(null)) {
			ChainProjComponent returnComponent1 = returnComponent.get(0);
			returnComponent1.setSelected('1');
			getEntityManager().merge(returnComponent1);
		}

	}

	@SuppressWarnings("unchecked")
	public void updateComponentRelations(int componentId,
			List<Integer> relationsIds) {
		
		String selectQuery = "Select c from ChainProjComponent c where c.id = :compId";

		Query q = getEntityManager().createQuery(selectQuery);

		q.setParameter("compId", componentId);

		List<ChainProjComponent> componentList = q.getResultList();	
		

		String selectQuery1 = "Select c2.id from ComponentRelation r join r.chainProjectComponent c1 join r.chainProjectComponent2 c2 where c1.id = :idComponent";

		Query q1 = getEntityManager().createQuery(selectQuery1);

		q1.setParameter("idComponent", componentId);

		List<Integer> relationsIdsAlreadyExist = q1.getResultList();
		

		for (Integer i : relationsIds) {

			if (!relationsIdsAlreadyExist.contains(i)) {

				String selectQuery2 = "Select c from ChainProjComponent c where c.id = :compId";

				Query q2 = getEntityManager().createQuery(selectQuery2);

				q2.setParameter("compId", i);

				List<ChainProjComponent> componentList2 = q2.getResultList();

				ComponentRelation newCompRel = new ComponentRelation();

				newCompRel.setChainProjectComponent(componentList.get(0));
				newCompRel.setChainProjectComponent2(componentList2.get(0));
				newCompRel.setProjectId(componentList.get(0).getId());

				getEntityManager().persist(newCompRel);

				ComponentRelation newCompRel2 = new ComponentRelation();

				newCompRel2.setChainProjectComponent(componentList2.get(0));
				newCompRel2.setChainProjectComponent2(componentList.get(0));
				newCompRel2.setProjectId(componentList.get(0).getId());

				getEntityManager().persist(newCompRel2);

			}

		}
		
		for (Integer i : relationsIdsAlreadyExist) {
			
			if (!relationsIds.contains(i)) {
				
				String selectQuery2 = "Select r from ComponentRelation r join r.chainProjectComponent c1 join r.chainProjectComponent2 c2 where c1.id = :idComponent1 and c2.id = :idComponent2";
				
				Query q2 = getEntityManager().createQuery(selectQuery2);

				q2.setParameter("idComponent1", componentId);
				q2.setParameter("idComponent2", i);
				
				List<ComponentRelation> relationDelete = q2.getResultList();
				
				getEntityManager().remove(relationDelete.get(0));
				
				selectQuery2 = "Select r from ComponentRelation r join r.chainProjectComponent c1 join r.chainProjectComponent2 c2 where c1.id = :idComponent1 and c2.id = :idComponent2";
				
				q2 = getEntityManager().createQuery(selectQuery2);

				q2.setParameter("idComponent1", i);
				q2.setParameter("idComponent2", componentId);
				
				relationDelete = q2.getResultList();
				
				getEntityManager().remove(relationDelete.get(0));
				
			}
			
		}

	}

	@SuppressWarnings("unchecked")
	public List<ChainProject> retrieveProjects(String project) {

		List<ChainProject> resultQuery = new ArrayList<ChainProject>();

		Query q = getEntityManager().createQuery(
				"Select m from ChainProject m where m.project = :project");

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

		List results = q.getResultList();

		if (!results.isEmpty()) {
			resultQuery = (ChainProject) results.get(0);
		}

		// resultQuery = (ChainProject) q.getSingleResult();

		return resultQuery;
	}

	public ChainProject retrieveProjectSelected(String project) {

		ChainProject resultQuery = new ChainProject();

		Query q = getEntityManager()
				.createQuery(
						"Select c from ChainProject c where c.project= :project and c.selected = '1'");
		q.setParameter("project", project);

		// List results = q.getResultList();
		//
		// if(!results.isEmpty()){
		// resultQuery = (ChainProject) results.get(0);
		// }

		resultQuery = (ChainProject) q.getSingleResult();

		return resultQuery;
	}

	public ChainProjComponent retrieveComponent(int componentId) {

		ChainProjComponent resultQuery = new ChainProjComponent();

		Query q = getEntityManager().createQuery(
				"Select c from ChainProjComponent where c.id = :idComp");

		q.setParameter("idComp", componentId);

		List results = q.getResultList();

		if (!results.isEmpty()) {
			resultQuery = (ChainProjComponent) results.get(0);
		}

		// resultQuery = (ChainProjComponent) q.getSingleResult();

		return resultQuery;

	}

	public ChainProjComponent retrieveSelectedComponentFromSelectedProject(
			String project) {

		ChainProjComponent resultQuery = new ChainProjComponent();
		ChainProject resultQuery1 = new ChainProject();

		String selectQuery = "Select c from ChainProject p join p.chainProjComponents c where p.project= :project and p.selected = '1' and c.selected = '1'";

		Query q = getEntityManager().createQuery(selectQuery);
		q.setParameter("project", project);

		List results = q.getResultList();

		selectQuery = "Select p from ChainProject p join p.chainProjComponents c where p.project= :project and p.selected = '1' and c.selected = '1'";

		q = getEntityManager().createQuery(selectQuery);
		q.setParameter("project", project);

		List results1 = q.getResultList();

		if (!results.isEmpty()) {
			resultQuery = (ChainProjComponent) results.get(0);
			resultQuery1 = (ChainProject) results1.get(0);
			resultQuery.setChainProject(resultQuery1);
		}

		return resultQuery;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> retrieveComponentRelations(int idComp) {

		Query q = getEntityManager()
				.createQuery(
						"Select c.chainProjectComponent2.id, p.chainProject.id, c.chainProjectComponent2.description, c.chainProjectComponent2.attribute from ChainProjComponent p join p.componentRelations c where p.id = :componentId");

		q.setParameter("componentId", idComp);

		List<Object[]> resultQuery = q.getResultList();

		// for (Object[] l : resultQuery) {
		//
		// System.out.println("Component -> Project: "
		// + Integer.toString((Integer) l[0]) + " Component1 - "
		// + (String) l[1] + " Component2 - " + (String) l[2]);
		// }

		return resultQuery;

	}

	@SuppressWarnings("unchecked")
	public List<Object[]> retrieveRelations(int idComp) {

		Query q = getEntityManager()
				.createQuery(
						"Select r2.id, r2.attribute, r2.description from ComponentRelation c join c.chainProjectComponent r1 join c.chainProjectComponent2 r2 where r1.id = :componentId");

		q.setParameter("componentId", idComp);

		List<Object[]> resultQuery = q.getResultList();

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
