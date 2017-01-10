package com.ajr.process.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the CHAINPROJECTS database table.
 * 
 */
@Entity
@Table(name="CHAINPROJRELATION")
public class ComponentRelation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int projectId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_COMPONENT1", nullable = false)
	private ChainProjComponent chainProjectComponent;	

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_COMPONENT2", nullable=false)
	ChainProjComponent chainProjectComponent2;	

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

	public ChainProjComponent getChainProjectComponent() {
		return chainProjectComponent;
	}

	public void setChainProjectComponent(ChainProjComponent chainProjectComponent) {
		this.chainProjectComponent = chainProjectComponent;
	}

	public ChainProjComponent getChainProjectComponent2() {
		return chainProjectComponent2;
	}

	public void setChainProjectComponent2(ChainProjComponent chainProjectComponent2) {
		this.chainProjectComponent2 = chainProjectComponent2;
	}
	
}