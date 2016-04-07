/*******************************************
Name          : tbl_network_annotations

Description   : Stores the annotaions of each node in a network

Called By     : sp_

Create By     : Sowjanya Ambati

Modified Date : 11/13/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_network_annotations
(
  networkid         VARCHAR(100)   NOT NULL,
  objectid          VARCHAR(100)   NOT NULL,
  annotationtext    TEXT           NOT NULL,
  annotationid      VARCHAR(100)   NOT NULL,
  username	        VARCHAR(50)    NOT NULL,
  objecttype		VARCHAR(50)    NOT NULL,
  updatedby         VARCHAR(20)    NOT NULL,
  updateddate       TIMESTAMP      NOT NULL,
  createdby         VARCHAR(20)    NOT NULL,
  createddate       DATETIME       NULL,
  PRIMARY KEY(annotationid)
)