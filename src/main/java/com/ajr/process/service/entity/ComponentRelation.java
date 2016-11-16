package com.ajr.process.service.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	
	private String attribute;
	
	private String description;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")	  
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "Id_Project")	
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

	@Column(name = "ATTRIBUTE")
	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}