/*******************************************
Name          : vw_dictionary_items

Description   : View to retrieve details of a tbl_dictionaru_items

Called By     : 

Create By     : Lohith Dwaraka

Modified Date : 06/05/2013

********************************************/
DROP VIEW IF EXISTS vw_dictionary_items;

CREATE VIEW vw_dictionary_items(id, term,termid,pos)
AS
SELECT id,
       term,termid,pos
FROM tbl_dictionary_items;