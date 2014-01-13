/*******************************************
Name          : vw_conceptcollections_collaborator

Description   : retrieves the collaborator roles for each collection

Called By     : 

Create By     : SatyaSwaroop Boddu

Modified Date : 06/04/2013

********************************************/
DROP VIEW IF EXISTS vw_conceptcollection_collaborator;

CREATE VIEW vw_conceptcollection_collaborator(conceptcollectionid,collaboratoruser,collaboratorrole)
AS
   SELECT conceptcollectionid,
          collaboratoruser,
          collaboratorrole
     FROM tbl_conceptcollection_collaborator;
