package com.ajr.process.service.dao;

import java.util.List;

import com.ajr.process.service.entity.ChainProjComponent;
import com.ajr.process.service.entity.ChainProject;


public interface ChainDAO {
	
	public void insertProject(String Project, String chainProject, String description);
	public void insertProjectComponent(int id, String attribute, String description);
	public void removeProjectComponents(int idProject);
	public List<ChainProject> retrieveProjects(String project);
	public ChainProject retrieveProject(int IdProject);
	public List<ChainProjComponent> retrieveProjectComponents(int IdProject);

}
