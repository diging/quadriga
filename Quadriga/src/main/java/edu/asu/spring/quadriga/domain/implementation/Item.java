package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.IItem;
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
		}	
		return isCopied;
	}
}
