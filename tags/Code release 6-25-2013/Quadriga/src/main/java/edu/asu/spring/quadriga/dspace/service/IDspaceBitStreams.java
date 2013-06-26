package edu.asu.spring.quadriga.dspace.service;

import java.util.List;

import edu.asu.spring.quadriga.dspace.service.IDspaceBitStreamEntityId;

public interface IDspaceBitStreams {

	public abstract List<IDspaceBitStreamEntityId> getBitstreams();

	public abstract void setBitstreams(List<IDspaceBitStreamEntityId> bitstreams);

}