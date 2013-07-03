package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceCollection;
import edu.asu.spring.quadriga.dspace.service.IDspaceItem;
import edu.asu.spring.quadriga.dspace.service.IDspaceItemEntity;


@XmlRootElement(name="items")
public class DspaceItemEntity implements IDspaceItemEntity {

	private List<IDspaceItem> items;

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IItemEntity#getItems()
	 */
	@XmlElementRefs({@XmlElementRef(type=DspaceItem.class)})
	@Override
	public List<IDspaceItem> getItems() {
		return items;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IItemEntity#setItems(java.util.List)
	 */
	@Override
	public void setItems(List<IDspaceItem> items) {
		this.items = items;
	}
	
	public static class Adapter extends XmlAdapter<DspaceItemEntity, IDspaceItemEntity>
	{

		@Override
		public IDspaceItemEntity unmarshal(DspaceItemEntity v) throws Exception {
			return v;
		}

		@Override
		public DspaceItemEntity marshal(IDspaceItemEntity v) throws Exception {
			return (DspaceItemEntity)v;
		}
		
	}
}
