/*******************************************
Name          : tbl_conceptcollections

Description   : Stores the project details.

Called By     : sp_getConceptCollections

Create By     : SatyaSwaroop Boddu

Modified Date : 06/04/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_conceptcollections
(
  collectionname  VARCHAR(50)   NOT NULL,
  description     TEXT          NULL,
  
  id              VARCHAR(100)           NOT NULL ,
  collectionowner VARCHAR(50)   NOT NULL ,
  accessibility   TINYINT       NOT NULL,
  updatedby       VARCHAR(10)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(10)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(id),UNIQUE KEY(collectionname)
)