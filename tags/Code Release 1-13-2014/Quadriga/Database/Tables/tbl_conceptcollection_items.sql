/*******************************************
Name          : tbl_conceptcollection_items

Description   : Stores the items in the colloaborator list

Called By     : 

Create By     : satyaswaroop boddu

Modified Date : 06/04/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_conceptcollection_items
(
  conceptcollectionid       VARCHAR(100)   NOT NULL,
  lemma     	            VARCHAR(255)   NOT NULL,
  item	                    VARCHAR(255)   NOT NULL,
  pos			            VARCHAR(255) ,
  description               TEXT,			
  updateddate               TIMESTAMP      NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(conceptcollectionid,item)
);