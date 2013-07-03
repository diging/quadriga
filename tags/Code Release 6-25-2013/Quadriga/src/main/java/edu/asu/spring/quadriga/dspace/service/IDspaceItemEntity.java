package edu.asu.spring.quadriga.dspace.service;

import java.util.List;


public interface IDspaceItemEntity {

	public abstract List<IDspaceItem> getItems();

	public abstract void setItems(List<IDspaceItem> items);

}