package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.ID3Link;


public class D3Link implements ID3Link {

	private int source;
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ID3Link#getSource()
	 */
	@Override
	public int getSource() {
		return source;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ID3Link#setSource(int)
	 */
	@Override
	public void setSource(int source) {
		this.source = source;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ID3Link#getTarget()
	 */
	@Override
	public int getTarget() {
		return target;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ID3Link#setTarget(int)
	 */
	@Override
	public void setTarget(int target) {
		this.target = target;
	}
	private int target;
	
}
