package edu.asu.spring.quadriga.domain.factory.impl.workbench;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workbench.IModifyProjectFormFactory;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyProject;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyProjectForm;


@Service
public class ModifyProjectFormFactory implements IModifyProjectFormFactory {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ModifyProjectForm createModifyProjectFormObject()
	{
		return new ModifyProjectForm();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ModifyProjectForm cloneModifyProjectFormObject(
			ModifyProjectForm projectForm) 
	{
		ModifyProjectForm clone = new ModifyProjectForm();
		List<ModifyProject> cloneProjectList = new ArrayList<ModifyProject>();
		for(ModifyProject project : projectForm.getProjectList())
		{
			cloneProjectList.add(project);
		}
		clone.setProjectList(cloneProjectList);
		
		return clone;
	}
}
