package edu.asu.spring.quadriga.d3.domain.impl;

import edu.asu.spring.quadriga.d3.domain.ID3Link;

/**
 * Implemenation of {@link ID3Link} domain interface.
 * Used specifically for D3 Jquery JSON building.
 * @author Lohith Dwaraka
 *
 */
public class D3Link implements ID3Link {

	private int source;
	private int target;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSource() {
		return source;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSource(int source) {
		this.source = source;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTarget() {
		return target;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTarget(int target) {
		this.target = target;
	}
	
	
}
