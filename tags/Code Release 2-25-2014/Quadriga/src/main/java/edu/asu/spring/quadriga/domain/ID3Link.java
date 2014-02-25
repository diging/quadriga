package edu.asu.spring.quadriga.domain;

/**
 * Interface to hold the link variables and methods in the network
 *
 */
public interface ID3Link {

	public abstract int getSource();

	public abstract void setSource(int source);

	public abstract int getTarget();

	public abstract void setTarget(int target);

}