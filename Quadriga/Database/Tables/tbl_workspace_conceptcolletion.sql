/*******************************************
Name          : tbl_workspace_conceptcollection

Description   : Adds concept colletion to the project details.

Called By     : 

Create By     : LohithDwaraka

Modified Date : 07/11/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_workspace_conceptcollection
(
  workspaceid           VARCHAR(150)   NOT NULL,
  conceptcollectionid   VARCHAR(150)   NOT NULL,
  updatedby             VARCHAR(20)    NOT NULL,
  updateddate           TIMESTAMP      NOT NULL,
  createdby             VARCHAR(20)    NOT NULL,
  createddate           DATETIME       NOT NULL,
  PRIMARY KEY(workspaceid,conceptcollectionid)
);