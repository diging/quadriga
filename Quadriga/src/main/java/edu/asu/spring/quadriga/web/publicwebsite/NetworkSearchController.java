package edu.asu.spring.quadriga.web.publicwebsite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.aspects.annotations.CheckAccess;
import edu.asu.spring.quadriga.aspects.annotations.CheckPublicAccess;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.aspects.annotations.InjectProjectByName;
import edu.asu.spring.quadriga.aspects.annotations.ProjectIdentifier;
import edu.asu.spring.quadriga.conceptpower.IConcept;
import edu.asu.spring.quadriga.conceptpower.IConceptpowerCache;
import edu.asu.spring.quadriga.conceptpower.IConceptpowerConnector;
import edu.asu.spring.quadriga.conceptpower.POS;
import edu.asu.spring.quadriga.conceptpower.model.ConceptpowerReply;
import edu.asu.spring.quadriga.conceptpower.model.ConceptpowerReply.ConceptEntry;
import edu.asu.spring.quadriga.domain.network.json.PublicSearchObject;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.IJsonCreator;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * This controller searches for concept terms and returns json as response and
 * also a combined network of searched terms
 *
 * @author Vikranth Doosa
 */
@Controller
public class NetworkSearchController {
 
    @Autowired
    private IConceptpowerConnector connector;

    @Autowired
    private IJsonCreator jsonCreator;

    @Autowired
    private INetworkTransformationManager transformationManager;

    @Autowired
    private IConceptpowerCache cpCache;

    private static String defaultJsonErrorMsg = "{\"status\" : 500,"
            + " \"message\": \"Unable to get the search terms\"}";

    private static final Logger logger = LoggerFactory.getLogger(NetworkSearchController.class);
    
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    
   // private static Map<Integer, Future<String>> searchResultMap = new HashMap<Integer, Future<String>>();
    private static Map<Integer, Future<PublicSearchObject>> searchResultMap = new HashMap<Integer, Future<PublicSearchObject>>();
    

    /**
     * This method will return a search page
     *
     * @return view
     * @throws QuadrigaStorageException
     */
    @CheckPublicAccess
    @InjectProjectByName
    @RequestMapping(value = "sites/{projectUnixName}/search", method = RequestMethod.GET)
    public String getSearch(@ProjectIdentifier @PathVariable("projectUnixName") String projectUnixName, Model model,
            @CheckAccess @InjectProject IProject project) throws QuadrigaStorageException {

        model.addAttribute("project", project);
        return "sites/search";
    }

