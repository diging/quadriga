/*******************************************
Name          : tbl_dspace_bitstream

Description   : Store the details of dpsace bitstream.

Called By     : TBD

Create By     : Ram Kumar Kumaresan

Modified Date : 07/03/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_dspace_bitstream
(
  communityid       	VARCHAR(20) NOT NULL,
  collectionid          VARCHAR(20) NOT NULL,
  itemid                VARCHAR(20) NOT NULL,
  bitstreamid           VARCHAR(20) NOT NULL,
  name	 				TEXT NOT NULL,
  size		 			TEXT NOT NULL,
  mimeType				TEXT NOT NULL,
  updatedby       	 	VARCHAR(50)   NOT NULL,
  updateddate     	 	TIMESTAMP     NOT NULL,
  createdby       	 	VARCHAR(50)   NOT NULL,
  createddate     	 	DATETIME      NOT NULL,
  PRIMARY KEY(bitstreamid)
)