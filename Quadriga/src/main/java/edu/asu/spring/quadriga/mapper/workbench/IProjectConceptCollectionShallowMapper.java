package edu.asu.spring.quadriga.mapper.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ProjectConceptCollectionDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectConceptCollectionShallowMapper {
    public List<IProjectConceptCollection> getProjectConceptCollectionList(
            IProject project, List<ProjectConceptCollectionDTO> projectCCDTOList)
            throws QuadrigaStorageException;

    public List<IProjectConceptCollection> getProjectConceptCollectionList(
            ConceptCollectionDTO ccDTO, IConceptCollection conceptCollection)
            throws QuadrigaStorageException;

}
