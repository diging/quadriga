package edu.asu.spring.quadriga.domain.impl.workbench;

/**
 * @description : Project class describing the properties of a Public Page object
 * 
 * @author : Prasanth Priya Nesan
 * 
 */

import edu.asu.spring.quadriga.domain.workbench.IPublicPage;

public class PublicPage implements IPublicPage {
	private String title;
	private String description;
	private int order;

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

}
