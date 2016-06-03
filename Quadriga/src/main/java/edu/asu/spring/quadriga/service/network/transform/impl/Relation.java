package edu.asu.spring.quadriga.service.network.transform.impl;

/**
 * This class represents a relationship in graphs
 * that should be searched and mapped.
 * 
 * @author Julia Damerow
 *
 */
public class Relation extends Node {

	private Node subject;
	private Node predicate;
	private Node object;
	
	public Node getSubject() {
		return subject;
	}
	public void setSubject(Node subject) {
		this.subject = subject;
	}
	public Node getPredicate() {
		return predicate;
	}
	public void setPredicate(Node predicate) {
		this.predicate = predicate;
	}
	public Node getObject() {
		return object;
	}
	public void setObject(Node object) {
		this.object = object;
	}
	
	
}
