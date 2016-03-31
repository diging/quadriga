package edu.asu.spring.quadriga.service.textfile.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.service.textfile.IFileSaveService;
import edu.asu.spring.quadriga.utilities.IFileManager;

@Service

public class FileSaveService implements IFileSaveService {

    @Qualifier("txtfileSaveUtil")
    @Autowired
    private IFileManager fileManager ;

    private String filePath;
    private ITextFile txtFile;

    @Override
    public boolean saveFileToLocal(ITextFile txtFile) throws FileStorageException, IOException {
        this.txtFile = txtFile;
        return true;
    }

    private boolean saveMetadata() throws IOException {
        File propFile = new File(filePath + "/meta.properties");
        FileWriter propFw = new FileWriter(propFile);
        propFw.write("WsId:" + txtFile.getWorkspaceId() + "\n");
        propFw.write("ProjectId:" + txtFile.getProjectId() + "\n");
        try {
            propFw.write("ReferenceId:" + txtFile.getRefId() + "\n");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        propFw.write("TextFileId:" + txtFile.getTextId() + "\n");
        propFw.close();
        return true;
    }

    private boolean saveFileContent() throws IOException {
        String fileName = txtFile.getFileName();
        File saveTxtFile;
        if (fileName.contains(".")) {
            saveTxtFile = new File(filePath + "/" + fileName);
        } else {
            saveTxtFile = new File(filePath + "/" + fileName + ".txt");
        }
        byte[] fileContentBytes = txtFile.getFileContent().getBytes("UTF-8");
        fileManager.saveFiletoDir("dirName", fileName, fileContentBytes);
        return true;
    }

}
