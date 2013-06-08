package edu.asu.spring.quadriga.service.impl;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.domain.implementation.Dictionary;
import edu.asu.spring.quadriga.domain.implementation.DictionaryEntry;
import edu.asu.spring.quadriga.domain.implementation.DictionaryEntryBackupXJC;

import edu.asu.spring.quadriga.domain.implementation.WordpowerReply;
import edu.asu.spring.quadriga.service.IDictionaryManager;

/**
 *   @Description : This class acts as a dummy Dictionary manager which adds list of Dictionary words
 *   				and their descriptions on the dictionary page.
 *   
 *   @implements  : IDictionaryManager Interface
 *   
 *   @Called By   : DictionaryController.java
 *   
 *   @author      : Lohith Dwaraka
 * 
 * 
 */
@Service
public class DictionaryManager implements IDictionaryManager {

	@Autowired
	@Qualifier("DBConnectionDictionaryManagerBean")
	private IDBConnectionDictionaryManager dbConnect;

	public List<IDictionary> getDictionariesList(String userId){

		List<IDictionary> dictionaryList = new ArrayList<IDictionary>();  

		dictionaryList = dbConnect.getDictionaryOfUser(userId);

		return dictionaryList;
	}


	public String addNewDictionary(Dictionary dictionary){

		String msg = dbConnect.addDictionary(dictionary);

		return msg;
	}

	public String updateDictionariesItems(Dictionary existingDictionaryList){
		return "";
	}

	public int deleteDictionariesItems(String dictionaryId){
		return 1;
	}

	public String addNewDictionariesItems(String dictionaryId,String item,String owner){
		String msg=null;
		try {
			msg = dbConnect.addDictionaryItems(dictionaryId,item,owner);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return msg;

	}



	public List<IDictionaryItems> getDictionariesItems(String dictionaryid) {

		List<IDictionaryItems> dictionaryItemList = null;
		try {
			dictionaryItemList = dbConnect.getDictionaryItemsDetails(dictionaryid);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return dictionaryItemList;
	}

	public String getDictionaryName(String dictionaryid) {

		String dictionaryName="";
		try {
			dictionaryName = dbConnect.getDictionaryName(dictionaryid);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return dictionaryName;
	}

	public DictionaryEntry  callRestUri(String url,String item,String pos){
		//RestTemplate rest = new RestTemplate();
		//ExtendingThis extendingthis = rest.getForObject("http://digitalhps-develop.asu.edu:8080/wordpower/rest/WordLookup/dog/noun",
		//	edu.asu.spring.quadriga.domain.implementation.ExtendingThis.class);
		DictionaryEntry dictionaryEntry=null;
		try{
			JAXBContext jaxbContext = JAXBContext.newInstance(WordpowerReply.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			String fullUrl=url+""+item+"/"+pos;
			System.out.println(" URL : "+fullUrl);
			URL wp = new URL(fullUrl);
			InputStream xml = wp.openStream();

			BufferedReader in = new BufferedReader(new InputStreamReader(xml));
			String inputLine;
			String append="";
			while ((inputLine = in.readLine()) != null){
				//System.out.println(inputLine);

				append=append+inputLine;
			}

			append=append.replaceAll("digitalHPS:", "");
			append=append.replaceAll("xmlns:digitalHPS=\"http://www.digitalhps.org/\"","");
			System.out.println(" XML \n"+append);
			in.close();
			StringReader sr = new StringReader(append);
			WordpowerReply wordpowerReply = (WordpowerReply) unmarshaller.unmarshal(sr);
			dictionaryEntry=wordpowerReply.dictionaryEntry;
			System.out.println("id "+dictionaryEntry.getId());
			System.out.println("Lemma "+dictionaryEntry.getLemma());
			System.out.println("Description "+dictionaryEntry.getDescription());
			System.out.println(" item " + item);
			System.out.println(" pos " + pos);

			// Debug purpose
			//	Marshaller marshaller = jaxbContext.createMarshaller();
			//	marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			//	marshaller.marshal(dictionaryEntry, System.out);
		}catch(Exception e){
			e.printStackTrace();
		}
		return dictionaryEntry;
	}



}
