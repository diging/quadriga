/*******************************************
Name          : sp_getDictionaryCollaborators

Description   : retrieves the users and their
                Collaborator role for a concept

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Rohit Pendbhaje

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getDictionaryCollaborators;

DELIMITER $$
CREATE PROCEDURE sp_getDictionaryCollaborators
(
  IN indictionaryid  VARCHAR(200),
  OUT errmsg     VARCHAR(255)
)
BEGIN
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(indictionaryid IS NULL OR indictionaryid = "")
     THEN SET errmsg = "dictionary id cannot be empty.";
    END IF;

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
      
      -- retrieve the collaborator details
      SELECT dictionaryid,collaboratoruser, 
         GROUP_CONCAT(collaboratorrole SEPARATOR ',')  AS 'Collaboratorrole'
        FROM vw_dictionary_collaborator
	    WHERE dictionaryid = indictionaryid;
      
     END IF;
END$$
DELIMITER ;