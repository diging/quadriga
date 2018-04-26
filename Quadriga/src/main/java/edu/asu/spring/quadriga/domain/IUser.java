package edu.asu.spring.quadriga.domain;

import java.util.Date;
import java.util.List;

/**
 * @description   	 interface to implement User class.
 * 
 * @author        	 Kiran Kumar Batna
 * @author 			 Ram Kumar Kumaresan
 */
public interface IUser 
{

	/**
	 * Method to set the full name of the user.
	 * @return name full name of the user.
	 */
	public abstract String getName();
	
	/**
	 * Method to set the full name of the user
	 * @param name text containing the full name of the user.
	 */
	public abstract void setName(String name);
	
	/**
	 * Method to set the user name associated with the user.
	 * @return user name used to login to quadriga.
	 */
	public abstract String getUserName();
	
	/**
	 * Method to set the user name associated with the quadriga user.
	 * @param userName text containing the user name used to login to Quadriga.
	 */
	public abstract void setUserName(String userName);
	
	/**
	 * Method to retrieve the email associated with the user.
	 * @return email - email of user.
	 */
	public abstract String getEmail();
	
	/**
	 * Method to set the email associated with the user.
	 * @param email -email of user.
	 */
	public abstract void setEmail(String email);
	
	/**
	 * Method to retrieve the password associated with the user.
	 * @return password - password of user.
	 */
	public abstract String getPassword();
	
	/**
	 * Method to set the password associated with the user. 
	 * @param password - text containing the password associated with the user.
	 */
	public abstract void setPassword(String password);
	
	/**
	 * Method to retrieve the list of quadriga roles associated with the user.
	 * @return quadrigaRoles - list of quadriga roles associated with the user.
	 */
	public abstract List<IQuadrigaRole> getQuadrigaRoles();
	
	/**
	 * Method to set the list of quadriga roles associated with the user.
	 * @param quadrigaRoles - list of quadriga roles associated with the user.
	 */
	public abstract void setQuadrigaRoles(List<IQuadrigaRole> quadrigaRoles);
	
	/**
	 * Method to retrieve the user name who created the user.
	 * @return createdBy - the user name who created the new quadriga user.
	 */
	public abstract String getCreatedBy();
	
	/**
	 * Method to set the user name who created the user.
	 * @param createdBy - the user name who created the new quadriga user.
	 */
	public abstract void setCreatedBy(String createdBy);
	
	/**
	 * Method to retrieve the date on which the user is inserted into the system.
	 * @return createdDate - the date on which the user is inserted into the system.
	 */
	public abstract Date getCreatedDate();
	
	/**
	 * Method to set the date on which the user is inserted into the quadriga database.
	 * @param createdDate - the date on which the user is inserted into the system.
	 */
	public abstract void setCreatedDate(Date createdDate);
	
	/**
	 * Method to retrieve the user name who updated the record.
	 * @return updatedBy - the user name who updated the record.
	 */
	public abstract String getUpdatedBy();
	
	/**
	 * Method to set the user name who updated the record.
	 * @param updatedBy - the user name who updates the record.
	 */
	public abstract void setUpdatedBy(String updatedBy);
	
	/**
	 * Method to retrieve the date on which the user record is updated.
	 * @return updateDate - the date on which the user record is updated.
	 */
	public abstract Date getUpdatedDate();
	
	/**
	 * Method to set the date on which the user record is updated.
	 * @param updatedDate - the date on which the user record is updated.
	 */
	public abstract  void setUpdatedDate(Date updatedDate);
	
	public String getQuadrigaRolesAsString();
	
	public abstract String getProvider();
	
	public abstract void setProvider(String provider);
	
	public abstract String getUserIdOfProvider();
	
	public abstract void setUserIdOfProvider(String userIdOfProvider);
	
}
