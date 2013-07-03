/*******************************************
Name          : vw_quadriga_user_requests

Description   : View to retrieve details of a user requests

Called By     : sp_getProjectList

Create By     : kiran kumar batna

Modified Date : 05/28/2013

********************************************/
DROP VIEW IF EXISTS vw_quadriga_user_requests;

CREATE VIEW vw_quadriga_user_requests(fullname,username,passwd,email)
AS
  SELECT fullname,
		 username,
         passwd,
		 email
    FROM tbl_quadriga_user_requests;

         
