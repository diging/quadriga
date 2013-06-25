package edu.asu.spring.quadriga.domain.implementation;

import java.util.ArrayList;
import java.util.List;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.dspace.service.IDspaceBitStreamEntityId;
import edu.asu.spring.quadriga.dspace.service.IDspaceItem;

/**
 * The class representation of the Item got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 */
public class Item implements IItem{

	private String name;
	private String id;
	private String handle;
	private List<String> bitids;
	private List<IBitStream> bitstreams;
	
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
			this.bitids = new ArrayList<String>();
			this.setBitstreams(new ArrayList<IBitStream>());
			if(dspaceItem.getBitstreams() != null)
			{
				if(dspaceItem.getBitstreams().getBitstreams() != null)
				{
					for(IDspaceBitStreamEntityId bitstream: dspaceItem.getBitstreams().getBitstreams()){
						this.bitids.add(bitstream.getId());
					}
				}
			}
			
		}	
		return isCopied;
	}
}
