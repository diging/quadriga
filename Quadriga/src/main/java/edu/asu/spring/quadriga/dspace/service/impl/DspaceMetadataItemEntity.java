package edu.asu.spring.quadriga.dspace.service.impl;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataItemEntity;


@XmlRootElement(name="itementity")
public class DspaceMetadataItemEntity implements IDspaceMetadataItemEntity {

	private String id;
	private String handle;
	private String name;
	
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
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	
	public static class Adapter extends XmlAdapter<DspaceMetadataItemEntity, IDspaceMetadataItemEntity>
	{

		@Override
		public IDspaceMetadataItemEntity unmarshal(DspaceMetadataItemEntity v) throws Exception {
			return v;
		}

		@Override
		public DspaceMetadataItemEntity marshal(IDspaceMetadataItemEntity v) throws Exception {
			return (DspaceMetadataItemEntity)v;
		}
	}
}
