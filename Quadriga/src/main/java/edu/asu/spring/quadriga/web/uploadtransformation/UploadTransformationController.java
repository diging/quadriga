package edu.asu.spring.quadriga.web.uploadtransformation;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 * @author Jaya Venkat
 * This is a controller which takes request from uploadTransfomation.jsp to upload tranformation files
 */
@Controller
public class UploadTransformationController {

	@RequestMapping(value="auth/uploadTransformation",method=RequestMethod.POST)
	/**
	 * 
	 * @param model
	 * @param principal
	 * @param request
	 * takes the mandatory tile and file name of both mappingFile and Transformation File and saves them using rest service
	 * @return
	 */
	public String uploadTransformationFiles(ModelMap model,Principal principal, HttpServletRequest request){
				
		String mappingTitle = request.getParameter("mappingTitle");
		String mappingDescription=""+request.getParameter("mappingDescription");				
		String mappingFilePath=request.getParameter("mappingFilePath");
		String transformTitle=request.getParameter("transformTitle");
		String transfomrDescription = request.getParameter("transfomrDescription");
		String transformFilePath = request.getParameter("transformFilePath");
		
		System.out.println("Mapping File Title is: "+mappingTitle);
		System.out.println("Mapping File Description is: "+mappingDescription);
		System.out.println("Mapping File Path is: "+mappingFilePath);
		
		System.out.println("Tranformation File Title is: "+ transformTitle);
		System.out.println("Transfomation File Description is: "+transfomrDescription);
		System.out.println("Tranformation File Name is: "+transformFilePath);
		
		
		request.setAttribute("success", 1);
		return "auth/uploadTransformation";
	}
}
