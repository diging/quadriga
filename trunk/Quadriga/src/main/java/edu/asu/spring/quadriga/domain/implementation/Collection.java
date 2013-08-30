package edu.asu.spring.quadriga.domain.implementation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.domain.factories.IItemFactory;
import edu.asu.spring.quadriga.domain.factories.impl.ItemFactory;
import edu.asu.spring.quadriga.dspace.service.IDspaceCollection;
import edu.asu.spring.quadriga.dspace.service.IDspaceItem;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dspace.service.impl.DspaceCollection;

/**
 * The class representation of the Collection got from Dspace repostiory. It also loads the dependent items within this collection.
 * This class will be used by Quadriga and its representation is independent of the Dspace Rest service output.
 * 
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
	private boolean isloaded;

	private RestTemplate restTemplate;
	private Properties dspaceProperties;
	private String userName;
	private String password;
	private IDspaceKeys dspaceKeys;
	
	private IItemFactory itemFactory;

	public Collection()
	{
		this.items = new ArrayList<IItem>();
		this.isloaded = false;
		this.itemFactory = new ItemFactory();
	}
	
	/**
	 * Initialize the required details to make a REST service call to Dspace
	 * @param id				The id of the collection.
	 * @param restTemplate		The RestTemplate object containing the details about the parser.
	 * @param dspaceProperties	The property strings related to dspace REST service connection.
	 * @param dspaceKeys		The Dspace Access keys used by the user.
	 * @param userName			The username of the authorized user.
	 * @param password			The password of the authorized user.
	 */
	public Collection(String id, RestTemplate restTemplate, Properties dspaceProperties, IDspaceKeys dspaceKeys, String userName, String password)
	{
		this.dspaceProperties = dspaceProperties;
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.restTemplate = restTemplate;
		this.dspaceProperties = dspaceProperties;
		this.dspaceKeys = dspaceKeys;
		this.isloaded = false;
		this.itemFactory = new ItemFactory();
	}

	@Override
	public boolean getLoadStatus() {
		return isloaded;
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
						item = itemFactory.createItemObject();
						item.setRestConnectionDetails(restTemplate, dspaceProperties, dspaceKeys, userName, password);
						if(item.copy(dspaceItem))
						{
							item.setLoadStatus(true);
							this.items.add(item);
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Used to generate the corresponding url necessary to access the collection details
	 * @return			Return the complete REST service url along with all the authentication information
	 * @throws NoSuchAlgorithmException 
	 */
	private String getCompleteUrlPath() throws NoSuchAlgorithmException
	{
		if(this.dspaceKeys == null)
		{
			return dspaceProperties.getProperty("dspace_url")+
					dspaceProperties.getProperty("collection_url")+	this.id+
					dspaceProperties.getProperty("xml")+dspaceProperties.getProperty("?")+dspaceProperties.getProperty("email")+
					this.userName +dspaceProperties.getProperty("&")+dspaceProperties.getProperty("password")+this.password;
		}
		else
		{
			String stringToHash = dspaceProperties.getProperty("collection_url")+this.id+dspaceProperties.getProperty("xml")+dspaceKeys.getPrivateKey();
			MessageDigest messageDigest = MessageDigest.getInstance(dspaceProperties.getProperty("algorithm"));
			messageDigest.update(stringToHash.getBytes());
			String digestKey = bytesToHex(messageDigest.digest());
			digestKey = digestKey.substring(0, 8);
			
			return dspaceProperties.getProperty("dspace_url")+
					dspaceProperties.getProperty("collection_url")+	this.id+
					dspaceProperties.getProperty("xml")+dspaceProperties.getProperty("?")+dspaceProperties.getProperty("api_key")+
					dspaceKeys.getPublicKey() +dspaceProperties.getProperty("&")+dspaceProperties.getProperty("api_digest")+digestKey;
		}
	}

	private String bytesToHex(byte[] b) {
		char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		StringBuffer buf = new StringBuffer();
		for (int j=0; j<b.length; j++) {
			buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
			buf.append(hexDigit[b[j] & 0x0f]);
		}
		return buf.toString();
	}

	/**
	 * This thread will make a REST service call to load the collection details. This service call will load details about the collection
	 * and all items in the collection. After the execution of this thread, the collection object will be populated with information. 
	 */
	@Override
	public void run() {
		String sRestServicePath;
		try {
			sRestServicePath = getCompleteUrlPath();

			IDspaceCollection dspaceCollection = (DspaceCollection) this.restTemplate.getForObject(sRestServicePath, DspaceCollection.class);
			if(dspaceCollection != null)
			{
				this.copy(dspaceCollection);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			this.isloaded = true;
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
	
	@Override
	public IItem getItem(String itemid)
	{
		if(this.items != null)
		{
			for(IItem item: this.items)
			{
				if(item.getId().equals(itemid))
				{
					return item;
				}
			}
		}
		return null;
	}
}
