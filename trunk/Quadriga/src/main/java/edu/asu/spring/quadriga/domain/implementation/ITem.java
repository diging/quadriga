package edu.asu.spring.quadriga.domain.implementation;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.IByteStream;
import edu.asu.spring.quadriga.domain.IItem;

public class ITem implements IItem{

	private String idetifierUri;
	private String description;
	private String title;
	private Date date;
	private List<IByteStream> byteStream;
	
	@Override
	public String getIdentifierUri() {
		return this.idetifierUri;
	}

	@Override
	public void setIdentifierUri(String identifierUri) {
		this.idetifierUri = identifierUri;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public Date getDate() {
		return this.date;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public List<IByteStream> getByteStream() {
		return this.byteStream;
	}

	@Override
	public void setByteStream(List<IByteStream> byteStream) {
		this.byteStream = byteStream;
	}

	@Override
	public void addByteStream(IByteStream byteStream) {
		this.byteStream.add(byteStream);
	}

	
}
