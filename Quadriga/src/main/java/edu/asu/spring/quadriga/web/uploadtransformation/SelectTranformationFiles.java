package edu.asu.spring.quadriga.web.uploadtransformation;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Controller
 * @author Jaya Venkat
 *
 */
@Controller
public class SelectTranformationFiles {
	
	@RequestMapping( value="auth/transformation/selectTransformationFiles",method=RequestMethod.GET)
	/**
	 * 
	 * @param model
	 * @param principal
	 * @param request
	 * This method redirect to uploadTranformation.jsp and sets an attribute  named success to value 0. 
	 * Success attribute is used to display only part of the jsp page
	 * @return
	 */
	public String selectTransformationFiles(ModelMap model, Principal principal,HttpServletRequest request){		
		request.setAttribute("success", 0);
		return "auth/uploadTransformation";
	}
}
