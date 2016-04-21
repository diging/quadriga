package edu.asu.spring.quadriga.web.transformation;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.service.transformation.ITransformationManager;

/**
 * 
 * @author JayaVenkat
 * This is a controller which takes request from uploadTransfomation.jsp to upload transformation files
 */
@Controller
public class UploadTransformFilesController {

	@Autowired
	private ITransformationManager transformationManager;
	
	@RequestMapping(value="auth/transformation/upload",method=RequestMethod.POST)
	public String uploadTransformFiles(@ModelAttribute("TransformationFilesBackingBean") TransformFilesBackingBean formBean, ModelMap map, 
			@RequestParam("file") MultipartFile[] file) throws IOException{

		String title = formBean.getTitle();
		String description = formBean.getDescription();
		
		String patternTitle= formBean.getPatternTitle();		
		String patternDescription = formBean.getPatternDescription();	    
		String patternFileName=file[0].getOriginalFilename();
		
		String mappingTitle = formBean.getMappingTitle();
		String mappingDescription=formBean.getMappingDescription();				
		String mappingFileName = file[1].getOriginalFilename();
					    		
		transformationManager.saveTransformation(title, description, patternFileName, patternTitle, patternDescription, mappingFileName, mappingTitle, mappingDescription);
		//Only meta data is being saved in database. Saving files is not yet done..  		
		
		map.addAttribute("show_success_alert",true);
		map.addAttribute("success_alert_msg","Upload Successful");
		return "auth/uploadTransformation";
	}
}
