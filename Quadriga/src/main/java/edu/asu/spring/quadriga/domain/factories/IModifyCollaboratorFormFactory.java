package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;

/**
 * Factory method to create modifyCollaboratorForm object
 * @author kiran batna
 *
 */
public interface IModifyCollaboratorFormFactory {

	public abstract ModifyCollaboratorForm createCollaboratorFormObject();

}
