/*******************************************
Name          : tbl_network_statements

Description   : Stores the network relation event components details.

Called By     : sp_getNetworkList

Create By     : Lohith Dwaraka

Modified Date : 08/06/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_network_statements
(
  networkid       varchar(100)           NOT NULL ,
  id	  varchar(100)           NOT NULL, 
  statementtype  varchar(10)           NOT NULL,
  istop 	int NOT NULL,
  updatedby       VARCHAR(10)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(10)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(networkid,id)
)