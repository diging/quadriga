package edu.asu.spring.quadriga.service.conceptcollection.mapper.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionDAO;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.conceptcollection.IConcept;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionConcepts;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionCollaboratorFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionConceptFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptFactory;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ConceptCollectionItemsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IProjectConceptCollectionShallowMapper;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceCCShallowMapper;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.conceptcollection.mapper.IConceptCollectionDeepMapper;
import edu.asu.spring.quadriga.service.user.mapper.IUserDeepMapper;

@Service
public class ConceptCollectionDeepMapper implements
        IConceptCollectionDeepMapper {
    @Autowired
    private IConceptCollectionDAO dbConnect;

    @Autowired
    private IConceptCollectionFactory ccFactory;

    @Autowired
    private IUserDeepMapper userDeepMapper;

    @Autowired
    private ICollaboratorFactory collaboratorFactory;

    @Autowired
    private IQuadrigaRoleFactory roleFactory;

    @Autowired
    private IQuadrigaRoleManager roleManager;

    @Autowired
    private IConceptCollectionCollaboratorFactory ccCollaboratorFactory;

    @Autowired
    private IProjectConceptCollectionShallowMapper projectCCShallowMapper;

    @Autowired
    private IWorkspaceCCShallowMapper workspaceCCShallowMapper;

    @Autowired
    private IConceptCollectionConceptFactory ccConceptsFactory;

    @Autowired
    private IConceptFactory conceptFactory;

    @Override
    @Transactional
    public IConceptCollection getConceptCollectionDetails(String ccId)
            throws QuadrigaStorageException {
        IConceptCollection conceptCollection = null;
        ConceptCollectionDTO ccDTO = dbConnect.getDTO(ccId);
        if (ccDTO != null) {
            conceptCollection = ccFactory.createConceptCollectionObject();
            fillConceptCollection(conceptCollection, ccDTO);
        }
        return conceptCollection;
    }

    /**
     * This method fills the provided concept collection with the data from the
     * provided concept collection DTO.
     * 
     * @param conceptCollection
     * @param ccDTO
     * @throws QuadrigaStorageException
     */
    @Override
    public void fillConceptCollection(IConceptCollection conceptCollection,
            ConceptCollectionDTO ccDTO) throws QuadrigaStorageException {
        conceptCollection
                .setConceptCollectionId(ccDTO.getConceptCollectionid());
        conceptCollection.setConceptCollectionName(ccDTO.getCollectionname());
        conceptCollection.setDescription(ccDTO.getDescription());
        conceptCollection.setCreatedBy(ccDTO.getCreatedby());
        conceptCollection.setCreatedDate(ccDTO.getCreateddate());
        conceptCollection.setUpdatedBy(ccDTO.getUpdatedby());
        conceptCollection.setUpdatedDate(ccDTO.getUpdateddate());
        conceptCollection.setOwner(userDeepMapper.getUser(ccDTO
                .getCollectionowner().getUsername()));

        // Setting dictionary collaborator
        conceptCollection
                .setConceptCollectionCollaborators(getConceptCollectionCollaboratorList(
                        ccDTO, conceptCollection));
        // Setting dictionary Projects
        conceptCollection.setConceptCollectionProjects(projectCCShallowMapper
                .getProjectConceptCollectionList(ccDTO, conceptCollection));
        // Setting dictionary Workspaces
        conceptCollection
                .setConceptCollectionWorkspaces(workspaceCCShallowMapper
                        .getWorkspaceConceptCollectionList(conceptCollection,
                                ccDTO));
        // Setting dictionary Items
        conceptCollection
                .setConceptCollectionConcepts(getConceptCollectionConceptsList(
                        ccDTO, conceptCollection));
    }

    public List<IConceptCollectionCollaborator> getConceptCollectionCollaboratorList(
            ConceptCollectionDTO ccDTO, IConceptCollection conceptCollection)
            throws QuadrigaStorageException {
        List<IConceptCollectionCollaborator> ccCollaboratorList = null;
        if (ccDTO.getConceptCollectionCollaboratorDTOList() != null
                && ccDTO.getConceptCollectionCollaboratorDTOList().size() > 0) {
            HashMap<String, IConceptCollectionCollaborator> userCCCollaboratorMap = mapUserCCCollaborator(
                    ccDTO, conceptCollection);
            for (String userID : userCCCollaboratorMap.keySet()) {
                if (ccCollaboratorList == null) {
                    ccCollaboratorList = new ArrayList<IConceptCollectionCollaborator>();
                }
                ccCollaboratorList.add(userCCCollaboratorMap.get(userID));
            }
        }
        return ccCollaboratorList;
    }

    public HashMap<String, IConceptCollectionCollaborator> mapUserCCCollaborator(
            ConceptCollectionDTO ccDTO, IConceptCollection conceptCollection)
            throws QuadrigaStorageException {

        HashMap<String, IConceptCollectionCollaborator> userCCCollaboratorMap = new HashMap<String, IConceptCollectionCollaborator>();

        for (ConceptCollectionCollaboratorDTO ccCollaboratorDTO : ccDTO
                .getConceptCollectionCollaboratorDTOList()) {
            String userName = ccCollaboratorDTO.getQuadrigaUserDTO()
                    .getUsername();

            if (userCCCollaboratorMap.containsKey(userName)) {
                String roleName = ccCollaboratorDTO.getCollaboratorDTOPK()
                        .getCollaboratorrole();

                IQuadrigaRole collaboratorRole = roleManager
                        .getQuadrigaRoleByDbId(
                                IQuadrigaRoleManager.CONCEPT_COLLECTION_ROLES,
                                roleName);
                // collaboratorRoleFactory.createCollaboratorRoleObject();
                // collaboratorRole.setRoleDBid(roleName);
                // collaboratorRole.setDisplayName(collaboratorRoleManager.getProjectCollaboratorRoleByDBId(roleName));
                roleManager.fillQuadrigaRole(
                        IQuadrigaRoleManager.CONCEPT_COLLECTION_ROLES,
                        collaboratorRole);

                IConceptCollectionCollaborator ccCollaborator = userCCCollaboratorMap
                        .get(userName);

                ICollaborator collaborator = ccCollaborator.getCollaborator();
                collaborator.getCollaboratorRoles().add(collaboratorRole);

                // Checking if there is a update latest then previous update
                // date
                if (ccCollaboratorDTO.getUpdateddate().compareTo(
                        ccCollaborator.getUpdatedDate()) > 0) {
                    ccCollaborator.setUpdatedBy(ccCollaboratorDTO
                            .getUpdatedby());
                    ccCollaborator.setUpdatedDate(ccCollaboratorDTO
                            .getUpdateddate());
                }

            } else {
                String roleName = ccCollaboratorDTO.getCollaboratorDTOPK()
                        .getCollaboratorrole();
                // Prepare collaborator roles
                IQuadrigaRole collaboratorRole = roleManager
                        .getQuadrigaRoleByDbId(
                                IQuadrigaRoleManager.CONCEPT_COLLECTION_ROLES,
                                roleName);

                roleManager.fillQuadrigaRole(
                        IQuadrigaRoleManager.CONCEPT_COLLECTION_ROLES,
                        collaboratorRole);
                // Create a Collaborator Role list
                List<IQuadrigaRole> collaboratorRoleList = new ArrayList<IQuadrigaRole>();
                // Add collaborator role to the list
                collaboratorRoleList.add(collaboratorRole);
                // Create a Collaborator
                ICollaborator collaborator = collaboratorFactory
                        .createCollaborator();
                // Set Collaborator Role List to the Collaborator
                collaborator.setCollaboratorRoles(collaboratorRoleList);
                collaborator.setUserObj(userDeepMapper.getUser(userName));

                // Create COncept Collection Collaborator object
                IConceptCollectionCollaborator ccCollaborator = ccCollaboratorFactory
                        .createConceptCollectionCollaboratorObject();
                ccCollaborator.setCollaborator(collaborator);
                ccCollaborator.setCreatedBy(ccCollaboratorDTO.getCreatedby());
                ccCollaborator.setCreatedDate(ccCollaboratorDTO
                        .getCreateddate());
                ccCollaborator.setUpdatedBy(ccCollaboratorDTO.getUpdatedby());
                ccCollaborator.setUpdatedDate(ccCollaboratorDTO
                        .getUpdateddate());
                ccCollaborator.setConceptCollection(conceptCollection);

                userCCCollaboratorMap.put(userName, ccCollaborator);

            }
        }
        return userCCCollaboratorMap;
    }

    public List<IConceptCollectionConcepts> getConceptCollectionConceptsList(
            ConceptCollectionDTO conceptColelctionDTO,
            IConceptCollection conceptCollection) {
        List<IConceptCollectionConcepts> ccItemList = new ArrayList<IConceptCollectionConcepts>();
        ;
        List<ConceptCollectionItemsDTO> conceptCollectionItemsDTOList = conceptColelctionDTO
                .getConceptCollectionItemsDTOList();

        if (conceptCollectionItemsDTOList != null) {
            for (ConceptCollectionItemsDTO ccItemsDTO : conceptCollectionItemsDTOList) {
                IConcept concept = conceptFactory.createConceptObject();

                // TODO : we need to fill this
                concept.setLemma(ccItemsDTO.getConceptDTO().getLemma());
                concept.setPos(ccItemsDTO.getConceptDTO().getPos());
                concept.setConceptId(ccItemsDTO
                        .getConceptCollectionItemsDTOPK().getConcept());
                concept.setDescription(ccItemsDTO.getConceptDTO()
                        .getDescription());

                IConceptCollectionConcepts ccCocnepts = ccConceptsFactory
                        .createConceptCollectionConceptsObject();
                ccCocnepts.setCreatedDate(ccItemsDTO.getCreateddate());
                ccCocnepts.setUpdatedDate(ccItemsDTO.getUpdateddate());
                ccCocnepts.setConceptCollection(conceptCollection);
                ccCocnepts.setConcept(concept);
                ccItemList.add(ccCocnepts);
            }
        }

        return ccItemList;
    }

}
