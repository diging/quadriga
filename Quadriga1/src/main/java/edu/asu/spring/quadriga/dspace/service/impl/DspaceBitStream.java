package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceBitStream;
import edu.asu.spring.quadriga.dspace.service.IDspaceBitStreamEntityId;

/**
 * The class representation of the bitstream list got from the Dspace repository.
 * This representation is used when making a REST service call to get the item and bitstream information.
 * 
 * @author Ram Kumar Kumaresan
 */
@XmlRootElement(name="bitstreams")
public class DspaceBitStream implements IDspaceBitStream {

	private List<IDspaceBitStreamEntityId> bitstreamentityid;

	@XmlElementRefs({@XmlElementRef(type=DspaceBitStreamEntityId.class)})
	@Override
	public List<IDspaceBitStreamEntityId> getBitstreamentityid() {
		return bitstreamentityid;
	}

	@Override
	public void setBitstreamentityid(List<IDspaceBitStreamEntityId> bitstreamentityid) {
		this.bitstreamentityid = bitstreamentityid;
	}
	
	public static class Adapter extends XmlAdapter<DspaceBitStream, IDspaceBitStream>
	{
		@Override
		public IDspaceBitStream unmarshal(DspaceBitStream v) throws Exception {
			return v;
		}

		@Override
		public DspaceBitStream marshal(IDspaceBitStream v) throws Exception {
			return (DspaceBitStream)v;
		}		
	}
	
}
