package edu.asu.spring.quadriga.mapper.workbench.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.proxy.DictionaryProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectDictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IProjectDictionaryShallowMapper;
import edu.asu.spring.quadriga.mapper.workbench.IProjectShallowMapper;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;

@Service
public class ProjectDictionaryShallowMapper implements IProjectDictionaryShallowMapper {

    @Autowired
    private IProjectShallowMapper projectShallowMapper;

    @Autowired
    private IDictionaryManager dictionaryManager;

    @Override
    public List<IDictionary> getDictionaries(IProject project, ProjectDTO projectDTO)
            throws QuadrigaStorageException {
        List<IDictionary> dictionaries = new ArrayList<IDictionary>();

        if (project != null && projectDTO != null) {
            List<ProjectDictionaryDTO> projectDictionaryDTOList = projectDTO.getProjectDictionaryDTOList();
            if (projectDictionaryDTOList == null) {
                return dictionaries;
            }

            for (ProjectDictionaryDTO projectDictionaryDTO : projectDictionaryDTOList) {
                IDictionary dictionaryProxy = new DictionaryProxy(dictionaryManager);
                dictionaryProxy.setDictionaryId(projectDictionaryDTO.getDictionary().getDictionaryid());
                dictionaryProxy.setDictionaryName(projectDictionaryDTO.getDictionary().getDictionaryname());
                dictionaryProxy.setDescription(projectDictionaryDTO.getDictionary().getDescription());
                dictionaryProxy.setCreatedBy(projectDictionaryDTO.getDictionary().getCreatedby());
                dictionaryProxy.setCreatedDate(projectDictionaryDTO.getDictionary().getCreateddate());
                dictionaryProxy.setUpdatedBy(projectDictionaryDTO.getDictionary().getUpdatedby());
                dictionaryProxy.setUpdatedDate(projectDictionaryDTO.getDictionary().getUpdateddate());
                dictionaries.add(dictionaryProxy);
            }
        }
        return dictionaries;

    }

    @Override
    public List<IProject> getProjectDictionaryList(DictionaryDTO dictionaryDTO, IDictionary dictionary)
            throws QuadrigaStorageException {
        List<IProject> projects = new ArrayList<IProject>();

        List<ProjectDictionaryDTO> projectDictionaryDTOList = dictionaryDTO.getProjectDictionaryDTOList();
        if (projectDictionaryDTOList == null) {
            return projects;
        }

        for (ProjectDictionaryDTO projectDictionaryDTO : projectDictionaryDTOList) {
            IProject project = projectShallowMapper.getProjectDetails(projectDictionaryDTO.getProject());
            projects.add(project);
        }

        return projects;
    }

}
