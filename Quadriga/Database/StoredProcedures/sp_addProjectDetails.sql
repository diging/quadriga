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
  IN  inunixname      VARCHAR(100),
  IN  inaccessibility  VARCHAR(30),
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

    IF(inunixname IS NULL OR inunixname = "")
      THEN SET errmsg = "Unix name cannot be empty.";
    END IF;

	IF EXISTS(SELECT 1 FROM vw_project
                WHERE unixname = inunixname)
      THEN SET errmsg = "unix name is already assigned to a project.";
     END IF;

    IF(inaccessibility IS NULL OR inaccessibility = "")
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
              INTO tbl_project(projectname,description,unixname,projectowner,accessibility,
                         updatedby,updateddate,createdby,createddate)
			 VALUES (inprojectname,indescription,inunixname,inprojectowner,inaccessibility,
                     inprojectowner,NOW(),inprojectowner,NOW());	
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









