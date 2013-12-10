package edu.asu.spring.quadriga.dspace.service.impl;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The class representation of the bitstream list got from the Dspace repository url /rest/bitstreams/<id>.xml
 * This representation is used when making a REST service call to get the item , collection and community information.
 * 
 * @author Ram Kumar Kumaresan
 */
@XmlRootElement(name="bitstreams")
public class DspaceBitStreamMetadata{

	private String checkSum;

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}
	
}
