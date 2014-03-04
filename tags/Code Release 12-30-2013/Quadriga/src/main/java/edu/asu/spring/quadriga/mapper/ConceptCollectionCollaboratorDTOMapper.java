package edu.asu.spring.quadriga.mapper;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Service
public class ConceptCollectionCollaboratorDTOMapper extends DAOConnectionManager
{
	@Autowired
	private SessionFactory sessionFactory;
	
	public void getCollaboratorDAO(List<ConceptCollectionCollaboratorDTO> collaboratorList,ICollaborator collaborator,
			String conceptCollectionId,String loggedInUser) throws QuadrigaStorageException
	{
		try
		{
			String collabUser = collaborator.getUserObj().getUserName();
			List<ICollaboratorRole> collaboratorRoles = collaborator.getCollaboratorRoles();
			QuadrigaUserDTO userDTO = getUserDTO(collabUser);
			
			ConceptCollectionDTO conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class,conceptCollectionId);
			for(ICollaboratorRole role : collaboratorRoles)
			{
				ConceptCollectionCollaboratorDTO collaboratorDTO = new ConceptCollectionCollaboratorDTO();
				ConceptCollectionCollaboratorDTOPK collaboratorKey = new ConceptCollectionCollaboratorDTOPK(conceptCollectionId,collabUser,role.getRoleDBid());
				collaboratorDTO.setConceptCollectionDTO(conceptCollection);
				collaboratorDTO.setQuadrigaUserDTO(userDTO);
				collaboratorDTO.setConceptCollectionCollaboratorDTOPK(collaboratorKey);
				collaboratorDTO.setCreatedby(loggedInUser);
				collaboratorDTO.setCreateddate(new Date());
				collaboratorDTO.setUpdatedby(loggedInUser);
				collaboratorDTO.setUpdateddate(new Date());
				collaboratorList.add(collaboratorDTO);
			}
			
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
	}

}