package edu.asu.spring.quadriga.web.workbench.backing;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryCollaborator;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.service.workbench.IProjectCollaboratorManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCollaboratorManager;

/**
 * this class returns the collaborator of ModifyCollaborator domain by calling
 * existing DBConnectonManager methods of conecptcollection,project and
 * dictionary which by default return collaborator of ICollaborator domain.
 * 
 * @author rohit pendbhaje
 *
 */
@Service
public class ModifyCollaboratorFormManager {

    @Autowired
    private IProjectCollaboratorManager projCollabManager;

    @Autowired
    private IDictionaryManager dictManager;

    @Autowired
    private IConceptCollectionManager collectionManager;

    @Autowired
    private IWorkspaceCollaboratorManager wsCollabManager;

    /**
     * takes project id to return collaborators of ModifyCollaborator domain
     * 
     * @param projectId
     * @return List<ModifyCollaborator> list of modifycollaborator domain
     * @throws QuadrigaStorageException
     */
    public List<ModifyCollaborator> getProjectCollaborators(String projectId) throws QuadrigaStorageException {

        List<ModifyCollaborator> modifyCollaborators = new ArrayList<ModifyCollaborator>();

        List<IProjectCollaborator> collaborators = projCollabManager.getProjectCollaborators(projectId);
        IUser user;

        if (collaborators != null)
            for (IProjectCollaborator projectCollaborator : collaborators) {
                ModifyCollaborator modifyCollab = new ModifyCollaborator();
                user = projectCollaborator.getCollaborator().getUserObj();
                modifyCollab.setUserName(user.getUserName());
                modifyCollab.setName(user.getName());
                modifyCollab.setCollaboratorRoles(projectCollaborator.getCollaborator().getCollaboratorRoles());
                modifyCollaborators.add(modifyCollab);
            }

        return modifyCollaborators;
    }

    /**
     * takes dictionary id to return collaborators of ModifyCollaborator domain
     * 
     * @param dictionaryId
     * @return List<ModifyCollaborator> list of modifycollaborator domain
     * @throws QuadrigaStorageException
     */
    public List<ModifyCollaborator> getDictionaryCollaborators(String dictionaryId) throws QuadrigaStorageException {

        List<ModifyCollaborator> modifyCollaborators = new ArrayList<ModifyCollaborator>();
        IUser user;

        // TODO: showCollaboratingUsers() needs to be modified to call mapper
        List<IDictionaryCollaborator> collaborators = dictManager.showCollaboratingUsers(dictionaryId);

        if (collaborators != null) {
            for (IDictionaryCollaborator dictCollaborator : collaborators) {
                ModifyCollaborator modifyCollab = new ModifyCollaborator();
                user = dictCollaborator.getCollaborator().getUserObj();
                modifyCollab.setUserName(user.getUserName());
                modifyCollab.setName(user.getName());
                modifyCollab.setCollaboratorRoles(dictCollaborator.getCollaborator().getCollaboratorRoles());
                modifyCollaborators.add(modifyCollab);
            }

        }
        return modifyCollaborators;
    }

    /**
     * takes collection id to return collaborators of ModifyCollaborator domain
     * 
     * @param collectionid
     * @return List<ModifyCollaborator> list of modifycollaborator domain
     * @throws QuadrigaStorageException
     */
    public List<ModifyCollaborator> getConceptCollectionCollaborators(String collectionId)
            throws QuadrigaStorageException {

        List<ModifyCollaborator> modifyCollaborators = new ArrayList<ModifyCollaborator>();
        IUser user;

        // TODO: showCollaboratingUsers() needs to be modified to call mapper
        List<IConceptCollectionCollaborator> collaborators = collectionManager.showCollaboratingUsers(collectionId);

        for (IConceptCollectionCollaborator ccCollaborator : collaborators) {
            ModifyCollaborator modifyCollab = new ModifyCollaborator();
            user = ccCollaborator.getCollaborator().getUserObj();
            modifyCollab.setUserName(user.getUserName());
            modifyCollab.setName(user.getName());
            modifyCollab.setCollaboratorRoles(ccCollaborator.getCollaborator().getCollaboratorRoles());
            modifyCollaborators.add(modifyCollab);
        }

        return modifyCollaborators;
    }

    public List<ModifyCollaborator> modifyWorkspaceCollaboratorManager(String workspaceId)
            throws QuadrigaStorageException {
        List<ModifyCollaborator> modifyCollaborators = new ArrayList<ModifyCollaborator>();
        IUser user;

        List<IWorkspaceCollaborator> collaborators = wsCollabManager.getWorkspaceCollaborators(workspaceId);

        if (collaborators != null) {
            for (IWorkspaceCollaborator wsCollaborator : collaborators) {
                ModifyCollaborator modifyCollab = new ModifyCollaborator();
                user = wsCollaborator.getCollaborator().getUserObj();
                modifyCollab.setUserName(user.getUserName());
                modifyCollab.setName(user.getName());
                modifyCollab.setCollaboratorRoles(wsCollaborator.getCollaborator().getCollaboratorRoles());
                List<String> roleIds = new ArrayList<String>();
                for (IQuadrigaRole role : modifyCollab.getCollaboratorRoles()) {
                    roleIds.add(role.getId());
                }
                modifyCollab.setRoleIds(roleIds);
                modifyCollaborators.add(modifyCollab);
            }
        }

        return modifyCollaborators;

    }

}
