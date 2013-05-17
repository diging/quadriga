package edu.asu.spring.quadriga.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConnectionManager;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.implementation.User;
import edu.asu.spring.quadriga.service.IUserManager;

//@Service("userManager")
public class UserManager implements IUserManager {

	@Autowired
	@Qualifier("DBConnectionManagerBean")
	private IDBConnectionManager dbConnect;

	@Autowired
	private QuadrigaRoleManager rolemanager;
	
	@Override
	public IUser getUserDetails(String sUserId) throws SQLException {
		IUser user = new User();
		user = dbConnect.getUserDetails(sUserId);
		
		//TODO: Get roles from DB
		
		//TODO: Call RoleManager to get the Name and Description - Objects and load in User object
		
		return user;
	}

	@Override
	public String updateUserDetails(User existingUser) {
		return null;
	}

	@Override
	public int deleteUser(String sUserId) {
		return 0;
	}

	@Override
	public int addNewUser(User newUser) {
		return 0;
	}

}
