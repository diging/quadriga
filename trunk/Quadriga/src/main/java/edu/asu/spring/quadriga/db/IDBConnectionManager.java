package edu.asu.spring.quadriga.db;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import edu.asu.spring.quadriga.domain.IUser;

public interface IDBConnectionManager {

	public abstract void setDataSource(DataSource dataSource);
	
//	public abstract User getUserDetails(String sUserId) throws SQLException;
	
	public abstract IUser getUserDetails(String sUserId) throws SQLException;

	public abstract List<String> getUserRoles(String userid);

}
