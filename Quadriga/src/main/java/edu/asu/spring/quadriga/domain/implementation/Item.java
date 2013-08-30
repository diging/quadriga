package edu.asu.spring.quadriga.domain.implementation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.domain.factories.IBitStreamFactory;
import edu.asu.spring.quadriga.domain.factories.impl.BitStreamFactory;
import edu.asu.spring.quadriga.dspace.service.IDspaceBitStreamEntityId;
import edu.asu.spring.quadriga.dspace.service.IDspaceItem;
import edu.asu.spring.quadriga.dspace.service.IDspaceItems;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dspace.service.impl.DspaceItems;

/**
 * The class representation of the Item got from Dspace repostiory. When needed it also loads the bitstreams from dspace.
 * This class will be used by Quadriga and its representation is independent of the Dspace Rest service output.
 * 
 * @author Ram Kumar Kumaresan
 */
public class Item implements IItem{

	private String name;
	private String id;
	private String handle;
	private List<String> bitids;
	private List<IBitStream> bitstreams;
	private boolean isloaded;

	private RestTemplate restTemplate;
	private Properties dspaceProperties;
	private String userName;
	private String password;
	private IDspaceKeys dspaceKeys;

	private IBitStreamFactory bitstreamFactory;
	
	public Item()
	{
		this.bitstreamFactory = new BitStreamFactory();
		this.bitids = new ArrayList<String>();
		this.bitstreams = new ArrayList<IBitStream>();	
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRestConnectionDetails(RestTemplate restTemplate, Properties dspaceProperties, IDspaceKeys dspaceKeys, String userName, String password)
	{
		this.restTemplate = restTemplate;
		this.dspaceProperties = dspaceProperties;
		this.userName = userName;
		this.password = password;
		this.dspaceKeys = dspaceKeys;
		this.isloaded = false;
	}

	@Override
	public boolean getLoadStatus() {
		return isloaded;
	}

	@Override
	public void setLoadStatus(boolean isloaded) {
		this.isloaded = isloaded;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IBitStream> getBitstreams() {
		//Found the item that user requested !
		//This is the first time this item is accessed for bitstreams
		if(this.bitstreams.size() == 0)
		{
			//Create bitstream objects for each bitstream id
			IBitStream bitstream = null;
			for(String bitid: this.bitids)
			{
				bitstream = bitstreamFactory.createBitStreamObject();
				bitstream.setId(bitid);
				this.bitstreams.add(bitstream);
			}

			//Start thread to load the bitstream objects from dspace
			Thread bitstreamThread = new Thread(this);
			bitstreamThread.start();
		}

		//Return the bitstream objects
		return bitstreams;
	}
	@Override
	public void setBitstreams(List<IBitStream> bitstreams) {
		this.bitstreams = bitstreams;
	}
	@Override
	public void addBitstream(IBitStream bitstream)
	{
		this.bitstreams.add(bitstream);
	}
	@Override
	public List<String> getBitids() {
		return bitids;
	}
	@Override
	public void setBitids(List<String> bitids) {
		this.bitids = bitids;
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
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String getHandle() {
		return handle;
	}
	@Override
	public void setHandle(String handle) {
		this.handle = handle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean copy(IDspaceItem dspaceItem)
	{	
		boolean isCopied = false;
		if(dspaceItem != null)
		{
			if(dspaceItem.getName() != null)
			{
				this.name = dspaceItem.getName();
				isCopied = true;
			}

			if(dspaceItem.getHandle() != null)
			{
				this.handle = dspaceItem.getHandle();
				isCopied = true;
			}

			if(dspaceItem.getId() != null)
			{
				this.id = dspaceItem.getId();
				isCopied = true;
			}

			//Load all the bitids associated with the item.
			this.bitids = Collections.synchronizedList(new ArrayList<String>());
			this.bitstreams = Collections.synchronizedList(new ArrayList<IBitStream>());
			if(dspaceItem.getBitstreams() != null)
			{
				if(dspaceItem.getBitstreams().getBitstreamentityid() != null)
				{
					for(IDspaceBitStreamEntityId bitstream: dspaceItem.getBitstreams().getBitstreamentityid()){
						this.bitids.add(bitstream.getId());
					}
				}
			}

		}	
		return isCopied;
	}

	/**
	 * Used to generate the corresponding url necessary to access the item details
	 * @return			Return the complete REST service url along with all the authentication information
	 * @throws NoSuchAlgorithmException 
	 */
	private String getCompleteUrlPath() throws NoSuchAlgorithmException
	{
		if(this.dspaceKeys == null)
		{
			return dspaceProperties.getProperty("dspace_url")+
					dspaceProperties.getProperty("item_url")+this.id+dspaceProperties.getProperty("xml")+
					dspaceProperties.getProperty("?")+dspaceProperties.getProperty("email")+this.userName+
					dspaceProperties.getProperty("&")+dspaceProperties.getProperty("password")+this.password;
		}
		else
		{
			String stringToHash = dspaceProperties.getProperty("item_url")+this.id+dspaceProperties.getProperty("xml")+dspaceKeys.getPrivateKey();
			MessageDigest messageDigest = MessageDigest.getInstance(dspaceProperties.getProperty("algorithm"));
			messageDigest.update(stringToHash.getBytes());
			String digestKey = bytesToHex(messageDigest.digest()).substring(0, 8);

			return dspaceProperties.getProperty("dspace_url")+
					dspaceProperties.getProperty("item_url")+this.id+dspaceProperties.getProperty("xml")+
					dspaceProperties.getProperty("?")+dspaceProperties.getProperty("api_key")+
					dspaceKeys.getPublicKey() +dspaceProperties.getProperty("&")+dspaceProperties.getProperty("api_digest")+digestKey;
		}
	}

	/**
	 * This thread will make a REST service call to load the bitstream details. This service call will load details about the bitstreams within this item. 
	 * After the execution of this thread, the item object will be populated with relevant bitstream information. 
	 * 
	 * NOTE: Bitstreams with metadata are found to be returned by dspace. This function ignores those bitstreams.
	 */
	@Override
	public void run() {
		String sRestServicePath;
		try {
			sRestServicePath = getCompleteUrlPath();

			IDspaceItems dspaceItems = (DspaceItems) this.restTemplate.getForObject(sRestServicePath, DspaceItems.class);
			if(dspaceItems != null)
			{
				//For each bitstream id load the data into the corresponding bitstream object
				if(dspaceItems.getBitstreams().getBitstreamentityid() != null)
				{
					for(IDspaceBitStreamEntityId dspaceBitStream: dspaceItems.getBitstreams().getBitstreamentityid())
					{
						//Check if the bitstream is already present
						boolean ispresent = false;
						for(IBitStream bit: this.bitstreams)
						{
							if(bit.getName()!=null)
								if(dspaceBitStream.getName().contains(bit.getName()))
									ispresent = true;
						}

						//Do not load the bitstream if already present
						if(!ispresent)
						{
							for(IBitStream bitstream: this.bitstreams){
								if(bitstream.getId().equals(dspaceBitStream.getId()))
								{
									bitstream.setName(dspaceBitStream.getName());
									bitstream.setSize(dspaceBitStream.getSize());
									bitstream.setMimeType(dspaceBitStream.getMimeType());
									bitstream.setLoadStatus(true);
									break;
								}
							}
						}
					}

					//Remove the bitstream objects of the metadata files
					Iterator<IBitStream> bitstreamIterator = this.bitstreams.iterator();
					while(bitstreamIterator.hasNext())
					{
						if(bitstreamIterator.next().getName() == null)
							bitstreamIterator.remove();
					}				
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
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

	@Override
	public IBitStream getBitStream(String bitstreamid)
	{
		if(this.bitstreams!=null)
		{
			//This is the first time this item is accessed for bitstreams
			if(this.bitstreams.size() == 0)
			{
				//Bitstreams are yet to be loaded. So load them
				this.getBitstreams();
			}
			else
			{
				for(IBitStream bitstream: this.bitstreams)
				{
					if(bitstream.getId().equals(bitstreamid))
						return bitstream;
				}
			}
		}
		return null;		
	}
}
