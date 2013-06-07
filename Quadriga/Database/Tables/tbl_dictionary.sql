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
  description     TEXT          NULL,
  dictionaryid       VARCHAR(100)  NOT NULL,
  id              INT           NOT NULL AUTO_INCREMENT,
  dictionaryowner    VARCHAR(50)   NOT NULL ,
  accessibility   TINYINT       NOT NULL,
  updatedby       VARCHAR(10)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(10)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(id),UNIQUE KEY(dictionaryname),UNIQUE KEY(dictionaryid)
)