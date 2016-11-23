package com.ajr.process.service.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CHAINPROJCOMPONENT database table.
 * 
 */
@Entity
@Table(name="CHAINPROJCOMPONENT")
public class ChainProjComponent implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	
	private ChainProject chainProject;
	
	private String attribute;

	private String description;
	
	private char selected;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")	    
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PROJECT", nullable = false)
	public ChainProject getChainProject() {
		return chainProject;
	}

	public void setChainProject(ChainProject chainProject) {
		this.chainProject = chainProject;
	}	

	@Column(name = "attribute")	
	public String getAttribute() {
		return this.attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	@Column(name = "description")	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "selected")	
	public char getSelected() {
		return selected;
	}

	public void setSelected(char selected) {
		this.selected = selected;
	}
	
}