package com.ajr.process.service.dao;

import java.util.List;

import com.ajr.process.service.entity.ChainProjComponent;
import com.ajr.process.service.entity.ChainProject;
import com.ajr.process.service.entity.ComponentRelation;


public interface ChainDAO {
	
	public void insertProject(String project, String chainProject, String description);
	public void insertProjectComponent(int id, String attribute, String description);
	public void removeProjectComponents(int idProject);
	public void updateSelecedProjectComponent(int idProject, int projChain, int component);
	public List<ChainProject> retrieveProjects(String project);
	public List<ChainProjComponent> retrieveChainProjectComponents(int chainProj);
	public List<ChainProjComponent> retrieveSelectedChainProjectComponents(String project);
	public ChainProject retrieveProject(int IdProject);
	public ChainProject retrieveProjectSelected(String project);
	public ChainProjComponent retrieveProjectComponent(int projectId, int componentId);
	public ChainProjComponent retrieveSelectedComponentFromSelectedProject(String project);
	public List<ChainProjComponent> retrieveComponentRelations(int idProj, int idComp);

}
