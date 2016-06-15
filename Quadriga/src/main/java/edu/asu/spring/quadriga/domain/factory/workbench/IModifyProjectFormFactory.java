package edu.asu.spring.quadriga.domain.factory.workbench;

import edu.asu.spring.quadriga.web.workbench.backing.ModifyProjectForm;

/**
 * Factory method to create ModifyProjectform object
 * @author kiran batna
 *
 */
public interface IModifyProjectFormFactory {

	public abstract ModifyProjectForm createModifyProjectFormObject();
	
	public abstract ModifyProjectForm cloneModifyProjectFormObject(
			ModifyProjectForm projectForm);

}
