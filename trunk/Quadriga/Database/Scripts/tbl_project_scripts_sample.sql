	use quadriga;
	
	INSERT tbl_project(projectname,description,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate)
	VALUES ('project1','Lifecyle of Reptiles',1,'test',1,SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());
	
	
	INSERT tbl_project(projectname,description,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate)
	VALUES ('project2','Lifecyle of Mammals',2,'dexter',1,SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());
	
	
	INSERT tbl_project(projectname,description,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate)
	VALUES ('project3','Lifecyle of Aquatic species',3,'deb',1,SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());
	
	INSERT tbl_project(projectname,description,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate)
	VALUES ('project4','Lifecycle of Vertebrates',4,'test',1,SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());
	
