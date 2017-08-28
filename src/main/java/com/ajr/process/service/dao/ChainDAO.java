package com.ajr.process.service.dao;

import java.util.List;

import com.ajr.process.service.entity.ChainProjComponent;
import com.ajr.process.service.entity.ChainProject;
import com.ajr.process.service.entity.ComponentRelation;
import com.ajr.process.service.exceptions.AttributeAlreadyExistsException;

public interface ChainDAO {

	public void insertProject(String project, String chainProject,
			String description);

	public void insertProjectComponent(String project, String attribute,
			String description) throws AttributeAlreadyExistsException;

	public void insertComponentsRelation(int compId1, int compId2);

	public void removeProjectComponents(int idProject);
	
	public void removeProjectComponent(int component);

	public void updateSelecedProjectComponent(String project, int projChain,
			int component);

	public void updateComponentRelations(int componentId,
			List<Integer> relations);

	public List<ChainProject> retrieveProjects(String project);

	public List<ChainProjComponent> retrieveChainProjectComponents(int chainProj);

	public List<ChainProjComponent> retrieveSelectedChainProjectComponents(
			String project);

	public ChainProject retrieveProject(int IdProject);

	public ChainProject retrieveProjectSelected(String project);

	public ChainProjComponent retrieveComponent(int componentId);

	public ChainProjComponent retrieveSelectedComponentFromSelectedProject(
			String project);

	public List<Object[]> retrieveComponentRelations(int idComp);

	public List<Object[]> retrieveRelations(int componentId);

}
