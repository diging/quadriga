package edu.asu.spring.quadriga.web.uploadtransformation;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SelectTranformationFiles {
	
	@RequestMapping( value="auth/selectTransformationFiles",method=RequestMethod.GET)
	public String selectTransformationFiles(ModelMap model, Principal principal,HttpServletRequest request){
		
		request.setAttribute("success", 0);
		return "auth/uploadTransformation";
	}
}
