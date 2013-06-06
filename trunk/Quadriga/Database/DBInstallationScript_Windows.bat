@ECHO OFF

REM Assinging folder names to local variables
 SET DB_TABLES=\tables
 SET DB_VIEWS=\views
 SET DB_PROC=\storedprocedures

REM Assigning file names to local variables
 SET DB_SCHEMA_FILE=DB_Schema.txt
 SET DB_PRIVILEGE_FILE=DB_Privilege.txt
 SET DB_TABLE_FILE=DB_Tables.txt
 SET DB_VIEWS_FILE=DB_Views.txt
 SET DB_PROC_FILE=DB_Proc.txt
 SET DB_LOG_FILE=DB_InstallLog.txt

REM Checking if the file exists
 IF EXIST %DB_LOG_FILE% (
   DEL %DB_LOG_FILE%
  )
 
REM Fetch the current directory
 SET CURRENT_DIR=%CD%

REM Fetch the mysql username and database name from the properties file
 For /F "tokens=1,2 delims==" %%G IN (database.props) DO SET %%G=%%H

REM Assining values from the properties file into the local variables
 SET DB_NAME=%dbname%
 SET DB_USER=%dbuser%
 SET DB_ADMIN_NAME=%dbadmin%
 SET DB_MYSQL_LOC=%mysqllocation%

REM Check if the input values are empty
 IF "%DB_NAME%"=="" (
    ECHO "Database name OR User name cannot be empty.Please specify a valid value." >> %DB_LOG_FILE%
    GOTO END
    )
 IF "%DB_USER%"=="" (
    ECHO "User name cannot be empty.Please specify a valid value." >> %DB_LOG_FILE%
    GOTO END
    )
 IF "%DB_MYSQL_LOC%"=="" (
     ECHO "MySQL exe location cannot be empty.Please specify a valid value." >> %DB_LOG_FILE%
     GOTO END
    )
    
REM Performing string manipulation on the location to remove the quotes
 SET DB_MYSQL_LOC=%DB_MYSQL_LOC:~1,-1%

REM Create a database schema statement and write it to a file
 SET DB_STMT=CREATE DATABASE IF NOT EXISTS %DB_NAME%;

REM checking if the file exists
IF EXIST %DB_SCHEMA_FILE% (
    DEL %DB_SCHEMA_FILE%
  ) 

REM Writing the statement to the file  
 ECHO %DB_STMT% > %DB_SCHEMA_FILE%

REM Create a privilege statement and write it to a file
 SET DB_PRIVILEGE=GRANT ALL PRIVILEGES ON %DB_NAME%.* TO '%DB_USER%'@'LOCALHOST';

REM Checking if the file exists
 IF EXIST %DB_PRIVILEGE_FILE% (
   DEL %DB_PRIVILEGE_FILE%
  )
  
REM Writing the statement to the file
 ECHO %DB_PRIVILEGE% > %DB_PRIVILEGE_FILE%
 
REM Moving on to the tables folder
CD %CURRENT_DIR%%DB_TABLES%

REM Checking if tje file exists
 IF EXIST %DB_TABLE_FILE% (
  DEL %DB_TABLE_FILE%
 )

REM Loop through all the files in tables and write it to a file
 FOR /F %%G IN ('dir /b /a-d') DO (
  ECHO SOURCE %CURRENT_DIR%%DB_TABLES%\%%G >> %DB_TABLE_FILE%
 )

REM Moving on to the views table
 CD %CURRENT_DIR%%DB_VIEWS%

REM Checking if the file exists
 IF EXIST %DB_VIEWS_FILE% (
  DEL %DB_VIEWS_FILE%
  )
 
REM Loop through all the views and write it to a file
 FOR /F %%G IN ('dir /b /a-d') DO (
  ECHO SOURCE %CURRENT_DIR%%DB_VIEWS%\%%G >> %DB_VIEWS_FILE%
 )

REM Moving on the stored procedures table
 CD %CURRENT_DIR%%DB_PROC%

REM Check if the file exists
 IF EXIST %DB_PROC_FILE% (
  DEL %DB_PROC_FILE%
 )

REM Loop through all the stored procedures and write it to a file 
 FOR /F %%G IN ('dir /b /a-d') DO (
  ECHO SOURCE %CURRENT_DIR%%DB_PROC%\%%G >> %DB_PROC_FILE%
 )

REM Moving the position to the current directory
 CD %CURRENT_DIR%

REM Creating database schema
 ECHO Creating database schema
 ECHO ^[%DB_ADMIN_NAME%^] Enter database admin password when prompted below
 "%DB_MYSQL_LOC%" --user=%DB_ADMIN_NAME% --password < %DB_SCHEMA_FILE% >> %DB_LOG_FILE% 2>&1

REM Creating previleges
 ECHO Assigning user privileges to the created database
 ECHO ^[%DB_ADMIN_NAME%^] Enter database admin password when prompted below
 "%DB_MYSQL_LOC%" --user=%DB_ADMIN_NAME% --password < %DB_PRIVILEGE_FILE% >> %DB_LOG_FILE% 2>&1

REM Creating tables
 SET DB_FILE=%CURRENT_DIR%%DB_TABLES%\%DB_TABLE_FILE%
ECHO Creating tables for the database %DB_NAME%
ECHO ^[%DB_USER%^] Enter database user password when prompted below
"%DB_MYSQL_LOC%" --user=%DB_USER% --password %DB_NAME% < "%DB_FILE%" >> %DB_LOG_FILE% 2>&1

REM Creating views
 SET DB_FILE=%CURRENT_DIR%%DB_VIEWS%\%DB_VIEWS_FILE%

ECHO Creating Views for the database %DB_NAME% 
ECHO ^[%DB_USER%^] Enter database user password when prompted below
"%DB_MYSQL_LOC%" --user=%DB_USER% --password %DB_NAME% < "%DB_FILE%" >> %DB_LOG_FILE% 2>&1

REM Creating stored procedures
 SET DB_FILE=%CURRENT_DIR%%DB_PROC%\%DB_PROC_FILE%

ECHO Creating stored procedures for the database %DB_NAME%
ECHO ^[%DB_USER%^] Enter database user password when prompted below
"%DB_MYSQL_LOC%" --user=%DB_USER% --password %DB_NAME% < "%DB_FILE%" >> %DB_LOG_FILE% 2>&1

REM End block
:END
  REM Checking if the log file exists
   IF EXIST %DB_LOG_FILE% (
    DEL %DB_LOG_FILE%
   )
   
   REM Checking if the schema file exists
    IF EXIST %DB_SCHEMA_FILE% (
       DEL %DB_SCHEMA_FILE%
    ) 
 
   REM Checking if the  privilege file exists
    IF EXIST %DB_PRIVILEGE_FILE% (
      DEL %DB_PRIVILEGE_FILE%
    )
    
   REM Checking if the table file exists
    SET DB_FILE=%CURRENT_DIR%%DB_TABLES%\%DB_TABLE_FILE%
    IF EXIST "%DB_FILE%" (
      DEL "%DB_FILE%"
     )
   
   REM Checking if the view file exists
    SET DB_FILE=%CURRENT_DIR%%DB_VIEWS%\%DB_VIEWS_FILE%
    
    IF EXIST "%DB_FILE%" (
      DEL "%DB_FILE%"
     )
    
   REM Checking if the stored procedure file exists
    SET DB_FILE=%CURRENT_DIR%%DB_PROC%\%DB_PROC_FILE%
    
    IF EXIST "%DB_FILE%" (
     DEL "%DB_FILE%"
     )
    