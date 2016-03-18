package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;

/**
 * Factory interface to create DSpaceKeys object
 * @author kiran batna
 *
 */
public interface IDspaceKeysFactory {

	public abstract IDspaceKeys createDspaceKeysObject();

}