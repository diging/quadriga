/*******************************************
Name          : tbl_concepts

Description   : Stores the items

Called By     : 

Create By     : Sowjanya Ambati

Modified Date : 02/14/2014

********************************************/
CREATE TABLE IF NOT EXISTS tbl_concepts
(
  lemma     
            VARCHAR(255)   NOT NULL,
  item
                    VARCHAR(255)   NOT NULL,
  pos
            VARCHAR(255) ,
  description               TEXT,
  updateddate               TIMESTAMP      NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(item)
);