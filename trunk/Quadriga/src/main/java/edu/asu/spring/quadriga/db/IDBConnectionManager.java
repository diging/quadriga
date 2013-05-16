package edu.asu.spring.quadriga.db;

import java.sql.SQLException;

import edu.asu.spring.quadriga.domain.implementation.User;

public interface IDBConnectionManager {

	public abstract User getUserDetails(String sUserId) throws SQLException;

}
