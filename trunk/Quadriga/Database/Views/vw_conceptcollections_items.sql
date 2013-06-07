/*******************************************
Name          : vw_conceptcollections_items

Description   : View to retrieve details of a conceptcollections_items

Called By     : 

Create By     : SatyaSwaroop Boddu

Modified Date : 06/04/2013

********************************************/
DROP VIEW IF EXISTS vw_conceptcollections_items;

CREATE VIEW vw_conceptcollections_items(id, item, pos, description)
AS
SELECT id,
       item,
       pos,
       description
FROM tbl_conceptcollections_items;