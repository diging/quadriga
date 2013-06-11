/*******************************************
Name          : tbl_dictionary_items

Description   : Stores the dictionary items.

Called By     : sp_

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_dictionary_items
(
  dictionaryid       VARCHAR(100)  NOT NULL,
  items    VARCHAR(50) NOT NULL,
  id    VARCHAR(50) NOT NULL,
  pos    VARCHAR(50) NOT NULL,
  updatedby       VARCHAR(10)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(10)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(dictionaryid,items,pos)
)