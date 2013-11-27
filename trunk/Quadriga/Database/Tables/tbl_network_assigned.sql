/*******************************************
Name          : tbl_network_assigned

Description   : Stores the assigned user and network details.

Called By     : sp_getNetworkList

Create By     : Lohith Dwaraka

Modified Date : 08/06/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_network_assigned
(
  networkid       varchar(100)  NOT NULL ,
  assigneduser    VARCHAR(50)   NOT NULL ,
  status 		  VARCHAR(50)   NOT NULL ,
  updatedby       VARCHAR(10)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(10)   NOT NULL,
  createddate     DATETIME      NOT NULL,
 isarchived 	  int  			NOT NULL,
  PRIMARY KEY(networkid,assigneduser,createddate)
)