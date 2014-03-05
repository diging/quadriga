package edu.asu.spring.quadriga.d3.domain.factory;

import edu.asu.spring.quadriga.d3.domain.ID3Link;
import edu.asu.spring.quadriga.d3.domain.impl.D3Link;

/**
 * Interface for {@link ID3Link} factory class which can help in creating factory object of {@link D3Link}
 * @author Lohith Dwaraka
 *
 */
public interface ID3LinkFactory {
	/**
	 * Creates the factory object of {@link D3Link} with any customization needed
	 * @return			Object of {@link ID3Link}
	 */
	public abstract ID3Link createD3LinkObject();

}