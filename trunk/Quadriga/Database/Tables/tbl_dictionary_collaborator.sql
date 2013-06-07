/*******************************************
Name          : tbl_dictionary_collaborator

Description   : Stores the collaborator roles for each dictionary

Called By     : sp_getDictionaryDetails

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_dictionary_collaborator
(
   dictionaryid        VARCHAR(50),
   collaboratoruser    VARCHAR(20),
   collaboratorrole    VARCHAR(100),
   updatedby           VARCHAR(10)   NOT NULL,
   updateddate         TIMESTAMP     NOT NULL,
   createdby           VARCHAR(10)   NOT NULL,
   createddate         DATETIME      NOT NULL,
   PRIMARY KEY(dictionaryid,collaboratoruser,collaboratorrole)
)