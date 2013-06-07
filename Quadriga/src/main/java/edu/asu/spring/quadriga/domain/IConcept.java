/**
 * 
 */
package edu.asu.spring.quadriga.domain;

/**
 * @author satyaswaroop
 *
 */
public interface IConcept {
	
	public abstract String getName();
	public abstract String getPos();
	public abstract void setName(String name);
	
	
	public abstract void setDescription(String discription);
	public abstract  String getDescription();
	public abstract void setPos(String pos);

}
