package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.conceptcollection.IConcept;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptFactory;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ConceptsDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;

@Service
public class ConceptCollectionDTOMapper extends BaseMapper {

    @Autowired
    private IConceptFactory conceptFactory;

    @Autowired
    private ICollaboratorFactory collaboratorFactory;

    @Autowired
    private UserDTOMapper userDTOMapper;

    @Autowired
    private IQuadrigaRoleManager roleManager;

    public IConcept getConcept(ConceptsDTO conceptDTO) {
        IConcept concept = conceptFactory.createConceptObject();
        concept.setConceptId(conceptDTO.getItem());
        concept.setDescription(conceptDTO.getDescription());
        concept.setLemma(conceptDTO.getLemma());
        concept.setPos(conceptDTO.getPos());
        return concept;
    }

    public List<IConcept> getConceptCollectionItemList(List<ConceptsDTO> conceptsList) {
        List<IConcept> conceptList = new ArrayList<IConcept>();
        if (conceptsList != null && conceptsList.size() > 0) {
            Iterator<ConceptsDTO> ccItemsIterator = conceptsList.iterator();
            while (ccItemsIterator.hasNext()) {
                conceptList.add(getConcept(ccItemsIterator.next()));
            }
        }
        return conceptList;
    }

    public ICollaborator getConceptCollectionCollaborators(
            ConceptCollectionCollaboratorDTO conceptCollectionCollaborator) {
        ICollaborator collaborator = null;
        List<IQuadrigaRole> collaboratorRoles = null;

        collaborator = collaboratorFactory.createCollaborator();
        collaboratorRoles = new ArrayList<IQuadrigaRole>();

        QuadrigaUserDTO userName = conceptCollectionCollaborator.getQuadrigaUserDTO();
        String role = conceptCollectionCollaborator.getCollaboratorDTOPK().getCollaboratorrole();

        collaboratorRoles.add(roleManager.getQuadrigaRoleByDbId(IQuadrigaRoleManager.CONCEPT_COLLECTION_ROLES, role));

        collaborator.setUserObj(userDTOMapper.getUser(userName));
        collaborator.setCollaboratorRoles(collaboratorRoles);
        // TODO : add collaborator description

        return collaborator;
    }

    /**
     * 
     * Returns ConceptcollectionsDTO mapped to Conceptcollection
     * 
     * @param conceptCollection
     * @return ConceptcollectionsDTO
     * @author Karthik Jayaraman
     * 
     */
    public ConceptCollectionDTO getConceptCollectionDTO(IConceptCollection conceptCollection)
            throws QuadrigaStorageException {
        ConceptCollectionDTO conceptcollectionsDTO = new ConceptCollectionDTO();
        conceptcollectionsDTO.setUpdatedby(conceptCollection.getOwner().getUserName());
        conceptcollectionsDTO.setUpdateddate(new Date());
        conceptcollectionsDTO.setCreatedby(conceptCollection.getOwner().getUserName());
        conceptcollectionsDTO.setCreateddate(new Date());
        conceptcollectionsDTO.setCollectionname(conceptCollection.getConceptCollectionName());
        conceptcollectionsDTO.setDescription(conceptCollection.getDescription());
        conceptcollectionsDTO.setCollectionname(conceptCollection.getConceptCollectionName());
        conceptcollectionsDTO.setCollectionowner(getUserDTO(conceptCollection.getOwner().getUserName()));
        conceptcollectionsDTO.setAccessibility(Boolean.FALSE);
        return conceptcollectionsDTO;
    }

}
