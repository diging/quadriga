/**
 * 
 */
package edu.asu.spring.quadriga.domain.conceptcollection;

import java.util.List;

/**
 * This is interface is used to refer to concepts downloaded from the conceptpower. 
 * @author satyaswaroop boddu
 *
 */
public interface IConcept 
{
	public abstract String getConceptId();
	
	public abstract void setConceptId(String conceptId);
	
	public abstract String getPos();
	
	public abstract void setPos(String pos);
	
	public abstract void setDescription(String discription);
	
	public abstract  String getDescription();
	
	public abstract String getLemma() ;

	public abstract void setLemma(String lemma);
	
	public abstract List<IConceptCollectionConcepts> getConceptCollectionConcepts();
	
	public abstract void setConceptCollectionConcepts(List<IConceptCollectionConcepts> conceptCollectionConcepts);
}
