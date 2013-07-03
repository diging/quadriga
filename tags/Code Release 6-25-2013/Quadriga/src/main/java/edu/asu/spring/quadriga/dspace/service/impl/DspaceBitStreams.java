package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceBitStreamEntityId;
import edu.asu.spring.quadriga.dspace.service.IDspaceBitStreams;

@XmlRootElement(name="bitstreams")
public class DspaceBitStreams implements IDspaceBitStreams {

	private List<IDspaceBitStreamEntityId> bitstreams;

	@XmlElementRefs({@XmlElementRef(type=DspaceBitStreamEntityId.class)})
	@Override
	public List<IDspaceBitStreamEntityId> getBitstreams() {
		return bitstreams;
	}

	@Override
	public void setBitstreams(List<IDspaceBitStreamEntityId> bitstreams) {
		this.bitstreams = bitstreams;
	}
	
	public static class Adapter extends XmlAdapter<DspaceBitStreams, IDspaceBitStreams>
	{
		@Override
		public IDspaceBitStreams unmarshal(DspaceBitStreams v) throws Exception {
			return v;
		}

		@Override
		public DspaceBitStreams marshal(IDspaceBitStreams v) throws Exception {
			return (DspaceBitStreams)v;
		}		
	}
	
}
