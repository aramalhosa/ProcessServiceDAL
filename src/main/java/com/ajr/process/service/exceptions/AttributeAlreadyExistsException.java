package com.ajr.process.service.exceptions;
 
public class AttributeAlreadyExistsException extends Exception {
 
    private static final long serialVersionUID = 1L;
 
    private String attribute;
    private String project;
 
    public AttributeAlreadyExistsException(String project, String attribute) {
        super("Project with id "+ project +" already as an attribute " + attribute);
        this.project = project;
        this.attribute = attribute;
    }
 
    public String getAttribute() { return attribute; }
    public String getProjectId() { return project; }
}
