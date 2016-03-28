/*******************************************
Name          : tbl_conceptcollection_collaborator

Description   : Stores the conceptcollection collaborator details.

Called By     : 

Create By     : satyaswaroop boddu

Modified Date : 06/04/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_conceptcollection_collaborator
(
  conceptcollectionid    VARCHAR(100),
  collaboratoruser	     VARCHAR(50),
  collaboratorrole	     VARCHAR(50),
  updatedby       	     VARCHAR(50)   NOT NULL,
  updateddate     	     TIMESTAMP     NOT NULL,
  createdby       	     VARCHAR(50)   NOT NULL,
  createddate     	     DATETIME      NOT NULL,
  PRIMARY KEY(conceptcollectionid,collaboratoruser,collaboratorrole)
)