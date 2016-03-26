package edu.asu.spring.quadriga.web.uploadtransformation;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author Jaya Vutukuri
 * controller for upload transformation button
 *
 */
@Controller
public class UploadTransformationController {

	@RequestMapping(value="auth/uploadTransformation",method=RequestMethod.POST)
	public String uploadTransformationFiles(ModelMap model, Principal principal){
				
		model.addAttribute("success","1");
		return "auth/uploadTransformation";
	}
}
