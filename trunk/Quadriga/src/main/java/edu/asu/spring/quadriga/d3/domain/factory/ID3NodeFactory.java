package edu.asu.spring.quadriga.d3.domain.factory;

import edu.asu.spring.quadriga.d3.domain.ID3Node;
import edu.asu.spring.quadriga.d3.domain.impl.D3Node;

/**
 * Interface for the {@link ID3Node} factory class which can help in creating factory object of {@link D3Node}
 * @author Lohith Dwaraka
 *
 */
public interface ID3NodeFactory {

	/**
	 * Creates the factory object of {@link D3Node} with any customization needed
	 * @return			Object of {@link ID3Node}
	 */
	public abstract ID3Node createD3NodeObject();

}