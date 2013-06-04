/*******************************************
Name          : tbl_conceptcollections_items

Description   : Stores the items in the colloaborator list

Called By     : 

Create By     : satyaswaroop boddu

Modified Date : 06/04/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_conceptcollections_items
(
  id       INT   REFERENCES tbl_conceptcollections(id),
  item      	  VARCHAR(10)	NOT NULL,
  updatedby       VARCHAR(10)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(10)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(id,item)
)