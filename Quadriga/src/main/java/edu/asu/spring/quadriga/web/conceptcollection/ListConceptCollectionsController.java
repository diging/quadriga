package edu.asu.spring.quadriga.web.conceptcollection;

import java.security.Principal;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;

@Controller
public class ListConceptCollectionsController {

    @Autowired
    private IConceptCollectionManager conceptControllerManager;

    
    /**
     * This is used to fetch the details of a concept from database and display
     * 
     * @param collection_id
     * @param model
     * @param principal
     * @return Returns the list of concept collections of user to the view
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     * @throws JSONException
     */
    @RequestMapping(value = "auth/conceptcollections/{collection_id}", method = RequestMethod.GET)
    public String conceptDetailsHandler(
            @PathVariable("collection_id") String collection_id,
            ModelMap model, Principal principal)
            throws QuadrigaStorageException, QuadrigaAccessException,
            JSONException {

        fillModel(collection_id, model, principal.getName());
        return "auth/conceptcollections/details";
    }
    
    private void fillModel(String collectionId, ModelMap model, String username)
            throws QuadrigaStorageException, QuadrigaAccessException,
            JSONException {
        IConceptCollection collection = conceptControllerManager.getConceptCollection(collectionId);
        model.addAttribute("concept", collection);
        conceptControllerManager.getCollaborators(collection);
        model.addAttribute("collectionid", collectionId);

        model.addAttribute("owner",
                collection.getOwner().getUserName().equals(username));

        // TODO: showCollaboratingUsers() should be changed with mapper
        List<IConceptCollectionCollaborator> collaboratingUsers = conceptControllerManager
                .showCollaboratingUsers(collectionId);
        model.addAttribute("collaboratingUsers", collaboratingUsers);
    }
}
