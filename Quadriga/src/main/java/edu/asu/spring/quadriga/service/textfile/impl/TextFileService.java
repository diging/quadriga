package edu.asu.spring.quadriga.service.textfile.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.textfile.ITextFileDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectWorkspaceDAO;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.dto.TextFileDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.textfile.ITextFileService;

@PropertySource(value = "classpath:/user.properties")
@Service
public class TextFileService implements ITextFileService {

    @Autowired
    private ITextFileDAO txtFileDAO;

    @Autowired
    private IProjectWorkspaceDAO projWSDAO;

    @Autowired
    private Environment env;

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.service.textfile.ITextFileService#saveTextFile(
     * edu.asu.spring.quadriga.domain.workspace.ITextFile) Service layer method
     * to handle Text Management Operations
     */
    @Override
    public boolean saveTextFile(ITextFile txtFile) throws QuadrigaStorageException, IOException {

        UUID textId = UUID.randomUUID();
        txtFile.setTextId(textId.toString());
        boolean fsStatus = saveTextFileLocal(txtFile);
        boolean dbStatus = saveTextFileDB(txtFile);
        txtFileDAO.getTextFileDTObyWsId(txtFile.getWorkspaceId());
        return fsStatus && dbStatus;
    }

    /**
     * @param txtFile
     *            TextFile object to be updated in the database
     * @return
     * @throws QuadrigaStorageException
     */
    private boolean saveTextFileDB(ITextFile txtFile) throws QuadrigaStorageException {
        TextFileDTO txtFileDTO = new TextFileDTO();
        txtFileDTO.setFilename(txtFile.getFileName());
        txtFileDTO.setProjectId(txtFile.getProjectId());
        txtFileDTO.setTextId(txtFile.getTextId());
        txtFileDTO.setRefId(txtFile.getRefId());
        txtFileDTO.setWorkspaceId(txtFile.getWorkspaceId());
        return txtFileDAO.saveTextFileDTO(txtFileDTO);
    }

    /**
     * @param txtFile
     *            TextFile object to be updated in the FileSystem
     * @return
     * @throws IOException
     */
    private boolean saveTextFileLocal(ITextFile txtFile) throws IOException {

        String saveDir = env.getProperty("textfile.location");
        System.out.println(env.getProperty("textfile.location"));
        String filePath = saveDir + "/" + txtFile.getTextId();
        File saveTxtFile;
        File dirFile = new File(filePath);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        String fileName = txtFile.getFileName();
        if (fileName.contains(".")) {
            saveTxtFile = new File(filePath + "/" + fileName);
        } else {
            saveTxtFile = new File(filePath + "/" + fileName + ".txt");
        }
        FileWriter fw = new FileWriter(saveTxtFile);
        fw.write(txtFile.getFileContent());
        File propFile = new File(filePath + "/meta.properties");
        FileWriter propFw = new FileWriter(propFile);
        try {
            propFw.write("WsId:" + txtFile.getWorkspaceId() + "\n");
            propFw.write("ProjectId:" + txtFile.getProjectId() + "\n");
            propFw.write("ReferenceId:" + txtFile.getRefId() + "\n");
            propFw.write("TextFileId:" + txtFile.getTextId() + "\n");
            fw.close();
            propFw.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

}
