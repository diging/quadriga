package edu.asu.spring.quadriga.web.publicwebsite;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.IJsonCreator;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.web.network.INetworkStatus;
import edu.asu.spring.quadriga.web.publicwebsite.cytoscapeobjects.PublicSearchObject;

@Controller
public class ExploreNetworksController {

    @Autowired
    private INetworkTransformationManager transformationManager;

    @Autowired
    private IJsonCreator jsonCreator;


    /**
     * This method gives the visualization of all the networks in a project
     * 
     * @param projectUnixName
     *            The project unix name
     * @param model
     *            Model
     * @return view
     * @throws JAXBException
     * @throws QuadrigaStorageException
     */
    @CheckPublicAccess
    @InjectProjectByName
    @RequestMapping(value = "sites/{projectUnixName}/networks", method = RequestMethod.GET)
    public String visualizeAllNetworks(@ProjectIdentifier @PathVariable("projectUnixName") String projectUnixName, Model model,
            @CheckAccess @InjectProject IProject project) throws JAXBException, QuadrigaStorageException {

        String projectId = jsonCreator.submitTransformationRequest(project);
        model.addAttribute("networkid", "\"\"");
        model.addAttribute("project", project);
        model.addAttribute("pId", projectId);
        model.addAttribute("unixName", projectUnixName);
        
        return "sites/networks/explore";

    }
    
    @ResponseBody
    @RequestMapping(value = "sites/{projectUnixName}/network/result", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PublicSearchObject getSearchTransformedNetwork(
            @ProjectIdentifier @PathVariable("projectUnixName") String projectUnixName, String pId
            ) {
        return jsonCreator.getSearchTransformedNetworkOfProject(pId);
    }
}
