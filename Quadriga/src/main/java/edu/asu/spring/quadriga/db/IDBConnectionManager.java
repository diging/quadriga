package edu.asu.spring.quadriga.db;

import java.util.List;

import javax.sql.DataSource;

import edu.asu.spring.quadriga.domain.IQuadrigaRoles;
import edu.asu.spring.quadriga.domain.IUser;

/**
 * @description : Interface for the DBConnectionManager Class.
 * 
 * @author : Kiran
 *
 */
public interface IDBConnectionManager 
{

	public abstract List<IQuadrigaRoles> UserRoles(String roles);

	public abstract IUser getUserDetails(String userid);

	public abstract void setDataSource(DataSource dataSource);
}
