package edu.asu.spring.quadriga.domain.factories.impl;

import java.util.Properties;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.dspace.ICollection;
import edu.asu.spring.quadriga.domain.factories.ICollectionFactory;
import edu.asu.spring.quadriga.domain.impl.dspace.Collection;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;

/**
 * Factory class for creating {@link @Collection}
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@Service
public class CollectionFactory implements ICollectionFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICollection createCollectionObject()
	{
		return new Collection();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICollection createCollectionObject(String collectionid, String communityid, RestTemplate restTemplate, Properties dspaceProperties, IDspaceKeys dspaceKeys, String userName, String password)
	{
		return new Collection(collectionid, communityid, restTemplate, dspaceProperties, dspaceKeys, userName, password);
	}
}
