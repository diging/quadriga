package edu.asu.spring.quadriga.web.workspace;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.factory.workbench.ITextFileFactory;
import edu.asu.spring.quadriga.domain.impl.workspace.TextFile;
import edu.asu.spring.quadriga.domain.impl.workspace.WorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.textfile.ITextFileService;
import edu.asu.spring.quadriga.validator.AddTextValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class AddTextController {

    @Autowired
    private ITextFileFactory textFileFactory;

    @Autowired
    private ITextFileService txtFileService;

    @Autowired
    private AddTextValidator txtValidator;

    /**
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {

        binder.setValidator(txtValidator);
    }

    /**
     * Method to generate a view to generate a view to add new text file.
     * 
     * @param workspaceid
     *            Path Variable to retrieve the associated Workspace id
     * @return
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({
            @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 1, userRole = {
                    RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN, RoleNames.ROLE_WORKSPACE_COLLABORATOR_EDITOR }),
            @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 2, userRole = {
                    RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
                    RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR }) })
    @RequestMapping(value = "/auth/workbench/workspace/{projectid}/{workspaceid}/addtext", method = RequestMethod.GET)
    public ModelAndView addTextFileForm(@PathVariable("workspaceid") String workspaceid,
            @PathVariable("projectid") String projid) throws QuadrigaStorageException, QuadrigaAccessException {

        ModelAndView model = new ModelAndView("auth/workbench/workspace/addtext");
        model.getModelMap().put("textfile", textFileFactory.createTextFileObject());
        model.getModelMap().put("workspaceId", workspaceid);
        model.getModelMap().put("myProjectId", projid);
        model.getModelMap().put("success", "0");
        return model;
    }

    /**
     * @param resp
     *            Generic HTTP Response Object
     * @param txtFile
     *            Web backing domain object to store the Text Details
     * @param result
     *            Generic BingingResults object to hold validation errors
     * @param workspaceid
     *            Path Variable to retrieve the associated Workspace id
     * @return
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     * @throws IOException
     */
    @AccessPolicies({
            @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 4, userRole = {
                    RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN, RoleNames.ROLE_WORKSPACE_COLLABORATOR_EDITOR }),
            @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 5, userRole = {
                    RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
                    RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR }) })
    @RequestMapping(value = "/auth/workbench/workspace/{projectid}/{workspaceid}/addtext", method = RequestMethod.POST)
    public ModelAndView saveTextFileForm(HttpServletResponse resp,
            @Validated @ModelAttribute("textfile") TextFile txtFile, BindingResult result,
            @PathVariable("workspaceid") String workspaceid, @PathVariable("projectid") String projid,
            RedirectAttributes redirectAttributes)
                    throws QuadrigaStorageException, QuadrigaAccessException, IOException {

        ModelAndView model;
        if (result.hasErrors()) {
            model = new ModelAndView("auth/workbench/workspace/addtext");
            model.getModelMap().put("textfile", txtFile);
            model.getModelMap().put("workspaceId", workspaceid);
            model.getModelMap().put("myProjectId", projid);
            model.getModelMap().put("success", "0");
        } else {
            model = new ModelAndView("redirect:/auth/workbench/workspace/workspacedetails/" + workspaceid);

            model.getModelMap().put("workspaceId", workspaceid);
            model.getModelMap().put("myProjectId", projid);
            txtFile.setWorkspaceId(workspaceid);
            txtFile.setProjectId(projid);
            if (txtFileService.saveTextFile(txtFile)) {

                redirectAttributes.addFlashAttribute("show_success_alert", true);
                System.out.println("fails ghere");
                redirectAttributes.addFlashAttribute("success_alert_msg", "The text file is successfully saved");
            } else {
                model.getModelMap().put("success", "2");
            }
        }
        return model;
    }

}
