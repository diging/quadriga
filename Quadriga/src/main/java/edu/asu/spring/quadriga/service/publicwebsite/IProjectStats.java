package edu.asu.spring.quadriga.service.publicwebsite;

import java.util.List;

import edu.asu.spring.quadriga.domain.IConceptStats;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectStats {

	public abstract List<IConceptStats> getTopConcepts(List<INetwork> Networks)
	        throws QuadrigaStorageException;

}