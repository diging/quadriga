/*******************************************
Name          : tbl_quadriga_user_denied

Description   : Store the details of denied users requests.

Create By     : Ram Kumar Kumaresan

Modified Date : 11/19/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_quadriga_user_denied
(
  actionid		VARCHAR(100)  NOT NULL,
  fullname      VARCHAR(50)   DEFAULT NULL,
  username      VARCHAR(50)   NOT NULL,
  passwd        VARCHAR(20)   DEFAULT NULL,
  email         VARCHAR(50)   DEFAULT NULL,
  deniedby		VARCHAR(50)   NOT NULL,
  updatedby     VARCHAR(20)   NOT NULL,
  updateddate   TIMESTAMP     NOT NULL,
  createdby     VARCHAR(20)   NOT NULL,
  createddate   DATETIME      NOT NULL,
  PRIMARY KEY(actionid)
)