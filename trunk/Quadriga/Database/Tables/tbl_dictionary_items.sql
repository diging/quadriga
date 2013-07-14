/*******************************************
Name          : tbl_dictionary_items

Description   : Stores the dictionary items.

Called By     : sp_

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_dictionary_items
(
  id       varchar(50)  NOT NULL,
  term    VARCHAR(200) NOT NULL,
  termid    VARCHAR(300) NOT NULL,
  pos    VARCHAR(50) NOT NULL,
  updatedby       VARCHAR(10)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(10)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(id,termid)
)