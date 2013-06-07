INSERT INTO tbl_quadriga_user
(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)
VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());
INSERT INTO tbl_quadriga_user
(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)
VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());
INSERT INTO tbl_quadriga_user
(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)
VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT INTO tbl_quadriga_user
(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)
VALUES('dexter','dexter',NULL,'dex@lsa.asu.edu','role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT INTO tbl_quadriga_user
(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)
VALUES('deb','deb',NULL,'deb@lsa.asu.edu','role2',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT INTO tbl_quadriga_user
(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)
VALUES('penny','penny',NULL,'penny@lsa.asu.edu','role6',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT INTO tbl_quadriga_user
(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)
VALUES('leonard','leonard',NULL,'leonard@lsa.asu.edu','role3,role5',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());
