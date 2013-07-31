/*******************************************
Name          : sp_updateWorkspaceDetails

Description   : updates the worksapce information

Called By     : UI (DBConnectionManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 07/12/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_updateWorkspaceDetails;
DELIMITER $$
CREATE PROCEDURE sp_updateWorkspaceDetails
(
 IN  inworkspacename   VARCHAR(50),
 IN  indescription     TEXT,
 IN  inuser            VARCHAR(30),
 IN  inworkspaceid     VARCHAR(50),
 OUT errmsg            VARCHAR(255)
)
BEGIN
      -- the error handler for any sql exception
  --  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
   --   SET errmsg = "SQL exception has occurred";

    -- validate the input parameters
    IF(inworkspacename IS NULL or inworkspacename = "")
     THEN SET errmsg = "Workspace name cannot be empty";
    END IF;

    IF (indescription IS NULL or indescription = "")
      THEN SET errmsg = "Workspace description cannot be empty";
    END IF;

    IF (inworkspaceid IS NULL or inworkspaceid = "")
      THEN SET errmsg = "workspaceid cannot be empty";
    END IF;

    IF NOT EXISTS(SELECT 1 FROM vw_workspace WHERE workspaceid = inworkspaceid)
      THEN SET errmsg = "No such record exists";
    END IF;

    -- Updating the record
    IF(errmsg IS NULL)
     THEN SET errmsg = "";
      UPDATE tbl_workspace ws
        SET ws.workspacename = IFNULL(inworkspacename,ws.workspacename),
            ws.description = IFNULL(indescription,ws.description),
            ws.updatedby = inuser,
            ws.updateddate = NOW()
	 WHERE ws.workspaceid = inworkspaceid; 
   IF(errmsg = "")
     THEN COMMIT;
   ELSE
      ROLLBACK;
   END IF;
  END IF;
END$$
DELIMITER ;