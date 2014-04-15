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

	public abstract String getName();
	
	public abstract void setName(String name);
	
	public abstract String getUserName();
	
	public abstract void setUserName(String userName);
	
	public abstract String getEmail();
	
	public abstract void setEmail(String email);
	
	public abstract String getPassword();
	
	public abstract void setPassword(String password);
	
	public abstract List<IQuadrigaRole> getQuadrigaRoles();
	
	public abstract void setQuadrigaRoles(List<IQuadrigaRole> quadrigaRoles);
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract  void setUpdatedDate(Date updatedDate);
}
