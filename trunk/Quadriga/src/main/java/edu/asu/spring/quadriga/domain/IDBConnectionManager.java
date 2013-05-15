package edu.asu.spring.quadriga.domain;

import java.sql.SQLException;

import edu.asu.spring.quadriga.domain.implementation.User;

public interface IDBConnectionManager {

	public abstract User getUserDetails(String sUserId) throws SQLException;

}
