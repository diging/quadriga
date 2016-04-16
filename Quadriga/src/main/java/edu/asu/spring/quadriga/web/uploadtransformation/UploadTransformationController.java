package edu.asu.spring.quadriga.web.uploadtransformation;



import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.service.uploadtransformation.ITransformationManager;

/**
 * 
 * @author JayaVenkat
 * This is a controller which takes request from uploadTransfomation.jsp to upload transformation files
 */
@Controller
public class UploadTransformationController {

	@Autowired
	private ITransformationManager uploadTnfmManager;
	
	@RequestMapping(value="auth/transformation/upload",method=RequestMethod.POST)
	public String uploadTransformationFiles(@ModelAttribute("UploadTransformationBackingBean") UploadTransformationBackingBean formBean, ModelMap map, 
			@RequestParam("file") MultipartFile[] file) throws IOException{

		String mappingTitle = formBean.getMappingTitle();
		String mappingDescription=""+formBean.getMappingDescription();				
		String mappingFileName = file[0].getOriginalFilename();
		System.out.println("Mapping File Title is: "+mappingTitle);
		System.out.println("Mapping File Description is: "+mappingDescription);
		System.out.println("Mapping File Name is: "+ mappingFileName);
		
		String transformTitle= formBean.getTransformTitle();		
		String transformDescription = formBean.getTransformDescription();	    
		String transfomrFileName=file[1].getOriginalFilename();
		System.out.println("Transfomation File Title is: "+transformTitle);
		System.out.println("Tranformation File Description is: "+transformDescription);
		System.out.println("Transformation File Name is: "+transfomrFileName);
		
		uploadTnfmManager.saveMetaData(mappingTitle, mappingDescription, mappingFileName, transformTitle, transformDescription, transfomrFileName);
				
		map.addAttribute("success", 1);
		return "auth/uploadTransformation";
	}
}