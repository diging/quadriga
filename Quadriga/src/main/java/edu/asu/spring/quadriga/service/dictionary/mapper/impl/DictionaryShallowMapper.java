package edu.asu.spring.quadriga.service.dictionary.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.dictionary.IDictionaryDAO;
import edu.asu.spring.quadriga.dao.dictionary.IDBConnectionRetrieveDictionaryManager;
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
	private IDictionaryDAO dbConnect;
	
	@Autowired
	private IDBConnectionRetrieveDictionaryManager dbconnect1;
	
	@Autowired
	private IDictionaryManager dictionaryManager;
	
	@Autowired
	private IUserDeepMapper userDeepMapper;
	
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
				dictionaryProxy.setOwner(userDeepMapper.getUser(dictionaryDTO.getDictionaryowner().getUsername()));
				dictionaryList.add(dictionaryProxy);
			}
		}
		
		return dictionaryList;
	}
	
	@Override
	@Transactional
	public List<IDictionary> getDictionaryListOfCollaborator(String userName) throws QuadrigaStorageException {
		
		List<DictionaryDTO> dictionaryDTOList = dbConnect.getDictionaryCollabOfUser(userName);
		
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
				dictionaryProxy.setOwner(userDeepMapper.getUser(dictionaryDTO.getDictionaryowner().getUsername()));
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
			dictionaryProxy.setOwner(userDeepMapper.getUser(dictionaryDTO.getDictionaryowner().getUsername()));
			dictionaryProxy.setCreatedBy(dictionaryDTO.getCreatedby());
			dictionaryProxy.setCreatedDate(dictionaryDTO.getCreateddate());
			dictionaryProxy.setUpdatedBy(dictionaryDTO.getUpdatedby());
			dictionaryProxy.setUpdatedDate(dictionaryDTO.getUpdateddate());
		}
		
		return dictionaryProxy;
	}


	@Override
	@Transactional
	public IDictionary getDictionaryDetails(String dictionaryId) throws QuadrigaStorageException{
		DictionaryDTO dictionaryDTO = dbconnect1.getDictionaryDTO(dictionaryId);
		IDictionary dictionaryProxy = null;
		if(dictionaryDTO != null){
			dictionaryProxy = new DictionaryProxy(dictionaryManager);
			dictionaryProxy.setDictionaryName(dictionaryDTO.getDictionaryname());
			dictionaryProxy.setDictionaryId(dictionaryDTO.getDictionaryid());
			dictionaryProxy.setDescription(dictionaryDTO.getDescription());
			dictionaryProxy.setCreatedBy(dictionaryDTO.getCreatedby());
			dictionaryProxy.setCreatedDate(dictionaryDTO.getCreateddate());
			dictionaryProxy.setUpdatedBy(dictionaryDTO.getUpdatedby());
			dictionaryProxy.setUpdatedDate(dictionaryDTO.getUpdateddate());
			dictionaryProxy.setOwner(userDeepMapper.getUser(dictionaryDTO.getDictionaryowner().getUsername()));
		}
		return dictionaryProxy;
	}
}
