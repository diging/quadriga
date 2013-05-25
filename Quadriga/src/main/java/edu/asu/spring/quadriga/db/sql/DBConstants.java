package edu.asu.spring.quadriga.db.sql;

/**
 * @Description   : interface to hold the DB string constants.
 * 
 * @Called By     : DBConnectionManager.java
 * 
 * @author        : Kiran
 * @author 		  : Ram Kumar Kumaresan
 */
public interface DBConstants 
{
	public final static String SP_CALL = "call";
	
	public final static String USER_DETAILS = "sp_getUserDetails";	
	public final static String ACTIVE_USER_DETAILS="sp_getActiveUsers";
	public final static String INACTIVE_USER_DETAILS="sp_getInActiveUsers";
	public final static String DEACTIVATE_USER="sp_deactivateUser";
	public final static String UPDATE_USER_ROLES="sp_updateUserRoles";
	public final static String GET_USER_REQUESTS="sp_getUserRequests";
	
}
