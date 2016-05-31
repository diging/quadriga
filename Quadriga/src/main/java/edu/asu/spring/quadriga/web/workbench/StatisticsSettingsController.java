package edu.asu.spring.quadriga.web.workbench;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.aspects.annotations.InjectProjectById;
import edu.asu.spring.quadriga.aspects.annotations.ProjectIdentifier;
import edu.asu.spring.quadriga.domain.IStatisticsSettings;
import edu.asu.spring.quadriga.domain.impl.workbench.StatisticsSettingsBean;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workbench.StatisticsSettingsService;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class StatisticsSettingsController {

    @Autowired
    private StatisticsSettingsService statsSettingsService;

    @Autowired
    IRetrieveProjectManager projectManager;

    /**
     * This method loads necessary settings option for statistics page if user
     * have sufficient permission
     *
     * @author Ajay Modi
     * @return modelandview - ModelAndView Object
     */

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/{projectid}/statistics", method = RequestMethod.GET)
    @InjectProjectById
    public ModelAndView publicPageStatisticsSettingsForm(@ProjectIdentifier @PathVariable("projectid") String projectid, @InjectProject IProject project)
            throws QuadrigaStorageException, QuadrigaAccessException {

        ModelAndView model = new ModelAndView("auth/workbench/publicstatistics");
        model.getModelMap().put("project", project);

        List<IStatisticsSettings> statisticsSettingsList = statsSettingsService.getStatisticsSettingsList(projectid);
        model.getModelMap().put("statistics", statisticsSettingsList);

        StatisticsSettingsBean statisticsSettingsBean = new StatisticsSettingsBean();
        model.getModelMap().put("statisticsSettingsBean", statisticsSettingsBean);

        return model;
    }

    /**
     * This method stores necessary settings option for statistics page if user
     * have sufficient permission
     *
     * @author Ajay Modi
     * @return json
     */

    @RequestMapping(method = RequestMethod.POST, value = "auth/workbench/{projectid}/submitstatistics")
    public ModelAndView submitStatisticsSettings(
            @ModelAttribute("statistics") StatisticsSettingsBean statisticsSettingsBean, BindingResult result,
            @PathVariable("projectid") String projectid, RedirectAttributes attr) throws QuadrigaStorageException,
            QuadrigaAccessException {
        ModelAndView model = new ModelAndView("redirect:/auth/workbench/" + projectid + "/statistics");

        try {
            String[] names = statisticsSettingsBean.getNames();
            statsSettingsService.addOrUpdateStatisticsSettings(projectid, names);
            attr.addFlashAttribute("show_success_alert", true);
            attr.addFlashAttribute("success_alert_msg", "Settings has been updated successfully.");
            model.getModelMap().put("projectid", projectid);
        } catch (QuadrigaStorageException e) {
            StringBuffer errorMsg = new StringBuffer();
            attr.addFlashAttribute("show_error_alert", true);
            errorMsg.append(e.getMessage());
            errorMsg.append("\n");
            attr.addFlashAttribute("error_alert_msg", errorMsg.toString());
        }

        return model;

    }
}