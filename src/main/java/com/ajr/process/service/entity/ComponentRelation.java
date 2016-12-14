package com.ajr.process.service.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the CHAINPROJECTS database table.
 * 
 */
@Entity
@Table(name="CHAINPROJRELATION")
public class ComponentRelation implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private int projectId;
	
	private int componentId;

	private int componentRelId;	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")	  
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "ID_PROJECT")	
	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	@Column(name = "ID_COMPONENT1")
	public int getComponentId() {
		return componentId;
	}

	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}

	@Column(name = "ID_COMPONENT2")
	public int getComponentRelId() {
		return componentRelId;
	}

	public void setComponentRelId(int componentRelId) {
		this.componentRelId = componentRelId;
	}
	
}