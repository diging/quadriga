package edu.asu.spring.quadriga.domain.implementation;


import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICollections;

/**
 * The class representation of the collections list got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
@XmlRootElement(name="collections")
public class Collections implements ICollections{

	private List<ICollection> collections;

	@XmlElementRefs({@XmlElementRef(type=Collection.class)})
	@Override
	public List<ICollection> getCollections() {
		return collections;
	}

	@Override
	public void setCollections(List<ICollection> collections) {
		this.collections = collections;
	}
	
	public static class Adapter extends XmlAdapter<Collections, ICollections>
	{
		@Override
		public ICollections unmarshal(Collections v) throws Exception {
			return v;
		}

		@Override
		public Collections marshal(ICollections v) throws Exception {
			return (Collections)v;
		}
	}
}
