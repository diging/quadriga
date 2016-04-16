package edu.asu.spring.quadriga.web.uploadtransformation;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.service.uploadtransformation.ITransformationManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This is a controller which takes request from uploadTransfomation.jsp to
 * upload transformation files
 * 
 * @author JayaVenkat
 */
@Controller
public class UploadTransformationController {

	@Autowired
	private ITransformationManager uploadTnfmManager;

	@RequestMapping(value = "auth/transformation/uploadFiles", method = RequestMethod.POST)
	public String uploadTransformationFiles(
			@ModelAttribute("UploadTransformationBackingBean") UploadTransformationBackingBean formBean,
			ModelMap map, @RequestParam("file") MultipartFile[] file)
			throws IOException {

		String mappingTitle = formBean.getMappingTitle();
		String mappingDescription = formBean.getMappingDescription();		
		System.out.println("Mapping File Title is: " + mappingTitle);
		System.out
				.println("Mapping File Description is: " + mappingDescription);

		String transformTitle = formBean.getTransformTitle();
		String transformDescription = formBean.getTransformDescription();
		System.out.println("Transfomation File Title is: " + transformTitle);
		System.out.println("Tranformation File Description is: "
				+ transformDescription);
		uploadTnfmManager.saveMetaData(mappingTitle, mappingDescription,
				transformTitle, transformDescription);

		map.addAttribute("success", 1);
		return "auth/uploadTransformation";
	}
	
	
	
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
			RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR,
			RoleNames.ROLE_COLLABORATOR_ADMIN }) })
	@RequestMapping(value = "auth/transformation/selectTransformationFiles", method = RequestMethod.GET)
	/**
	 * 
	 * @param model
	 * @param principal
	 * @param request
	 * This method redirect to uploadTranformation.jsp and sets an attribute  named success to value 0. 
	 * Success attribute is used to display only part of the jsp page
	 * @return
	 */
	public String selectTransformationFiles(HttpServletRequest request) {

		request.setAttribute("success", 0);
		return "auth/uploadTransformation";
	}
	
}
