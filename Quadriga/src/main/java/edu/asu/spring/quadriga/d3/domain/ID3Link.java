package edu.asu.spring.quadriga.d3.domain;

import java.util.HashMap;
import java.util.List;

import edu.asu.spring.quadriga.d3.domain.impl.D3Node;

/**
 * Interface to hold the link between source to target in the network.
 * This is very specific to D3 JQuery, we can use this interface to build the
 * D3 Json for force directed graph.
 *
 *	@author Lohith Dwaraka
 */

public interface ID3Link {

	/**
	 * Getter for the source where the link would start
	 * @return 		returns the source node number from  {@link HashMap} of {@link D3Node}
	 */
	public abstract int getSource();

	/**
	 * Setter for the source where the link would start. We would set the {@link D3Node} number from the {@link HashMap} of {@link D3Node}
	 * @param source		{@link Integer} value of D3Node number from {@link HashMap} of {@link D3Node}.
	 */
	public abstract void setSource(int source);

	/**
	 * Getter for the target where the link would end
	 * @return 		returns the target node number from {@link HashMap} of {@link D3Node}
	 */
	public abstract int getTarget();

	/**
	 * Setter for the target where the link would start. We would set the {@link D3Node} number from the {@link HashMap} of {@link D3Node}
	 * @param target		{@link Integer} value of D3Node number from {@link HashMap} of {@link D3Node}.
	 */
	public abstract void setTarget(int target);

}