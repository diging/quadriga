package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factories.IModifyProjectFormFactory;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyProjectForm;


@Service
public class ModifyProjectFormFactory implements IModifyProjectFormFactory {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ModifyProjectForm createModifyProjectForm()
	{
		return new ModifyProjectForm();
	}

}
