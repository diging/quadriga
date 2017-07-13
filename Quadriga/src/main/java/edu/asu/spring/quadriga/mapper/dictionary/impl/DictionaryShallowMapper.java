package edu.asu.spring.quadriga.mapper.dictionary.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.proxy.DictionaryProxy;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.dictionary.IDictionaryShallowMapper;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.service.user.mapper.IUserDeepMapper;

@Service
public class DictionaryShallowMapper implements IDictionaryShallowMapper {

    @Autowired
    private IDictionaryManager dictionaryManager;

    @Autowired
    private IUserDeepMapper userDeepMapper;

    @Override
    @Transactional
    public List<IDictionary> getDictionaryList(List<DictionaryDTO> dictionaryDTOList) throws QuadrigaStorageException {

        List<IDictionary> dictionaryList = new ArrayList<IDictionary>();
        if (dictionaryDTOList != null) {
            for (DictionaryDTO dictionaryDTO : dictionaryDTOList) {
                dictionaryList.add(createDictionaryProxy(dictionaryDTO));
            }
        }

        return dictionaryList;
    }

    @Override
    @Transactional
    public List<IDictionary> getNonAssociatedProjectDictionaries(List<DictionaryDTO> dictionaryDTOList)
            throws QuadrigaStorageException {

        List<IDictionary> dictionaryList = new ArrayList<IDictionary>();
        if (dictionaryDTOList != null) {
            for (DictionaryDTO dictionaryDTO : dictionaryDTOList) {
                dictionaryList.add(createDictionaryProxy(dictionaryDTO));
            }
        }

        return dictionaryList;
    }

    @Override
    @Transactional
    public List<IDictionary> getDictionaryListOfCollaborator(List<DictionaryDTO> dictionaryDTOList)
            throws QuadrigaStorageException {

        List<IDictionary> dictionaryList = new ArrayList<IDictionary>();
        if (dictionaryDTOList != null) {
            for (DictionaryDTO dictionaryDTO : dictionaryDTOList) {
                dictionaryList.add(createDictionaryProxy(dictionaryDTO));
            }
        }

        return dictionaryList;
    }

    private IDictionary createDictionaryProxy(DictionaryDTO dictionaryDTO) throws QuadrigaStorageException {
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
    public IDictionary getDictionaryDetails(DictionaryDTO dictionaryDTO) throws QuadrigaStorageException {
        if (dictionaryDTO == null)
            return null;
        return createDictionaryProxy(dictionaryDTO);
    }

}
