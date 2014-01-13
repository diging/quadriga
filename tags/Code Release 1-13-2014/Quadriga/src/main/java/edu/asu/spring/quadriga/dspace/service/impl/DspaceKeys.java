package edu.asu.spring.quadriga.dspace.service.impl;

import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;

public class DspaceKeys implements IDspaceKeys {

	private String publicKey;
	private String privateKey;
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IDspaceKeys#getPublicKey()
	 */
	@Override
	public String getPublicKey() {
		return publicKey;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IDspaceKeys#setPublicKey(java.lang.String)
	 */
	@Override
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IDspaceKeys#getPrivateKey()
	 */
	@Override
	public String getPrivateKey() {
		return privateKey;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IDspaceKeys#setPrivateKey(java.lang.String)
	 */
	@Override
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
}
