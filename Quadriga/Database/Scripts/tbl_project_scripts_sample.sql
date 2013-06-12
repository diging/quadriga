	use quadriga;
	
	INSERT tbl_project(projectname,description,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate)
	VALUES ('project1','Lifecyle of Reptiles',1,'test',ACCESSIBLE,SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());
	
	
	INSERT tbl_project(projectname,description,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate)
	VALUES ('project2','Lifecyle of Mammals',2,'dexter',ACCESSIBLE,SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());
	
	
	INSERT tbl_project(projectname,description,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate)
	VALUES ('project3','Lifecyle of Aquatic species',3,'deb',NOT_ACCESSIBLE,SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());
	
	INSERT tbl_project(projectname,description,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate)
	VALUES ('project4','Lifecycle of Vertebrates',4,'test',NOT_ACCESSIBLE,SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());
	
