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
  collectionid    VARCHAR(100)  NOT NULL,
  id              INT           NOT NULL AUTO_INCREMENT,
  collectionowner VARCHAR(50)   NOT NULL ,
  accessibility   TINYINT       NOT NULL,
  updatedby       VARCHAR(10)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(10)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(id),UNIQUE KEY(collectionname),UNIQUE KEY(collectionid),
  FOREIGN KEY(collectionowner) REFERENCES tbl_quadriga_user(username)
)