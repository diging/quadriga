package edu.asu.spring.quadriga.domain.impl;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionDAO;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.conceptcollection.impl.ConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This class validates if the concept collection name is unique and valid.
 * @author Satya swaroop
 *
 */
@Service
public class CollectionsValidator implements Validator {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CollectionsValidator.class);

	@Autowired
	private IConceptCollectionDAO dbConnect;
	@Override
	public boolean supports(Class<?> arg0) {
		return arg0.isAssignableFrom(ConceptCollection.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		IConceptCollection c = (IConceptCollection) obj;
		String id = c.getConceptCollectionName();
		
			try {
				validateId(id, errors);
			} catch (QuadrigaStorageException e) {
				e.printStackTrace();
			}
		
	}

	@Transactional
	private void validateId(String id, Errors errors) throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		String ret=dbConnect.validateId(id);
		logger.info("After Validater:"+ret);
		if(!( ret == null || ret.isEmpty()) )
		{
			errors.rejectValue("name", "CollectionsValidator.id.notValid",
			          "Not a Unique Id");
		}
	}

}
