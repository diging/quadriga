package edu.asu.spring.quadriga.dao.sql.impl;

import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface DspaceManagerDAO {

	IDspaceKeys getDspaceKeys(String username) throws QuadrigaStorageException;

	int saveOrUpdateDspaceKeys(IDspaceKeys dspaceKeys, String username)
			throws QuadrigaStorageException;

	int deleteDspaceKeys(String username) throws QuadrigaStorageException;

}
