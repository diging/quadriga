package edu.asu.spring.quadriga.web.publicwebsite;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.aspects.annotations.CheckPublicAccess;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.aspects.annotations.InjectProjectByName;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IPublicPage;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.ID3Creator;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.service.publicwebsite.IPublicPageBlockLinkTargets;
import edu.asu.spring.quadriga.service.workbench.IPublicPageManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

/**
 * This controller has all the mappings required to view the external website of
 * a project, view all the networks in that project and visualize the networks
 * 
 * @author Sayalee Mehendale
 *
 */

@PropertySource(value = "classpath:/user.properties")
@Controller
public class WebsiteProjectController {

    @Autowired
    private IRetrieveProjectManager projectManager;

    @Autowired
    private Environment env;

    @Autowired
    private IPublicPageManager publicPageManager;

    /**
     * This method displays the public or external Website for the particular
     * project
     * 
     * If the project has been set to 'accessible', then the public website page
     * is displayed. If the project does not exist then an error page is shown.
     * 
     * @param <T>
     * 
     * @param unixName
     *            unix name that is given to the project at the time of its
     *            creation
     * @param model
     *            Model object to map values to view
     * @return returns a string to access the external website main page
     * @throws QuadrigaStorageException
     *             Database storage exception thrown
     */
    @CheckPublicAccess(projectIndex = 4)
    @InjectProjectByName(projectIndex = 4)
    @RequestMapping(value = "sites/{ProjectUnixName}", method = RequestMethod.GET)
    public <T> String showProject(
            Model model,
            @PathVariable("ProjectUnixName") String unixName,
            Principal principal,
            @InjectProject(unixNameParameter = "ProjectUnixName") IProject project)
            throws QuadrigaStorageException {

        model.addAttribute("project_baseurl",
                env.getProperty("project.cite.baseurl"));

        List<IPublicPage> publicPages = publicPageManager
                .retrievePublicPageContent(project.getProjectId());
        Collections.sort(publicPages, new Comparator<IPublicPage>() {
            @Override
            public int compare(IPublicPage o1, IPublicPage o2) {
                return o1.getOrder() - o2.getOrder();
            }
        });

        Map<String, String> linkToMap = getLinkTargetMap();
        publicPages.forEach(item -> item.setLinkTo(linkToMap.get(item
                .getLinkTo())));

        model.addAttribute("blocks", publicPages);

        model.addAttribute("project", project);
        return "sites/website";

    }


    /*
     * This is kind of ugly and should be replace with a better solution. But
     * well, it works.
     */
    private Map<String, String> getLinkTargetMap() {
        Map<String, String> linkTypes = new HashMap<String, String>();
        linkTypes.put(IPublicPageBlockLinkTargets.ABOUT, "about");
        linkTypes.put(IPublicPageBlockLinkTargets.BLOG, "projectBlog");
        linkTypes.put(IPublicPageBlockLinkTargets.BROWSE, "browsenetworks");
        linkTypes.put(IPublicPageBlockLinkTargets.EXPLORE, "networks");
        linkTypes.put(IPublicPageBlockLinkTargets.SEARCH, "search");
        linkTypes.put(IPublicPageBlockLinkTargets.STATS, "statistics");
        return linkTypes;
    }

}
