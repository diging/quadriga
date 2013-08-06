package edu.asu.spring.quadriga.web.network;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This class will handle list dictionaries controller for the dictionary
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class NetworkListManager {

	@RequestMapping(value = "auth/networks", method = RequestMethod.GET)
	public String listDictionary(ModelMap model, Principal principal) {
		
		return "auth/networks";
	}
}
