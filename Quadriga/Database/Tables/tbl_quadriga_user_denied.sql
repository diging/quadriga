/*******************************************
Name          : tbl_quadriga_user_denied

Description   : Store the details of denied users requests.

Create By     : Ram Kumar Kumaresan

Modified Date : 05/24/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_quadriga_user_denied
(
  fullname      VARCHAR(50) DEFAULT NULL,
  username      VARCHAR(10) NOT NULL,
  passwd        VARCHAR(10) DEFAULT NULL,
  email         VARCHAR(50) DEFAULT NULL,
  deniedby		VARCHAR(50) NOT NULL,
  updatedby     VARCHAR(10) NOT NULL,
  updateddate   TIMESTAMP   NOT NULL,
  createdby     VARCHAR(10) NOT NULL,
  createddate   DATETIME    NOT NULL 
)