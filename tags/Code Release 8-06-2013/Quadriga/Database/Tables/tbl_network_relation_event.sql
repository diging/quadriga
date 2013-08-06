/*******************************************
Name          : tbl_network_relation_event

Description   : Stores the network relation event components details.

Called By     : sp_getNetworkList

Create By     : Lohith Dwaraka

Modified Date : 08/06/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_network_relation_event
(
  networkid       varchar(100)           NOT NULL ,
  relationeventid	  varchar(100)           NOT NULL, 
  updatedby       VARCHAR(10)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(10)   NOT NULL,
  createddate     DATETIME      NOT NULL
)