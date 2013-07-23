/*******************************************
Name          : sp_addworkspacecollaborators

Description   : adds the workspace collaborators

Called By     : UI (DBConnectionManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 08/22/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_addworkspacecollaborators;
DELIMITER $$
CREATE PROCEDURE sp_addworkspacecollaborators
(
  	IN inworkspaceid 		VARCHAR(100),
	IN incollaboratoruser	VARCHAR(30),
	IN incollaboratorrole	TEXT,
    IN inuser               VARCHAR(10),
	OUT errmsg				VARCHAR(200)
)
BEGIN

    -- Declare local varaibles
    DECLARE rowvalue   VARCHAR(50);
    DECLARE position   INT;

   	 -- the error handler for any sql exception
   -- DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
    --  SET errmsg = "SQL exception has occurred";

    -- deleting the temp table
    DROP TEMPORARY TABLE IF EXISTS temp_tbl_collaboratorrole;

   -- validate the input parameters
   IF (inworkspaceid IS NULL OR inworkspaceid = "")
   THEN SET errmsg = "Workspace id cannot be empty.";
   END IF;

   IF (incollaboratoruser IS NULL OR incollaboratoruser = "")
   THEN SET errmsg = "Collaborator user cannot be empty";
   END IF;

   IF (incollaboratorrole IS NULL OR incollaboratorrole = "")
   THEN SET errmsg = "Collaborator role cannot be empty.";
   END IF;

   IF (inuser IS NULL OR inuser = "")
   THEN SET errmsg = "User name cannnot be empty";
   END IF;

   IF NOT EXISTS (SELECT 1 FROM vw_workspace WHERE workspaceid = inworkspaceid)
   THEN SET errmsg = "Workspace id is invalid";
   END IF;

   IF NOT EXISTS (SELECT 1 FROM vw_quadriga_user WHERE username = incollaboratoruser)
   THEN SET errmsg = "User name is invalid.";
   END IF;

   IF NOT EXISTS (SELECT 1 FROM vw_quadriga_user WHERE username = inuser)
   THEN SET errmsg = "User name is invalid";
   END IF;

	-- inserting the input into a temp table
    CREATE TEMPORARY TABLE temp_tbl_collaboratorrole
    (
         role VARCHAR(50)
    );

    SET position = LOCATE(',',incollaboratorrole);
	
    WHILE(position > 0) DO
        SET rowvalue = SUBSTRING_INDEX(incollaboratorrole,',',1);
        INSERT INTO temp_tbl_collaboratorrole(role) VALUES(rowvalue);
        SET incollaboratorrole = SUBSTRING(incollaboratorrole FROM position+1);
		SET position = LOCATE(',',incollaboratorrole);
    END WHILE;
   
     -- inserting the row when the input has a single value
    INSERT INTO temp_tbl_collaboratorrole(role) VALUES(incollaboratorrole);
    
    -- insert the collaborator into the table
    IF (errmsg IS NULL)
	THEN SET errmsg = "";
     START TRANSACTION;
        INSERT INTO tbl_workspace_collaborator(workspaceid,username,collaboratorrole,updatedby,
           updateddate,createdby,createddate)
		SELECT inworkspaceid,incollaboratoruser,role,inuser,NOW(),inuser,NOW() FROM
		temp_tbl_collaboratorrole;
     IF(errmsg = "")
      THEN COMMIT;
	 ELSE ROLLBACK;
     END IF;
   END IF;
END