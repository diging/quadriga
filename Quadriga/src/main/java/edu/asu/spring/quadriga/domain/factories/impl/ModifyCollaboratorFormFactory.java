package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factories.IModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;

@Service
public class ModifyCollaboratorFormFactory implements
		IModifyCollaboratorFormFactory 
{
	@Override
	public ModifyCollaboratorForm createCollaboratorFormObject()
	{
		return new ModifyCollaboratorForm();
	}

}
