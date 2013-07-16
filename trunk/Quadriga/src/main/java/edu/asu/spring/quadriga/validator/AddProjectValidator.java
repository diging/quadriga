package edu.asu.spring.quadriga.validator;

import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectAccessManager;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
@Service
public class AddProjectValidator implements Validator {

	@Autowired
	@Qualifier("DBConnectionProjectAccessManagerBean")
	IDBConnectionProjectAccessManager dbConnect;
	
	//@Resource(name = "validationMessages")
	//private Properties errorProperties;
	
	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return arg0.isAssignableFrom(Project.class);
	}

	@Override
	public void validate(Object obj, Errors err) {

		Project project = (Project)obj;
		String projName = project.getName();
		String projDescription = project.getDescription();
		String projUnixName = project.getUnixName();
		
		validateProjName(projName,err);
		validateProjDescription(projDescription,err);
		try
		{
			validateUnixName(projUnixName,err);
		}
		catch(QuadrigaStorageException e)
		{
			e.printStackTrace();
		}
	}
	
	public void validateProjName(String projName,Errors err)
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "name", "projName.required");
	}
	
	public void validateProjDescription(String description,Errors err)
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "description", "projDescription.required");
	}

	public void validateUnixName(String unixName,Errors err) throws QuadrigaStorageException
	{
		boolean isDuplicate;
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "unixName", "projectUnixName.required");
		
		//Verifying if the Unix name already exists
		isDuplicate = dbConnect.chkDuplicateProjUnixName(unixName);
		
		if(isDuplicate)
		{
			err.rejectValue("unixName","projectUnixName.unique");
		}
	}
}
