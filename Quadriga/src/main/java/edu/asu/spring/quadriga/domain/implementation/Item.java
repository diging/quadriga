package edu.asu.spring.quadriga.domain.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.dspace.service.IDspaceBitStreamEntityId;
import edu.asu.spring.quadriga.dspace.service.IDspaceItem;
import edu.asu.spring.quadriga.dspace.service.IDspaceItems;
import edu.asu.spring.quadriga.dspace.service.impl.DspaceItems;

/**
 * The class representation of the Item got from Dspace repostiory.
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

	private RestTemplate restTemplate;
	private String url;
	private String userName;
	private String password;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRestConnectionDetails(RestTemplate restTemplate, String url, String userName, String password)
	{
		this.restTemplate = restTemplate;
		this.url = url;
		this.userName = userName;
		this.password = password;
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
	public String getUrl() {
		return url;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
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
	public List<IBitStream> getBitstreams() {
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
	 * @param restPath The REST path required to access the item in Dspace. This will be appended to the actual domain url.
	 * @return			Return the complete REST service url along with all the authentication information
	 */
	private String getCompleteUrlPath(String restPath)
	{
		return "https://"+this.url+restPath+this.id+".xml?email="+this.userName+"&password="+this.password;
	}
	
	/**
	 * This thread will make a REST service call to load the bitstream details. This service call will load details about the bitstreams within this item. 
	 * After the execution of this thread, the item object will be populated with relevant bitstream information. 
	 */
	@Override
	public void run() {
		String sRestServicePath = getCompleteUrlPath("/rest/items/");
		IDspaceItems dspaceItems = (DspaceItems) getRestTemplate().getForObject(sRestServicePath, DspaceItems.class);
		if(dspaceItems != null)
		{
			//For each bitstream id fetch the load the data into the corresponding bitstream object
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
	}	
}
