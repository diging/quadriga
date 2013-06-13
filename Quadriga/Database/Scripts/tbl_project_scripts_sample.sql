	
	INSERT tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate)
	VALUES ('project1','Lifecyle of Reptiles','unixname1',1,'test','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());
	
	
	INSERT tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate)
	VALUES ('project2','Lifecyle of Mammals','unixname2',2,'jdoe','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());
	
	
	INSERT tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate)
	VALUES ('project3','Lifecyle of Aquatic species','unixname3',3,'test','NOT_ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());
	
	INSERT tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate)
	VALUES ('project4','Lifecycle of Vertebrates','unixname4',4,'test','NOT_ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());
	
