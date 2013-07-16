package edu.asu.spring.quadriga.domain.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.dspace.service.IDspaceCollection;
import edu.asu.spring.quadriga.dspace.service.IDspaceItem;
import edu.asu.spring.quadriga.dspace.service.impl.DspaceCollection;

/**
 * The class representation of the Collection got from Dspace repostiory.
 * This class will be used by Quadriga and its representation is independent of the Dspace Rest service output.
 * 
 * @author Ram Kumar Kumaresan
 */
public class Collection implements ICollection{

	private String id;
	private String name;
	private String shortDescription;
	private String entityReference;
	private String handle;
	private String countItems;
	private List<IItem> items;

	private RestTemplate restTemplate;
	private String url;
	private String userName;
	private String password;

	/**
	 * Initialize the required details to make a REST service call to Dspace
	 * @param id			The id of the collection.
	 * @param restTemplate	The RestTemplate object containing the details about the parser.
	 * @param url			The REST service url/domain.
	 * @param userName		The username of the authorized user.
	 * @param password		The password of the authorized user.
	 */
	public Collection(String id, RestTemplate restTemplate, String url, String userName, String password)
	{
		this.url = url;
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.setRestTemplate(restTemplate);
	}

	@Override
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	@Override
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getShortDescription() {
		return shortDescription;
	}

	@Override
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	@Override
	public String getEntityReference() {
		return entityReference;
	}

	@Override
	public void setEntityReference(String entityReference) {
		this.entityReference = entityReference;
	}

	@Override
	public String getHandle() {
		return handle;
	}

	@Override
	public void setHandle(String handle) {
		this.handle = handle;
	}

	@Override
	public String getCountItems() {
		return countItems;
	}

	@Override
	public void setCountItems(String countItems) {
		this.countItems = countItems;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean copy(IDspaceCollection dspaceCollection)
	{		
		if(dspaceCollection != null)
		{
			if(!dspaceCollection.getId().equals(this.id))
			{
				return false;
			}

			if(dspaceCollection.getName() != null)
			{
				this.name = dspaceCollection.getName();
			}

			if(dspaceCollection.getShortDescription() != null)
			{
				this.shortDescription = dspaceCollection.getShortDescription();
			}

			if(dspaceCollection.getEntityReference() != null)
			{
				this.entityReference = dspaceCollection.getEntityReference();
			}

			if(dspaceCollection.getHandle() != null)
			{
				this.handle = dspaceCollection.getHandle();
			}

			if(dspaceCollection.getCountItems() != null)
			{
				this.countItems = dspaceCollection.getCountItems();
			}

			this.items = Collections.synchronizedList(new ArrayList<IItem>());
			IItem item = null;
			if(dspaceCollection.getItemsEntity() != null)
			{
				if(dspaceCollection.getItemsEntity().getItems() != null)
				{
					for(IDspaceItem dspaceItem: dspaceCollection.getItemsEntity().getItems()){
						item = new Item();
						if(item.copy(dspaceItem))
							this.items.add(item);
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Used to generate the corresponding url necessary to access the collection details
	 * @param restPath The REST path required to access the collection in Dspace. This will be appended to the actual domain url.
	 * @return			Return the complete REST service url along with all the authentication information
	 */
	private String getCompleteUrlPath(String restPath)
	{
		return "https://"+this.url+restPath+this.id+".xml?email="+this.userName+"&password="+this.password;
	}

	/**
	 * This thread will make a REST service call to load the collection details. This service call will load details about the collection
	 * and all items in the collection. After the execution of this thread, the collection object will be populated with information. 
	 */
	@Override
	public void run() {
		String sRestServicePath = getCompleteUrlPath("/rest/collections/");
		IDspaceCollection dspaceCollection = (DspaceCollection) getRestTemplate().getForObject(sRestServicePath, DspaceCollection.class);

		if(dspaceCollection != null)
		{
			this.copy(dspaceCollection);
		}		
	}

	@Override
	public List<IItem> getItems() {
		return items;
	}

	@Override
	public void setItems(List<IItem> items) {
		this.items = items;
	}
}
