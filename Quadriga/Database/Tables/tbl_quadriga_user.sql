/*******************************************
Name          : tbl_quadriga_user

Description   : Store the details of users.

Called By     : sp_getUserDetails

Create By     : Kiran Kumar Batna

Modified Date : 05/21/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_quadriga_user
(
  fullname      VARCHAR(50)   DEFAULT NULL,
  username      VARCHAR(20)   NOT NULL,
  passwd        VARCHAR(15)   NULL,
  email         VARCHAR(50)   DEFAULT NULL,
  quadrigarole  VARCHAR(100)  NOT NULL,
  updatedby     VARCHAR(10)   NOT NULL,
  updateddate   TIMESTAMP     NOT NULL,
  createdby     VARCHAR(10)   NOT NULL,
  createddate   DATETIME      NOT NULL,
  PRIMARY KEY(username) 
)