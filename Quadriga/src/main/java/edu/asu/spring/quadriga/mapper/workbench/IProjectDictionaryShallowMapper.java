package edu.asu.spring.quadriga.mapper.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectDictionaryShallowMapper {
    public List<IDictionary> getDictionaries(IProject project, ProjectDTO projectDTO)
            throws QuadrigaStorageException;

    public List<IProject> getProjectDictionaryList(DictionaryDTO dictionaryDTO, IDictionary dictionary)
            throws QuadrigaStorageException;

}
