/*******************************************
Name          : tbl_conceptcollection

Description   : Stores the project details.

Called By     : sp_getConceptCollections

Create By     : SatyaSwaroop Boddu

Modified Date : 06/04/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_conceptcollection
(
  collectionname         VARCHAR(50)   NOT NULL,
  description            TEXT          NULL,
  conceptcollectionid    VARCHAR(100)  NOT NULL ,
  collectionowner        VARCHAR(50)   NOT NULL ,
  accessibility          VARCHAR(50)   NOT NULL,
  updatedby              VARCHAR(20)   NOT NULL,
  updateddate            TIMESTAMP     NOT NULL,
  createdby              VARCHAR(20)   NOT NULL,
  createddate            DATETIME      NOT NULL,
  PRIMARY KEY(conceptcollectionid)
)