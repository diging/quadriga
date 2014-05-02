package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectDictionary;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjectManager;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectDictionary;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IProjectDictionaryManager;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectDeepMapper;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectDictionaryShallowMapper;

/**
 * This class acts a service layer to associate {@link IProject} and {@link IDictionary}.
 * 
 * @author Lohith Dwaraka
 *
 */
@Service
public class ProjectDictionaryManager implements IProjectDictionaryManager {

	@Autowired
	private IDBConnectionProjectDictionary dbConnect;
	
	@Autowired
	private IProjectDictionaryShallowMapper projDictShallowMapper;
	
	@Autowired
	private IProjectDeepMapper projDeepMapper;
	
	@Autowired
	private IDBConnectionRetrieveProjectManager projManager;
	
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
	public List<IProjectDictionary> listProjectDictionary(String projectId,
			String userId) throws QuadrigaStorageException {
		IProject project = projDeepMapper.getProjectDetails(projectId);
		ProjectDTO projectDTO = projManager.getProjectDTO(projectId, userId);
		List<IProjectDictionary> dictionaryList = projDictShallowMapper.getProjectDictionaryList(project, projectDTO);
		return dictionaryList;
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