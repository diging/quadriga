/*******************************************
Name          : vw_conceptcollections

Description   : View to retrieve details of a conceptcollections

Called By     : sp_getConceptCollections

Create By     : SatyaSwaroop Boddu

Modified Date : 06/04/2013

********************************************/
DROP VIEW IF EXISTS vw_conceptcollection;

CREATE VIEW vw_conceptcollection
AS
SELECT collectionname,
       description,
       conceptcollectionid,
       collectionowner,
	   accessibility
FROM tbl_conceptcollection;