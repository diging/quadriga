/*******************************************
Name          : tbl_quadriga_dspace_keys

Description   : Store the details of dspace keys.

Called By     : sp_addDspaceKeys and sp_updateDspaceKeys

Create By     : Ram Kumar Kumaresan

Modified Date : 08/08/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_quadriga_dspace_keys
(
  username      VARCHAR(20)   NOT NULL,
  publickey     VARCHAR(100)  NOT NULL,
  privatekey    VARCHAR(100)  NOT NULL,
   PRIMARY KEY(username,privatekey,publickey) 
)