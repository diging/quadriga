/*******************************************
Name          : sp_getDictionaryCollabPerm.sql

Description   : retrieves the dictionary details
				of a particular dictionary w.r.t collaborator permission

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getDictionaryCollabPerm;

DELIMITER $$
CREATE PROCEDURE sp_getDictionaryCollabPerm
(
  IN  incollabuser  VARCHAR(20),
  IN  indictionaryid  VARCHAR(100),
 OUT errmsg    VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(incollabuser IS NULL OR incollabuser = "")
     THEN SET errmsg = "Dictionary owner name cannot be empty.";
    END IF;
    
    IF(indictionaryid IS NULL OR indictionaryid = "")
     THEN SET errmsg = "Dictionary ID cannot be empty.";
    END IF;
   

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the dictionary details
	select collaboratorrole from tbl_dictionary_collaborator 
	where dictionaryid =indictionaryid and collaboratoruser=incollabuser;
	END IF;
END$$
DELIMITER ;