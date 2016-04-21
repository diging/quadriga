package edu.asu.spring.quadriga.web.transformation;

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
public class ShowTranformationFilesUiController {
	
	/**
	 * 
	 * This method will show uploadTranformation.jsp to user 
	 * @return
	 */
	@RequestMapping( value="auth/transformation/selectTransformationFiles",method=RequestMethod.GET)	
	public String selectTransformationFiles(HttpServletRequest request){				
		return "auth/uploadTransformation";
	}
}
