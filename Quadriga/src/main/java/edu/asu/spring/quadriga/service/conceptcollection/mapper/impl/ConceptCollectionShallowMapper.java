package edu.asu.spring.quadriga.service.conceptcollection.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionDAO;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.proxy.ConceptCollectionProxy;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.conceptcollection.mapper.IConceptCollectionShallowMapper;
import edu.asu.spring.quadriga.service.user.mapper.IUserDeepMapper;

@Service
public class ConceptCollectionShallowMapper implements
		IConceptCollectionShallowMapper {

	@Autowired
	private IConceptCollectionDAO dbConnect;
	
	@Autowired
	private IConceptCollectionManager ccManager;
	
	@Autowired
	private IUserDeepMapper userDeepMapper;
	
	@Override
	public List<IConceptCollection> getConceptCollectionList(String userName)
			throws QuadrigaStorageException {
      List<ConceptCollectionDTO> ccDTOList = dbConnect.getConceptsOwnedbyUser(userName);
		
		List<IConceptCollection> ccList = new ArrayList<IConceptCollection>();
		if(ccDTOList != null)
		{
			for(ConceptCollectionDTO ccDTO: ccDTOList)
			{
				IConceptCollection ccProxy = new ConceptCollectionProxy(ccManager);
//				ccProxy.setConceptCollectionName(ccDTO.getCollectionname());
//				ccProxy.setConceptCollectionId(ccDTO.getConceptCollectionid());
//				ccProxy.setDescription(ccDTO.getDescription());
//				ccProxy.setCreatedBy(ccDTO.getCreatedby());
//				ccProxy.setCreatedDate(ccDTO.getCreateddate());
//				ccProxy.setUpdatedBy(ccDTO.getUpdatedby());
//				ccProxy.setUpdatedDate(ccDTO.getUpdateddate());
//				ccProxy.setOwner(userManager.getUserDetails(ccDTO.getCollectionowner().getUsername()));
				ccProxy = getConceptCollectionDetails(ccDTO);
				ccList.add(ccProxy);
			}
		}
		
		return ccList;
	}
	@Override
	public List<IConceptCollection> getConceptCollectionListOfCollaborator(String userName)
			throws QuadrigaStorageException {
      List<ConceptCollectionDTO> ccDTOList = dbConnect.getCollaboratedConceptsofUser(userName);
		
		List<IConceptCollection> ccList = new ArrayList<IConceptCollection>();
		if(ccDTOList != null)
		{
			for(ConceptCollectionDTO ccDTO: ccDTOList)
			{
				IConceptCollection ccProxy = new ConceptCollectionProxy(ccManager);
				ccProxy = getConceptCollectionDetails(ccDTO);
				ccList.add(ccProxy);
			}
		}
		
		return ccList;
	}


	@Override
	public IConceptCollection getConceptCollectionDetails(
			ConceptCollectionDTO ccDTO) throws QuadrigaStorageException {
		
		IConceptCollection ccProxy = null;
		
		if(ccDTO != null){
			ccProxy = new ConceptCollectionProxy(ccManager);
			ccProxy.setConceptCollectionId(ccDTO.getConceptCollectionid());
			ccProxy.setConceptCollectionName(ccDTO.getCollectionname());
			ccProxy.setDescription(ccDTO.getDescription());
			ccProxy.setOwner(userDeepMapper.getUser(ccDTO.getCollectionowner().getUsername()));
			ccProxy.setCreatedBy(ccDTO.getCreatedby());
			ccProxy.setCreatedDate(ccDTO.getCreateddate());
			ccProxy.setUpdatedBy(ccDTO.getUpdatedby());
			ccProxy.setUpdatedDate(ccDTO.getUpdateddate());
		}
		
		return ccProxy;
	}

}
