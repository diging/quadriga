package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dao.ICollaboratorDAO;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.dto.CollaboratingDTO;
import edu.asu.spring.quadriga.dto.CollaboratorDTO;
import edu.asu.spring.quadriga.dto.CollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.UserDTOMapper;
import edu.asu.spring.quadriga.service.ICollaboratorManager;

public abstract class CollaboratorManager<V extends CollaboratorDTO<T, V>, T extends CollaboratorDTOPK,C extends CollaboratingDTO<T, V>,D extends BaseDAO<C>> implements ICollaboratorManager {
    
	@Autowired
	private UserDTOMapper userMapper;
	
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.service.impl.ICollaboratorManager#updateCollaborators(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public void updateCollaborators(String dtoId,String collabUser,String collaboratorRole,String username) throws QuadrigaStorageException {
        IBaseDAO<C> dao = getDao();
        IBaseDAO<V> collaboratorDao = getCollaboratorDao();
        
        C dto = dao.getDTO(dtoId);
        if(dto == null) {
            return;
        }
        
        List<V> collaboratorList = dto.getCollaboratorList();
        List<String> newCollaboratorRoles = Arrays.asList(collaboratorRole.split(","));
        List<String> existingRoles = new ArrayList<String>();
        
        //remove the user roles which are not associated with the input selection
        Iterator<V> iterator = collaboratorList.iterator();
        while(iterator.hasNext()) {
            V projectCollaborator = iterator.next();
            T collaboratorKey = projectCollaborator.getCollaboratorDTOPK();
            String collaborator = projectCollaborator.getQuadrigaUserDTO().getUsername();
            String collabRole = collaboratorKey.getCollaboratorrole();
            if(collaborator.equals(collabUser)) {
                if(!newCollaboratorRoles.contains(collabRole)) {
                    iterator.remove();
                    collaboratorDao.deleteDTO(projectCollaborator);
                } else {
                    existingRoles.add(collabRole);
                }
            }
        }
        
        //add the new roles to the collaborator
        QuadrigaUserDTO user = dao.getUserDTO(collabUser);
        Date date = new Date();
        
        for(String role : newCollaboratorRoles) {
            if(!existingRoles.contains(role)) {
                V collaboratorDto = createNewCollaboratorDTO();
                T collaboratorKey = createNewCollaboratorDTOPK(dtoId,collabUser,role);
                collaboratorDto.setRelatedDTO(dto);
                collaboratorDto.setCollaboratorDTOPK(collaboratorKey);
                collaboratorDto.setQuadrigaUserDTO(user);
                collaboratorDto.setCreatedby(username);
                collaboratorDto.setCreateddate(date);
                collaboratorDto.setUpdatedby(username);
                collaboratorDto.setUpdateddate(date);
                collaboratorList.add(collaboratorDto);
            }
        }
        
