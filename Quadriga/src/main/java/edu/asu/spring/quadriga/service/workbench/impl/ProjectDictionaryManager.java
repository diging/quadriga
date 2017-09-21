package edu.asu.spring.quadriga.service.workbench.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workbench.IProjectDictionaryDAO;
import edu.asu.spring.quadriga.dao.workbench.IRetrieveProjectDAO;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IProjectDeepMapper;
import edu.asu.spring.quadriga.mapper.workbench.IProjectDictionaryShallowMapper;
import edu.asu.spring.quadriga.service.workbench.IProjectDictionaryManager;

/**
 * This class acts a service layer to associate {@link IProject} and {@link IDictionary}.
 * 
 * @author Lohith Dwaraka
 *
 */
@Service
public class ProjectDictionaryManager implements IProjectDictionaryManager {

	@Autowired
	private IProjectDictionaryDAO dbConnect;
	
	@Autowired
	private IProjectDictionaryShallowMapper projDictShallowMapper;
	
	@Autowired
	private IProjectDeepMapper projDeepMapper;
	
	@Autowired
	private IRetrieveProjectDAO projManager;
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void addDictionaryToProject(String projectId, String dictionaryId,
			String userId) throws QuadrigaStorageException {
		dbConnect.addProjectDictionary(projectId, dictionaryId, userId);
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<IDictionary> getDictionaries(String projectId) throws QuadrigaStorageException {
		ProjectDTO projectDTO = projManager.getDTO(projectId);
		IProject project = projDeepMapper.getProject(projectDTO);
        return projDictShallowMapper.getDictionaries(project, projectDTO);
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void deleteProjectDictionary(String projectId,String userId,String dictioanaryId)throws QuadrigaStorageException{
		dbConnect.deleteProjectDictionary(projectId, userId, dictioanaryId);
	}
}
