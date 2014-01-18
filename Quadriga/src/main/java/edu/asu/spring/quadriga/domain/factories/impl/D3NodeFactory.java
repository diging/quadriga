package edu.asu.spring.quadriga.domain.factories.impl;

import edu.asu.spring.quadriga.domain.ID3Node;
import org.springframework.stereotype.Service;
import edu.asu.spring.quadriga.domain.factories.ID3NodeFactory;
import edu.asu.spring.quadriga.domain.implementation.D3Node;

@Service
public class D3NodeFactory implements ID3NodeFactory  {

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.factories.impl.ID3NodeFactory#createD3NodeObject()
	 */
	@Override
	public ID3Node createD3NodeObject() {
		// TODO Auto-generated method stub
		return new D3Node();
	}
}
