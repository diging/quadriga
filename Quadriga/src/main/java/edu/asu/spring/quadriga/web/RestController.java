package edu.asu.spring.quadriga.web;

import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.impl.DictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.implementation.WordpowerReply;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.IUserManager;

@Controller
public class RestController {


	
	@Autowired 
	IDictionaryManager dictonaryManager;

	private static final Logger logger = LoggerFactory.getLogger(RestController.class);

	@Autowired 
	IUserManager usermanager;

	@Autowired 
	IDictionaryFactory dictionaryFactory;

	@Autowired 
	DictionaryItemsFactory dictionaryItemsFactory;

	@RequestMapping(value="api/dictionaries/{userID}", method = RequestMethod.GET , produces = "application/xml")
	@ResponseBody
	public String listDictionary(@PathVariable("userID") String userId, ModelMap model){
		String result=null;
		WordpowerReply.DictionaryEntry dictionaryEntry = dictonaryManager.searchWordPower("cat", "noun");
		WordpowerReply wordPowerReply= new WordpowerReply();
		wordPowerReply.SetDictionaryEntry(dictionaryEntry);
		logger.info("came to Rest controller : "+userId);
		try {
			JAXBContext jaxbContext= JAXBContext.newInstance(WordpowerReply.class);
			javax.xml.bind.Marshaller marshaller=  jaxbContext.createMarshaller();
			Writer writer =new StringWriter();
			marshaller.marshal(wordPowerReply, writer);
			result = writer.toString();
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result; 
	}
}
