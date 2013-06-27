package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceBitStream;
import edu.asu.spring.quadriga.dspace.service.IDspaceCollection;
import edu.asu.spring.quadriga.dspace.service.IDspaceItem;
import edu.asu.spring.quadriga.dspace.service.IDspaceItemEntity;
import edu.asu.spring.quadriga.dspace.service.IDspaceItems;

/**
 * This class is used to read the individual bitstreams associated with an item in Dspace.
 * @author Ram Kumar Kumaresan
 *
 */
@XmlRootElement(name="items")
public class DspaceItems implements IDspaceItems {

	private IDspaceBitStream bitstreams;

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IDspaceItems#getBitstreams()
	 */
	@XmlElementRefs({@XmlElementRef(type=DspaceBitStreams.class)})
	@Override
	public IDspaceBitStream getBitstreams() {
		return bitstreams;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IDspaceItems#setBitstreams(edu.asu.spring.quadriga.dspace.service.IDspaceBitStreams)
	 */
	@Override
	public void setBitstreams(IDspaceBitStream bitstreams) {
		this.bitstreams = bitstreams;
	}
}
