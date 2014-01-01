package edu.asu.spring.quadriga.service.impl.editor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.editor.IDBConnectionEditorAccessManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.editor.IEditorAccessManager;

@Service
public class EditorAccessManager implements IEditorAccessManager 
{
	@Autowired
	private IDBConnectionEditorAccessManager dbConnect;
	
	@Override
	@Transactional
	public boolean checkIsEditor(String userName) throws QuadrigaStorageException
	{
		boolean isEditor;
		
		isEditor = false;
		
		dbConnect.chkIsEditor(userName);
		
		return isEditor;
	}
	
	@Override
	@Transactional
	public boolean checkIsNetworkEditor(String networkId,String userName) throws QuadrigaStorageException
	{
		boolean isEditor;
		
		isEditor = false;
		
		dbConnect.chkIsNetworkEditor(networkId, userName);
		
		return isEditor;
	}

}
