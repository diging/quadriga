package edu.asu.spring.quadriga.service.dictionary.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.dictionary.IDictionaryDAO;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.proxy.DictionaryProxy;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.service.dictionary.mapper.IDictionaryShallowMapper;
import edu.asu.spring.quadriga.service.user.mapper.IUserDeepMapper;

@Service
public class DictionaryShallowMapper implements IDictionaryShallowMapper {

	@Autowired
	private IDictionaryDAO dictDao;
	
	@Autowired
	private IDictionaryManager dictionaryManager;
	
	@Autowired
	private IUserDeepMapper userDeepMapper;
	
	@Override
	@Transactional
	public List<IDictionary> getDictionaryList(String userName) throws QuadrigaStorageException {
		
		List<DictionaryDTO> dictionaryDTOList = dictDao.getDictionaryDTOList(userName);
		
		List<IDictionary> dictionaryList = new ArrayList<IDictionary>();
		if(dictionaryDTOList != null) {
			for(DictionaryDTO dictionaryDTO: dictionaryDTOList) {
				dictionaryList.add(createDictionaryProxy(dictionaryDTO));
			}
		}
		
		return dictionaryList;
	}
	
	@Override
    @Transactional
    public List<IDictionary> getNonAssociatedProjectDictionaries(String projectId) throws QuadrigaStorageException {
	    List<DictionaryDTO> dictionaryDTOList = dictDao.getNonAssociatedProjectDictionaries(projectId);
        
        List<IDictionary> dictionaryList = new ArrayList<IDictionary>();
        if(dictionaryDTOList != null) {
            for(DictionaryDTO dictionaryDTO: dictionaryDTOList) {
                dictionaryList.add(createDictionaryProxy(dictionaryDTO));
            }
        }
        
        return dictionaryList;
	}
	
	@Override
	@Transactional
	public List<IDictionary> getDictionaryListOfCollaborator(String userName) throws QuadrigaStorageException {
		
		List<DictionaryDTO> dictionaryDTOList = dictDao.getDictionaryCollabOfUser(userName);
		List<IDictionary> dictionaryList = new ArrayList<IDictionary>();
		if(dictionaryDTOList != null) {
			for(DictionaryDTO dictionaryDTO: dictionaryDTOList) {
				dictionaryList.add(createDictionaryProxy(dictionaryDTO));
			}
		}
		
		return dictionaryList;
	}

    private IDictionary createDictionaryProxy(DictionaryDTO dictionaryDTO)
            throws QuadrigaStorageException {
        IDictionary dictionaryProxy = new DictionaryProxy(dictionaryManager);
        dictionaryProxy.setDictionaryName(dictionaryDTO.getDictionaryname());
        dictionaryProxy.setDictionaryId(dictionaryDTO.getDictionaryid());
        dictionaryProxy.setDescription(dictionaryDTO.getDescription());
        dictionaryProxy.setCreatedBy(dictionaryDTO.getCreatedby());
        dictionaryProxy.setCreatedDate(dictionaryDTO.getCreateddate());
        dictionaryProxy.setUpdatedBy(dictionaryDTO.getUpdatedby());
        dictionaryProxy.setUpdatedDate(dictionaryDTO.getUpdateddate());
        dictionaryProxy.setOwner(userDeepMapper.getUser(dictionaryDTO.getDictionaryowner().getUsername()));
        return dictionaryProxy;
    }

	@Override
	public IDictionary getDictionaryDetails(DictionaryDTO  dictionaryDTO)
			throws QuadrigaStorageException {
	    if (dictionaryDTO == null)
	        return null;
	    return createDictionaryProxy(dictionaryDTO);
	}


	@Override
	@Transactional
	public IDictionary getDictionaryDetails(String dictionaryId) throws QuadrigaStorageException{
		DictionaryDTO dictionaryDTO = dictDao.getDTO(dictionaryId);
		if (dictionaryDTO == null)
            return null;
        return createDictionaryProxy(dictionaryDTO);
	}
}
