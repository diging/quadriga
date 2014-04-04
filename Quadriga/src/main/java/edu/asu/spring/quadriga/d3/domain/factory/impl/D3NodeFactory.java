package edu.asu.spring.quadriga.d3.domain.factory.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.d3.domain.ID3Node;
import edu.asu.spring.quadriga.d3.domain.factory.ID3NodeFactory;
import edu.asu.spring.quadriga.d3.domain.impl.D3Node;
import edu.asu.spring.quadriga.service.network.domain.IStatementObject;

/**
 * Implementation of {@link ID3NodeFactory}. Creates the factory object of {@link ID3Node} * 
 * @author Dwaraka Lohith
 */

@Service
public class D3NodeFactory implements ID3NodeFactory  {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ID3Node createD3NodeObject() {
		ID3Node d3Node = new D3Node();
		List<IStatementObject> statementIdList = new ArrayList<IStatementObject>();
		// To add statement id to mark the Node's Statement
		d3Node.setStatementIdList(statementIdList);
		return d3Node;
	}
}
