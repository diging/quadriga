package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.domain.INetworkNodeInfo;

/**
 * Factory interface to create NetworkNodeInfo object
 * @author kiran batna
 *
 */
public interface INetworkNodeInfoFactory {

	public abstract INetworkNodeInfo createNetworkNodeInfoObject();

}