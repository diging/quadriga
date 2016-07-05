package edu.asu.spring.quadriga.web.transformation;

import java.io.IOException;
import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.workspace.TransformationFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.transformation.ITransformationManager;
import edu.asu.spring.quadriga.validator.TransfomationFilesValidator;

/**
 * 
 * @author JayaVenkat This is a controller which takes request from
 *         uploadTransfomation.jsp to upload transformation files
 */
@Controller
public class UploadTransformFilesController {

    @Autowired
    private ITransformationManager transformationManager;

    @Autowired
    private IUserManager userManager;

    @Autowired
    private TransfomationFilesValidator validator;

    private static final Logger logger = LoggerFactory.getLogger(UploadTransformFilesController.class);

    @InitBinder("transformationFilesBackingBean")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(value = "auth/transformation/upload", method = RequestMethod.POST)
    public ModelAndView uploadTransformFiles(
            @Validated @ModelAttribute("transformationFilesBackingBean") TransformFilesBackingBean formBean,
            BindingResult result, ModelMap map, Principal principal, @RequestParam("file") MultipartFile[] file)
            throws IOException, QuadrigaStorageException, FileStorageException {

        ModelAndView model;
        model = new ModelAndView("auth/uploadTransformation");
        if (result.hasErrors()) {
            model.getModelMap().put("show_error_alert", true);
            model.getModelMap().put("error_alert_msg", "Please enter all the required fields.");
            return model;
        } else if (file.length != 2) {
            model.getModelMap().put("show_error_alert", true);
            model.getModelMap().put("error_alert_msg", "Please upload all the required files.");
            return model;
        } else if (file[0].getSize() == 0 || file[1].getSize() == 0) {
            model.getModelMap().put("show_error_alert", true);
            model.getModelMap().put("error_alert_msg", "Please upload all the required files.");
            return model;
        } else {
            String title = formBean.getTitle();
            String description = formBean.getDescription();
            IUser user = userManager.getUser(principal.getName());
            String userName = user.getName();
            String patternTitle = formBean.getPatternTitle();
            String patternDescription = formBean.getPatternDescription();
            String patternFileName = file[0].getOriginalFilename();

            String mappingTitle = formBean.getMappingTitle();
            String mappingDescription = formBean.getMappingDescription();
            String mappingFileName = file[1].getOriginalFilename();

            TransformationFile transformationFile = new TransformationFile();
            transformationFile.setTitle(title);
            transformationFile.setDescription(description);
            transformationFile.setUserName(userName);
            transformationFile.setPatternTitle(patternTitle);
            transformationFile.setPatternDescription(patternDescription);
            transformationFile.setPatternFileName(patternFileName);
            transformationFile.setPatternFileContent(file[0].getBytes());
            transformationFile.setMappingTitle(mappingTitle);
            transformationFile.setMappingDescription(mappingDescription);
            transformationFile.setMappingFileName(mappingFileName);
            transformationFile.setMappingFileContent(file[1].getBytes());

            transformationManager.saveTransformations(transformationFile);

            model.getModelMap().put("show_success_alert", true);
            model.getModelMap().put("success_alert_msg", "Upload Successful.");
            return model;
        }

    }
}
