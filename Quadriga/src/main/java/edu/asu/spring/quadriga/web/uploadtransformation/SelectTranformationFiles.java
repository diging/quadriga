package edu.asu.spring.quadriga.web.uploadtransformation;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * @Controller
 * @author JayaVenkat
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
	public String selectTransformationFiles(HttpServletRequest request){		
		request.setAttribute("success", 0);
		return "auth/uploadTransformation";
	}
}
