package edu.asu.spring.quadriga.service.impl.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.dao.dictionary.IDictionaryDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectDictionaryDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDictionaryDAO;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryCollaborator;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryItems;
import edu.asu.spring.quadriga.domain.factory.impl.dictionary.DictionaryItemFactory;
import edu.asu.spring.quadriga.domain.impl.WordpowerReply;
import edu.asu.spring.quadriga.domain.impl.dictionary.Item;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTO;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
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

    private static final Logger logger = LoggerFactory.getLogger(DictionaryManager.class);

    @Autowired
    private IDictionaryDAO dictDao;

    @Autowired
    private DictionaryItemFactory dictionaryItemsFactory;

    @Autowired
    private IProjectDictionaryDAO connectProjectDictionary;

    @Autowired
    private IRetrieveProjectManager retrieveProjectManager;

    @Autowired
    private IWorkspaceDictionaryDAO connectWorkspaceDictionary;

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
        return wordPowerURL + "" + searchWordPowerURLPath;
    }

    /**
     * Gets the updateFromWordPowerURL
     * 
     * @return String updateFromWordPowerURL
     */
    public String getUpdateFromWordPowerURL() {
        return wordPowerURL + "" + updateFromWordPowerURLPath;
    }

    /**
     * Method to retrieve all dictionaries a user is the owner of.
     * 
     * @return Return to list dictionary to controller
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public List<IDictionary> getDictionariesList(String userId) throws QuadrigaStorageException {
        List<DictionaryDTO> dictionaryDTOList = dictDao.getDictionaryDTOList(userId);
        return dictShallowMapper.getDictionaryList(dictionaryDTOList);

    }

    @Override
    @Transactional
    public List<IDictionary> getNonAssociatedProjectDictionaries(String projectId) throws QuadrigaStorageException {
        List<DictionaryDTO> dictionaryDTOList = dictDao.getNonAssociatedProjectDictionaries(projectId);
        return dictShallowMapper.getNonAssociatedProjectDictionaries(dictionaryDTOList);
    }

    /**
     * Checks for user permission on dictionary
     * 
     * @param userId
     * @param dicitonaryId
     * @return
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @Override
    @Transactional
    public boolean userDictionaryPerm(String userId, String dicitonaryId)
            throws QuadrigaStorageException, QuadrigaAccessException {
        boolean result = dictDao.userDictionaryPerm(userId, dicitonaryId);
        if (result == false) {
            throw new QuadrigaAccessException();
        }
        return result;
    }

    /**
     * Checks for user permission on dictionary
     * 
     * @param userId
     * @param dicitonaryId
     * @return
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public List<IDictionary> getDictionaryCollabOfUser(String userId) throws QuadrigaStorageException {
        List<DictionaryDTO> dictionaryDTOList = dictDao.getDictionaryCollabOfUser(userId);
        return dictShallowMapper.getDictionaryListOfCollaborator(dictionaryDTOList);
    }

    @Override
    @Transactional
    public List<String> getDictionaryCollaboratorRoles(String userId, String dicitonaryId)
            throws QuadrigaStorageException {
        DictionaryDTO dictionary = dictDao.getDTO(dicitonaryId);
        List<String> roles = new ArrayList<String>();
        List<DictionaryCollaboratorDTO> dictionaryCollaborators = dictionary.getDictionaryCollaboratorDTOList();

        for (DictionaryCollaboratorDTO collaborator : dictionaryCollaborators) {
            if (collaborator.getQuadrigaUserDTO().getUsername().equals(userId)) {
                roles.add(collaborator.getDictionaryCollaboratorDTOPK().getCollaboratorrole());
            }
        }

        return roles;
    }

    /**
     * Adds a new dictionaries for the user
     * 
     * @return Return to success or error msg to controller
     */

    @Override
    @Transactional
    public void addNewDictionary(IDictionary dictionary) throws QuadrigaStorageException {
        dictDao.addDictionary(dictionary);
    }

    /**
     * Add a new dictionary item to dictionary of the user
     * 
     * @return Return to success or error message to controller
     */

    @Override
    @Transactional
    public void addNewDictionariesItems(String dictionaryId, String item, String id, String pos, String owner)
            throws QuadrigaStorageException {
        dictDao.addDictionaryItems(dictionaryId, item, id, pos, owner);
    }

    /**
     * Delete the dictionary item from the dictionary of the user
     * 
     * @return Return success or error message to controller
     */
    @Override
    @Transactional
    public void deleteDictionariesItems(String dictionaryId, String itemid, String ownerName)
            throws QuadrigaStorageException {
        dictDao.deleteDictionaryItems(dictionaryId, itemid, ownerName);
    }

    @Override
    @Transactional
    public void deleteDictionaryItemsCollab(String dictionaryId, String itemid) throws QuadrigaStorageException {
        dictDao.deleteDictionaryItemsCollab(dictionaryId, itemid);
    }

    /**
     * Update the dictionary item of the dictionary from the word power
     * 
     * @return Return error or success message to controller
     */
    @Override
    @Transactional
    public void updateDictionariesItems(String dictionaryId, String termid, String term, String pos)
            throws QuadrigaStorageException {
        dictDao.updateDictionaryItems(dictionaryId, termid, term, pos);
    }

    
    /**
     * Retrieves the list of entries in a dictionary.
     * 
     * @return Return to list of dictionary item to controller
     */
    @Override
    @Transactional
    public List<IDictionaryItems> getDictionaryItems(String dictionaryid)
            throws QuadrigaStorageException {
        DictionaryDTO dictionaryDTO = dictDao.getDTO(dictionaryid);
        
        IDictionary dictionary = dictDeepMapper.getDictionaryDetails(dictionaryDTO);
        return dictionary.getDictionaryItems();
    }

    @Override
    @Transactional
    public List<IDictionaryItems> getDictionaryItemsDetailsCollab(String dictionaryid) throws QuadrigaStorageException {
        DictionaryDTO dictionaryDTO = dictDao.getDTO(dictionaryid);
        IDictionary dictionary = dictDeepMapper.getDictionaryDetails(dictionaryDTO);
        return dictionary.getDictionaryItems();
    }

    /**
     * Gets dictionary name of the dictionary from dictionary ID
     * 
     * @return Return the dictionary name to controller
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public String getDictionaryName(String dictionaryid) {
        DictionaryDTO dictionary = dictDao.getDTO(dictionaryid);
        return dictionary.getDictionaryname();
    }

    /**
     * This method retrieves the dictionary owner for the given dictionary id
     * 
     * @param dictionaryid
     *            - dictionary id
     * @return String - dictionary owner associated with the given dictionary id
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public String getDictionaryOwner(String dictionaryid) {
        DictionaryDTO dict = dictDao.getDTO(dictionaryid);
        return dict.getDictionaryowner().getUsername();
    }

    /**
     * Call the word power for a term and fetch the xml from word power rest
     * 
     * @return Return the dictionaryEntry bean to controller
     */
    @Override
    @Transactional
    public List<WordpowerReply.DictionaryEntry> searchWordPower(String item, String pos) {

        String fullUrl = getSearchWordPowerURL() + "" + item + "/" + pos;
        logger.debug("Search Word Power URL : " + fullUrl);
        WordpowerReply wordpowerReply = (WordpowerReply) restTemplate.getForObject(fullUrl, WordpowerReply.class);
        return wordpowerReply.getDictionaryEntry();
    }

    /**
     * This method deletes the dictionary associated with the given dictionary
     * id.
     * 
     * @param dictionaryId
     *            - dictionary id
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public void deleteDictionary(String dictionaryId) throws QuadrigaStorageException {
        dictDao.deleteDictionary(dictionaryId);
    }

    /**
     * Call the word power for a term and fetch the xml from word power rest
     * 
     * @return Return the dictionaryEntry bean to controller
     */

    public List<WordpowerReply.DictionaryEntry> getUpdateFromWordPower(String dictionaryId, String itemid) {

        logger.debug("Update url from func : " + getUpdateFromWordPowerURL());
        itemid = itemid.substring(itemid.lastIndexOf("/") + 1, itemid.length());
        logger.debug("Update Item ID : " + itemid);
        logger.debug("URL From rest xml : --" + getUpdateFromWordPowerURL() + "--");
        String fullUrl = getUpdateFromWordPowerURL() + "" + itemid;
        logger.debug("Update Word Power URL : " + fullUrl);
        WordpowerReply wordpowerReply = (WordpowerReply) restTemplate.getForObject(fullUrl, WordpowerReply.class);
        return wordpowerReply.getDictionaryEntry();
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
    public void addDictionaryItems(Item dictionartItems, String[] values, String dictionaryId)
            throws QuadrigaStorageException {
        for (int i = 0; i < values.length; i++) {

            Item di = getDictionaryItemIndex(values[i], dictionartItems);
            addNewDictionariesItems(dictionaryId, di.getTerm(), di.getDictionaryItemId(), di.getPos(),
                    getDictionaryOwner(dictionaryId));
        }
    }

    /**
     * Get index of term from a list for update and deleting term from
     * dictionary
     * 
     * @return Return the dictionaryEntry bean to controller
     */
    public Item getDictionaryItemIndex(String termId, Item dictionaryItems) {

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
        Item di = dictionaryItemsFactory.createDictionaryItemObject();
        di.setDictionaryItemId(ids[index]);
        di.setTerm(terms[index]);
        di.setPos(pos[index]);

        return di;
    }

    /**
     * this method used to return collaborators which are present in the current
     * dictionary
     * 
     * @param dictionaryid
     * @return List of collaborators
     * @exception QuadrigaStorageException
     */
    @Override
    @Transactional
    public List<IDictionaryCollaborator> showCollaboratingUsers(String dictionaryId) throws QuadrigaStorageException {

        List<IDictionaryCollaborator> dictionaryCollaboratorList = null;
        DictionaryDTO dictionaryDTO = dictDao.getDTO(dictionaryId);
        IDictionary dictionary = dictDeepMapper.getDictionaryDetails(dictionaryDTO);
        if (dictionary != null) {
            dictionaryCollaboratorList = dictionary.getDictionaryCollaborators();
        }
        return dictionaryCollaboratorList;
    }

    /**
     * This method retrieves the dictionary id for the given dictionary name
     * 
     * @param dictName
     *            - dictionary name
     * @return String - dictionary id associated with the given dictionary name
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public String getDictionaryId(String dictName) throws QuadrigaStorageException {
        return dictDao.getDictionaryId(dictName);
    }

    @Override
    @Transactional
    public String getProjectsTree(String userName, String dictionaryId) throws QuadrigaStorageException, JSONException {

        JSONObject core = new JSONObject();

        List<IProject> projectList = retrieveProjectManager.getProjectList(userName);
        JSONArray dataArray = new JSONArray();

        List<IProject> dictProjectList = connectProjectDictionary.getprojectsByDictId(dictionaryId);
        List<IWorkSpace> dictWorkspaceList = connectWorkspaceDictionary.getWorkspaceByDictId(dictionaryId);

        if (projectList == null)
            return "";

        for (IProject project : projectList) {
            JSONObject data = new JSONObject();
            String projectlink = null;
            data.put("id", project.getProjectId());
            data.put("parent", "#");

            if (dictProjectList.contains(project)) {
                projectlink = project.getProjectName();
            } else {
                projectlink = "<a href='#' id='" + project.getProjectId() + "'name= '" + project.getProjectName()
                        + "'onclick='javascript:addDictToProjects(this.id,this.name);'>" + project.getProjectName()
                        + "</a>";
            }

            data.put("text", projectlink);
            dataArray.put(data);

            String wsParent = project.getProjectId();
            List<IWorkSpace> wsList = wsManager.listActiveWorkspace(wsParent, userName);
            if (wsList != null) {
                for (IWorkSpace workSpace : wsList) {

                    JSONObject data1 = new JSONObject();
                    data1.put("id", workSpace.getWorkspaceId());
                    data1.put("parent", wsParent);
                    String wsLink = null;
                    if (dictWorkspaceList.contains(workSpace)) {
                        wsLink = workSpace.getWorkspaceName();
                    } else {

                        wsLink = "<a href ='#' id='" + workSpace.getWorkspaceId() + "' name='"
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
    @Transactional
    public IDictionary getDictionaryDetails(String dictionaryId) throws QuadrigaStorageException {
        DictionaryDTO dictionaryDTO = dictDao.getDTO(dictionaryId);
        return dictDeepMapper.getDictionaryDetails(dictionaryDTO);
    }

    /**
     * This method updates the dictionary details
     * 
     * @param dictionary
     *            - IDictionary object containing dictionary details.
     * @param userName
     *            - logged in user
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public void updateDictionaryDetailsRequest(IDictionary dictionary, String userName)
            throws QuadrigaStorageException {
        dictDao.updateDictionary(dictionary, userName);
    }

}
