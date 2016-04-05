package edu.asu.spring.quadriga.web.projectblog;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.impl.projectblog.ProjectBlog;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.projectblog.IProjectBlogManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This controller is responsible for providing UI to create project blog entry
 * and save the entry to database table <code>tbl_projectblog</code>.
 *
 * @author PawanMahalle
 */
@PropertySource(value = "classpath:/user.properties")
@Controller
@Transactional(rollbackFor = { Exception.class })
public class AddProjectBlogController {

    @Autowired 
    IUserManager userManager;
   
    @Autowired
    IProjectBlogManager projectBlogManager;
    
    @Autowired
    private IRetrieveProjectManager projectManager;

    /**
     * This method provides page to add project blog entry to the authorized
     * user. Only project owners are allowed to add entry to the blog.
     * 
     * @param projectUnixName
     *            The project Unix name
     * @param projectId
     *            The id assigned to project by framework
     * @param model
     *            Model instance
     * @return view to add new project blog entry
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     *             This exception is thrown when user other than project admin
     *             and owner tries to add project blog entry.
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "sites/{projectId}/addprojectblog", method = RequestMethod.GET)
    public String addProjectBlogForm(@PathVariable("projectId") String projectId, Model model)
            throws QuadrigaStorageException, QuadrigaAccessException {

        IProject project = projectManager.getProjectDetails(projectId);

        if (project == null) {
            return "forbidden";
        }

        model.addAttribute("projectBlog", new ProjectBlog());
        model.addAttribute("project", project);

        return "sites/addprojectblog";
    }

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 3, userRole = {
            RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "sites/{projectId}/addprojectblog", method = RequestMethod.POST)
    public ModelAndView addProjectBlogRequest(@Validated @ModelAttribute("projectBlog") ProjectBlog projectBlog,
            BindingResult result, @PathVariable("projectId") String projectId, Model model, Principal principal)
                    throws QuadrigaStorageException, QuadrigaAccessException {
        
        String author = principal.getName();
        projectBlog.setAuthor(author);
        
        projectBlogManager.addNewProjectBlog(projectBlog, principal.getName());
        
        //After the blog is created, go to page containing all project blog entries
        IProject project = projectManager.getProjectDetails(projectId);
        ModelAndView redirection = new ModelAndView("redirect:"+"/sites/"+project.getUnixName()+"/projectblog");
        
        return redirection;
    }
}
