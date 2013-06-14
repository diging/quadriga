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

  passwd        VARCHAR(10) NULL,
  email         VARCHAR(50) NULL,
  deniedby		VARCHAR(50) NULL,
  updatedby     VARCHAR(10) NULL,
  updateddate   TIMESTAMP   NULL,
  createdby     VARCHAR(10) NULL,
  createddate   DATETIME    NULL 

  

)