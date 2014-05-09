package edu.asu.spring.quadriga.service.impl.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.dictionary.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectDictionary;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionWorkspaceDictionary;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryCollaborator;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryItems;
import edu.asu.spring.quadriga.domain.factory.impl.dictionary.DictionaryItemFactory;
import edu.asu.spring.quadriga.domain.impl.dictionary.Item;
import edu.asu.spring.quadriga.domain.implementation.WordpowerReply;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.service.dictionary.mapper.IDictionaryDeepMapper;
import edu.asu.spring.quadriga.service.dictionary.mapper.IDictionaryShallowMapper;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;

/**
 * This class acts as a Dictionary manager which adds list of Dictionary words
 * and their descriptions on the dictionary page.
 * 
 * @implements : IDictionaryManager Interface
 * 
 * @Called By : DictionaryController.java
 * 
 * @author : Lohith Dwaraka
 * 
 * 
 */
@Service
public class DictionaryManager implements IDictionaryManager {

	@Autowired
	@Qualifier("restTemplate")
	RestTemplate restTemplate;

	@Autowired
	@Qualifier("wordPowerURL")
	private String wordPowerURL;

	@Autowired
	@Qualifier("searchWordPowerURLPath")
	private String searchWordPowerURLPath;

	@Autowired
	@Qualifier("updateFromWordPowerURLPath")
	private String updateFromWordPowerURLPath;


	private static final Logger logger = LoggerFactory
			.getLogger(DictionaryManager.class);

	@Autowired
	private IDBConnectionDictionaryManager dbConnect;

	@Autowired
	private DictionaryItemFactory dictionaryItemsFactory;

	@Autowired
	private IDBConnectionProjectDictionary connectProjectDictionary;

	@Autowired
	private IRetrieveProjectManager retrieveProjectManager;

	@Autowired
	private IDBConnectionWorkspaceDictionary connectWorkspaceDictionary;

	@Autowired
	private IListWSManager wsManager;

	@Autowired
	private IDictionaryDeepMapper dictDeepMapper;

	@Autowired
	private IDictionaryShallowMapper dictShallowMapper;


	/**
	 * Gets the searchWordPowerURL
	 * 
	 * @return String URL
	 */
	public String getSearchWordPowerURL() {
		return wordPowerURL+""+searchWordPowerURLPath;
	}

	/**
	 * Gets the updateFromWordPowerURL
	 * 
	 * @return String updateFromWordPowerURL
	 */
	public String getUpdateFromWordPowerURL() {
		return wordPowerURL+""+updateFromWordPowerURLPath;
	}

	/**
	 * Gets all the dictionaries of the user
	 * 
	 * @return Return to list dictionary to controller
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public List<IDictionary> getDictionariesList(String userId)
			throws QuadrigaStorageException {

		List<IDictionary> dictionaryList = new ArrayList<IDictionary>();

		try {
			//dictionaryList = dbConnect.getDictionaryOfUser(userId);
			dictionaryList = dictShallowMapper.getDictionaryList(userId);
		} catch (Exception e) {
			logger.error("getDictionariesList",e);
		}

		return dictionaryList;
	}

	//	/**
	//	 * @throws QuadrigaStorageException 
	//	 * 
	//	 * 
	//	 * 
	//	 * 
	//	 */
	//	@Override
	//	public IDictionary getDictionaryDetails(String userName) throws QuadrigaStorageException {
	//		
	//		IDictionary dictionary = dbConnect.getDictionaryDetails(userName);
	//		
	//		return dictionary;
	//	}
	//
	//	


	/**
	 * Checks for user permission on dictionary
	 * @param userId
	 * @param dicitonaryId
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException 
	 */
	@Override
	@Transactional
	public boolean userDictionaryPerm(String userId, String dicitonaryId) throws QuadrigaStorageException, QuadrigaAccessException{
		boolean result=dbConnect.userDictionaryPerm(userId,dicitonaryId);
		if(result == false){
			throw new QuadrigaAccessException();
		}
		return result;
	}

	//	/**
	//	 * Checks for user permission on dictionary
	//	 * @param userId
	//	 * @param dicitonaryId
	//	 * @return
	//	 * @throws QuadrigaStorageException
	//	 * @throws QuadrigaAccessException 
	//	 */
	//	@Override
	//	@Transactional
	//	public List<IDictionary> getDictionaryCollabOfUser(String userId) throws QuadrigaStorageException, QuadrigaAccessException{
	//		List<IDictionary> dictionaryList= dbConnect.getDictionaryCollabOfUser(userId);
	//		return dictionaryList;
	//	}

