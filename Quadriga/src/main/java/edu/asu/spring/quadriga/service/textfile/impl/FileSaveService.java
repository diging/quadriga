package edu.asu.spring.quadriga.service.textfile.impl;

import java.io.IOException;

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
    private ITextFile txtFile;

    @Override
    public boolean saveFileToLocal(ITextFile txtFile) throws FileStorageException, IOException {
        this.txtFile = txtFile;
        saveMetadata();
        saveFileContent();
        return true;
    }

    /**
     * @return
     * @throws IOException
     * @throws FileStorageException
     */
    private boolean saveMetadata() throws IOException, FileStorageException {
        StringBuilder fileContent = new StringBuilder();
        fileContent.append("WsId:" + txtFile.getWorkspaceId() + "\n");
        fileContent.append("ProjectId:" + txtFile.getProjectId() + "\n");
        fileContent.append("ReferenceId:" + txtFile.getRefId() + "\n");
        fileContent.append("TextFileId:" + txtFile.getTextId() + "\n");
        String filePath = txtFile.getTextId();
        fileManager.saveFiletoDir(filePath, "/meta.properties", fileContent.toString().getBytes());
        return true;
    }

    private boolean saveFileContent() throws IOException, FileStorageException {
        String fileName = txtFile.getFileName();
        String saveTxtFile;
        if (fileName.contains(".")) {
            saveTxtFile = ("/" + fileName);
        } else {
            saveTxtFile = ("/" + fileName + ".txt");
        }
        byte[] fileContentBytes = txtFile.getFileContent().getBytes("UTF-8");
        fileManager.saveFiletoDir(txtFile.getTextId(), saveTxtFile, fileContentBytes);
        return true;
    }

}
