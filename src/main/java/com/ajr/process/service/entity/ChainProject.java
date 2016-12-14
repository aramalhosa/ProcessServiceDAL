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
@Table(name = "CHAINPROJECTS")
public class ChainProject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String project;

	private String chainProject;

	private String description;

	private char selected;

	@OneToMany(targetEntity = ChainProjComponent.class, mappedBy = "chainProject", fetch = FetchType.LAZY)
	private Set<ChainProjComponent> chainProjComponents = new HashSet<ChainProjComponent>();

	public ChainProject() {
	}

	@Column(name = "ID")
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "PROJECT")
	public String getProject() {
		return this.project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	@Column(name = "CHAINPROJECT")
	public String getChainProject() {
		return chainProject;
	}

	public void setChainProject(String chainProject) {
		this.chainProject = chainProject;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "SELECTED")
	public char getSelected() {
		return selected;
	}

	public void setSelected(char selected) {
		this.selected = selected;
	}

	public Set<ChainProjComponent> getChainProjComponents() {
		return chainProjComponents;
	}

	public void setChainProjComponents(
			Set<ChainProjComponent> chainProjComponents) {
		this.chainProjComponents = chainProjComponents;
	}

}