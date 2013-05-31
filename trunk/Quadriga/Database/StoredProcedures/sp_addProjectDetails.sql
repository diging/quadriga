/*******************************************
Name          : sp_addProjectDetails

Description   : adds the project details to
				tbl_project table

Called By     : UI (DBConnectionManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 05/28/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_addProjectDetails;
DELIMITER $$
CREATE PROCEDURE sp_addProjectDetails
(
  IN  inprojectname    VARCHAR(50),
  IN  indescription    TEXT,
  IN  inprojectid      VARCHAR(100),
  IN  inaccessibility  TINYINT,
  IN  inprojectowner   VARCHAR(50),
  OUT errmsg           VARCHAR(255)    
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- validating the input variables
    IF(inprojectname IS NULL OR inprojectname = "")
	  THEN SET errmsg = "project name cannot be empty.";
    END IF;

    IF EXISTS(SELECT 1 FROM vw_project
                WHERE projectname = inprojectname)
      THEN SET errmsg = "project name already exists.";
	END IF;

    IF(inprojectid IS NULL OR inprojectid = "")
      THEN SET errmsg = "projectid cannot be empty.";
    END IF;

	IF EXISTS(SELECT 1 FROM vw_project
                WHERE projectid = inprojectid)
      THEN SET errmsg = "projectid is already assigned to a project.";
     END IF;

    IF(inaccessibility IS NULL)
       THEN SET errmsg = "accessibility cannot be empty";
    END IF;

    IF (inprojectowner IS NULL OR inprojectowner = "")
	 THEN SET errmsg = "project owner cannot be empty";
	END IF;

    IF NOT EXISTS(SELECT 1 FROM vw_quadriga_user
				   WHERE username = inprojectowner)
      THEN SET errmsg = "Invalid owner.Please enter the correct value.";
    END IF; 

    -- Inserting the record into the tbl_project table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
            INSERT 
              INTO tbl_project(projectname,description,projectid,projectowner,accessibility,
                         updatedby,updateddate,createdby,createddate)
			 VALUES (inprojectname,indescription,inprojectid,inprojectowner,inaccessibility,
                     inprojectowner,NOW(),inprojectowner,NOW());	
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









