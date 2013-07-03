/*******************************************
Name          : vw_quadriga_user_denied

Description   : View to retrieve details of denied user requests.

Called By     : sp_denyUserRequest

Create By     : kiran kumar batna

Modified Date : 05/28/2013

********************************************/
DROP VIEW IF EXISTS vw_quadriga_user_denied;

CREATE VIEW vw_quadriga_user_denied(fullname,username,passwd,email,deniedby)
AS
   SELECT fullname,
		  username,
		  passwd,
          email,
          deniedby
     FROM tbl_quadriga_user_denied;
