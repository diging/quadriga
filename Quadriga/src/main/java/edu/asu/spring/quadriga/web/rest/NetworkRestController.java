package edu.asu.spring.quadriga.web.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.exceptions.QuadrigaException;

/**
 * Controller for networks related rest api's exposed to other clients
 * 
 * @author LohithDwaraka
 * 
 */
@Controller
public class NetworkRestController {

	private static final Logger logger = LoggerFactory
			.getLogger(NetworkRestController.class);

	/**
	 * Rest interface for uploading XML
	 * http://<<URL>:<PORT>>/quadriga/rest/uploadnetworks
	 * hhttp://localhost:8080/quadriga/rest/uploadnetworks
	 * 
	 * @author Lohith Dwaraka
	 * @param request
	 * @param response
	 * @param xml
	 * @param accept
	 * @return
	 * @throws QuadrigaException
	 */
	@ResponseBody
	@RequestMapping(value = "rest/uploadnetworks", method = RequestMethod.POST)
	public String getXMLFromVogon(HttpServletRequest request,
			HttpServletResponse response, @RequestBody String xml,
			@RequestHeader("Accept") String accept) throws QuadrigaException {

		if (xml.equals("")) {
			response.setStatus(500);
			return "Please provide XML in body of the post request.";

		} else {
			if (accept != null && accept.equals("application/xml")) {
				logger.info("" + xml);
			}
			response.setStatus(200);
			response.setContentType(accept);
			return "";
		}
	}
}
