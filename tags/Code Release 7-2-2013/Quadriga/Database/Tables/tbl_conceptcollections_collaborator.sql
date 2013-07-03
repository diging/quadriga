/*******************************************
Name          : tbl_conceptcollections_collaborator

Description   : Stores the conceptcollection collaborator details.

Called By     : 

Create By     : satyaswaroop boddu

Modified Date : 06/04/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_conceptcollections_collaborator
(
  collectionid       INT,
  collaboratoruser	 VARCHAR(20),
  collaboratorrole	 VARCHAR(20),
  updatedby       	 VARCHAR(10)   NOT NULL,
  updateddate     	 TIMESTAMP     NOT NULL,
  createdby       	 VARCHAR(10)   NOT NULL,
  createddate     	 DATETIME      NOT NULL,
  PRIMARY KEY(collectionid,collaboratoruser,collaboratorrole)
)