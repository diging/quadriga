/*******************************************
Name          : vw_dictionary

Description   : View to retrieve details of a dictionary.

Called By     : sp_getDictionaryList

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/
DROP VIEW IF EXISTS vw_dictionary;

CREATE VIEW vw_dictionary(dictionaryname,description,dictionaryid,dicitonaryowner,accessibility)
AS
SELECT dictionaryname,
       description,
       dictionaryid,
       dictionaryowner,
	   accessibility
FROM tbl_dictionary;