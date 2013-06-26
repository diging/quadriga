/*******************************************
Name          : tbl_conceptcollections_items

Description   : Stores the items in the colloaborator list

Called By     : 

Create By     : satyaswaroop boddu

Modified Date : 06/04/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_conceptcollections_items
(
  id       INT   NOT NULL,
  lemma     	 VARCHAR(40)	NOT NULL,
  item	 VARCHAR(255)    NOT NULL,
  
  pos			  VARCHAR(40) ,
  description     TEXT,			
 
  updateddate     TIMESTAMP     NOT NULL,
  
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(id,item)
);