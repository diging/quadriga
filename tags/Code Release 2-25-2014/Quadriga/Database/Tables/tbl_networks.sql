/*******************************************
Name          : tbl_network

Description   : Stores the network details.

Called By     : sp_getNetworkList

Create By     : Lohith Dwaraka

Modified Date : 08/06/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_networks
(
  networkid       varchar(150)  NOT NULL,
  workspaceid     varchar(150)  NOT NULL,
  networkname	  varchar(100)  NOT NULL,
  networkowner    VARCHAR(50)   NOT NULL,
  status		  VARCHAR(50)   NOT NULL,
  updatedby       VARCHAR(20)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(20)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(networkid)
)