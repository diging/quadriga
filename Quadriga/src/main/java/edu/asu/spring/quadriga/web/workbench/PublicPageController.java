package edu.asu.spring.quadriga.web.workbench;

import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.factory.workbench.IPublicPageFactory;
import edu.asu.spring.quadriga.domain.impl.workbench.PublicStatisticsPage;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.StatisticsSettingsService;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class PublicPageController {

    @Autowired
    private IPublicPageFactory publicPageFactory;

    @Autowired
    private StatisticsSettingsService statsSettingsService;

    @Resource(name = "projectconstants")
    private Properties messages;

    /**
     * This method is called during the load of Public page settings form
     *
     * @return model - model object
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_ADMIN,
            RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/{projectid}/addpublicpage", method = RequestMethod.GET)
    public ModelAndView publicPageSettingsForm(
            @PathVariable("projectid") String projectid)
            throws QuadrigaStorageException, QuadrigaAccessException {
        ModelAndView model = new ModelAndView("auth/workbench/addpublicpage");
        model.getModelMap().put("publicpage",
                publicPageFactory.createPublicPageObject());
        model.getModelMap().put("publicpageprojectid", projectid);
        return model;
    }

    /**
     * This method is used update the database with the information provided in
     * the Public settings page
     *
     * @return json
     */

    @RequestMapping(method = RequestMethod.POST, value = "auth/workbench/{projectid}/addpublicpagesuccess")
    public @ResponseBody ResponseEntity<String> getExistingConcepts(
            @RequestParam("data") String data) throws QuadrigaStorageException,
            QuadrigaAccessException {
        if (data.isEmpty())
            return null;
        try {
            return new ResponseEntity<String>("Successfully updated",
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method acts as a controller for handling settings for statistics
     * page on publicly accessible page
     *
     * @author Ajay Modi
     * @return model - model object
     */

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_ADMIN,
            RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/{projectid}/loadpublicstatistics", method = RequestMethod.GET)
    public ModelAndView publicPageStatisticsSettingsForm(
            @PathVariable("projectid") String projectid)
            throws QuadrigaStorageException, QuadrigaAccessException {

        ModelAndView model = new ModelAndView("auth/workbench/publicstatistics");
        PublicStatisticsPage publicStatisticsPage = (PublicStatisticsPage) publicPageFactory
                .createPublicStatisticsPageObject();

        model.getModelMap().put("publicpageprojectid", projectid);
        Map<String, Boolean> mapStatisticsSettings = statsSettingsService
                .getStatisticsSettingsMap(projectid);
        model.getModelMap().put("statistics", mapStatisticsSettings);
        model.getModelMap().put("publicstatisticspage", publicStatisticsPage);
        // model.getModelMap().put("statistics", listStatisticsSettings);
        return model;
    }

    /**
     * This method is used update the database with the information provided in
     * the Public Statistics settings page
     *
     * @return json
     */

    @RequestMapping(method = RequestMethod.POST, value = "auth/workbench/{projectid}/submitpublicstatistics")
    public @ResponseBody ResponseEntity<String> submitStatisticsSettings(
            @ModelAttribute("SpringWeb") PublicStatisticsPage publicStatisticsPage,
            ModelMap model, @PathVariable("projectid") String projectid)
            throws QuadrigaStorageException, QuadrigaAccessException {
        try {
            String[] names = publicStatisticsPage.getNames();
            statsSettingsService.updateSettings(projectid, names);
            return new ResponseEntity<String>("Successfully updated",
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}