/*******************************************
Name          : tbl_quadriga_userprofile

Description   : Store the profile details of users.

Called By     : 

Create By     : Rohit Pendbhaje

Modified Date : 09/09/2013

********************************************/

CREATE TABLE IF NOT EXISTS tbl_quadriga_userprofile
(
	username		VARCHAR(20)		NOT NULL,
	servicename		VARCHAR(100)	DEFAULT NULL,
	uri				VARCHAR(256)	DEFAULT NULL,
	updatedby     	VARCHAR(10)   	NOT NULL,
	updateddate   	TIMESTAMP     	NOT NULL,
	createdby     	VARCHAR(10)   	NOT NULL,
	createddate   	DATETIME      	NOT NULL,
	PRIMARY KEY(username)
)