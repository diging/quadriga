package edu.asu.spring.quadriga.validator;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectAccessManager;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
@Service
public class ProjectValidator implements Validator {

	@Autowired
	IDBConnectionProjectAccessManager dbConnect;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectValidator.class);
	
	@Override
	public boolean supports(Class<?> arg0) {
		return arg0.isAssignableFrom(Project.class);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		//validate all the input parameters
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "name", "project_name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "description", "project_description.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "projectAccess", "project_projectAccess.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "unixName", "project_unixname.required");
		
		Project project = (Project)obj;
		
		String projUnixName = project.getUnixName();
		String projectId = project.getInternalid();
		EProjectAccessibility projectAccess = project.getProjectAccess();
		
		if(err.getFieldError("projectAccess")==null)
		{
			//validate the selected project accessibility
			validateProjectAccessibility(projectAccess,err);
		}
		
		if(err.getFieldError("unixName")==null)
		{
		//validate the regular expression
		validateUnixNameExp(projUnixName,err);
		}
		
		
		if(err.getFieldError("unixName")==null)
		{
			try
			{
				validateUnixName(projUnixName,projectId,err);
			}
			catch(QuadrigaStorageException e)
			{
				logger.error("Error", e);
			}
		}
	}
	
	public void validateProjectAccessibility( EProjectAccessibility projectAccess,Errors err)
	{
		if(projectAccess == null)
		{
			err.rejectValue("projectAccess", "project_projectAccess_selection.required");
		}
		
	}
	
	public void validateUnixNameExp(String unixName,Errors err)
	{
		String regex = "^[a-zA-Z0-9-_.+!*'()]*$";
		Pattern pattern = Pattern.compile(regex);
		
		if(!pattern.matcher(unixName).matches())
		{
			err.rejectValue("unixName","project_UnixName.expression");
		}
	}

	public void validateUnixName(String unixName,String projectId,Errors err) throws QuadrigaStorageException
	{
		boolean isDuplicate;
		
		//Verifying if the Unix name already exists
		isDuplicate = dbConnect.chkDuplicateProjUnixName(unixName,projectId);
		if(isDuplicate)
		{
			err.rejectValue("unixName","projectUnixName.unique");
		}
	}
}
