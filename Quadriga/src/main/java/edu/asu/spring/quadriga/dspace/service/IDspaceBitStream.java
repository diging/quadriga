package edu.asu.spring.quadriga.dspace.service;

import java.util.List;

import edu.asu.spring.quadriga.dspace.service.IDspaceBitStreamEntityId;

public interface IDspaceBitStream {

	public abstract List<IDspaceBitStreamEntityId> getBitstreamentityid();

	public abstract void setBitstreamentityid(List<IDspaceBitStreamEntityId> bitstreams);

}