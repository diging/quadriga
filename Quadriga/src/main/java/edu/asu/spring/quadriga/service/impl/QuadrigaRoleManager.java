package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;

/**
 * QuadrigaRoleManager is used to read the user roles from context file and
 * create list of QuadrigaRole objects.
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@Service
public class QuadrigaRoleManager implements IQuadrigaRoleManager {

    /** List of QuadrigaRole objects which will store various user roles. */
    @Autowired
    @Qualifier("mainRoles")
    private List<IQuadrigaRole> quadrigaRoles;

    @Autowired
    @Qualifier("projectCollaborator")
    private List<IQuadrigaRole> projectCollabRoles;

    @Autowired
    @Qualifier("ccCollaborator")
    private List<IQuadrigaRole> ccCollabRoles;

    @Autowired
    @Qualifier("dictCollaborator")
    private List<IQuadrigaRole> dictCollabRoles;

    @Autowired
    @Qualifier("workspaceCollaborator")
    private List<IQuadrigaRole> wsCollabRoles;

    private Map<String, List<IQuadrigaRole>> rolesMap;

    @PostConstruct
    public void init() {
        rolesMap = new HashMap<String, List<IQuadrigaRole>>();
        rolesMap.put(MAIN_ROLES, quadrigaRoles);
        rolesMap.put(PROJECT_ROLES, projectCollabRoles);
        rolesMap.put(CONCEPT_COLLECTION_ROLES, ccCollabRoles);
        rolesMap.put(DICT_ROLES, dictCollabRoles);
        rolesMap.put(WORKSPACE_ROLES, wsCollabRoles);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IQuadrigaRole getQuadrigaRoleByDbId(String type, String sQuadrigaRoleDBId) {
        List<IQuadrigaRole> roles = rolesMap.get(type);
        if (roles == null)
            return null;

        for (IQuadrigaRole role : roles) {
            if (role.getDBid().equals(sQuadrigaRoleDBId))
                return role;
        }
        return null;
    }

    @Override
    public IQuadrigaRole getQuadrigaRoleById(String type, String id) {
        List<IQuadrigaRole> roles = rolesMap.get(type);
        if (roles == null)
            return null;

        for (IQuadrigaRole role : roles) {
            if (role.getId().equals(id))
                return role;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getQuadrigaRoleDBId(String type, String sQuadrigaRoleId) {
        List<IQuadrigaRole> roles = rolesMap.get(type);
        if (roles == null)
            return null;

        for (IQuadrigaRole role : roles) {
            if (role.getId().equals(sQuadrigaRoleId))
                return role.getDBid();
        }

        return null;
    }

    /**
     * @description: maps roleDBid of the collaborator roles from database to
     *               xml
     * 
     * @param collaboratorRoleId
     *            incoming collaborator DBid from the database
     * 
     * @author rohit pendbhaje
     * 
     */
    @Override
    public void fillQuadrigaRole(String type, IQuadrigaRole collaboratorRole) {

        List<IQuadrigaRole> roles = rolesMap.get(type);
        if (roles == null)
            return;

        for (IQuadrigaRole role : roles) {
            if (role.getDBid().equals(collaboratorRole.getDBid())) {
                collaboratorRole.setId(role.getId());
                collaboratorRole.setDBid(role.getDBid());
                collaboratorRole.setName(role.getName());
                collaboratorRole.setDescription(role.getDescription());
                collaboratorRole.setDisplayName(role.getDisplayName());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IQuadrigaRole> getQuadrigaRoles(String type) {
        List<IQuadrigaRole> listForType = rolesMap.get(type);
        List<IQuadrigaRole> returnList = new ArrayList<IQuadrigaRole>(listForType);
        return returnList;
    }

    @Override
    public List<IQuadrigaRole> getSelectableQuarigaRoles(String type) {
        List<IQuadrigaRole> selectableRoles = new ArrayList<IQuadrigaRole>();
        for (IQuadrigaRole role : rolesMap.get(type)) {
            if (role.isSelectable())
                selectableRoles.add(role);
        }
        return selectableRoles;
    }

}