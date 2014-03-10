package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.web.workspace.backing.ModifyWorkspaceForm;

/**
 * Factory interface to create ModifyWorkspaceForm object
 * @author kiran batna
 *
 */
public interface IWorkspaceFormFactory {

	public abstract ModifyWorkspaceForm createModifyWorkspaceForm();

}
