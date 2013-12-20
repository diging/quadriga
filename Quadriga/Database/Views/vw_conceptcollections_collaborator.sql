/*******************************************
Name          : vw_conceptcollections_collaborator

Description   : retrieves the collaborator roles for each collection

Called By     : 

Create By     : SatyaSwaroop Boddu

Modified Date : 06/04/2013

********************************************/
DROP VIEW IF EXISTS vw_conceptcollections_collaborator;

CREATE VIEW vw_conceptcollections_collaborator(collectionid,collaboratoruser,collaboratorrole)
AS
   SELECT collectionid,
          collaboratoruser,
          collaboratorrole
     FROM tbl_conceptcollection_collaborator;
