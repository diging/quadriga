/*******************************************
Name          : sp_getDictionaryCollab.sql

Description   : retrieves the dictionary details
				of a particular dictionary w.r.t collaborator

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getDictionaryCollab;

DELIMITER $$
CREATE PROCEDURE sp_getDictionaryCollab
(
  IN  incollabuser  VARCHAR(20),
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
   

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the dictionary details
	 select dictionaryname,description,dict.id,accessibility,dict.dictionaryowner,
	 collab.collaboratoruser,collab.collaboratorrole 
	 from tbl_dictionary dict ,tbl_dictionary_collaborator collab where
	(dict.id = collab.id and collaboratoruser = incollabuser);
	END IF;
END$$
DELIMITER ;