package edu.asu.spring.quadriga.web.publicwebsite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
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
public class PublicBlogController {
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
        model.addAttribute("project", project);

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
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 2, userRole = {
            RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "sites/{projectUnixName}/{projectId}/addprojectblog", method = RequestMethod.GET)
    public String addprojectblog(@PathVariable("projectUnixName") String projectUnixName,
            @PathVariable("projectId") String projectId, Model model)
                    throws QuadrigaStorageException, QuadrigaAccessException {

        IProject project = projectManager.getProjectDetailsByUnixName(projectUnixName);

        if (project == null) {
            return "forbidden";
        }

        model.addAttribute("project", project);

        return "sites/addprojectblog";
    }
}
