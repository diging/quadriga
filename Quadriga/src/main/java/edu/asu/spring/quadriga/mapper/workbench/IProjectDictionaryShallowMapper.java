package edu.asu.spring.quadriga.mapper.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectDictionary;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectDictionaryShallowMapper {
    public List<IProjectDictionary> getProjectDictionaryList(IProject project,
            ProjectDTO projectDTO) throws QuadrigaStorageException;

    public List<IProjectDictionary> getProjectDictionaryList(
            DictionaryDTO dictionaryDTO, IDictionary dictionary)
            throws QuadrigaStorageException;

}
