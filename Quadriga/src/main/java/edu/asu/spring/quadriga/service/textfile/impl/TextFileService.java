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
import edu.asu.spring.quadriga.dao.workbench.IProjectDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectWorkspaceDAO;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.dto.TextFileDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.textfile.ITextFileService;

@PropertySource(value = "classpath:/user.properties")
@Service
public class TextFileService implements ITextFileService {

    @Autowired
    private TextFileDTO txtFileDTO;

    @Autowired
    private ITextFileDAO txtFileDAO;

    @Autowired
    private IProjectWorkspaceDAO projWSDAO;

    @Autowired
    private Environment env;

    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.service.textfile.ITextFileService#saveTextFile(edu.asu.spring.quadriga.domain.workspace.ITextFile)
     */
    @Override
    public boolean saveTextFile(ITextFile txtFile) throws QuadrigaStorageException, IOException {

        UUID refId = UUID.randomUUID();
        txtFile.setRefId(refId.toString());
        saveTextFileLocal(txtFile);
        saveTextFileDB(txtFile);

        return true;
    }

    /**
     * @param txtFile
     * @return
     * @throws QuadrigaStorageException
     */
    private boolean saveTextFileDB(ITextFile txtFile) throws QuadrigaStorageException {
        txtFileDTO.setFilename(txtFile.getFileName());
        txtFileDTO.setProjectId(txtFile.getProjectId());
        txtFileDTO.setRefId(txtFile.getRefId());
        txtFileDTO.setWorkspaceId(txtFile.getWorkspaceId());

        txtFileDAO.saveTextFileDTO(txtFileDTO);
        return true;
    }

    /**
     * @param txtFile
     * @return
     * @throws IOException
     */
    private boolean saveTextFileLocal(ITextFile txtFile) throws IOException {

        String saveDir = env.getProperty("textfile.location");
        String filePath = saveDir + "/" + txtFile.getRefId();
        File dirFile = new File(filePath);
        dirFile.mkdir();

        File saveTxtFile = new File(filePath + "/" + txtFile.getFileName() + ".txt");
        FileWriter fw = new FileWriter(saveTxtFile);
        fw.write(txtFile.getFileContent());
        File propFile = new File(filePath + "/meta.properties");
        FileWriter propFw = new FileWriter(propFile);
        propFw.write("WsId:" + txtFile.getWorkspaceId() + "\n");
        propFw.write("ProjectId:" + txtFile.getProjectId() + "\n");
        propFw.write("Reference Id:" + txtFile.getRefId() + "\n");

        fw.close();
        propFw.close();

        return true;

    }

}
