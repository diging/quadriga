package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.IUserDAO;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.impl.User;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDeniedDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserRequestsDTO;
import edu.asu.spring.quadriga.email.IEmailNotificationManager;
import edu.asu.spring.quadriga.email.impl.EmailNotificationManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaNotificationException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.UserOwnsOrCollaboratesDeletionException;
import edu.asu.spring.quadriga.exceptions.UsernameExistsException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.user.mapper.IUserDeepMapper;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.manageusers.beans.AccountRequest;

/**
 * @description UserManager class implementing the User functionality
 * 
 * @author Kiran Kumar Batna
 * @author Ram Kumar Kumaresan
 * 
 */
@Service
public class UserManager implements IUserManager {

    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);

    @Autowired
    private IQuadrigaRoleManager rolemanager;

    @Autowired
    private IUserDeepMapper userDeepMapper;

    @Autowired
    private IUserFactory userFactory;

    @Autowired
    private IEmailNotificationManager emailManager;

    @Autowired
    private IUserDAO usermanagerDAO;

    /**
     * @description : retrieve the user details from DBConectionManager and
     *              retrieve the associate roles from the quadriga-roles.xml
     * 
     * @param : userId - the userid for which the details are obtained.
     * 
     * @return : IUser - User object with full details of user if he is present
     *         in the Quadriga DB. - User object assigned to 'No Account' role'
     *         if he is not present in Quadriga DB.
     * @author : Kiran
     * @throws QuadrigaStorageException
     *
     */
    @Override
    @Transactional
    public IUser getUser(String sUserId) throws QuadrigaStorageException {
        return userDeepMapper.getUser(sUserId);
    }

    /**
     * List all active users in the Quadriga database
     * 
     * @return List of all active user objects
     * @author Ram Kumar Kumaresan
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public List<IUser> getAllActiveUsers() throws QuadrigaStorageException {
        List<IUser> listUsers = userDeepMapper.getAllActiveUsers();

        return listUsers;
    }

    /**
     * List all inactive users in the quadriga database
     * 
     * @return List of all inactive user objects
     * @author Ram Kumar Kumaresan
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public List<IUser> getAllInActiveUsers() throws QuadrigaStorageException {
        List<IUser> listUsers = userDeepMapper.getAllInActiveUsers();
        return listUsers;
    }

    /**
     * List all the open user requests available in the quadriga database
     * 
     * @return List all open user requests
     * @author Ram Kumar Kumaresan
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public List<IUser> getUserRequests() throws QuadrigaStorageException {
        List<IUser> listUsers = userDeepMapper.getUserRequests();

        return listUsers;
    }

    /**
     * Deactivate a user account so that the particular user cannot access
     * quadriga anymore.
     * 
     * @param sUserId
     *            The userid of the user whose account is to be deactivated.
     * @param sAdminId
     *            The userid of the admin who is changing the user setting.
     * @return Return the status of the operation. 1- Success and 0 - Failure.
     * 
     * @author Ram Kumar Kumaresan
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public int deactivateUser(String sUserId, String sAdminId) throws QuadrigaStorageException {
        // Find the ROLEDBID for Deactivated account
        String sDeactiveRoleDBId = rolemanager.getQuadrigaRoleDBId(IQuadrigaRoleManager.MAIN_ROLES,
                RoleNames.ROLE_QUADRIGA_DEACTIVATED);

        // Add the new role to the user.
        int iResult = usermanagerDAO.deactivateUser(sUserId, sDeactiveRoleDBId, sAdminId);

        if (iResult == SUCCESS) {
            logger.info("The admin " + sAdminId + " deactivated the account of " + sUserId);
            IUser user = this.getUser(sUserId);

            if (user.getEmail() != null && !user.getEmail().equals(""))
                emailManager.sendAccountDeactivationEmail(user, sAdminId);
            else
                logger.info("The user " + sUserId + " did not have an email address to send account deactivation email");
        }
        return iResult;
    }

    /**
     * Method to delete Quadriga user by admin
     * 
     * @param sUserId
     * @param sAdminId
     * @throws QuadrigaStorageException
     * @throws UserOwnsOrCollaboratesDeletionException
     */
    @Override
    @Transactional
    public void deleteUser(String deleteUser, String adminUser) throws QuadrigaStorageException,
            UserOwnsOrCollaboratesDeletionException {
        // Find the ROLEDBID for Deactivated account
        String sDeactiveRoleDBId = rolemanager.getQuadrigaRoleDBId(IQuadrigaRoleManager.MAIN_ROLES,
                RoleNames.ROLE_QUADRIGA_DEACTIVATED);

        usermanagerDAO.deleteUser(deleteUser, sDeactiveRoleDBId);

    }

    /**
     * Approve a user request to access quadriga.
     * 
     * @param sUserId
     *            The userid of the user whose account has been approved.
     * @param sRoles
     *            The set of roles that are assigned to the user.
     * @param sAdminId
     *            The userid of the admin who is changing the user setting
     * @return Return the status of the operation. 1- Success and 0 - Failure.
     * 
     * @author Ram Kumar Kumaresan
     * @throws QuadrigaStorageException
     * @throws QuadrigaNotificationException 
     */
    @Override
    @Transactional
    public int approveUserRequest(String sUserId, String sRoles, String sAdminId) throws QuadrigaStorageException, QuadrigaNotificationException {

        int iResult = usermanagerDAO.approveUserRequest(sUserId, sRoles, sAdminId);
        if (iResult == IUserDAO.SUCCESS) {
            IUser user = getUser(sUserId);
            emailManager.sendAccountProcessedEmail(user, true);
        }
        return iResult;
    }

    /**
     * Deny a user request to access the quadriga.
     * 
     * @param sUserId
     *            The userid of the user whose account has been denied.
     * @param sAdminId
     *            The userid of the admin who is changing the user setting.
     * @return Return the status of the operation. 1- Success and 0 - Failure.
     * 
     * @author Ram Kumar Kumaresan
     * @throws QuadrigaStorageException
     * @throws QuadrigaNotificationException 
     */
    @Override
    @Transactional
    public int denyUserRequest(String sUserId, String sAdminId) throws QuadrigaStorageException, QuadrigaNotificationException {

        QuadrigaUserRequestsDTO user = usermanagerDAO.getUserRequestDTO(sUserId);
        IUser deniedUser = new User();
        deniedUser.setName(user.getFullname());
        deniedUser.setEmail(user.getEmail());
        deniedUser.setUserName(user.getUsername());
        int iResult = usermanagerDAO.denyUserRequest(sUserId, sAdminId);
        if (iResult == IUserDAO.SUCCESS) {
            emailManager.sendAccountProcessedEmail(deniedUser, false);
        }
        return iResult;
    }

    /**
     * Activate an already existing user so that the user can access quadriga.
     * 
     * @param sUserId
     *            The userid of the user whose account has been activated.
     * @param sAdminId
     *            The userid of the admin who is changing the user setting.
     * @return Return the status of the operation. 1- Success and 0 - Failure.
     * 
     * @author Ram Kumar Kumaresan
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public int activateUser(String sUserId, String sAdminId) throws QuadrigaStorageException {

        int iResult = 0;

        // Find the deactivated role id and create a QuadrigaRole Object
        String sDeactiveRoleDBId = rolemanager.getQuadrigaRoleDBId(IQuadrigaRoleManager.MAIN_ROLES,
                RoleNames.ROLE_QUADRIGA_DEACTIVATED);

        // Find all the roles of the user
        IUser user = null;

        user = userDeepMapper.getUser(sUserId);

        // Remove the deactivated role from user roles
        if (user != null) {
            IQuadrigaRole quadrigaRole = null;
            List<IQuadrigaRole> rolesList = new ArrayList<IQuadrigaRole>();
            List<IQuadrigaRole> userRoles = user.getQuadrigaRoles();
            for (IQuadrigaRole role : userRoles) {
                if (!role.getDBid().equals(sDeactiveRoleDBId)) {
                    quadrigaRole = rolemanager.getQuadrigaRoleByDbId(IQuadrigaRoleManager.MAIN_ROLES, role.getDBid());
                    rolesList.add(quadrigaRole);
                }
            }
            user.setQuadrigaRoles(rolesList);

            // Convert the user roles to one string with DBROLEIDs
            // Update the role in the Quadriga Database.
            // TODO: Change the method to update the user roles.
            iResult = usermanagerDAO.updateUserRoles(sUserId, user.getQuadrigaRolesAsString(), sAdminId);

            if (iResult == SUCCESS) {
                logger.info("The admin " + sAdminId + " activated the account of " + sUserId);

                if (user.getEmail() != null && !user.getEmail().equals(""))
                    emailManager.sendAccountActivationEmail(user, sAdminId);
                else {
                    logger.info("The user " + sUserId
                            + " did not have an email address to send account activation email");
                }
            }
        }

        return iResult;
    }

    /**
     * This method adds a new user account request to the database. it encrypts
     * the user's password using the BCrypt algorithm.
     * 
     * @param request
     *            The {@link AccountRequest} encapsulating the user's data.
     * @return true if request was successfully added; otherwise false
     * @author jdamerow
     * @throws QuadrigaStorageException
     * @throws UsernameExistsException
     * @throws QuadrigaNotificationException 
     */
    @Override
    @Transactional
    public boolean addNewUser(AccountRequest request) throws QuadrigaStorageException, UsernameExistsException, QuadrigaNotificationException {
        QuadrigaUserDTO userDTO = usermanagerDAO.getUserDTO(request.getUsername());

        // Check if username is already in use
        if (userDTO != null)
            throw new UsernameExistsException("Username already in use.");

        QuadrigaUserRequestsDTO userRequest = usermanagerDAO.getUserRequestDTO(request.getUsername());
        if (userRequest != null)
            throw new UsernameExistsException("Username already in use.");

        String plainPassword = request.getPassword();
        boolean success = usermanagerDAO.addNewUserAccountRequest(request.getUsername(), encryptPassword(plainPassword),
                request.getName(), request.getEmail());
        
        if (success) {
            IQuadrigaRole role = rolemanager.getQuadrigaRoleById(IQuadrigaRoleManager.MAIN_ROLES, RoleNames.ROLE_QUADRIGA_ADMIN);
            List<QuadrigaUserDTO> admins = usermanagerDAO.getUserDTOList(role.getDBid());
            for (QuadrigaUserDTO admin : admins) {
                emailManager.sendAccountCreatedEmail(request.getName(), request.getUsername(), admin.getFullname(), admin.getEmail());
            }
        }
        
        return success;
    }

    private String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Add a new user request to access quadriga.
     * 
     * @param userId
     *            The user id of the user who needs access to quadriga
     * @return Integer value that specifies the status of the operation. 1 -
     *         Successfully place the request. 0 - An open request is already
     *         placed for the userid.
     * 
     * @author Ram Kumar Kumaresan
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public int addAccountRequest(String userId) throws QuadrigaStorageException {
        int iUserStatus;

        // Get all open user requests
        List<IUser> listUsers = userDeepMapper.getUserRequests();

        // Check if an open request is already placed for the userid
        for (IUser user : listUsers) {
            if (user.getUserName().equalsIgnoreCase(userId)) {
                return FAILURE;
            }
        }

        // Place a new access request
        iUserStatus = usermanagerDAO.addAccountRequest(userId);

        // Check the status of the request
        if (iUserStatus == SUCCESS) {
            String sAdminRoleDBId = rolemanager.getQuadrigaRoleDBId(IQuadrigaRoleManager.MAIN_ROLES,
                    RoleNames.ROLE_QUADRIGA_ADMIN);
            List<IUser> listAdminUsers = userDeepMapper.getUsersByRoleId(sAdminRoleDBId);

            // Ignore the user if the account is deactivated
            adminlabel: for (IUser admin : listAdminUsers) {
                List<IQuadrigaRole> roles = admin.getQuadrigaRoles();
                IQuadrigaRole quadrigaRole = null;

                for (IQuadrigaRole role : roles) {
                    quadrigaRole = rolemanager.getQuadrigaRoleByDbId(IQuadrigaRoleManager.MAIN_ROLES, role.getDBid());
                    if (quadrigaRole.getId().equals(RoleNames.ROLE_QUADRIGA_DEACTIVATED)) {
                        // Continue to the next user as this user account is
                        // deactivated
                        continue adminlabel;
                    }
                }

                if (admin.getEmail() != null && !admin.getEmail().equals("")) {
                    emailManager.sendNewAccountRequestPlacementEmail(admin, userId);
                } else
                    logger.info("The system tried to send email to the admin " + admin.getUserName()
                            + " but the admin did not have an email setup");
            }
        }
        return iUserStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateUserQuadrigaRoles(String userName, String quadrigaRoles, String loggedInUser)
            throws QuadrigaStorageException {
        try {
            usermanagerDAO.updateUserRoles(userName, quadrigaRoles, loggedInUser);
        } catch (Exception ex) {
            throw new QuadrigaStorageException();
        }
    }

    /**
     * This method calls the DAO layer method to insert Quadriga Admin user
     * record into the daabase.
     * 
     * @param userName
     *            - Quadriga admin user name.
     * @param sRoles
     *            - quadriga Roles possed by the admin.
     * @throws QuadrigaStorageException
     *             - represents any database exception.
     * @author kiran batna
     */
    @Override
    @Transactional
    public void insertQuadrigaAdminUser(String userName) throws QuadrigaStorageException {
        try {
            // retreive the db names of the quadriga admin roles
            StringBuilder quadrigaRoles = new StringBuilder();
            String role = null;

            role = rolemanager.getQuadrigaRoleDBId(IQuadrigaRoleManager.MAIN_ROLES, RoleNames.ROLE_QUADRIGA_ADMIN);
            quadrigaRoles.append(role);
            role = rolemanager.getQuadrigaRoleDBId(IQuadrigaRoleManager.MAIN_ROLES,
                    RoleNames.ROLE_QUADRIGA_USER_STANDARD);
            quadrigaRoles.append(",");
            quadrigaRoles.append(role);
            role = rolemanager.getQuadrigaRoleDBId(IQuadrigaRoleManager.MAIN_ROLES,
                    RoleNames.ROLE_QUADRIGA_USER_COLLABORATOR);
            quadrigaRoles.append(",");
            quadrigaRoles.append(role);

            usermanagerDAO.insertQuadrigaAdminUser(userName, quadrigaRoles.toString());
        } catch (Exception ex) {
            throw new QuadrigaStorageException(ex.getMessage());
        }
    }

    public IUserFactory getUserFactory() {
        return userFactory;
    }

    public void setUserFactory(IUserFactory userFactory) {
        this.userFactory = userFactory;
    }

}