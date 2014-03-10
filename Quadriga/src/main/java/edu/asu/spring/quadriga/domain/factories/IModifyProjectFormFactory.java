package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.web.workbench.backing.ModifyProjectForm;

/**
 * Factory method to create ModifyProjectform object
 * @author kiran batna
 *
 */
public interface IModifyProjectFormFactory {

	public abstract ModifyProjectForm createModifyProjectForm();

}
