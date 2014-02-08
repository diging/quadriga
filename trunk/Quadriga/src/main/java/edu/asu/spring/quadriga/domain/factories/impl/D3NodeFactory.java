package edu.asu.spring.quadriga.domain.factories.impl;

import java.util.ArrayList;
import java.util.List;

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
		ID3Node d3Node = new D3Node();
		List<String> statementIdList = new ArrayList<String>();
		d3Node.setStatementIdList(statementIdList);
		return d3Node;
	}
}
