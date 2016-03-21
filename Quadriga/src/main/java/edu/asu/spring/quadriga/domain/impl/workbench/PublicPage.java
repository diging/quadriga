package edu.asu.spring.quadriga.domain.impl.workbench;

import java.util.Date;
import java.util.List;

/**
 * @description : Project class describing the properties of a Public Page object
 * 
 * @author : Prasanth Priya Nesan
 * 
 */

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.workbench.IPublicPage;

@Service
public class PublicPage implements IPublicPage {
	private String title1;
	private String description1;
	private String title2;
	private String description2;
	private String title3;
	private String description3;
	
	/**
	 * retrieves the Title 1
	 */
	@Override
	public String getTitle1() {
		return title1;
	}

	/**
	 * assigns the Title 1
	 */
	@Override
	public void setTitle1(String title1) {
		this.title1 = title1;
	}
	
	/**
	 * retrieves the Title 2
	 */
	@Override
	public String getTitle2() {
		return title2;
	}

	/**
	 * assigns the Title 2
	 */
	@Override
	public void setTitle2(String title2) {
		this.title2 = title2;
	}
	
	
	/**
	 * retrieves Title 3
	 */
	@Override
	public String getTitle3() {
		return title3;
	}

	/**
	 * assigns the Title 3
	 */
	@Override
	public void setTitle3(String title3) {
		this.title3 = title3;
	}
		

	/**
	 * retrieves the description 1 of the project
	 */
	@Override
	public String getDescription1() {
		return description1;
	}

	/**
	 * assigns the description 1 of the project
	 */
	@Override
	public void setDescription1(String description1) {
		this.description1 = description1;
	}

	
	/**
	 * retrieves the description 2 of the project
	 */
	@Override
	public String getDescription2() {
		return description2;
	}

	/**
	 * assigns the description 2 of the project
	 */
	@Override
	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	
	/**
	 * retrieves the description 3 of the project
	 */
	@Override
	public String getDescription3() {
		return description3;
	}

	/**
	 * assigns the description 3 of the project
	 */
	@Override
	public void setDescription3(String description3) {
		this.description3 = description3;
	}

}
