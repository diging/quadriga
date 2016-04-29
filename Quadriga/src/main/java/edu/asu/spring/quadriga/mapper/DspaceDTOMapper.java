package edu.asu.spring.quadriga.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factories.impl.DspaceKeysFactory;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dto.DspaceKeysDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Service
public class DspaceDTOMapper extends BaseMapper {
	
	@Autowired
	private DspaceKeysFactory dsapceKeysFactory;

	public IDspaceKeys getIDspaceKeys(DspaceKeysDTO dspaceKeysDTO) throws QuadrigaStorageException
	{
		IDspaceKeys dspaceKeys = dsapceKeysFactory.createDspaceKeysObject();
		dspaceKeys.setPublicKey(dspaceKeysDTO.getDspaceKeysDTOPK().getPublickey());
		dspaceKeys.setPrivateKey(dspaceKeysDTO.getDspaceKeysDTOPK().getPrivatekey());
		return dspaceKeys;
	}
}
