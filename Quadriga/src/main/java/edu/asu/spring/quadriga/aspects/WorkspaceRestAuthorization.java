package edu.asu.spring.quadriga.aspects;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.accesschecks.IWSSecurityChecker;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;

/**
 * Service level Implementation of {@link IAuthorization} for {@link IWorkSpace} for REST APIs.
 * This class specifically works on authorization check of user for {@link IWorkSpace} access. 
 * @author Kiran kumar
 *
 */
@Service("workspaceRestAuthorization")
public class WorkspaceRestAuthorization implements IAuthorization 
{
    @Autowired
    private IWorkspaceManager wsManager;

    @Autowired
    private IWSSecurityChecker wsSecurityManager;

    /**
     * This checks the access permissions for the logged in user for the 
     * given workspace id
     * @param : userName - logged in user
     * @param : userRoles - set of roles for which the user should be checked 
     * for access.
     * @param : workspaceid 
     * @throws : QuadrigaStorageException, QuadrigaAccessException
     * @return : hasAccess - true
     *           no Access - false
     */
    @Override
    public boolean chkAuthorization(String userName,String workspaceId,String[] userRoles) throws QuadrigaStorageException, QuadrigaAccessException
    {
        boolean haveAccess;
        String workspaceOwner;
        String collaboratorName = null;
        String collaboratorRoleId;
        IWorkSpace workspace;
        List<IWorkspaceCollaborator> workspaceCollaboratorList= null;
        List<IQuadrigaRole> collaboratorRoles = null;
        ArrayList<String> roles;

        haveAccess = false;

        //fetch the details of the workspace
        workspace = wsManager.getWorkspaceDetails(workspaceId, userName);
        if(workspace ==null){
            throw new QuadrigaAccessException();
        }
        //check if the logged in user is workspace owner
        if(workspace.getOwner()!=null)
        {
            workspaceOwner = workspace.getOwner().getUserName();

            if(userName.equals(workspaceOwner))
            {
                haveAccess = true;
            }

            if(!haveAccess)
            {
                if(userRoles.length>0)
                {
                    roles = getAccessRoleList(userRoles);

                    workspaceCollaboratorList = workspace.getWorkspaceCollaborators();
                    if(workspaceCollaboratorList != null){
                        for(IWorkspaceCollaborator workspaceCollaborator : workspaceCollaboratorList)
                        {
                            //check if he is a collaborator to the project
                            if(workspaceCollaborator.getCollaborator() != null){
                                collaboratorName = workspaceCollaborator.getCollaborator().getUserObj().getUserName();
                            }
                            if(collaboratorName != null){
                                if(userName.equals(collaboratorName))
                                {
                                    if(workspaceCollaborator.getCollaborator() != null){
                                        collaboratorRoles = workspaceCollaborator.getCollaborator().getCollaboratorRoles();
                                    }

                                    if(collaboratorRoles != null){
                                        for(IQuadrigaRole collabRole : collaboratorRoles)
                                        {
                                            collaboratorRoleId = collabRole.getId();
                                            if(roles.contains(collaboratorRoleId))
                                            {
                                                haveAccess = true;
                                                return haveAccess;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return haveAccess;
    }

    /**
     * check if the user as a owner has any workspaces associated
     * check if the user as the given role has any workspaces associated
     * @param : userName - logged in user
     * @param : userRoles - set of roles for which the user should be checked 
     * for access.
     * @throws : QuadrigaStorageException, QuadrigaAccessException
     * @return : hasAccess - true
     *           no Access - false
     */
    @Override
    public boolean chkAuthorizationByRole(String userName,String[] userRoles )
            throws QuadrigaStorageException, QuadrigaAccessException
            {
        boolean haveAccess;
        ArrayList<String> roles;
        haveAccess = false;

        //fetch the details of the project
        haveAccess = wsSecurityManager.checkIsWorkspaceAssociated(userName);

        //check the user roles if he is not a project owner
        if(!haveAccess)
        {
            if(userRoles.length>0)
            {
                roles = getAccessRoleList(userRoles);

                //check if the user associated with the role has any projects
                for(String role : roles)
                {
                    haveAccess = wsSecurityManager.chkIsCollaboratorWorkspaceAssociated(userName, role);

                    if(haveAccess)
                        break;
                }
            }
        }
        return haveAccess;

            }

    /**
     * This method converts the the string array into a list
     * @param userRoles
     * @return ArrayList<String>
     */
    public ArrayList<String> getAccessRoleList(String[] userRoles)
    {
        ArrayList<String> rolesList = new ArrayList<String>();

        for(String role : userRoles)
        {
            rolesList.add(role);
        }

        return rolesList;
    }

    @Override
    public boolean chkAuthorization(String userName, List<String> accessObjectId, String[] userRoles)
            throws QuadrigaStorageException, QuadrigaAccessException {
        // TODO Auto-generated method stub
        return false;
    }
}
