package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaboratorForm;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFormFactory;
import edu.asu.spring.quadriga.domain.implementation.CollaboratorForm;

@Service
public class CollaboratorFormFactory implements ICollaboratorFormFactory 
{
	
   @Override
public ICollaboratorForm createCollaboratorForm()
   {
	   return new CollaboratorForm();
   }
   
   @Override
public ICollaboratorForm cloneCollaboratorForm(ICollaboratorForm collaborator)
   {
	   ICollaboratorForm clone = createCollaboratorForm();
	   
	   clone.setCollaborator(collaborator.getCollaborator());
	   
	   return clone;
   }
}
