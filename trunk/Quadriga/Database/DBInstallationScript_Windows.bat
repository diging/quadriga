@ECHO OFF

REM Assinging folder names to local variables
 SET DB_TABLES=\tables
 SET DB_SCRIPTS=\scripts

REM Assigning file names to local variables
 SET DB_SCHEMA_FILE=DB_Schema.txt
 SET DB_OBJECTS_FILE=DB_Objects.txt
 SET DB_LOG_FILE=DB_InstallLog.log

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
 ECHO %DB_STMT% >> %DB_SCHEMA_FILE%

REM Create a privilege statement and write it to a file
 SET DB_PRIVILEGE=GRANT ALL PRIVILEGES ON %DB_NAME%.* TO '%DB_USER%'@'LOCALHOST';

REM Writing the statement to the file
 ECHO %DB_PRIVILEGE% >> %DB_SCHEMA_FILE%
 
REM Checking if the objects file exists
  IF EXIST %DB_OBJECTS_FILE% (
   DEL %DB_OBJECTS_FILE%
  )

 REM Loop through all the files in tables and write it to a file
  FOR /F %%G IN ('dir .\%DB_TABLES% /b /a-d') DO (
   ECHO SOURCE %CURRENT_DIR%%DB_TABLES%\%%G >> %DB_OBJECTS_FILE%
 )
 
 REM Loop through scripts and write it to a file 
 FOR /F %%G IN ('dir .\%DB_SCRIPTS% /b /a-d') DO (
  ECHO SOURCE %CURRENT_DIR%%DB_SCRIPTS%\%%G >> %DB_OBJECTS_FILE%
 )

REM Creating database and granting privilege to User
 ECHO Creating database
 ECHO Enter database admin ^[%DB_ADMIN_NAME%^] password when prompted below
 "%DB_MYSQL_LOC%" --user=%DB_ADMIN_NAME% --password < %DB_SCHEMA_FILE% >> %DB_LOG_FILE% 2>&1

REM Creating database schema
 ECHO Creating database objects
ECHO Enter database user ^[%DB_USER%^] password when prompted below
"%DB_MYSQL_LOC%" --user=%DB_USER% --password %DB_NAME% < "%DB_OBJECTS_FILE%" >> %DB_LOG_FILE% 2>&1

REM End block
:END
   REM Checking if the schema file exists
    IF EXIST %DB_SCHEMA_FILE% (
       DEL %DB_SCHEMA_FILE%
    ) 
 
   REM Checking if the  privilege file exists
    IF EXIST %DB_OBJECTS_FILE% (
      DEL %DB_OBJECTS_FILE%
    )
    