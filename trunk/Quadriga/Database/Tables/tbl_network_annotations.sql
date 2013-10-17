/*******************************************
Name          : tbl_network_annotations

Description   : Stores the annotaions of each node in a network

Called By     : sp_

Create By     : Sowjanya Ambati

Modified Date : 10/16/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_network_annotations
(
  networkid       varchar(50)  NOT NULL,
  nodename    VARCHAR(50) NOT NULL,
  annotationtext    TEXT NOT NULL,
  annotationid    VARCHAR(50) NOT NULL,
  userid	VARCHAR(50) NOT NULL,
  updatedby       VARCHAR(10)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(10)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(annotationid)
)