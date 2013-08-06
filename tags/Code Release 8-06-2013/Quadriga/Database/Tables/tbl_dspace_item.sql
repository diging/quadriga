/*******************************************
Name          : tbl_dspace_item

Description   : Store the details of dpsace item.

Called By     : TBD

Create By     : Ram Kumar Kumaresan

Modified Date : 07/03/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_dspace_item
(
  communityid       	VARCHAR(20) NOT NULL,
  collectionid			VARCHAR(20) NOT NULL,
  itemid                VARCHAR(20) NOT NULL,
  name	 				TEXT NOT NULL,
  handle	 			TEXT NOT NULL,
  updatedby       	 	VARCHAR(50)   NOT NULL,
  updateddate     	 	TIMESTAMP     NOT NULL,
  createdby       	 	VARCHAR(50)   NOT NULL,
  createddate     	 	DATETIME      NOT NULL,
  PRIMARY KEY(itemid)
)