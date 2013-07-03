/*******************************************
Name          : vw_dictionary_collaborator

Description   : Stores the collaborator roles for each dictionary.

Called By     : sp_getDictionaryDetails

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/
DROP VIEW IF EXISTS vw_dictionary_collaborator;

CREATE VIEW vw_dictionary_collaborator(dictionaryid,collaboratoruser,collaboratorrole)
AS
   SELECT id,
          collaboratoruser,
          collaboratorrole
     FROM tbl_dictionary_collaborator;
