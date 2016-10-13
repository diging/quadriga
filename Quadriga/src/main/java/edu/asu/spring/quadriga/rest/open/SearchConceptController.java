package edu.asu.spring.quadriga.rest.open;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.conceptpower.IConceptpowerConnector;
import edu.asu.spring.quadriga.conceptpower.POS;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply.ConceptEntry;

@Controller
public class SearchConceptController {

    private static final Logger logger = LoggerFactory.getLogger(SearchConceptController.class);

    @Autowired
    private IConceptpowerConnector connector;

    @RequestMapping(value = "/public/concept/search", method = RequestMethod.GET)
    public ResponseEntity<String> searchConcepts(@RequestParam("searchTerm") String searchTerm) {
        // FIXME once the new Conceptpower is release, this should be replace
        // with
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

        CopyOnWriteArrayList<ConceptEntry> cowConceptList = new CopyOnWriteArrayList<>(conceptList);
        Iterator<ConceptEntry> conceptIterator = cowConceptList.iterator();
        
        if (conceptList != null) {
            while (conceptIterator.hasNext()) {

                ConceptEntry result = conceptIterator.next();
                try {
                    JSONObject jsonResult = new JSONObject();
                    jsonResult.put("id", result.getId());
                    jsonResult.put("name", result.getLemma());
                    jsonResult.put("description", result.getDescription());
                    jsonResult.put("pos", result.getPos());
                    jsonResult.put("type", result.getType());
                    jsonResults.add(jsonResult);
                } catch (JSONException e) {
                    // Exception
                    logger.error("Json exception while adding the results", e);
                }
            }

        }

        JSONObject jsonResponse = new JSONObject();
        try {
            jsonResponse.put("terms", jsonResults);
        } catch (JSONException e) {
            return new ResponseEntity<String>("{'error':'" + e.getMessage() + "'}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>(jsonResponse.toString(), HttpStatus.OK);
    }
}
