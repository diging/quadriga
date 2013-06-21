/*******************************************
Name          : sp_archiveworkspace

Description   : Archive the workspace

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 06/20/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_archiveworkspace;

DELIMITER $$
CREATE PROCEDURE sp_archiveworkspace
(
  IN inworkspaceid   BIGINT,
  IN inarchive       TINYINT,
  OUT errmsg         VARCHAR(255)
)
BEGIN
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQLException occurred";

    -- validating the input parameters
    IF(inworkspaceid IS NULL)
       THEN SET errmsg = "Workspaceid cannot be empty.";
    END IF;

    IF NOT EXISTS(SELECT 1 FROM vw_workspace WHERE workspaceid = inworkspaceid)
      THEN SET errmsg = "Workspace does not exists to archive.";
	END IF;

    IF(inarchive IS NULL)
       THEN SET errmsg = "Archive parameter cannot be empty.";
    END IF;

    IF(errmsg IS NULL)
      THEN SET errmsg = "";
	   START TRANSACTION;
          UPDATE tbl_workspace
             SET  isarchived = inarchive
           WHERE workspaceid = inworkspaceid;
	   IF(errmsg = "")
          THEN COMMIT;
       ELSE ROLLBACK;
       END IF;
     END IF;
END$$
DELIMITER ;
