/*******************************************
Name          : tbl_quadriga_userprofile

Description   : Store the profile details of users.

Called By     : 

Create By     : Rohit Pendbhaje

Modified Date : 09/09/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_quadriga_userprofile
(
	username		VARCHAR(50)		NOT NULL,
	serviceid		VARCHAR(100)	NOT NULL,
	profilename		VARCHAR(50)		DEFAULT NULL,
	profileid		VARCHAR(100)	DEFAULT NULL,
	description		TEXT	        DEFAULT NULL,
	updatedby     	VARCHAR(20)   	NOT NULL,
	updateddate   	TIMESTAMP     	NOT NULL,
	createdby     	VARCHAR(20)   	NOT NULL,
	createddate   	DATETIME      	NOT NULL,
	PRIMARY KEY(username,serviceid,profileid)
)