/*******************************************
Name          : sp_updateworkspacecollaborator

Description   : updates the workspace collaborator roles

Called By     : UI (DBConnectionManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 08/7/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_updateworkspacecollaborator;
DELIMITER $$
CREATE PROCEDURE sp_updateworkspacecollaborator
(
	IN inworkspaceid 		VARCHAR(50),
	IN incollaboratoruser	VARCHAR(10),
	IN incollaboratorrole	TEXT,
    IN inuser               VARCHAR(10),
	OUT errmsg				VARCHAR(200)
)
BEGIN
	-- declare local variables
    DECLARE rowvalue   VARCHAR(30);
    DECLARE position   INT;

	 -- the error handler for any sql exception
     DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
     SET errmsg = "SQL exception has occurred";

	-- validating the input variables
	IF(inworkspaceid IS NULL OR inworkspaceid = "")
	 THEN SET errmsg = "workspace id cannot be empty";
	END IF;
      
	IF(incollaboratoruser IS NULL OR incollaboratoruser = "")
		THEN SET errmsg = "collaborator user cannot be empty";
	END IF;

	IF(incollaboratorrole IS NULL OR incollaboratorrole = "")
		THEN SET errmsg = "collaborator role cannot be empty";
	END IF;

    IF (inuser IS NULL OR inuser = "")
      THEN SET errmsg = "User name cannot be empty";
    END IF;

    IF NOT EXISTS (SELECT 1 FROM vw_workspace WHERE workspaceid = inworkspaceid)
     THEN SET errmsg = "Invalid workspace id";
    END IF;

    IF NOT EXISTS (SELECT 1 FROM vw_quadriga_user WHERE username = incollaboratoruser)
     THEN SET errmsg = "Invalid user";
    END IF;

    IF NOT EXISTS (SELECT 1 FROM vw_quadriga_user WHERE username = inuser)
       THEN SET errmsg = "Invalid user";
    END IF;

    IF NOT EXISTS (SELECT 1 FROM vw_workspace_collaborator WHERE username = incollaboratoruser
          AND workspaceid = inworkspaceid)
     THEN SET errmsg = "Selected user is not collaborator to the given workspace";
    END IF;

	   -- split the comma seperated string into a table
    DROP TEMPORARY TABLE IF EXISTS temp_tbl_collabrole;

	CREATE TEMPORARY TABLE temp_tbl_collabrole
    (
         role VARCHAR(30)
    );

    SET position = LOCATE(',',incollaboratorrole);

    WHILE(position > 0) DO
        SET rowvalue = SUBSTRING_INDEX(incollaboratorrole,',',1);
        INSERT INTO temp_tbl_collabrole(role) VALUES(rowvalue);
        SET incollaboratorrole = SUBSTRING(incollaboratorrole FROM position+1);
		SET position = LOCATE(',',incollaboratorrole);
    END WHILE;

    INSERT INTO temp_tbl_collabrole(role) VALUES(incollaboratorrole);

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
         START TRANSACTION;
           -- delete the roles associated with the user
           DELETE FROM tbl_workspace_collaborator
             WHERE username = incollaboratoruser
               AND workspaceid = inworkspaceid;
   
           -- insert the roles associated with the user
           INSERT INTO tbl_workspace_collaborator(workspaceid,username,collaboratorrole,updatedby,updateddate,
                        createdby,createddate)
           SELECT inworkspaceid,incollaboratoruser,role,inuser,NOW(),inuser,NOW()
             FROM temp_tbl_collabrole;
        IF (errmsg = "")
          THEN COMMIT;
        ELSE ROLLBACK;
        END IF;
    END IF;
END$$
DELIMITER ;