/*******************************************
Name          : tbl_quadriga_user_role

Description   : Store the quadriga roles of users.

Called By     : sp_getUserDetails

Create By     : Kiran Kumar Batna

Modified Date : 06/29/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_quadriga_user_role
(
  username      VARCHAR(20)   NOT NULL,
  quadrigarole  VARCHAR(100)  NOT NULL,
  updatedby     VARCHAR(10)   NOT NULL,
  updateddate   TIMESTAMP     NOT NULL,
  createdby     VARCHAR(10)   NOT NULL,
  createddate   DATETIME      NOT NULL,
  PRIMARY KEY(username,quadrigarole) 
)