/*******************************************
Name          : tbl_dictionary_collaborator

Description   : Stores the collaborator roles for each dictionary

Called By     : sp_getDictionaryDetails

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_dictionary_collaborator
(
   dictionaryid        VARCHAR(100)  NOT NULL,
   collaboratoruser    VARCHAR(50),
   collaboratorrole    VARCHAR(100),
   updatedby           VARCHAR(20)   NOT NULL,
   updateddate         TIMESTAMP     NOT NULL,
   createdby           VARCHAR(20)   NOT NULL,
   createddate         DATETIME      NOT NULL,
   PRIMARY KEY(dictionaryid,collaboratoruser,collaboratorrole)
)