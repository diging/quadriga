package edu.asu.spring.quadriga.domain.workbench;

import java.util.Date;
import java.util.List;

/**
 * @description : interface to implement PublicPage class.
 * 
 * @author : Prasanth Priya Nesan
 *
 */
public interface IPublicPage {

	public abstract String getTitle1();

	public abstract void setTitle1(String title1);

	public abstract String getTitle2();

	public abstract void setTitle2(String title2);

	public abstract String getTitle3();

	public abstract void setTitle3(String title3);

	public abstract String getDescription1();

	public abstract void setDescription1(String description1);

	public abstract String getDescription2();

	public abstract void setDescription2(String description2);

	public abstract String getDescription3();

	public abstract void setDescription3(String description3);

}