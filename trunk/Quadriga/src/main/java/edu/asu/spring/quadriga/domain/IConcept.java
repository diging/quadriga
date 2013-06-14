/**
 * 
 */
package edu.asu.spring.quadriga.domain;

/**
 * @author satyaswaroop
 *
 */
public interface IConcept {
	
	public abstract String getId();
	public abstract String getPos();
	public abstract void setId(String name);
	
	
	
	public abstract void setDescription(String discription);
	public abstract  String getDescription();
	public abstract void setPos(String pos);
	public abstract String getLemma() ;

	public abstract void setLemma(String lemma);

}
