package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceBitStream;
import edu.asu.spring.quadriga.dspace.service.IDspaceBitStreamEntityId;

/**
 * The class representation of the bitstream list got from the Dspace repository
 * 
 * @author Ram Kumar Kumaresan
 */
@XmlRootElement(name="bitstreams")
public class DspaceBitStreams implements IDspaceBitStream {

	private List<IDspaceBitStreamEntityId> bitstreamentityid;

	@XmlElementRefs({@XmlElementRef(type=DspaceBitStreamEntity.class)})
	@Override
	public List<IDspaceBitStreamEntityId> getBitstreamentityid() {
		return bitstreamentityid;
	}

	@Override
	public void setBitstreamentityid(List<IDspaceBitStreamEntityId> bitstreamentityid) {
		this.bitstreamentityid = bitstreamentityid;
	}
	
	public static class Adapter extends XmlAdapter<DspaceBitStreams, IDspaceBitStream>
	{
		@Override
		public IDspaceBitStream unmarshal(DspaceBitStreams v) throws Exception {
			return v;
		}

		@Override
		public DspaceBitStreams marshal(IDspaceBitStream v) throws Exception {
			return (DspaceBitStreams)v;
		}		
	}
	
}
