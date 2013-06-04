/*******************************************
Name          : vw_dictionary

Description   : View to retrieve details of a dictionary.

Called By     : sp_getDictionaryList

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/
DROP VIEW IF EXISTS vw_dictionary;

CREATE VIEW vw_dictionary
AS
SELECT dictionaryname,
       description,
	   dictionaryid,
       id,
       dictionaryowner,
	   accessibility
FROM tbl_dictionary;