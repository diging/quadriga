package edu.asu.spring.quadriga.service.textfile.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.service.textfile.IFileSaveService;
import edu.asu.spring.quadriga.utilities.IFileSaveUtility;

/**
 * @author Nischal Samji
 *
 */
@Service
public class FileSaveService implements IFileSaveService {

    @Qualifier("txtfileSaveUtil")
    @Autowired
    private IFileSaveUtility fileManager;

    @Override
    public boolean saveFileToLocal(ITextFile txtFile) throws FileStorageException {
        return saveMetadata(txtFile) && saveFileContent(txtFile);
    }

    /**
     * @return
     * @throws IOException
     * @throws FileStorageException
     */
    private boolean saveMetadata(ITextFile txtFile) throws FileStorageException {
        StringBuilder fileContent = new StringBuilder();
        fileContent.append("WsId:" + txtFile.getWorkspaceId() + "\n");
        fileContent.append("ProjectId:" + txtFile.getProjectId() + "\n");
        fileContent.append("ReferenceId:" + txtFile.getRefId() + "\n");
        fileContent.append("TextFileId:" + txtFile.getTextId() + "\n");
        String filePath = txtFile.getTextId();
        if (fileManager.saveFiletoDir(filePath, "/meta.properties", fileContent.toString().getBytes())) {
            return true;
        } else
            return false;
    }

    private boolean saveFileContent(ITextFile txtFile) throws FileStorageException {
        String fileName = txtFile.getFileName();
        String saveTxtFile;
        if (fileName.contains(".")) {
            saveTxtFile = ("/" + fileName);
        } else {
            saveTxtFile = ("/" + fileName + ".txt");
        }
        byte[] fileContentBytes;
        try {
            fileContentBytes = txtFile.getFileContent().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new FileStorageException(e);
        }
        if (fileManager.saveFiletoDir(txtFile.getTextId(), saveTxtFile, fileContentBytes)) {
            return true;
        } else
            return false;
    }

    @Override
    public String retrieveFileFromLocal(String fileName, String txtId) throws FileStorageException {        
        return fileManager.readFileContent(fileName, txtId);
    }

}
