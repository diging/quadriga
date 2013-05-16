package edu.asu.spring.quadriga.db;

import edu.asu.spring.quadriga.domain.implementation.User;

public interface IDBConnection {

	public abstract User getUserDetails(String sUserId);
}
