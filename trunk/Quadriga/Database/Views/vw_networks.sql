/*******************************************
Name          : vw_networks

Description   : Stores the network details.

Called By     : 

Create By     : Kiran Kumar Batna

Modified Date : 10/30/2013

********************************************/
DROP VIEW IF EXISTS vw_networks;
CREATE VIEW vw_networks(networkid,workspaceid,networkname,networkowner,status)
AS 
  SELECT networkid,workspaceid,networkname,networkowner,status
   FROM tbl_networks;