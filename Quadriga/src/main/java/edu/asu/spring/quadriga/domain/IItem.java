package edu.asu.spring.quadriga.domain;

import java.util.Date;
import java.util.List;

public interface IItem {

	public abstract String getIdentifierUri();
	
	public abstract void setIdentifierUri(String identifierUri);
	
	public abstract String getDescription();
	
	public abstract void setDescription(String description);
	
	public abstract String getTitle();
	
	public abstract void setTitle(String title);
	
	public abstract Date getDate();
	
	public abstract void setDate(Date date);
	
	public abstract List<IByteStream> getByteStream();
	
	public abstract void setByteStream(List<IByteStream> byteStream);
	
	public abstract void addByteStream(IByteStream byteStream);
}
