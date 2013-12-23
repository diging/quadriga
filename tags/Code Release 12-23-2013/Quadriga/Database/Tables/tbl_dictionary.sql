/*******************************************
Name          : tbl_dictionary

Description   : Stores the dictionary details.

Called By     : sp_getDictionaryList

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_dictionary
(
  dictionaryname     VARCHAR(50)   NOT NULL,
  description        TEXT          NULL,  
  dictionaryid       VARCHAR(100)  NOT NULL ,
  dictionaryowner    VARCHAR(50)   NOT NULL ,
  accessibility      VARCHAR(20)   NOT NULL,
  updatedby          VARCHAR(20)   NOT NULL,
  updateddate        TIMESTAMP     NOT NULL,
  createdby          VARCHAR(20)   NOT NULL,
  createddate        DATETIME      NOT NULL,
  PRIMARY KEY(dictionaryid)
)