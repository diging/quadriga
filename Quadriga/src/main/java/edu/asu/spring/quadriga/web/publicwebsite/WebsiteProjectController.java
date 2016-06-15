package edu.asu.spring.quadriga.web.publicwebsite;

import java.security.Principal;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.aspects.annotations.CheckAccess;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.aspects.annotations.ProjectIdentifier;
import edu.asu.spring.quadriga.domain.impl.projectblog.ProjectBlogEntry;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.projectblog.IProjectBlogEntry;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.ID3Creator;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.service.projectblog.IProjectBlogEntryManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

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
    private INetworkManager networkmanager;

    @Autowired
    private INetworkTransformationManager transformationManager;

    @Autowired
    private ID3Creator d3Creator;

    @Autowired
    private Environment env;
    
    @Autowired
    private IProjectBlogEntryManager projectBlogEntryManager;

    public IRetrieveProjectManager getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(IRetrieveProjectManager projectManager) {
        this.projectManager = projectManager;
    }


    
    /**
     * This method displays the public or external Website for the particular
     * project
     * 
     * If the project has been set to 'accessible', then the public website page
     * is displayed. If the project does not exist then an error page is shown.
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
    @RequestMapping(value = "sites/{ProjectUnixName}", method = RequestMethod.GET)
    public String showProject(Model model, @ProjectIdentifier @PathVariable("ProjectUnixName") String unixName,
            Principal principal, @CheckAccess @InjectProject IProject project) throws QuadrigaStorageException {

        String user = null;
        if (principal != null) {
            user = principal.getName();
        }
        // Fetch blog entries for a project identified by project unix name
        String projectId = project.getProjectId();
        Integer count = 1;
        List<IProjectBlogEntry> latestProjectBlogEntryList = projectBlogEntryManager.getProjectBlogEntryList(projectId,
                count);
        model.addAttribute("projectBlogEntry",new ProjectBlogEntry());
        if (latestProjectBlogEntryList.size() < 1 ) {
            model.addAttribute("blogEntryExists",false);
        }
        else {
            model.addAttribute("blogEntryExists",true);
            model.addAttribute("latestProjectBlogEntrySnippet",latestProjectBlogEntryList.get(0).getSnippet(40));
            model.addAttribute("latestProjectBlogEntry", latestProjectBlogEntryList.get(0));
        }
        model.addAttribute("project_baseurl", env.getProperty("project.cite.baseurl"));

        if (user == null) {
            if (projectManager.getPublicProjectWebsiteAccessibility(unixName)) {
                model.addAttribute("project", project);
                return "sites/website";
            } else {
                return "forbidden";
            }
        }

        if (projectManager.canAccessProjectWebsite(unixName, user)
                || projectManager.getPublicProjectWebsiteAccessibility(unixName)) {
            model.addAttribute("project", project);
            return "sites/website";

        } else {
            return "forbidden";
        }
    }

}