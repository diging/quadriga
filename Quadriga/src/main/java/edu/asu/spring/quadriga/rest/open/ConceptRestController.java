package edu.asu.spring.quadriga.rest.open;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.conceptpower.IConceptpowerConnector;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply.ConceptEntry;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Controller
public class ConceptRestController {
    
    
    @Autowired
    private IConceptpowerConnector connector;

    
    @RequestMapping(value = "/public/concept", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getConceptById(@RequestParam String id) {
        ConceptpowerReply reply = connector.getById(id);
        List<ConceptEntry> conceptList = reply.getConceptEntry();
        Iterator<ConceptEntry> conceptListIterator = conceptList.iterator();
        while (conceptListIterator.hasNext()) {

            ConceptEntry ce = conceptListIterator.next();
            return new ResponseEntity<String>(ce.getDescription(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("", HttpStatus.OK);
    }
    
    /**
     * This controller method would get description of the lemma to javascript
     * when called through a Ajax call
     * 
     * @author Lohith Dwaraka
     * @param lemma
     * @param request
     * @param response
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     * @throws JAXBException
     */
    @RequestMapping(value = "/public/concept/lemma/{lemma}", method = RequestMethod.GET)
    @ResponseBody
    public String getConceptCollectionObject(@PathVariable("lemma") String lemma, HttpServletRequest request,
            HttpServletResponse response, ModelMap model) throws QuadrigaStorageException,
            JAXBException {

        // This is done as string with a dot (.) in between in the path variable
        // is not read as expected so we could replace it by $ in the javascript
        // and revert back in our controller
        lemma = lemma.replace('$', '.');
        ConceptpowerReply conceptPowerReply = connector.search(lemma, "NOUN");
        List<ConceptEntry> conceptList = conceptPowerReply.getConceptEntry();
        Iterator<ConceptEntry> conceptListIterator = conceptList.iterator();
        while (conceptListIterator.hasNext()) {

            ConceptEntry ce = conceptListIterator.next();
            if (ce.getLemma().equalsIgnoreCase(lemma)) {
                response.setStatus(200);
                return ce.getDescription();
            }
        }
        conceptPowerReply = connector.search(lemma, "VERB");
        conceptList = conceptPowerReply.getConceptEntry();
        conceptListIterator = conceptList.iterator();
        while (conceptListIterator.hasNext()) {

            ConceptEntry ce = conceptListIterator.next();
            if (ce.getLemma().equalsIgnoreCase(lemma)) {
                response.setStatus(200);
                return ce.getDescription();
            }
        }

        conceptPowerReply = connector.search(lemma, "adverb");
        conceptList = conceptPowerReply.getConceptEntry();
        conceptListIterator = conceptList.iterator();
        while (conceptListIterator.hasNext()) {

            ConceptEntry ce = conceptListIterator.next();
            if (ce.getLemma().equalsIgnoreCase(lemma)) {
                response.setStatus(200);
                return ce.getDescription();
            }
        }

        conceptPowerReply = connector.search(lemma, "adjective");
        conceptList = conceptPowerReply.getConceptEntry();
        conceptListIterator = conceptList.iterator();
        while (conceptListIterator.hasNext()) {

            ConceptEntry ce = conceptListIterator.next();
            if (ce.getLemma().equalsIgnoreCase(lemma)) {
                response.setStatus(200);
                return ce.getDescription();
            }
        }

        conceptPowerReply = connector.search(lemma, "others");
        conceptList = conceptPowerReply.getConceptEntry();
        conceptListIterator = conceptList.iterator();
        while (conceptListIterator.hasNext()) {

            ConceptEntry ce = conceptListIterator.next();
            if (ce.getLemma().equalsIgnoreCase(lemma)) {
                response.setStatus(200);
                return ce.getDescription();
            }
        }

        return "";
    }
}
