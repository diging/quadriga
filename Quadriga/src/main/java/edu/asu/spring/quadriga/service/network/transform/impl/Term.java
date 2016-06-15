package edu.asu.spring.quadriga.service.network.transform.impl;

import java.util.ArrayList;
import java.util.List;

public class Term {

	private List<TermPart> termParts;
	private String sourceUri;

	public List<TermPart> getTermParts() {
		return termParts;
	}
	public void setTermParts(List<TermPart> termParts) {
		this.termParts = termParts;
	}
	public String getSourceUri() {
		return sourceUri;
	}
	public void setSourceUri(String sourceUri) {
		this.sourceUri = sourceUri;
	}
	
	public void addTermPart(TermPart termPart) {
		if(termParts == null) {
			termParts = new ArrayList<TermPart>();
		}
		termParts.add(termPart);
	}
	
}
