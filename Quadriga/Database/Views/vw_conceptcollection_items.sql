/*******************************************
Name          : vw_conceptcollections_items

Description   : View to retrieve details of a conceptcollections_items

Called By     : 

Create By     : SatyaSwaroop Boddu

Modified Date : 06/04/2013

********************************************/
DROP VIEW IF EXISTS vw_conceptcollection_items;

CREATE VIEW vw_conceptcollection_items(conceptcollectionid, item, pos, description, lemma)
AS
SELECT conceptcollectionid,
       item,
       pos,
       description,
       lemma
FROM tbl_conceptcollection_items;