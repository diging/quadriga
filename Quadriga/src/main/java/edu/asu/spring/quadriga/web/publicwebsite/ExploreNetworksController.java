package edu.asu.spring.quadriga.web.publicwebsite;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.aspects.annotations.CheckPublicAccess;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.ID3Creator;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

@Controller
public class ExploreNetworksController {
    
    @Autowired
    private INetworkTransformationManager transformationManager;

    @Autowired
    private ID3Creator d3Creator;


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
    @CheckPublicAccess(projectIndex = 3)
    @RequestMapping(value = "sites/{projectUnixName}/networks", method = RequestMethod.GET)
    public String visualizeAllNetworks(
            @PathVariable("projectUnixName") String projectUnixName,
            Model model,
            @InjectProject(unixNameParameter = "projectUnixName") IProject project)
            throws JAXBException, QuadrigaStorageException {

        ITransformedNetwork transformedNetwork = transformationManager
                .getTransformedNetworkOfProject(project.getProjectId(), INetworkStatus.APPROVED);

        String json = null;
        if (transformedNetwork != null) {
            json = d3Creator.getD3JSON(transformedNetwork.getNodes(),
                    transformedNetwork.getLinks());
        }

        model.addAttribute("jsonstring", json);
        model.addAttribute("networkid", "\"\"");
        model.addAttribute("project", project);

        return "sites/networks/explore";

    }
}
