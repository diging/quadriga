package edu.asu.spring.quadriga.dao.sql;

import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;


public interface IUserManagerDAO {

	public abstract int addAccountRequest(String sUserId) throws QuadrigaStorageException;

	public abstract List<IUser> getUserRequests() throws QuadrigaStorageException;

}
