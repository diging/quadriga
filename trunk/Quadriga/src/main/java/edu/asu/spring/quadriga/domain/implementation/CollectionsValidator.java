package edu.asu.spring.quadriga.domain.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.db.IDBConnectionCCManager;
import edu.asu.spring.quadriga.domain.implementation.ConceptCollection;
@Service
public class CollectionsValidator implements Validator {

	@Autowired
	@Qualifier("DBConnectionCCManagerBean")
	private IDBConnectionCCManager dbConnect;
	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return arg0.isAssignableFrom(ConceptCollection.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ConceptCollection c = (ConceptCollection) obj;
		String id = c.getId();
		validateId(id, errors);
	}

	private void validateId(String id, Errors errors) {
		// TODO Auto-generated method stub
		String ret=dbConnect.validateId(id);
		
		if(!( ret == null || ret.isEmpty()) )
		{
			errors.rejectValue("id", "CollectionsValidator.id.notValid",
			          "Not a Unique Id");
		}
	}

}
