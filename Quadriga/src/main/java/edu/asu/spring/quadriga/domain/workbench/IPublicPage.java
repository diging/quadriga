package edu.asu.spring.quadriga.domain.workbench;

/**
 * @description : interface to implement PublicPage class.
 * 
 * @author : Prasanth Priya Nesan
 *
 */
public interface IPublicPage {

	public abstract String getTitle();

	public abstract void setTitle(String title);

	public abstract String getDescription();

	public abstract void setDescription(String description);

	public abstract int getOrder();

	public abstract void setOrder(int order);

}