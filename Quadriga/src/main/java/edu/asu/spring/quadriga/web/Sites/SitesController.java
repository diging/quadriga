package edu.asu.spring.quadriga.web.Sites;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This Controller is used to perform all the changes related to Quadriga public sites
 * @author Charan Thej Aware
 *
 */

@Controller
public class SitesController {
	
    /**
     * This method is used to access the public page -sites that enlists the public pages of all the projects 
     * @param locale
     * @param model
     * @return
     */
    @RequestMapping(value = "sites", method = RequestMethod.GET)
    public String showQuadrigaPublicPages(Locale locale, Model model) {
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
                DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);
        model.addAttribute("serverTime", formattedDate);
        return "sites";
    }

}
