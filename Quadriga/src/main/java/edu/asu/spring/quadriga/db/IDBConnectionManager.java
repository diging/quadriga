package edu.asu.spring.quadriga.db;

import java.sql.SQLException;

import javax.sql.DataSource;

import edu.asu.spring.quadriga.domain.implementation.User;

public interface IDBConnectionManager {

	public abstract void setDataSource(DataSource dataSource);
	
	public abstract User getUserDetails(String sUserId) throws SQLException;

}
