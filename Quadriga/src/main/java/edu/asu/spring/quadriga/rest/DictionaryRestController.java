package edu.asu.spring.quadriga.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.accesschecks.IWSSecurityChecker;
import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.RestAccessPolicies;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryItems;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.factory.dictionary.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.impl.dictionarylist.DictionaryItem;
import edu.asu.spring.quadriga.domain.impl.dictionarylist.DictionaryItemList;
import edu.asu.spring.quadriga.domain.impl.dictionarylist.QuadrigaDictDetailsReply;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IRestMessage;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.service.workbench.IProjectDictionaryManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceDictionaryManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * Controller for dictionary related rest apis exposed to other clients
 * 
 * @author SatyaSwaroop Boddu
 * @author LohithDwaraka
 * 
 */

@Controller
public class DictionaryRestController {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryRestController.class);

    @Autowired
    private IWorkspaceDictionaryManager workspaceDictionaryManager;

    @Autowired
    private IWSSecurityChecker checkWSSecurity;

    @Autowired
    private IUserManager usermanager;

    @Autowired
    private IRestMessage errorMessageRest;

    @Autowired
    private IDictionaryManager dictionaryManager;

    @Autowired
    private IDictionaryFactory dictionaryFactory;

    @Autowired
    private IRestVelocityFactory restVelocityFactory;

    @Autowired
    @Qualifier("updateFromWordPowerURLPath")
    private String updateFromWordPowerURLPath;

    @Autowired
    @Qualifier("wordPowerURL")
    private String wordPowerURL;

    @Autowired
    private IProjectDictionaryManager projectDictionaryManager;

    /**
     * Rest interface for the List Dictionary for the userId
     * http://<<URL>:<PORT>>/quadriga/rest/dictionaries
     * http://localhost:8080/quadriga/rest/dictionaries
     * 
     * @author Lohith Dwaraka
     * @param userId
     * @param model
     * @return
     * @throws RestException
     */
    @RequestMapping(value = "rest/dictionaries", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<String> listDictionaries(ModelMap model, Principal principal, HttpServletRequest req)
            throws RestException {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            VelocityEngine engine = restVelocityFactory.getVelocityEngine();
            engine.init();
            List<IDictionary> dictionaryList = dictionaryManager.getDictionariesList(user.getUsername());
            Template template = engine.getTemplate("velocitytemplates/dictionarylist.vm");
            VelocityContext context = new VelocityContext();
            context.put("list", dictionaryList);
            context.put("url", ServletUriComponentsBuilder.fromContextPath(req).toUriString());
            
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            return new ResponseEntity<String>(writer.toString(), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RestException(404, e);
        } catch (ParseErrorException e) {
            throw new RestException(500, e);
        } catch (MethodInvocationException e) {
            throw new RestException(500, e);
        } catch (Exception e) {
            throw new RestException(500, e);
        }

    }

    /**
     * Rest interface for the List Dictionary for the userId
     * http://<<URL>:<PORT>>/quadriga/rest/workspace/<workspaceID>/dictionaries
     * http://localhost:8080/quadriga/rest/workspace/WS_23048829469196290/
     * dictionaries
     * 
     * @author Lohith Dwaraka
     * @param userId
     * @param model
     * @return
     * @throws RestException
     */
    @RequestMapping(value = "rest/workspace/{workspaceId}/dictionaries", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<String> listWorkspaceDictionaries(@PathVariable("workspaceId") String workspaceId,
            ModelMap model, Principal principal, HttpServletRequest req) throws RestException {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            VelocityEngine engine = restVelocityFactory.getVelocityEngine();
            engine.init();
            List<IDictionary> dictionaryList = workspaceDictionaryManager.getDictionaries(workspaceId,
                    user.getUsername());
            Template template = engine.getTemplate("velocitytemplates/workspacedictionarylist.vm");
            VelocityContext context = new VelocityContext();
            context.put("url", ServletUriComponentsBuilder.fromContextPath(req).toUriString());
            
            context.put("list", dictionaryList);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            return new ResponseEntity<String>(writer.toString(), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RestException(404, e);
        } catch (ParseErrorException e) {
            throw new RestException(500, e);
        } catch (MethodInvocationException e) {
            throw new RestException(500, e);
        } catch (Exception e) {
            throw new RestException(500, e);
        }

    }

    /**
     * Rest interface to get dictionaries related to workspace
     * http://<<URL>:<PORT
     * >>/quadriga/auth/rest/workspace/<workspaceid>/dictionaries.json
     * http://<<URL>:<PORT>>/quadriga/auth/rest/workspace/e23a8585-20bc-
     * 458e-ab7d-c758962b11aa/dictionaries.json
     * 
     * 
     * @author Ajay Modi & Bharath Srikantan
     * @param req
     * @param model
     * @param principal
     * @return
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 2, userRole = {
            RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN, RoleNames.ROLE_WORKSPACE_COLLABORATOR_CONTRIBUTOR }) })
    @RequestMapping(value = "auth/rest/workspace/{workspaceid}/dictionaries.json", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> listWorkspaceDictionaryJson(HttpServletRequest req,
            @PathVariable("workspaceid") String workspaceId, Model model, Principal principal) {
        String userId = principal.getName();

        List<IDictionary> dictionaries = null;

        try {
            dictionaries = workspaceDictionaryManager.getDictionaries(workspaceId, userId);
        } catch (QuadrigaStorageException e) {
            logger.error("QuadrigaStorageException:", e);
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        JSONArray ja = new JSONArray();

        if (dictionaries != null) {
            for (IDictionary dictionary : dictionaries) {
                JSONObject j = new JSONObject();
                try {
                    j.put("id", dictionary.getDictionaryId());
                    j.put("name", dictionary.getDictionaryName());
                    ja.put(j);
                } catch (JSONException e) {
                    logger.error("JSONException:", e);
                    return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }

        return new ResponseEntity<String>(ja.toString(), HttpStatus.OK);
    }

    /**
     * Rest interface for the List Dictionary items for the dictionary Id
     * http://<<URL>:<PORT>>/quadriga/rest/dictionaryDetails/{DictionaryID}
     * http://localhost:8080/quadriga/rest/dictionaryDetails/68
     * 
     * @author Lohith Dwaraka
     * @param dictionaryId
     * @param model
     * @return
     * @throws RestException
     */
    @RequestMapping(value = "rest/dictionaryDetails/{dictionaryId}", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<String> listDictionaryItems(@PathVariable("dictionaryId") String dictionaryId,
            ModelMap model, HttpServletRequest req) throws RestException {

        // TODO details not getting retrieved

        try {
            VelocityEngine engine = restVelocityFactory.getVelocityEngine();
            engine.init();
            logger.debug("Getting dictionary items list for dictionary id : " + dictionaryId);
            List<IDictionaryItems> dictionaryItemsList = dictionaryManager.getDictionaryItems(dictionaryId);
            if (dictionaryItemsList == null) {
                throw new RestException(404);
            }
            Template template = engine.getTemplate("velocitytemplates/dictionaryitemslist.vm");
            VelocityContext context = new VelocityContext();
            context.put("url", ServletUriComponentsBuilder.fromContextPath(req).toUriString());
            
            String updateFromWordPowerURL = wordPowerURL;
            context.put("list", dictionaryItemsList);
            context.put("wordPowerURL", updateFromWordPowerURL);
            context.put("path", updateFromWordPowerURLPath);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            return new ResponseEntity<String>(writer.toString(), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RestException(404, e);
        } catch (ParseErrorException e) {
            throw new RestException(500, e);
        } catch (MethodInvocationException e) {
            throw new RestException(500, e);
        } catch (Exception e) {
            throw new RestException(500, e);
        }
    }

    /**
     * Rest interface add a new dictionary with a list of dictionary
     * http://<<URL>:<PORT>>/quadriga/rest/workspace/<workspaceid>/createdict
     * http://localhost:8080/quadriga/rest/workspace/WS_22992652874022949/
     * createdict
     * 
     * @author Lohith Dwaraka
     * @param userId
     * @param model
     * @return
     * @throws RestException
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     * @throws Exception
     */
    @RestAccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE_REST, paramIndex = 1, userRole = {
            RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR }) })
    @RequestMapping(value = "rest/workspace/{workspaceId}/createdict", method = RequestMethod.POST)
    public ResponseEntity<String> addConceptCollectionsToWorkspace(@PathVariable("workspaceId") String workspaceId,
            HttpServletRequest request, HttpServletResponse response, @RequestBody String xml,
            @RequestHeader("Accept") String accept, ModelMap model, Principal principal) throws RestException,
            QuadrigaStorageException, QuadrigaAccessException {
        IUser user = usermanager.getUser(principal.getName());
        if (!checkWSSecurity.checkIsWorkspaceExists(workspaceId)) {
            String errorMsg = errorMessageRest.getErrorMsg("Workspace ID : " + workspaceId + " doesn't exist", request);
            return new ResponseEntity<String>(errorMsg, HttpStatus.NOT_FOUND);
        }

        String dictName = request.getParameter("name");
        String desc = request.getParameter("desc");

        if (dictName == null || dictName.isEmpty()) {
            String errorMsg = errorMessageRest.getErrorMsg("Please provide dictionary name", request);
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        if (desc == null || desc.isEmpty()) {
            String errorMsg = errorMessageRest.getErrorMsg("Please provide dictionary description", request);
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        logger.debug("XML : " + xml);
        JAXBElement<QuadrigaDictDetailsReply> response1 = null;
        try {

            JAXBContext context = JAXBContext.newInstance(QuadrigaDictDetailsReply.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
            InputStream is = new ByteArrayInputStream(xml.getBytes());
            response1 = unmarshaller.unmarshal(new StreamSource(is), QuadrigaDictDetailsReply.class);
        } catch (JAXBException e) {
            logger.error("Error in unmarshalling", e);
            String errorMsg = errorMessageRest.getErrorMsg("Error in unmarshalling", request);
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }

        QuadrigaDictDetailsReply qReply = response1.getValue();
        DictionaryItemList dictList = qReply.getDictionaryItemsList();
        List<DictionaryItem> dictionaryList = dictList.getDictionaryItems();
        if (dictionaryList.size() < 1) {
            String errorMsg = errorMessageRest.getErrorMsg("Dictionary XML is not valid", request);
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }

        IDictionary dictionary = dictionaryFactory.createDictionaryObject();
        dictionary.setDescription(desc);
        dictionary.setOwner(user);
        dictionary.setDictionaryName(dictName);

        dictionaryManager.addNewDictionary(dictionary);
        String dictId = dictionaryManager.getDictionaryId(dictName);

        Iterator<DictionaryItem> iter = dictionaryList.iterator();

        while (iter.hasNext()) {
            DictionaryItem dicItem = iter.next();
            try {
                dictionaryManager.addNewDictionariesItems(dictId, dicItem.getTerm(), dicItem.getUri(),
                        dicItem.getPos(), user.getUserName());
                dictionaryManager
                        .updateDictionariesItems(dictId, dicItem.getUri(), dicItem.getTerm(), dicItem.getPos());
            } catch (QuadrigaStorageException e) {
                logger.error("Errors in adding items", e);
                String errorMsg = errorMessageRest.getErrorMsg("Failed to add due to DB Error", request);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.valueOf(accept));
                return new ResponseEntity<String>(errorMsg, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        workspaceDictionaryManager.addWorkspaceDictionary(workspaceId, dictId, user.getUserName());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(accept));
        return new ResponseEntity<String>(dictId, httpHeaders, HttpStatus.OK);
    }

    /**
     * Rest interface for uploading XML for concept collection http://<<URL>:
     * <PORT>>/quadriga/rest/syncdictionary/{dictionaryID}
     * hhttp://localhost:8080/quadriga/rest/syncdictionary/
     * 
     * @author Lohith Dwaraka
     * @param request
     * @param response
     * @param xml
     * @param accept
     * @return
     * @throws QuadrigaException
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws JAXBException
     * @throws QuadrigaAccessException
     * @throws QuadrigaStorageException
     * @throws RestException
     */
    @RestAccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY, paramIndex = 1, userRole = {
            RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR }) })
    @RequestMapping(value = "rest/syncdictionary/{dictionaryID}", method = RequestMethod.POST)
    public ResponseEntity<String> syncDictionary(@PathVariable("dictionaryID") String dictionaryID,
            HttpServletRequest request, HttpServletResponse response, @RequestBody String xml,
            @RequestHeader("Accept") String accept, Principal principal) throws QuadrigaException,
            ParserConfigurationException, SAXException, IOException, JAXBException, QuadrigaAccessException,
            QuadrigaStorageException, RestException {
        IUser user = usermanager.getUser(principal.getName());
        if (xml.equals("")) {
            String errorMsg = errorMessageRest.getErrorMsg("Please provide XML in body of the post request.");
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }

        logger.debug("XML : " + xml);

        JAXBElement<QuadrigaDictDetailsReply> response1 = null;
        try {
            JAXBContext context = JAXBContext.newInstance(QuadrigaDictDetailsReply.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
            InputStream is = new ByteArrayInputStream(xml.getBytes());
            response1 = unmarshaller.unmarshal(new StreamSource(is), QuadrigaDictDetailsReply.class);
        } catch (JAXBException e) {
            logger.error("Error in unmarshalling", e);
            String errorMsg = errorMessageRest.getErrorMsg("Error in unmarshalling", request);
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }

        QuadrigaDictDetailsReply qReply = response1.getValue();
        DictionaryItemList dictList = qReply.getDictionaryItemsList();
        List<DictionaryItem> dictionaryList = dictList.getDictionaryItems();
        if (dictionaryList.size() < 1) {
            String errorMsg = errorMessageRest.getErrorMsg("Dictionary XML is not valid", request);
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }

        Iterator<DictionaryItem> iter = dictionaryList.iterator();

        while (iter.hasNext()) {
            DictionaryItem dicItem = iter.next();
            try {
                dictionaryManager.addNewDictionariesItems(dictionaryID, dicItem.getTerm().trim(), dicItem.getUri()
                        .trim(), dicItem.getPos().trim(), user.getUserName());
                dictionaryManager.updateDictionariesItems(dictionaryID, dicItem.getUri(), dicItem.getTerm(),
                        dicItem.getPos());
            } catch (QuadrigaStorageException e) {
                logger.error("Errors in adding items", e);
                String errorMsg = errorMessageRest.getErrorMsg("Failed to add due to DB Error", request);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.valueOf(accept));
                return new ResponseEntity<String>(errorMsg, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(accept));
        return new ResponseEntity<String>("Success", httpHeaders, HttpStatus.OK);
    }

    /**
     * Rest interface to get dictionaries related to project
     * http://<<URL>:<PORT>>/quadriga/auth/rest/<projectid>/dictionaries.json
     * http://localhost:8080/quadriga/auth/rest/PROJ_bb7ad41b-3e85-4309-b2ff-47d
     * 644307b9b/dictionaries.json
     * 
     * 
     * @author Ajay Modi & Bharath Srikantan
     * @param projectid
     * @param model
     * @param principal
     * @return
     */

    @RequestMapping(value = "auth/rest/{projectid}/dictionaries.json", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> listProjectDictionaryJson(HttpServletRequest req,
            @PathVariable("projectid") String projectid, Model model, Principal principal) {
        List<IDictionary> dictionaries = null;
        // TODO: listProjectDictionary() is to be changed according to
        // mapper
        try {
            dictionaries = projectDictionaryManager.getDictionaries(projectid);
        } catch (QuadrigaStorageException e) {
            // TODO Auto-generated catch block
            logger.error("QuadrigaStorageException:", e);
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        JSONArray ja = new JSONArray();

        if (dictionaries != null) {
            for (IDictionary dictionary : dictionaries) {
                JSONObject j = new JSONObject();
                try {
                    j.put("id", dictionary.getDictionaryId());
                    j.put("name", dictionary.getDictionaryName());
                    ja.put(j);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    logger.error("JSONException:", e);
                    return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }

        return new ResponseEntity<String>(ja.toString(), HttpStatus.OK);

    }
}
