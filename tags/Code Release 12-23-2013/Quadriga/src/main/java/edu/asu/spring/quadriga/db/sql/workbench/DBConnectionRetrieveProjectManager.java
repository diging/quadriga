package edu.asu.spring.quadriga.db.sql.workbench;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjectManager;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;

public class DBConnectionRetrieveProjectManager extends ADBConnectionManager implements IDBConnectionRetrieveProjectManager 
{	
	@Autowired
	private IProjectFactory projectFactory;
	
	@Autowired
    private IUserManager userManager;
	
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionRetrieveProjectManager.class);

	
	/**
	 * This method fetches the list of projects for current logged in user.
	 * If the logged in user is quadriga admin all the projects are retrieved.                
	 * @returns  List of projects
	 * @throws	 QuadrigaStorageException 
	 * @author   Rohit Sukelshwar Pendbhaje
     */
	public List<IProject> getProjectList(String sUserName,String spName) throws QuadrigaStorageException
	{
		String dbCommand;
		String errmsg;
		String projOwnerName;
		IUser projOwner;
        IProject project;
        CallableStatement sqlStatement;
        List<IProject> projectList = new ArrayList<IProject>();
        
		dbCommand = DBConstants.SP_CALL + " " + spName + "(?,?)";
		try {
			
	        //establish the connection
			getConnection();
			
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, sUserName);
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(2);
			
			if(errmsg == "")
			{
				ResultSet resultset = sqlStatement.getResultSet();
				
				if(resultset.isBeforeFirst())
				{
					while(resultset.next())
			        {
						project = projectFactory.createProjectObject();
			        	project.setName(resultset.getString(1));
			        	project.setDescription(resultset.getString(2));
			        	project.setUnixName(resultset.getString(3));
			        	project.setInternalid(resultset.getString(4));
			        	//retrieve the user details
			        	projOwnerName = resultset.getString(5);
			        	projOwner = userManager.getUserDetails(projOwnerName);
			        	project.setOwner(projOwner);
			        	project.setProjectAccess(EProjectAccessibility.valueOf(resultset.getString(6)));
			        	
			        	projectList.add(project);
			         }
				}
			}
			else
			{
				throw new QuadrigaStorageException(errmsg);
			}
		} 
		catch (SQLException e)
		{
			logger.error("Retrieve projects list method :",e);
			throw new QuadrigaStorageException();
		}
        finally
        {
        	closeConnection();
        }
		return projectList;
	}
	
	/**
	 * This method fetches the list of projects for current logged in user.
	 * and for the specified collaborator role.
	 * @returns  List of projects
	 * @throws	 QuadrigaStorageException 
	 * @author   Rohit Sukelshwar Pendbhaje
     */
	public List<IProject> getCollaboratorProjectList(String sUserName,String collaboratorRole) throws QuadrigaStorageException
	{
		String dbCommand;
		String errmsg;
		String projOwnerName;
		IUser projOwner;
        IProject project;
        CallableStatement sqlStatement;
        List<IProject> projectList = new ArrayList<IProject>();
        
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.PROJECT_DETAILS_BY_COLLAB_ROLE + "(?,?,?)";
		try {
			
	        //establish the connection
			getConnection();
			
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, sUserName);
			sqlStatement.setString(2, collaboratorRole);
			sqlStatement.registerOutParameter(3, Types.VARCHAR);
			
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(3);
			
			if(errmsg.equals(""))
			{
				ResultSet resultset = sqlStatement.getResultSet();
				
				if(resultset.isBeforeFirst())
				{
					while(resultset.next())
			        {
						project = projectFactory.createProjectObject();
			        	project.setName(resultset.getString(1));
			        	project.setDescription(resultset.getString(2));
			        	project.setUnixName(resultset.getString(3));
			        	project.setInternalid(resultset.getString(4));
			        	//retrieve the user details
			        	projOwnerName = resultset.getString(5);
			        	projOwner = userManager.getUserDetails(projOwnerName);
			        	project.setOwner(projOwner);
			        	project.setProjectAccess(EProjectAccessibility.valueOf(resultset.getString(6)));
			        	projectList.add(project);
			         }
				}
			}
			else
			{
				logger.error("Retrieve project list",errmsg);
				throw new QuadrigaStorageException(errmsg);
			}
		} 
		catch (SQLException e)
		{
			logger.error("Retrieve projects list method :",e);
			throw new QuadrigaStorageException();
		}
        finally
        {
        	closeConnection();
        }
		return projectList;
	}
	
	/**
	 * This retrieves the project details for the supplied project internal id.
	 * @param projectId
	 * @return IProject - project object containing details.
	 * @throws QuadrigaStorageException
	 * @author Rohit Sukelshwar Pendbhaje
	 */
	@Override
	public IProject getProjectDetails(String projectId) throws QuadrigaStorageException 
	{
		String dbCommand;
		String errmsg;
		String projOwnerName;
		CallableStatement sqlStatement ;
		IProject project = null;
		IUser projOwner = null;
		
		//command to call stored procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.PROJECT_DETAILS + "(?,?)";
		
		//establish a connection
		getConnection();
		
		try
		{
	        sqlStatement = connection.prepareCall("{"+dbCommand+"}");
	        sqlStatement.setString(1, projectId);
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(2);
			
			if(errmsg =="")
			{
				project = projectFactory.createProjectObject();
				ResultSet resultset = sqlStatement.getResultSet();
				
				if(resultset.isBeforeFirst())
				{
					while(resultset.next())
			        {
					    project.setName(resultset.getString(1));
			        	project.setDescription(resultset.getString(2));
			        	project.setUnixName(resultset.getString(3));
			        	project.setInternalid(resultset.getString(4));
			        	
			        	//retrieve the user details
			        	projOwnerName = resultset.getString(5);
			        	projOwner = userManager.getUserDetails(projOwnerName);
			        	project.setOwner(projOwner);
			        	project.setProjectAccess(EProjectAccessibility.valueOf(resultset.getString(6)));
			        }
				}
			}
			else
			{
				throw new QuadrigaStorageException(errmsg);
			}
		}
		catch(SQLException e)
		{
			logger.info("Retrieve project details method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		finally
		{
			closeConnection();
		}
		return project;
	}

	@Override
	public List<IProject> getProjectList(String sUserName)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IProject> getProjectListAsWorkspaceOwner(String sUserName)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IProject> getProjectListAsWorkspaceCollaborator(String sUserName)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProject getProjectDetailsByUnixName(String unixName)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IProject> getCollaboratorProjectList(String sUserName)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IProject> getProjectListByCollaboratorRole(String sUserName,
			String collaboratorRole) throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}
}