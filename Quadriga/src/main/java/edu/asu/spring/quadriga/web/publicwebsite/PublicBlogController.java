package edu.asu.spring.quadriga.web.publicwebsite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.projectblog.IProjectBlog;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.projectblog.IProjectBlogManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

/**
 * This controller is responsible for showing project blog entries for a
 * particular project.
 *
 * @author Kavinya Rajendran
 * @author PawanMahalle
 */
@PropertySource(value = "classpath:/user.properties")
@Controller
@Transactional(rollbackFor = { Exception.class })
public class PublicBlogController {

    @Autowired
    private IRetrieveProjectManager projectManager;

    @Autowired
    private IProjectBlogManager projectBlogManager;

    /**
     * fetches the project blog entries from <code>tbl_projectblog</code>.
     * 
     * @param projectUnixName
     *            The project unix name
     * @param model
     *            Model
     * @return view
     * @throws QuadrigaStorageException
     */
    @RequestMapping(value = "sites/{projectUnixName}/projectblog", method = RequestMethod.GET)
    public String projectblog(@PathVariable("projectUnixName") String projectUnixName, Model model)
            throws QuadrigaStorageException {
        
        IProject project = projectManager.getProjectDetailsByUnixName(projectUnixName);

        if (project == null) {
            return "forbidden";
        }

        //Obtain project blog entries for given project
        String projectId = project.getProjectId();
        List<IProjectBlog> projectBlogList = projectBlogManager.getProjectBlogList(projectId);
    
        //Add the critical data to model object
        model.addAttribute("projectBlogList", projectBlogList);
        model.addAttribute("project", project);

        return "sites/projectblog";
    }

}
