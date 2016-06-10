package edu.asu.spring.quadriga.web.transformation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Controller
 * @author JayaVenkat
 *
 */
@Controller
public class ShowUploadTranformationFilesUiController {

    /**
     * 
     * This method will show uploadTranformation.jsp to user where user can
     * select the transformation files which are supposed to be saved.
     * 
     * @return
     */
    @RequestMapping(value = "auth/transformation/selectTransformationFiles", method = RequestMethod.GET)
    public String selectTransformationFiles() {
        return "auth/uploadTransformation";
    }
}
