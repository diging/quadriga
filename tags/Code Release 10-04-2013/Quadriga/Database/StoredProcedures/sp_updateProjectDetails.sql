/*******************************************
Name          : sp_updateProjectDetails

Description   : adds the project details to
				tbl_project table

Called By     : UI (DBConnectionManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 05/28/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_updateProjectDetails;
DELIMITER $$
CREATE PROCEDURE sp_updateProjectDetails
(
  IN   inprojectname    VARCHAR(50),
  IN   indescription    TEXT,
  IN   inunixname      VARCHAR(100),
  IN   inaccessibility  VARCHAR(50),
  IN   inuser           VARCHAR(50),
  IN   inprojid         VARCHAR(50),
  OUT  errmsg           VARCHAR(255)
)
BEGIN
              
    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- validating the input paramters
    IF(inprojectname IS NULL AND indescription IS NULL AND inunixname IS NULL AND
       inaccessibility IS NULL AND inprojid IS NULL)
      THEN SET errmsg = "there is no value to update.Please specify a value to update.";
    END IF;

    IF(inprojid IS NULL)
      THEN SET errmsg = "Invalid id value.Please specify correct value.";
    END IF;
    
    IF NOT EXISTS(SELECT 1 FROM vw_project
                    WHERE projectid = inprojid)
      THEN SET errmsg = "No such record exists.Please specify correct value";
    END IF;

    -- Updating the record
    IF (errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
           UPDATE tbl_project proj
              SET proj.projectname = IFNULL(inprojectname,proj.projectname),
                  proj.description = IFNULL(indescription,proj.description),
                  proj.unixname   = IFNULL(inunixname,proj.unixname),
                  proj.accessibility = IFNULL(inaccessibility,proj.accessibility),
                  proj.updatedby     = inuser,
				  proj.updateddate   = NOW()
			WHERE proj.projectid = inprojid;
          IF(errmsg = "")
             THEN COMMIT;
		  ELSE 
              ROLLBACK;
		   END IF;
    END IF;
END$$
DELIMITER ;