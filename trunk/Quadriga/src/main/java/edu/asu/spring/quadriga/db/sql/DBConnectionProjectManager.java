package edu.asu.spring.quadriga.db.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.IDBConnectionProjectManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;

/**
 * Contains all the database connections to the workbench component
 * @author Kiran Kumar Batna
 * @author Rohit Pendbhaje
 *
 */
public class DBConnectionProjectManager implements IDBConnectionProjectManager 
{
	private Connection connection;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	@Autowired
	private IProjectFactory projectfactory;
	
	@Autowired
	private ICollaboratorRoleFactory collaboratorRoleFactory;
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;

	private static final Logger logger = LoggerFactory.getLogger(DBConnectionProjectManager.class);
		
	/**
	 *  @Description: Assigns the data source
	 *  
	 *  @param : dataSource
	 *  
	 *  @author Kiran Kumar Batna
	 */
	@Override
	public void setDataSource(DataSource dataSource) 
	{
		this.dataSource = dataSource;
	}
	
	/**
	 * @Description : Close the DB connection
	 * 
	 * @return : 0 on success
	 *           -1 on failure
	 *           
	 * @throws : SQL Exception
	 *    
	 * @author Kiran Kumar Batna       
	 */
	private int closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
			return 0;
		}
		catch(SQLException se)
		{
			return -1;
		}
	}
	
	/**
	 * @Description : Establishes connection with the Quadriga DB
	 * 
	 * @return      : connection handle for the created connection
	 * 
	 * @throws      : SQLException 
	 * 
	 * @author      : Kiran Kumar Batna
	 */
	private void getConnection() {
		try
		{
			connection = dataSource.getConnection();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description : Executes the supplied query to set up the test
	 *                environment
	 *                
	 * @return      : 1 on success.
	 * 
	 * @throws      : SQLException
	 * 
	 * @author      : Kiran Kumar Batna
	 */
	@Override
	public int setupTestEnvironment(String sQuery)
	{
		try
		{
			getConnection();
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sQuery);
			return 1;
		}
		catch(SQLException ex)
		{
			throw new RuntimeException(ex.getMessage());
		}
	}
	
	/**
	 * @Description     This method fetches the list of projects for current logged in user                    
	 * 
	 * @returns         List of projects
	 * 
	 * @throws			SQLException
	 *                     
	 * @author          Rohit Sukelshwar Pendbhaje
	 * 
     */
	@Override
	public List<IProject> getProjectOfUser(String sUserName) {


		String dbCommand;
		String outputErrorValue;
        IProject project;
        
		getConnection();
		List<IProject> projectList = new ArrayList<IProject>();
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.PROJECT_LIST + "(?,?)";
		try {
			
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			sqlStatement.setString(1, sUserName);
			
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			
			sqlStatement.execute();
			
			outputErrorValue = sqlStatement.getString(2);
			
			if(outputErrorValue == "")
			{

				ResultSet resultset = sqlStatement.getResultSet();
				
				while(resultset.next())
		        {
					project = projectfactory.createProjectObject();
		        	project.setName(resultset.getString(1));
		        	project.setDescription(resultset.getString(2));
		        	project.setId(resultset.getString(3));
		        	project.setInternalid(resultset.getInt(4));
		        	projectList.add(project);
		        
		         }
			}
			else
			{
					throw new SQLException(outputErrorValue);
			}
		
		} 
		catch (SQLException e) {

			e.printStackTrace();
		}
        finally
        {
        	closeConnection();
        }
		return projectList;
	}
	/**
	 * @Description     This method takes string from database and converts it into the owner 
	 * 					(type of User class) object 
	 * 					 
	 * @returns         User object
	 * 
	 * @throws			SQLException
	 *                     
	 * @author          Rohit Sukelshwar Pendbhaje
	 * 
     */
	@Override
	public List<ICollaboratorRole> splitAndCreateCollaboratorRoles(String role)
	{
        String[] roles;
		List<ICollaboratorRole> collaboratorRoleList = new ArrayList<ICollaboratorRole>();
		ICollaboratorRole collaboratorRole = null;
		
		roles = role.split(",");
		
		for(int i=0;i<roles.length;i++)
		{
			collaboratorRole = collaboratorRoleFactory.createCollaboratorRoleObject();
			collaboratorRole.setRoleDBid(roles[i]);
			collaboratorRoleList.add(collaboratorRole);
		}
		
		return collaboratorRoleList;
	}
	
	/**
	 * @Description     This method fetches the details of projects from database for the logged in user                    
	 * 
	 * @returns         project object
	 * 
	 * @throws			SQLException
	 *                     
	 * @author          Rohit Sukelshwar Pendbhaje
	 * 
     */
	@Override
	public IProject getProjectDetails(int projectId) throws SQLException {
		
		String dbCommand;
		String outErrorValue,outputValue;
		CallableStatement sqlStatement ;
		
		getConnection();

		IProject project = null;
		project = projectfactory.createProjectObject();
		project.setCollaborators(new ArrayList<ICollaborator>());
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.PROJECT_DETAILS + "(?,?)";

		try {		
        sqlStatement = connection.prepareCall("{"+dbCommand+"}");
        sqlStatement.setString(1, Integer.toString(projectId));
		sqlStatement.registerOutParameter(2, Types.VARCHAR);
		sqlStatement.execute();
		outErrorValue = sqlStatement.getString(2);
		
		if( outErrorValue == "")
		{
			ResultSet resultset = sqlStatement.getResultSet();
			
			while(resultset.next())
	        {
				    project.setName(resultset.getString(1));
		        	project.setDescription(resultset.getString(2));
		        	project.setId(resultset.getString(3));
		        	project.setInternalid(resultset.getInt(4));
		        	IUser owner = userFactory.createUserObject();
		        	owner.setName(resultset.getString(5));
		        	project.setOwner(owner);
	        }
		}
		else
		{
			throw new RuntimeException(outErrorValue);
		}
	
	    dbCommand = DBConstants.SP_CALL+ " " + DBConstants.PROJECT_COLLABORATORS + "(?,?)";
		sqlStatement = connection.prepareCall("{"+dbCommand+"}");
		sqlStatement.setString(1,Integer.toString(projectId));
		sqlStatement.registerOutParameter(2, Types.VARCHAR);

		sqlStatement.execute();
		outputValue = sqlStatement.getString(2);
		
		if(outputValue.isEmpty())
		{
			ResultSet resultset1 = sqlStatement.getResultSet();
		
			while(resultset1.next())
			{		
				IUser collaboratorUser = userFactory.createUserObject();
			    
					project.setInternalid(resultset1.getInt(1));
					
					collaboratorUser.setName(resultset1.getString(2));
				    					
					ICollaborator collaborator = collaboratorFactory.createCollaborator();
					List<ICollaboratorRole> collaboratorRoles = splitAndCreateCollaboratorRoles(resultset1.getString(3));
					collaborator.setCollaboratorRoles(collaboratorRoles);
					
					collaborator.setUserObj(collaboratorUser);
					project.getCollaborators().add(collaborator);
			}
		}
		
	} 
		 finally{
			 closeConnection();
		 }
	
		return project;
	}
	
	/**
	 *  @Description : Displays the collaborators for the 
	 *                 supplied project
	 *                 
	 *  @param       : ProjectID
	 *  
	 *  @return      : IProject - object of Project class.
	 *  
	 *  @author      : Rohit Sukelshwar Pendbhaje
	 */
	@Override
	public IProject showCollaboratorsRequest(int projectid) {

		System.out.println("projectid showCollaboratorsRequest" + projectid );
		String dbCommand;
		String outErrorValue;
		CallableStatement sqlStatement ;
		ICollaborator collaborator = null;

		IProject project = projectfactory.createProjectObject();
		project.setCollaborators(new ArrayList<ICollaborator>());

		IUser collaboratorUser=null;

		getConnection();

	    dbCommand = DBConstants.SP_CALL+ " " + DBConstants.SHOW_COLLABORATOR_REQUEST + "(?,?)";

	    try {
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, Integer.toString(projectid));
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			sqlStatement.execute();

			outErrorValue = sqlStatement.getString(2);

			if(outErrorValue.equals(" "))
			{
				ResultSet resultset = sqlStatement.getResultSet();

				while(resultset.next())
				{
					collaboratorUser = userFactory.createUserObject();
					collaboratorUser.setName(resultset.getString(1));

					collaborator = collaboratorFactory.createCollaborator();
					collaborator.setUserObj(collaboratorUser);

					project.getCollaborators().add(collaborator);
					
				}
		     }
			
			else
			{
				throw new SQLException(outErrorValue);
			} 
		     
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   
	    finally{
			 closeConnection();
		 }
		
		return project;
	}
	
	@Override
	public List<IUser> nonCollaboratoringUsersRequest(int projectid) {
		
		System.out.println("projectid showNonCollaboratorsRequest" + projectid );

		String dbCommand;
		String outErrorValue;
		CallableStatement sqlStatement ;
		ICollaborator collaborator = null;
		IUser collaboratorUser=null;
		List<IUser> collaboratingUsers = new ArrayList<IUser>();

		
		//IProject project = projectfactory.createProjectObject();
		//project.setCollaborators(new ArrayList<ICollaborator>());


		getConnection();
		
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.SHOW_NONCOLLABORATOR_REQUEST + "(?,?)";

	    try {
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, Integer.toString(projectid));
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			sqlStatement.execute();

			outErrorValue = sqlStatement.getString(2);

			if(outErrorValue.equals(" "))
			{
				ResultSet resultset = sqlStatement.getResultSet();

				while(resultset.next())
				{
					collaboratorUser = userFactory.createUserObject();
					collaboratorUser.setName(resultset.getString(1));

					//collaborator = collaboratorFactory.createCollaborator();
					//collaborator.setUserObj(collaboratorUser);

					//project.getCollaborators().add(collaborator);
					collaboratingUsers.add(collaboratorUser);
					
					
				}
		     }
			
			else
			{
				throw new SQLException(outErrorValue);
			} 
		     
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
	    finally{
			 closeConnection();
		 }
		
		return collaboratingUsers;
	    
	}
	
	/**
	 *  @Description  : add the collaborator request
	 *  
	 *  @param        : collaborator
	 *  
	 *  @return       : 1 on success and 0 on failure
	 *  
	 *  @author       : Rohit Sukelshwar Pendbhaje
	 */
	public String addCollaboratorRequest(IProject project)
	{
		List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
		List<ICollaborator> collaboratorList = project.getCollaborators();
		
		for(ICollaborator collaborator:collaboratorList)
		{
			IUser collabUser = collaborator.getUserObj();
			System.out.println("----- DB username:"+collabUser.getUserName());
			for(ICollaboratorRole collaboratorRole:collaborator.getCollaboratorRoles())
			{
				System.out.println("--------- DB roleDBid" + collaboratorRole.getRoleDBid());

			}
		}
		
		/*collaborator.getCollaboratorRoles();
		IUser collboratoruser =	collaborator.getUserObj();
		String userName = collboratoruser.getUserName();
		System.out.println("-----username"+collboratoruser.getUserName());

		String dbCommand;
		String outErrorValue;
		CallableStatement sqlStatement ;
		
		
		
		dbCommand =  DBConstants.SP_CALL+ " " + DBConstants.ADD_COLLABORATOR_REQUEST + "(?,?,?,?)" ;
		
		try {
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			sqlStatement.setString(2,collaboratorUser);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} */

		
		return null;
		
	} 
	
		
	/**
	 *  This method inserts a record for new project
	 *  @param  project object
	 *  @return error message on error else a balnk string
	 *  @exception SQL Exception
	 *  @author Kiran Kumar Batna 
	 */
	@Override
	public String addProjectRequest(IProject project)
	{
		String name;
		String description;
		String id;
		String projectAccess;
        IUser owner = null;
        String dbCommand;
        String errmsg;
        CallableStatement sqlStatement;
        
        //fetch the values from the project object
        name = project.getName();
        description = project.getDescription();
        id = project.getId();
        owner = project.getOwner();
        projectAccess = project.getProjectAccess().name();
        
        //command to call the SP
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ADD_PROJECT_REQUEST + "(?,?,?,?,?,?)";
        
        //get the connection
        getConnection();
        
        //establish the connection with the database
        try
        {
        	sqlStatement = connection.prepareCall("{"+dbCommand+"}");
        	
        	//adding the input variables to the SP
        	sqlStatement.setString(1, name);
        	sqlStatement.setString(2, description);
        	sqlStatement.setString(3,id);
        	sqlStatement.setString(4,projectAccess);
        	sqlStatement.setString(5,owner.getUserName());
        	
        	//adding output variables to the SP
			sqlStatement.registerOutParameter(6,Types.VARCHAR);

			sqlStatement.execute();

			errmsg = sqlStatement.getString(6);
			
			return errmsg;
			
        }
        catch(SQLException e)
        {
        	throw new RuntimeException(e.getMessage());
        }
        finally
        {
        	closeConnection();
        }
	}
	
	/**
	 * @description     : This project deletes the projects form 
	 *                    tbl_project_workspace,tbl_project_collaborator
	 *                    tbl_project
	 * @param           : projectIdList - Project internal id's in a comma 
	 *                    separated string.
	 * @return          : errmsg - blank identifies success and null identifies
	 *                    error in deleting the records.
	 * @author          : Kiran Kumar Batna
	 */
	@Override
	public String deleteProjectRequest(String projectIdList)
	{
		String dbCommand;
		CallableStatement sqlStatement;
		String errmsg;
		
		//command to call the SP
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.DELETE_PROJECT_REQUEST + "(?,?)";
        
        logger.info("Deleting project inernal ids : "+projectIdList);
        //get the connection
        getConnection();
        
        try
        {
        	sqlStatement = connection.prepareCall("{"+dbCommand+"}");
        	
        	sqlStatement.setString(1,projectIdList);
        	sqlStatement.registerOutParameter(2, Types.VARCHAR);
        	
        	sqlStatement.execute();
        	
        	errmsg = sqlStatement.getString(2);
        	
        	return errmsg;
        }
        catch(SQLException ex)
        {
        	logger.info("Deleting project inernal ids error : "+ex.getMessage());
        	throw new RuntimeException(ex.getMessage());
        	
        }
	}

	
}
