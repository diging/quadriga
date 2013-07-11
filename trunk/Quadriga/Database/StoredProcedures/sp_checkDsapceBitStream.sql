/*******************************************
Name          : sp_checkDsapceBitStream

Description   : Check whether the bitstream data 
				is already present in Quadriga

Called By     : UI (DBConnectionDspaceManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 07/10/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_checkDsapceBitStream;

DELIMITER $$
CREATE PROCEDURE sp_checkDsapceBitStream
(
  IN  inBitstreamid		VARCHAR(20),
  OUT dataStatus        VARCHAR(50)   
)
BEGIN

	IF EXISTS(SELECT 1 FROM tbl_dspace_bitstream
			WHERE bitstreamid = inBitstreamid)
		THEN 
			SET dataStatus = "bitstream exists";
	END IF;

END$$

