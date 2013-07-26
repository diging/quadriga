/**
 * 
 */
package edu.asu.spring.quadriga.domain;

/**
 * This is interface is used to refer to concepts downloaded from the conceptpower. 
 * @author satyaswaroop boddu
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
