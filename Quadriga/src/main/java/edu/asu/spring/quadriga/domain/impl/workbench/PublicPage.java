package edu.asu.spring.quadriga.domain.impl.workbench;

import edu.asu.spring.quadriga.domain.workbench.IPublicPage;

/**
 * @description : Public Page class describing the properties of a Public Page
 *              object
 * 
 * @author : Prasanth Priya Nesan
 * 
 */
public class PublicPage implements IPublicPage {
	private String PublicPageId;
	private String title;
	private String description;
	private int order;
	private String projectId;

	/**
	 * retrieves the Title
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * assigns the Title
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * retrieves the description of the Given Title
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * assigns the description of the Given Title
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * retrieves the order of the Given Title
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * assigns the order of the Given Title
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getPublicPageId() {
		return PublicPageId;
	}

	public void setPublicPageId(String publicPageId) {
		PublicPageId = publicPageId;
	}

}