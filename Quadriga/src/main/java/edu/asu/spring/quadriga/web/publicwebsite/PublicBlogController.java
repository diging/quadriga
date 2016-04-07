package edu.asu.spring.quadriga.web.publicwebsite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.domain.projectblog.IProjectBlogEntry;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.projectblog.IProjectBlogEntryManager;

/**
 * This controller is responsible for showing project blog entries for a
 * particular project.
 *
 * @author Kavinya Rajendran
 * @author PawanMahalle
 */
@PropertySource(value = "classpath:/user.properties")
@Controller
public class PublicBlogController {

    @Autowired
    private IProjectBlogEntryManager projectBlogEntryManager;

    /**
     * fetches project blog entries from <code>tbl_projectblogentry</code>.
     * 
     * @param projectUnixName
     *            The project unix name
     * @param model
     *            Model
     * @return view
     * @throws QuadrigaStorageException
     */
    @RequestMapping(value = "sites/{projectUnixName}/projectblog", method = RequestMethod.GET)
    public String projectblog(@PathVariable("projectUnixName") String projectUnixName, Model model,
            @InjectProject(unixNameParameter = "ProjectUnixName") IProject project) throws QuadrigaStorageException {

        // Obtain project blog entries for given project
        String projectId = project.getProjectId();
        List<IProjectBlogEntry> projectBlogEntryList = projectBlogEntryManager.getProjectBlogEntryList(projectId);

        // Add the critical data to model object
        model.addAttribute("projectBlogEntryList", projectBlogEntryList);
        model.addAttribute("project", project);

        return "sites/projectblog";
    }

}
