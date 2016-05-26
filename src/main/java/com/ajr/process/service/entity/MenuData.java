package com.ajr.process.service.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the MENUSDATA database table.
 * 
 */
@Entity
@Table(name="MENUSDATA")
public class MenuData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "project")
	private String project;

	@Column(name = "menunum")
	private int menunum;
	
	@Column(name = "submenu1num")
	private int submenu1num;
	
	@Column(name = "submenu2num")
	private int submenu2num;
	
	@Column(name = "label")
	private String label;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public int getMenunum() {
		return menunum;
	}

	public void setMenunum(int menunum) {
		this.menunum = menunum;
	}

	public int getSubmenu1num() {
		return submenu1num;
	}

	public void setSubmenu1num(int submenu1num) {
		this.submenu1num = submenu1num;
	}

	public int getSubmenu2num() {
		return submenu2num;
	}

	public void setSubmenu2num(int submenu2num) {
		this.submenu2num = submenu2num;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
