/*******************************************
Name          : tbl_network

Description   : Stores the network details.

Called By     : sp_getNetworkList

Create By     : Lohith Dwaraka

Modified Date : 08/06/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_networks
(
  networkid       varchar(100)           NOT NULL ,
  networkname	varchar(100)           NOT NULL ,
  networkowner    VARCHAR(50)   NOT NULL ,
  accessibility   TINYINT       NOT NULL,
  status		  VARCHAR(50)   NOT NULL,
  updatedby       VARCHAR(10)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(10)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(networkid)
)