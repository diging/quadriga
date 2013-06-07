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

import org.apache.catalina.connector.OutputBuffer;
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
import edu.asu.spring.quadriga.domain.implementation.Project;

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

		
	/**
	 *  @Description: Assigns the data source
	 *  
	 *  @param : dataSource
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
	public IProject getProjectDetails(String projectId) throws SQLException {
		
		String dbCommand,dbCommand1;
		String outErrorValue,outputValue;
		CallableStatement sqlStatement ;
		
		getConnection();

		IProject project = null;
		project = projectfactory.createProjectObject();
		project.setCollaborators(new ArrayList<ICollaborator>());
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.PROJECT_DETAILS + "(?,?)";

		try {		
        sqlStatement = connection.prepareCall("{"+dbCommand+"}");
        sqlStatement.setString(1, projectId);
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
		        	project.setId("quadriga" + resultset.getString(3));
		        	IUser owner = userFactory.createUserObject();
		        	owner.setName(resultset.getString(4));
		        	project.setOwner(owner);
	        }
		}
		else
		{
			throw new RuntimeException(outErrorValue);
		}
	
	    dbCommand1 = DBConstants.SP_CALL+ " " + DBConstants.PROJECT_COLLABORATORS + "(?,?)";
		sqlStatement = connection.prepareCall("{"+dbCommand1+"}");
		sqlStatement.setString(1,projectId);
		sqlStatement.registerOutParameter(2, Types.VARCHAR);

		sqlStatement.execute();
		outputValue = sqlStatement.getString(2);
		
		if(outputValue.isEmpty())
		{
			ResultSet resultset1 = sqlStatement.getResultSet();
		
			while(resultset1.next())
			{		
				    project.setId(resultset1.getString(1));
				    
					//project.setProjectCollaborator(collaboratorUser);
					
					ICollaborator collaborator = collaboratorFactory.createCollaborator();
					List<ICollaboratorRole> collaboratorRoles = splitAndCreateCollaboratorRoles(resultset1.getString(3));
					collaborator.setCollaboratorRoles(collaboratorRoles);
					
					IUser collaboratorUser = userFactory.createUserObject();
					collaboratorUser.setName(resultset1.getString(2));
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
	 *  This method inserts a record for new project
	 *  @param  project object
	 *  @return 0 on success else exception
	 *  @exception SQL Exception
	 *  @author Kiran Kumar Batna 
	 */
	
	@Override
	public int addProjectRequest(IProject project)
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
			
			if(errmsg.isEmpty())
			{
				return 1;
			}
			else
			{
				throw new RuntimeException(errmsg);
			}
			
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

	
}