        dto.setCollaboratorList(collaboratorList);
        dto.setUpdatedby(username);
        dto.setUpdateddate(date);
        dao.updateDTO(dto);    
    }
    
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.service.impl.ICollaboratorManager#deleteCollaborators(java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public void deleteCollaborators(String collaboratorListAsString,String dtoId) throws QuadrigaStorageException {
        IBaseDAO<C> dao = getDao();
        IBaseDAO<V> collaboratorDao = getCollaboratorDao();
        
        C dto = dao.getDTO(dtoId);
        if(dto == null) {
            return;
        }
        
        List<V> collaboratorDtoList = dto.getCollaboratorList();
        if (collaboratorDtoList == null)
            return;
        
        List<String> collaborators = Arrays.asList(collaboratorListAsString.split(","));
        
        Iterator<V> iterator = collaboratorDtoList.iterator();
        while(iterator.hasNext()) {
            V collabDto = iterator.next();
            String userName = collabDto.getCollaboratorDTOPK().getCollaboratoruser();
            if(collaborators.contains(userName)) {
                iterator.remove();
                collaboratorDao.deleteDTO(collabDto);
            }
        }
        
        dao.updateDTO(dto);
    }
    
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.service.impl.ICollaboratorManager#addCollaborator(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public void addCollaborator(String collaboratorName,String collabRoleList,String dtoId,String userAddingCollaborator) throws QuadrigaStorageException
    {
        IBaseDAO<C> dao = getDao();
        
        C dto = dao.getDTO(dtoId);
        if(dto == null) {
            return;
        }
        
        List<V> collaboratorList = dto.getCollaboratorList();
        List<String> collabRoles = Arrays.asList(collabRoleList.split(","));
                
        for(String role: collabRoles) {
            if (role.trim().isEmpty())
                continue;
            V collaborator = createCollaborator(collaboratorName, userAddingCollaborator, dto, role);
            collaboratorList.add(collaborator);
        }
        
        dao.updateDTO(dto);
    }
    
    /**
     * This method adds the given collaborator to the specified DTO.
     * @param collaborator Collaborator to add.
     * @param dtoId Id of the DTO the collaboartor should be added to.
     * @param loggedInUser Username of the user who initiated the process.
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public void addCollaborator(ICollaborator collaborator, String dtoId, String loggedInUser) throws QuadrigaStorageException {
        StringBuffer rolesString = new StringBuffer();
        for (IQuadrigaRole role : collaborator.getCollaboratorRoles()) {
            rolesString.append(role.getDBid() + ",");
        }
        addCollaborator(collaborator.getUserObj().getUserName(), rolesString.toString(), dtoId, loggedInUser);
    }
    
    /**
     * This method transfers ownership from one user to another.
     * @param dtoId The id of the dto, the ownership should be transfered on.
     * @param oldOwner The previous owner of the dto.
     * @param newOwner The new owner of the dto.
     * @param collabRole The role the old owner should have a as collaborator.
     * @throws QuadrigaException If the given old owner is not the owner on the specified
     *      DTO, this exception is thrown.
     */
    @Override
    @Transactional
    public void transferOwnership(String dtoId,
            String oldOwner, String newOwner, String collabRole) throws QuadrigaException {
        
        Date date = new Date();
        IBaseDAO<C> dao = getDao();
        IBaseDAO<V> collaboratorDao = getCollaboratorDao();
        
        C dto = dao.getDTO(dtoId);
        if(dto == null) {
            return;
        }
        
        if (!dto.getOwner().getUsername().equals(oldOwner))
            throw new QuadrigaException("Given owner does not correspond to owner on DTO object.");
        
        // update the owner of the concept collection
        dto.setOwner(dao.getUserDTO(newOwner));
        dto.setUpdatedby(oldOwner);
        dto.setUpdateddate(date);

        // remove the new owner from the collaborator role
        List<V> collaborators = dto.getCollaboratorList();
        Iterator<V> collaboratorIterator = collaborators.iterator();
        
        while (collaboratorIterator.hasNext()) {
            V collaborator = collaboratorIterator.next();
            if (collaborator.getQuadrigaUserDTO().getUsername()
                    .equals(newOwner)) {
                collaboratorIterator.remove();
                collaboratorDao.deleteDTO(collaborator);
            }
        }

        // add the existing owner as collaborator admin role
        V collaborator = createNewCollaboratorDTO();
        collaborator.setRelatedDTO(dto);
        collaborator
                .setCollaboratorDTOPK(createNewCollaboratorDTOPK(
                        dtoId, oldOwner, collabRole));
        collaborator.setQuadrigaUserDTO(dao.getUserDTO(oldOwner));
        collaborator.setCreatedby(oldOwner);
        collaborator.setCreateddate(date);
        collaborator.setUpdatedby(oldOwner);
        collaborator.setUpdateddate(date);
        collaborators.add(collaborator);

        dao.updateDTO(dto);
    }
    
    /**
     * Method to get all users that are not collaborating on a DTO.
     * @param dtoId Id of the DTO for which the users should be retrieved.
     */
    @Override
	@Transactional
    public List<IUser> getUsersNotCollaborating(String dtoId) {
        IBaseDAO<C> dao = getDao();
        C dto = dao.getDTO(dtoId);
        if(dto == null) {
            return null;
        }
        
        List<IUser> users = new ArrayList<IUser>();
        List<QuadrigaUserDTO> collaborator = getCollaboratorDao().getUsersNotCollaborating(dtoId);
        
        for(QuadrigaUserDTO tempCollab : collaborator) {
            if(!dto.getOwner().getUsername().equals(tempCollab.getUsername())) {
            	users.add(userMapper.getUser(tempCollab));
            }
        }
        return users;
    }
    
    /**
     * Method to create a new workspace collaborator DTO object.
     * @param collaboratorName Username of collaborator
     * @param workspaceid  Id of workspace of the new collaborator.
     * @param userName Username of user who added a new collaborator.
     * @param wsDTO    Workspace to which the new collaborator is added to.
     * @param role     Role of the new collaborator.
     * @return a newly created {@link WorkspaceCollaboratorDTO} object
     */
    private V createCollaborator(String collaboratorName, String userName,
            C wsDTO, String role) {
        V collaborator = createNewCollaboratorDTO();
        T collaboratorPK = createNewCollaboratorDTOPK(wsDTO.getId(), collaboratorName, role);
        collaborator.setRelatedDTO(wsDTO);
        collaborator.setCollaboratorDTOPK(collaboratorPK);
        collaborator.setQuadrigaUserDTO(getDao().getUserDTO(collaboratorName));
        collaborator.setCreatedby(userName);
        collaborator.setCreateddate(new Date());
        collaborator.setUpdatedby(userName);
        collaborator.setUpdateddate(new Date());
        return collaborator;
    }
    
    public abstract V createNewCollaboratorDTO();
    public abstract T createNewCollaboratorDTOPK(String id, String collabUser, String role);
    
    public abstract IBaseDAO<C> getDao();
    public abstract ICollaboratorDAO<V> getCollaboratorDao();
}
