package edu.asu.spring.quadriga.web.publicwebsite;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.dao.impl.projectblog.IProjectBlogDAO;
import edu.asu.spring.quadriga.domain.impl.projectblog.ProjectBlog;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectBlogDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ProjectBlogDTOMapper;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This controller displays the public blog contents and returns json as
 * response with blog title, date, author and content.
 *
 * @author Kavinya Rajendran
 * @author PawanMahalle
 */
@PropertySource(value = "classpath:/user.properties")
@Controller
@Transactional(rollbackFor = { Exception.class })
public class PublicBlogController {

    @Autowired
    private IProjectBlogDAO projectBlogDao;

    @Autowired
    private ProjectBlogDTOMapper projectBlogDTOMapper;

    @Autowired
    private IRetrieveProjectManager projectManager;

    /**
     * This method gives the the projectblog
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

        // Creating Dummy Object
        List dummyList = new ArrayList();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("title", "Article: Apple's iPhone Blunder");
        map.put("text",
                "<b> Can the United States </b> government compel Apple to help break into the phone of Syed Rizwan Farook, who, along with his wife Tafsheen Malil, gunned down fourteen innocent people last December at the Inland Regional Center in San Bernardino? That question has sparked fireworks in recent days. The dispute arises because Apple has equipped its new iPhones with encryption settings that erase the data contained on the phone whenever ten false password entries have been made. It was agreed on all sides that only Apple has the technology that might overcome the encryption device. [...]");
        map.put("date", "February 22, 2016");
        map.put("author", "Daniel T. Richards ");

        dummyList.add(map);

        model.addAttribute("blockentrylist", dummyList);
        model.addAttribute("project_id", project.getProjectId());

        return "sites/projectblog";
    }

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
    public String addprojectblog(@PathVariable("projectId") String projectId, Model model)
                    throws QuadrigaStorageException, QuadrigaAccessException {

        IProject project = projectManager.getProjectDetails(projectId);

        if (project == null) {
            return "forbidden";
        }

        model.addAttribute("projectBlog", new ProjectBlog());
        model.addAttribute("project_id", projectId);
        model.addAttribute("success", 0);

        return "sites/addprojectblog";
    }

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 3, userRole = {
            RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "sites/{projectId}/addprojectblog", method = RequestMethod.POST)
    public String addprojectblogEntry(@Validated @ModelAttribute("projectBlog") ProjectBlog projectBlog,
            BindingResult result, @PathVariable("projectId") String projectId, Model model, Principal principal)
                    throws QuadrigaStorageException, QuadrigaAccessException {

        // The ProjectBlog object contains title and description of the blog
        // obtained from user interface
        // Now, populating the ProjectBlog object with the following information
        // setting author which is a user who is currently logged in
        projectBlog.setAuthor(principal.getName());
        // setting project id of the blog
        projectBlog.setProjectId(projectId);
        // setting date of creation for the blog
        projectBlog.setCreatedDate(new Date());

        ProjectBlogDTO projectBlogDTO = projectBlogDTOMapper.getProjectBlogDTO(projectBlog);
        
        projectBlogDao.addProjectBlogDTO(projectBlogDTO);
        
        model.addAttribute("project_id", projectId);
     
        return "sites/projectblog";
    }
}