	/**
	 * Checks for user permission on dictionary
	 * @param userId
	 * @param dicitonaryId
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException 
	 */
	@Override
	@Transactional
	public List<IDictionary> getDictionaryCollabOfUser(String userId) throws QuadrigaStorageException, QuadrigaAccessException{
		List<IDictionary> dictionaryList = dictShallowMapper.getDictionaryListOfCollaborator(userId);
		return dictionaryList;
	}

	@Override
	@Transactional
	public List<String> getDictionaryCollabPerm(String userId,String dicitonaryId) throws QuadrigaStorageException {
		List<String> role=dbConnect.getDictionaryCollaboratorRoles(userId, dicitonaryId);
		return role;
	}
	/**
	 * Adds a new dictionaries for the user
	 * 
	 * @return Return to success or error msg to controller
	 */

	@Override
	@Transactional
	public void addNewDictionary(IDictionary dictionary)
			throws QuadrigaStorageException {

		try {
			dbConnect.addDictionary(dictionary);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Add a new dictionary item to dictionary of the user
	 * 
	 * @return Return to success or error message to controller
	 */

	@Override
	@Transactional
	public void addNewDictionariesItems(String dictionaryId, String item,
			String id, String pos, String owner)
					throws QuadrigaStorageException {
		try {
			dbConnect.addDictionaryItems(dictionaryId, item, id, pos,
					owner);
		} catch (HibernateException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Delete the dictionary item from the dictionary of the user
	 * 
	 * @return Return success or error message to controller
	 */
	@Override
	@Transactional
	public void deleteDictionariesItems(String dictionaryId, String itemid,
			String ownerName) throws QuadrigaStorageException {
		try {
			dbConnect.deleteDictionaryItems(dictionaryId, itemid,
					ownerName);
		} catch (HibernateException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	@Transactional
	public void deleteDictionaryItemsCollab(String dictionaryId, String itemid) throws QuadrigaStorageException {
		try {
			dbConnect.deleteDictionaryItemsCollab(dictionaryId, itemid);
		} catch (HibernateException e) {
			logger.error(e.getMessage());
		}

	}

	/**
	 * Update the dictionary item of the dictionary from the word power
	 * 
	 * @return Return error or success message to controller
	 */
	@Override
	@Transactional
	public void updateDictionariesItems(String dictionaryId, String termid,
			String term, String pos) throws QuadrigaStorageException {
		try {
			dbConnect.updateDictionaryItems(dictionaryId, termid, term,
					pos);
		} catch (HibernateException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Gets all the dictionary item of the dictionary of the user
	 * 
	 * @return Return to list of dictionary item to controller
	 */
	@Override
	@Transactional
	public List<IDictionaryItems> getDictionariesItems(String dictionaryid,
			String ownerName) throws QuadrigaStorageException {

		IDictionary dictionary = dictDeepMapper.getDictionaryDetails(dictionaryid, ownerName);

		List<IDictionaryItems> dictionaryItemList = null;
		try {
			dictionaryItemList = dictionary.getDictionaryItems();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return dictionaryItemList;
	}

	@Override
	@Transactional
	public List<IDictionaryItems> getDictionaryItemsDetailsCollab(String dictionaryid) throws QuadrigaStorageException {


		IDictionary dictionary = dictDeepMapper.getDictionaryDetails(dictionaryid);

		List<IDictionaryItems> dictionaryItemList = null;
		try {
			dictionaryItemList = dictionary.getDictionaryItems();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return dictionaryItemList;
	}

	/**
	 * Gets dictionary name of the dictionary from dictionary ID
	 * 
	 * @return Return the dictionary name to controller
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public String getDictionaryName(String dictionaryid)
			throws QuadrigaStorageException {

		String dictionaryName = "";
		try {
			dictionaryName = dbConnect.getDictionaryName(dictionaryid);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return dictionaryName;
	}

	/**
	 * This method retrieves the dictionary owner for the given dictionary id
	 * @param dictionaryid - dictionary id
	 * @return String - dictionary owner associated with the given dictionary id
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public String getDictionaryOwner(String dictionaryid)
			throws QuadrigaStorageException {

		String dictionaryOwner = "";
		try {
			dictionaryOwner = dbConnect.getDictionaryOwner(dictionaryid);
			logger.info(" checking --- "+dictionaryOwner);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return dictionaryOwner;
	}

	/**
	 * Call the word power for a term and fetch the xml from word power rest
	 * 
	 * @return Return the dictionaryEntry bean to controller
	 */
	@Override
	@Transactional
	public List<WordpowerReply.DictionaryEntry> searchWordPower(String item,
			String pos) {

		List<WordpowerReply.DictionaryEntry> dictionaryEntry = null;
		try {

			String fullUrl = getSearchWordPowerURL() + "" + item + "/" + pos;
			logger.debug("Search Word Power URL : " + fullUrl);
			WordpowerReply wordpowerReply = (WordpowerReply) restTemplate
					.getForObject(fullUrl, WordpowerReply.class);
			dictionaryEntry = wordpowerReply.getDictionaryEntry();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return dictionaryEntry;
	}

	/**
	 * This method deletes the dictionary associated with the given dictionary id.
	 * @param user - logged in user
	 * @param dictionaryId - dictionary id
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public void deleteDictionary(String user, String dictionaryId)
			throws QuadrigaStorageException {

		try {
			logger.debug("deleting from dictionary manager");
			dbConnect.deleteDictionary(user, dictionaryId);
		} catch (HibernateException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Call the word power for a term and fetch the xml from word power rest
	 * 
	 * @return Return the dictionaryEntry bean to controller
	 */

	public List<WordpowerReply.DictionaryEntry> getUpdateFromWordPower(
			String dictionaryId, String itemid) {

		List<WordpowerReply.DictionaryEntry> dictionaryEntry = null;
		try {
			logger.debug("Update url from func : " + getUpdateFromWordPowerURL());
			itemid = itemid.substring(itemid.lastIndexOf("/") + 1,
					itemid.length());
			logger.debug("Update Item ID : " + itemid);
			logger.debug("URL From rest xml : --" + getUpdateFromWordPowerURL()
					+ "--");
			String fullUrl = getUpdateFromWordPowerURL() + "" + itemid;
			logger.debug("Update Word Power URL : " + fullUrl);
			WordpowerReply wordpowerReply = (WordpowerReply) restTemplate
					.getForObject(fullUrl, WordpowerReply.class);
			dictionaryEntry = wordpowerReply.getDictionaryEntry();
		} catch (Exception e) {

			logger.error(e.getMessage());
		}
		return dictionaryEntry;
	}

	/**
	 * Adding dictionary items for a dictionaryId
	 * 
	 * @param dictionartItems
	 * @param values
	 * @param dictionaryId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public void addDictionaryItems(Item dictionartItems, String [] values,String dictionaryId) throws QuadrigaStorageException{
		for (int i = 0; i < values.length; i++) {

			Item di = getDictionaryItemIndex(
					values[i], dictionartItems);
			addNewDictionariesItems(dictionaryId,
					di.getTerm(), di.getDictionaryItemId(), di.getPos(), getDictionaryOwner(dictionaryId));
		}
	}


	/**
	 * Get index of term from a list for update and deleting term from
	 * dictionary
	 * 
	 * @return Return the dictionaryEntry bean to controller
	 */
	public Item getDictionaryItemIndex(String termId,
			Item dictionaryItems) {

		String terms[] = dictionaryItems.getTerm().split(",");
		String ids[] = dictionaryItems.getDictionaryItemId().split(",");
		String pos[] = dictionaryItems.getPos().split(",");
		int index = -1;
		if (ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				if (ids[i].equals(termId)) {
					index = i;
					i = ids.length;
				}
			}
		}
		Item di = dictionaryItemsFactory
				.createDictionaryItemObject();
		di.setDictionaryItemId(ids[index]);
		di.setTerm(terms[index]);
		di.setPos(pos[index]);

		return di;
	}

	//	/**
	//	 * this method used to return collaborators which are present in the current dictionary
	//	 * 
	//	 * @param		dictionaryid
	//	 * @return		List of collaborators
	//	 * @exception 	QuadrigaStorageException	
	//	 */
	//	@Override
	//	@Transactional
	//	public List<ICollaborator> showCollaboratingUsers(String dictionaryid) {
	//		
	//		List<ICollaborator> collaborators = null;
	//		try {
	//			
	//		collaborators = dbConnect.showCollaboratingUsersRequest(dictionaryid);
	//		
	//		} catch (QuadrigaStorageException e) {
	//			e.printStackTrace();
	//		}
	//	
	//		return collaborators;
	//	}
	//	
	/**
	 * this method used to return collaborators which are present in the current dictionary
	 * 
	 * @param		dictionaryid
	 * @return		List of collaborators
	 * @exception 	QuadrigaStorageException	
	 */
	@Override
	@Transactional
	public List<IDictionaryCollaborator> showCollaboratingUsers(String dictionaryId) throws QuadrigaStorageException {

		List<IDictionaryCollaborator>  dictionaryCollaboratorList =  null;
		IDictionary dictionary =  dictDeepMapper.getDictionaryDetails(dictionaryId);
		if(dictionary != null){
			dictionaryCollaboratorList = dictionary.getDictionaryCollaborators(); 
		}
		return dictionaryCollaboratorList;
	}

	/**
	 * this method used to return collaborators which are not present in the current dictionary
	 * 
	 * @param		dictionaryid
	 * @return		List of users
	 * @exception 	QuadrigaStorageException	
	 */
	@Override
	@Transactional
	public List<IUser> showNonCollaboratingUsers(String dictionaryid) {

		List<IUser> nonCollabUsers = null;
		try {
			nonCollabUsers = dbConnect.showNonCollaboratingUsersRequest(dictionaryid);
		} catch (QuadrigaStorageException e) {
			e.printStackTrace();
		}
		return nonCollabUsers;
	}

	/**
	 * this method used to add collaborators in the current dictionary
	 * 
	 * @param		dictionaryid
	 * @return		List of users
	 * @exception 	QuadrigaStorageException	
	 */
	@Override
	@Transactional
	public void addCollaborators(ICollaborator collaborator, String dictionaryid, String userName, String sessionUser) {
		try {
			dbConnect.addCollaborators(collaborator, dictionaryid, userName, sessionUser);

		} catch (QuadrigaStorageException e) {
			e.printStackTrace();
		}
	}


	/**
	 * this method used to delete collaborators in the current dictionary
	 * 
	 * @param dictionaryid
	 * @param userName			current session user
	 * @return List of users
	 * @exception 	QuadrigaStorageException	
	 */
	@Override
	@Transactional
	public void deleteCollaborator(String dictionaryid, String userName) throws QuadrigaStorageException {

		dbConnect.deleteCollaborators(dictionaryid, userName);

	}

	/**
	 * This method retrieves the dictionary id for the given dictionary name
	 * @param dictName - dictionary name
	 * @return String - dictionary id associated with the given dictionary name
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public String getDictionaryId(String dictName) throws QuadrigaStorageException
	{
		String dictId = dbConnect.getDictionaryId(dictName);
		return dictId;
	}


	@Override
	@Transactional
	public String getProjectsTree(String userName, String dictionaryId) throws QuadrigaStorageException, JSONException {

		JSONObject core = new JSONObject();

		List<IProject> projectList = retrieveProjectManager.getProjectList(userName);
		JSONArray dataArray = new JSONArray();

		List<IProject> dictProjectList = connectProjectDictionary.getprojectsByDictId(dictionaryId);
		List<IWorkSpace> dictWorkspaceList = connectWorkspaceDictionary.getWorkspaceByDictId(dictionaryId);

		for(IProject project : projectList)
		{
			JSONObject data = new JSONObject();
			String projectlink = null;
			data.put("id", project.getProjectId());
			data.put("parent", "#");

			if(dictProjectList.contains(project))
			{
				projectlink = project.getProjectName();
			}
			else
			{
				projectlink = "<a href='#' id='"
						+project.getProjectId()
						+"'name= '"
						+project.getProjectName()
						+"'onclick='javascript:addDictToProjects(this.id,this.name);'>"
						+project.getProjectName()
						+"</a>";
			}

			data.put("text", projectlink);
			dataArray.put(data);

			String wsParent = project.getProjectId();
			List<IWorkSpace> wsList = wsManager.listActiveWorkspace(wsParent, userName);
			if(wsList != null){
				for(IWorkSpace workSpace: wsList)
				{

					JSONObject data1 = new JSONObject();
					data1.put("id", workSpace.getWorkspaceId());
					data1.put("parent", wsParent);
					String wsLink = null;
					if(dictWorkspaceList.contains(workSpace))
					{
						wsLink = workSpace.getWorkspaceName();
					}
					else{

						wsLink = "<a href ='#' id='"
								+ workSpace.getWorkspaceId()
								+ "' name='"
								+ workSpace.getWorkspaceName()
								+ "' onclick='javascript:addDictToWorkspace(this.id,this.name);' >"
								+ workSpace.getWorkspaceName() + "</a>";
					}

					data1.put("text", wsLink);
					dataArray.put(data1);
				}
			}
		}

		JSONObject dataList = new JSONObject();
		dataList.put("data", dataArray);

		core.put("core", dataList);

		return core.toString(1);
	}

	@Override
	public IDictionary getDictionaryDetails(String userName)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}



}
