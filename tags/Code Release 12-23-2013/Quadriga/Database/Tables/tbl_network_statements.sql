/*******************************************
Name          : tbl_network_statements

Description   : Stores the network relation event components details.

Called By     : sp_getNetworkList

Create By     : Lohith Dwaraka

Modified Date : 08/06/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_network_statements
(
  networkid       VARCHAR(100)   NOT NULL ,
  statementid	  VARCHAR(100)   NOT NULL, 
  statementtype   VARCHAR(10)    NOT NULL,
  istop 	      INT            NOT NULL,
  isarchived      INT            NOT NULL,
  updatedby       VARCHAR(20)    NOT NULL,
  updateddate     TIMESTAMP      NOT NULL,
  createdby       VARCHAR(20)    NOT NULL,
  createddate     DATETIME       NOT NULL,
  PRIMARY KEY(networkid,statementid)
)