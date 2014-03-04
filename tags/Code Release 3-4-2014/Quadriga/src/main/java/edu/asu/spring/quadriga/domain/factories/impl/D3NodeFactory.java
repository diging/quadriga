package edu.asu.spring.quadriga.domain.factories.impl;

import java.util.ArrayList;
import java.util.List;

import edu.asu.spring.quadriga.domain.ID3Node;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factories.ID3NodeFactory;
import edu.asu.spring.quadriga.domain.implementation.D3Node;
/**
 * Factory for {@link ID3Node}
 * Used to form the JSON object for D3 JQuery
 * 
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
		List<String> statementIdList = new ArrayList<String>();
		// To add statement id to mark the Node's Statement
		d3Node.setStatementIdList(statementIdList);
		return d3Node;
	}
}
