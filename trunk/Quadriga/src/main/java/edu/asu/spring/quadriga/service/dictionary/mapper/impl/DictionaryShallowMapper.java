package edu.asu.spring.quadriga.service.dictionary.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.dictionary.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.proxy.DictionaryProxy;
import edu.asu.spring.quadriga.domain.proxy.ProjectProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.service.dictionary.mapper.IDictionaryShallowMapper;

public class DictionaryShallowMapper implements IDictionaryShallowMapper {

	@Autowired
	private IDBConnectionDictionaryManager dbConnect;
	
	@Autowired
	private IDictionaryManager dictionaryManager;
	
	@Autowired
	private IUserManager userManager;
	
	@Override
	@Transactional
	public List<IDictionary> getDictionaryList(String userName) throws QuadrigaStorageException {
		
		List<DictionaryDTO> dictionaryDTOList = dbConnect.getDictionaryDTOList(userName);
		
		List<IDictionary> dictionaryList = new ArrayList<IDictionary>();
		if(dictionaryDTOList != null)
		{
			for(DictionaryDTO dictionaryDTO: dictionaryDTOList)
			{
				IDictionary dictionaryProxy = new DictionaryProxy(dictionaryManager);
				dictionaryProxy.setDictionaryName(dictionaryDTO.getDictionaryname());
				dictionaryProxy.setDictionaryId(dictionaryDTO.getDictionaryid());
				dictionaryProxy.setDescription(dictionaryDTO.getDescription());
				dictionaryProxy.setCreatedBy(dictionaryDTO.getCreatedby());
				dictionaryProxy.setCreatedDate(dictionaryDTO.getCreateddate());
				dictionaryProxy.setUpdatedBy(dictionaryDTO.getUpdatedby());
				dictionaryProxy.setUpdatedDate(dictionaryDTO.getUpdateddate());
				dictionaryProxy.setOwner(userManager.getUserDetails(dictionaryDTO.getDictionaryowner().getUsername()));
				dictionaryList.add(dictionaryProxy);
			}
		}
		
		return dictionaryList;
	}

	@Override
	public IDictionary getDictionaryDetails(DictionaryDTO  dictionaryDTO)
			throws QuadrigaStorageException {
		
		IDictionary dictionaryProxy = null;
		
		if(dictionaryDTO != null){
			dictionaryProxy = new DictionaryProxy(dictionaryManager);
			dictionaryProxy.setDictionaryId(dictionaryDTO.getDictionaryid());
			dictionaryProxy.setDictionaryName(dictionaryDTO.getDictionaryname());
			dictionaryProxy.setDescription(dictionaryDTO.getDescription());
			dictionaryProxy.setOwner(userManager.getUserDetails(dictionaryDTO.getDictionaryowner().getUsername()));
			dictionaryProxy.setCreatedBy(dictionaryDTO.getCreatedby());
			dictionaryProxy.setCreatedDate(dictionaryDTO.getCreateddate());
			dictionaryProxy.setUpdatedBy(dictionaryDTO.getUpdatedby());
			dictionaryProxy.setUpdatedDate(dictionaryDTO.getUpdateddate());
		}
		
		return dictionaryProxy;
	}

}
