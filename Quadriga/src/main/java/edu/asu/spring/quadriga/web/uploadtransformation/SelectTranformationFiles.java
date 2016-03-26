package edu.asu.spring.quadriga.web.uploadtransformation;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SelectTranformationFiles {
	
	@RequestMapping(value="auth/selectTransformationFiles",method=RequestMethod.GET)
	public String selectTransformationFiles(ModelMap model, Principal principal){
		model.addAttribute("sucess",'0');
		return "auth/uploadTransformation";
	}
}
