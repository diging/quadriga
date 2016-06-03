package edu.asu.spring.quadriga.rest.open;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.service.network.INetworkManager;

@Controller
public class NodeInfoController {
    
    @Autowired
    private INetworkManager networkManager;

    @RequestMapping(value = "public/concept/texts", method = RequestMethod.GET)
    public ResponseEntity<String> getTextsForConcepts(@RequestParam String conceptId) throws Exception {
        networkManager.getTextsForConceptId(conceptId);
        return null;
    }
}
