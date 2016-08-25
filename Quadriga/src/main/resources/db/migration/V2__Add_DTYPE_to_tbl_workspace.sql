ALTER TABLE tbl_workspace ADD DTYPE varchar(31);
UPDATE tbl_workspace SET DTYPE='WorkspaceDTO' WHERE DTYPE IS NULL;