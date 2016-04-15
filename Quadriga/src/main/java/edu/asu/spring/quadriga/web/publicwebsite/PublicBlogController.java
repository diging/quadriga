package edu.asu.spring.quadriga.web.publicwebsite;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
 * particular project. Number of project blogs to display is specified in
 * <code>user.properties</code> as <code>projectblogentry.list.count</code>
 *
 * @author Kavinya Rajendran
 * @author PawanMahalle
 */
@Controller
public class PublicBlogController {

    // Default number of blogs to display when projectblogentry.list.count is
    // absent or ill-formatted.
    private static final Integer DEFAULT_BLOG_ENTRIES_COUNT = 5;

    @Autowired
    private Environment env;

    @Autowired
    private IProjectBlogEntryManager projectBlogEntryManager;

    @Autowired
    private IProjectSecurityChecker projectSecurity;

    /**
     * generated data for project blog page by fetching project blog entries
     * from <code>tbl_projectblogentry</code> and checking user privileges.
     * 
     * @param projectUnixName
     *            parameter passed from url as {projectUnixName}
     * @param model
     *            model object
     * @param project
     *            project instance obtained using @InjectProject annotation
     * @param principal
     *            principal object which is required to fetch information about
     *            logged in user.
     * @return project blog page containing list of all project blogs from given
     *         project
     * @throws QuadrigaStorageException
     * @throws NoSuchRoleException
     */
    @RequestMapping(value = "sites/{ProjectUnixName}/projectblog", method = RequestMethod.GET)
    public String projectBlog(@PathVariable("ProjectUnixName") String projectUnixName, Model model,
            @InjectProject(unixNameParameter = "ProjectUnixName") IProject project, Principal principal)
            throws QuadrigaStorageException, NoSuchRoleException {

        // Fetch blog entries for a project identified by project unix name
        String projectId = project.getProjectId();
        Integer count = getBlogEntryCount();
        List<IProjectBlogEntry> projectBlogEntryList = projectBlogEntryManager.getProjectBlogEntryList(projectId,
                count);

        // Add the critical data to model object
        model.addAttribute("projectBlogEntryList", projectBlogEntryList);
        model.addAttribute("project", project);

        // check the roles of logged in user to decide whether to show add
        // project link or not. Only project admins and owners are allowed to
        // add project blogs for a project under consideration
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
        } else {
            model.addAttribute("isProjectAdmin", false);
            model.addAttribute("isProjectOWner", false);
        }

        return "sites/projectblog";
    }

    /**
     * reads the number of blog entries to be displayed from user properties
     * file using {@linkplain Environment} object
     * 
     * @return number of blog entries to be displayed specified as
     *         <code>projectblogentry.list.count<code> property.
     */
    private Integer getBlogEntryCount() {

        Integer count = null;

        // Check if property to set limit on number of project blog entries to
        // be fetched is specified in as environment variable.
        if (env.getProperty("projectblogentry.list.count") != null) {
            try {
                count = Integer.parseInt(env.getProperty("projectblogentry.list.count"));
            } catch (NumberFormatException ex) {
                // If invalid numeric value is set in the environment property,
                // use default value
                count = DEFAULT_BLOG_ENTRIES_COUNT;
            }
        } else {
            // If the environment property is not present, use default value
            count = DEFAULT_BLOG_ENTRIES_COUNT;
        }

        return count;
    }
}
