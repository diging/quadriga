
INSERT INTO tbl_quadriga_user
(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)
VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role4,role3,role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT INTO tbl_quadriga_user
(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)
VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3,role5',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT INTO tbl_quadriga_user
(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)
VALUES('Admin','admin',NULL,'admin@lsa.asu.edu','role4,role3,role5',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT INTO tbl_quadriga_user
(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)
VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role4,role3,role5',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());