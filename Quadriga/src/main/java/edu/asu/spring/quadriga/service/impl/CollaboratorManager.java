package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dto.CollaboratingDTO;
import edu.asu.spring.quadriga.dto.CollaboratorDTO;
import edu.asu.spring.quadriga.dto.CollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public abstract class CollaboratorManager<V extends CollaboratorDTO<T, V>, T extends CollaboratorDTOPK,C extends CollaboratingDTO<T, V>,D extends BaseDAO<C>> {
    
    @Transactional
    public void updateCollaborators(String dtoId,String collabUser,String collaboratorRole,String username) throws QuadrigaStorageException
    {
        IBaseDAO<C> dao = getDao();
        
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
                } else {
                    existingRoles.add(collabRole);
                }
            }
        }
        
        //add the new roles to the collaborator
        QuadrigaUserDTO user = dao.getUserDTO(collabUser);
        
        for(String role : newCollaboratorRoles) {
            if(!existingRoles.contains(role)) {
                Date date = new Date();
                V collaboratorDto = createNewDTO();
                T collaboratorKey = createNewDTOPK(dtoId,collabUser,role);
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
        dao.updateDTO(dto);
        
    }
    
    public abstract V createNewDTO();
    public abstract T createNewDTOPK(String id, String collabUser, String role);
    
    public abstract IBaseDAO<C> getDao();
}
