package edu.asu.spring.quadriga.web.publicwebsite;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.accesschecks.IProjectSecurityChecker;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.domain.projectblog.IProjectBlogEntry;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.NoSuchRoleException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.projectblog.IProjectBlogEntryManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

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

    @Autowired
    private IProjectSecurityChecker projectSecurity;

    /**
     * fetches project blog entries from <code>tbl_projectblogentry</code>.
     * 
     * @param projectUnixName
     *            The project unix name
     * @param model
     *            Model
     * @return view
     * @throws QuadrigaStorageException
     * @throws NoSuchRoleException
     */
    @RequestMapping(value = "sites/{ProjectUnixName}/projectblog", method = RequestMethod.GET)
    public String projectBlog(@PathVariable("ProjectUnixName") String projectUnixName, Model model,
            @InjectProject(unixNameParameter = "ProjectUnixName") IProject project, Principal principal)
            throws QuadrigaStorageException, NoSuchRoleException {

        // Obtain project blog entries for given project
        String projectId = project.getProjectId();
        List<IProjectBlogEntry> projectBlogEntryList = projectBlogEntryManager.getProjectBlogEntryList(projectId);

        // Add the critical data to model object
        model.addAttribute("projectBlogEntryList", projectBlogEntryList);
        model.addAttribute("project", project);

        if (principal != null) {
            String userName = principal.getName();
            if (projectSecurity.isCollaborator(userName, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
                    project.getProjectId())) {
                model.addAttribute("isProjectAdmin", true);
            } else {
                model.addAttribute("isProjectAdmin", false);
            }

            if (projectSecurity.isProjectOwner(userName, project.getProjectId())) {
                model.addAttribute("isProjectOWner", true);
            } else {
                model.addAttribute("isProjectOWner", false);
            }
        }else{
            model.addAttribute("isProjectAdmin", false);
            model.addAttribute("isProjectOWner", false);
        }
        return "sites/projectblog";
    }

}
