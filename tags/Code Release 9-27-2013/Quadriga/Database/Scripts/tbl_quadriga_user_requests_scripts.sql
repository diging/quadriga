
INSERT INTO tbl_quadriga_user_requests
(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)
VALUES('dexter','dexter',NULL,'dexter@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());
INSERT INTO tbl_quadriga_user_requests
(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)
VALUES('deb','deb',NULL,'deb@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());
INSERT INTO tbl_quadriga_user_requests
(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)
VALUES('harrison','harrison',NULL,'harrison@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());