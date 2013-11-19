package edu.asu.spring.quadriga.dao.sql;

import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;


public interface IUserManagerDAO {

	public abstract int addAccountRequest(String sUserId) throws QuadrigaStorageException;

	public abstract List<IUser> getUserRequests() throws QuadrigaStorageException;

	public abstract IUser getUserDetails(String userid) throws QuadrigaStorageException;

	public abstract List<IUser> getUsers(String userRoleId) throws QuadrigaStorageException;

	public abstract List<IUser> getUsersNotInRole(String userRoleId) throws QuadrigaStorageException;

	public abstract int deleteUser(String deleteUser, String adminUser, String adminRole, String deactivatedRole) throws QuadrigaStorageException;

	public abstract int deactivateUser(String sUserId, String sDeactiveRoleDBId, String sAdminId) throws QuadrigaStorageException;

	public abstract int updateUserRoles(String sUserId, String sRoles, String sAdminId)	throws QuadrigaStorageException;

	public abstract int approveUserRequest(String sUserId, String sRoles, String sAdminId) throws QuadrigaStorageException;

	public abstract int denyUserRequest(String sUserId, String sAdminId) throws QuadrigaStorageException;

}
