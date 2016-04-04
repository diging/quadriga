package edu.asu.spring.quadriga.web.uploadtransformation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 
 * @author Jaya Venkat
 * This is a controller which takes request from uploadTransfomation.jsp to upload tranformation files
 */
@Controller
public class UploadTransformationController {

	@RequestMapping(value="auth/uploadTransformation",method=RequestMethod.POST)
	public String uploadTransformationFiles(@ModelAttribute("UploadTransformationBackingBean") UploadTransformationBackingBean formBean, ModelMap model,Principal principal, HttpServletRequest request) throws IOException{
				
		String mappingTitle = formBean.getMappingFileTitle();
		String mappingDescription=""+formBean.getMappingFileDescription();				
		File mappingFile=formBean.getMappingFile();
		
		BufferedReader br = new BufferedReader(new FileReader(mappingFile));
		String mappingFileContent = br.readLine();
		/*String transformTitle=request.getParameter("transformTitle");
		String transfomrDescription = request.getParameter("transfomrDescription");
		String transformFilePath = request.getParameter("transformFilePath");*/
		
		System.out.println("Mapping File Title is: "+mappingTitle);
		System.out.println("Mapping File Description is: "+mappingDescription);
		System.out.println("Mapping File Path is: "+mappingFileContent);
		
		/*System.out.println("Tranformation File Title is: "+ transformTitle);
		System.out.println("Transfomation File Description is: "+transfomrDescription);
		System.out.println("Tranformation File Name is: "+transformFilePath);*/
		
		
		request.setAttribute("success", 1);
		return "auth/uploadTransformation";
	}
}
