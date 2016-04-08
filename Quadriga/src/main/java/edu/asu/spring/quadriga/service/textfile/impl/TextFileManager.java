package edu.asu.spring.quadriga.service.textfile.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import edu.asu.spring.quadriga.dao.textfile.ITextFileDAO;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.dto.TextFileDTO;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.textfile.IFileSaveService;
import edu.asu.spring.quadriga.service.textfile.ITextFileManager;
import edu.asu.spring.quadriga.service.textfile.mapper.ITextFileShallowMapper;
import edu.asu.spring.quadriga.utilities.IFileSaveUtility;

/**
 * @author Nischal Samji
 *
 */
@Service
public class TextFileManager implements ITextFileManager {

    @Autowired
    private ITextFileDAO txtFileDAO;

    @Autowired
    private IFileSaveService fileSaveServ;

    @Qualifier("txtfileSaveUtil")
    @Autowired
    private IFileSaveUtility fileManager;

    @Autowired
    private ITextFileShallowMapper tfSMapper;

    
    @Override
    public boolean saveTextFile(ITextFile txtFile) throws FileStorageException, IOException, QuadrigaStorageException {
        String txtId = txtFileDAO.generateUniqueID();
        txtFile.setTextId(txtId);
        TextFileDTO txtFileDTO = tfSMapper.getTextFileDTO(txtFile);
        txtFileDTO.setTextId(txtId);
        boolean fsStatus = saveTextFileLocal(txtFile);
        boolean dbStatus = saveTextFileDB(txtFileDTO);
        return fsStatus && dbStatus;
    }

    /**
     * @param txtFile
     *            TextFile object to be updated in the database
     * @return returns true if file is successfully saved else returns false.
     * @throws QuadrigaStorageException
     */
    private boolean saveTextFileDB(TextFileDTO tfDTO) throws QuadrigaStorageException {
        return txtFileDAO.saveTextFileDTO(tfDTO);
    }

    /**
     * @param txtFile
     *            TextFile object to be updated in the FileSystem
     * @return returns true if file is successfully saved else returns false.
     * @throws IOException
     */
    private boolean saveTextFileLocal(ITextFile txtFile) throws FileStorageException, IOException {
        return fileSaveServ.saveFileToLocal(txtFile);

    }

    @Override
    public List<ITextFile> retrieveTextFiles(String wsId) {
        List<TextFileDTO> tfDTOList = txtFileDAO.getTextFileDTObyWsId(wsId);
        return tfSMapper.getTextFileList(tfDTOList);

    }

    @Override
    public String retrieveTextFileContent(String txtId) throws FileStorageException, IOException {

        TextFileDTO tfDTO = txtFileDAO.getTextFileDTO(txtId);
        return fileManager.readFileContent(tfDTO.getFilename(), tfDTO.getTextId());

        
    }

}
