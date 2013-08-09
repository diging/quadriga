package edu.asu.spring.quadriga.dspace.service;

public interface IDspaceKeys {

	public abstract String getPublicKey();

	public abstract void setPublicKey(String publicKey);

	public abstract String getPrivateKey();

	public abstract void setPrivateKey(String privateKey);

	public abstract String getDigestKey();

	public abstract void setDigestKey(String digestKey);

}