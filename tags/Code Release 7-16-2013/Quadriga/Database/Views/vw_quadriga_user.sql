/*******************************************
Name          : vw_quadriga_user

Description   : View to retrieve the details of user.

Called By     : sp_getUserDetails

Create By     : kbatna

Modified Date : 05/21/2013

********************************************/
DROP VIEW IF EXISTS vw_quadriga_user;

CREATE VIEW vw_quadriga_user(fullname,username,passwd,email,quadrigarole)
AS
SELECT fullname,
       username,
       passwd,
       email,
       quadrigarole
  FROM tbl_quadriga_user;
