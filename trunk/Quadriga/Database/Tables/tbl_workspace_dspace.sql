/*******************************************
Name          : tbl_workspace_dspace

Description   : Store the bitstreams associated with each workspace

Create By     : Ram Kumar Kumaresan

Modified Date : 07/09/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_workspace_dspace
(
  workspaceid     VARCHAR(150)   NOT NULL,
  itemid 	      VARCHAR(50)	NOT NULL,
  bitstreamid     VARCHAR(50)	NOT NULL,
  createdby       VARCHAR(20)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(workspaceid,bitstreamid)  
)