    /**
     * This method returns json data for search term
     *
     * @return json
     */
    @CheckPublicAccess
    @RequestMapping(value = "sites/{projectUnixName}/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @InjectProjectByName
    public ResponseEntity<String> getSearchTerms(@RequestParam("searchTerm") String searchTerm,
            @ProjectIdentifier @PathVariable("projectUnixName") String projectUnixName, @CheckAccess @InjectProject IProject project) {
        
        // FIXME once the new Conceptpower is release, this should be replace with
        // one call to the search api
        ConceptpowerReply reply = connector.search(searchTerm, POS.NOUN);
        List<ConceptEntry> conceptList = reply.getConceptEntry();
        
        reply = connector.search(searchTerm, POS.VERB);
        conceptList.addAll(reply.getConceptEntry());
        
        reply = connector.search(searchTerm, POS.ADJECTIVE);
        conceptList.addAll(reply.getConceptEntry());
        
        reply = connector.search(searchTerm, POS.ADVERB);
        conceptList.addAll(reply.getConceptEntry());
        
        List<JSONObject> jsonResults = new ArrayList<JSONObject>();
        try {
            if (conceptList != null) {
                for (ConceptEntry result : conceptList) {
                    JSONObject jsonResult = new JSONObject();
                    jsonResult.put("id", result.getId());
                    jsonResult.put("name", result.getLemma());
                    jsonResult.put("description", result.getDescription());
                    jsonResult.put("pos", result.getPos());
                    jsonResult.put("type", result.getType());
                    jsonResults.add(jsonResult);
                }
            }

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("terms", jsonResults);

            return new ResponseEntity<String>(jsonResponse.toString(), HttpStatus.OK);
        } catch (JSONException e) {
            // Exception
            logger.error("Json exception while adding the results", e);
        }
        return new ResponseEntity<String>(defaultJsonErrorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This method returns a display of the network that consists of all the statements in the
     * given projects that contain the provided concept id.
     * @param projectUnixName Unix name of the project whose networks will be searched for the concept
     * @param conceptId Id of the concept
     * @param project
     * @param model
     * @return relative path to the web-page that displays the network
     * @throws QuadrigaStorageException
     */
   /* @CheckPublicAccess
    @InjectProjectByName
    @RequestMapping(value = "sites/{projectUnixName}/networks/search", method = RequestMethod.GET)
    public String getSearchTransformedNetwork(@ProjectIdentifier @PathVariable("projectUnixName") String projectUnixName,
            @RequestParam("conceptId") String conceptId, @CheckAccess @InjectProject IProject project, Model model)
                    throws QuadrigaStorageException {
        
        String lemma = "";
        String searchNodeLabel = "";
        
        // Fetch ConceptPower entries related to the conceptId
        IConcept concept = cpCache.getConceptByUri(conceptId);
   
        if (concept != null) {
            searchNodeLabel = concept.getWord();
            lemma = concept.getDescription();
           
        }
     
        ITransformedNetwork transformedNetwork = transformationManager
                .getSearchTransformedNetwork(project.getProjectId(), conceptId, INetworkStatus.APPROVED);

        String json = null;
        if (transformedNetwork != null) {
            json = jsonCreator.getJson(transformedNetwork.getNodes(), transformedNetwork.getLinks());
        }

        if (transformedNetwork == null || transformedNetwork.getNodes().size() == 0) {
            model.addAttribute("isNetworkEmpty", true);
        }

        model.addAttribute("jsonstring", json);
       // model.addAttribute("networkid", "\"\"");
        //model.addAttribute("project", project);
        model.addAttribute("searchNodeLabel", searchNodeLabel);
        model.addAttribute("description", lemma);
        model.addAttribute("unixName", projectUnixName);

        return "sites/networks/searchednetwork";
    }*/
   @CheckPublicAccess
    @InjectProjectByName
    @ResponseBody
    @RequestMapping(value = "sites/{projectUnixName}/networks/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> submitSearchTransformedNetworkRequest(@ProjectIdentifier @PathVariable("projectUnixName") String projectUnixName,
            @RequestParam("conceptId") String conceptId, @CheckAccess @InjectProject IProject project)
                    throws QuadrigaStorageException {
                    
                    
           Callable<PublicSearchObject> callable = () -> {
                 String lemma = "";
                 String searchNodeLabel = "";
        
                 // Fetch ConceptPower entries related to the conceptId
                 IConcept concept = cpCache.getConceptByUri(conceptId);
                 System.out.println("Here");
                 if (concept != null) {
                        searchNodeLabel = concept.getWord();
                        lemma = concept.getDescription();
                 }
     
                ITransformedNetwork transformedNetwork = transformationManager
                .getSearchTransformedNetwork(project.getProjectId(), conceptId, INetworkStatus.APPROVED);

                String json = null;
                if (transformedNetwork != null) {
                    json = jsonCreator.getJson(transformedNetwork.getNodes(), transformedNetwork.getLinks());
                    System.out.println("JSON String: "+json);
                }
        
                JSONObject jsonResult = new JSONObject();
                HashMap<String, Object> jsonMap = new HashMap<String, Object>();
                PublicSearchObject publicSearchObject = new PublicSearchObject();
                try {
                    if (transformedNetwork == null || transformedNetwork.getNodes().size() == 0) {
                        jsonResult.put("isNetworkEmpty", true);
                        publicSearchObject.setNetworkEmpty(true);
                    }

                    jsonResult.put("jsonstring", json);
                    // jsonMap.put("jsonstring", json);
                    publicSearchObject.setJsonString(json);
                    
                    jsonResult.put("networkid", "\"\"");
                    //jsonMap.put("networkid", "\"\"");
                   
                    
                    jsonResult.put("project", project);
                    //jsonMap.put("project", project);
                    
                    jsonResult.put("searchNodeLabel", searchNodeLabel);
                    //jsonMap.put("searchNodeLabel", searchNodeLabel);
                    publicSearchObject.setSearchNodeLabel(searchNodeLabel);
                    
                    jsonResult.put("description", lemma);
                    //jsonMap.put("description", lemma);
                    publicSearchObject.setDescription(lemma);
                    
                    jsonResult.put("unixName", projectUnixName);
                    //jsonMap.put("unixName", projectUnixName);
                    publicSearchObject.setUnixName(projectUnixName);
                    
                    publicSearchObject.setStatus(1);
                }
                catch(JSONException e){
                     logger.error("Json exception while adding the results", e);
                }
                System.out.println("JSON Result String: "+jsonResult.toString());
               // System.out.println("JSON Map String: "+jsonMap.toString());
                System.out.println("Public Search Object String: "+publicSearchObject.getJsonString());
                //return jsonResult.toString();
                return publicSearchObject;
                
        };            
        Future<PublicSearchObject> future = executorService.submit(callable);
        Random randomTokenGenerator = new Random();
        Integer randomToken = randomTokenGenerator.nextInt(100);
        while(searchResultMap.containsKey(randomToken)){
            randomToken = randomTokenGenerator.nextInt(100);
        }
        searchResultMap.put(randomToken, future);
        JSONObject resultToken = new JSONObject();
        try {
            resultToken.put("token", randomToken);
        } catch (JSONException e) {
            logger.error("Json exception while adding the token", e);
        }
        return new ResponseEntity<String>(resultToken.toString(), HttpStatus.OK);
    }
   
   

   @ResponseBody
   @RequestMapping(value = "sites/{projectUnixName}/networks/search/result", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
   public PublicSearchObject getSearchTransformedNetwork(@ProjectIdentifier @PathVariable("projectUnixName") String projectUnixName, @RequestParam("tokenId") Integer tokenId){
       JSONObject result = new JSONObject();
       PublicSearchObject publicSearchObject = new PublicSearchObject();
       if(!searchResultMap.containsKey(tokenId)){
           try {
            result.put("message", "The request doesn't exist");
        } catch (JSONException e) {
            logger.error("Json exception while adding the message", e);
        }
          // return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
           publicSearchObject.setStatus(2);
           return publicSearchObject;
       }
       
       Future<PublicSearchObject> futureResult = searchResultMap.get(tokenId);
       if (futureResult != null && futureResult.isDone()) {
           try {
            return futureResult.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Exception while retrieving the result", e);
        }
       }
       else{
           try {
               result.put("message", "The request is being processed");
           } catch (JSONException e) {
               logger.error("Json exception while adding the message", e);
           }
           
       }
       //return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
       publicSearchObject.setStatus(3);
       return  publicSearchObject;
   }
}
